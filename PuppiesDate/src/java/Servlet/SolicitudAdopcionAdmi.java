package Servlet;

import Controlador.CorreoUtil;
import Controlador.Estado_solicitudDAO;
import Controlador.EntrevistaDAO;
import Controlador.PerritoDAO;
import Controlador.Solicitud_adopcionDAO;
import Modelo.Entrevista;
import Modelo.Solicitud_adopcion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SolicitudAdopcionAdmi", urlPatterns = {"/SolicitudAdopcionAdmi"})
public class SolicitudAdopcionAdmi extends HttpServlet {

    // IDs fijos del catálogo Estado_perrito (ver script de creación)
    private static final int ESTADO_PERRITO_DISPONIBLE = 1;
    private static final int ESTADO_PERRITO_EN_PROCESO = 2;
    private static final int ESTADO_PERRITO_ADOPTADO = 3;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarListas(request);
        request.getRequestDispatcher("/Vista/SolicitudAdopcionAdmi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        Solicitud_adopcionDAO dao = new Solicitud_adopcionDAO();

        if ("actualizarEstado".equalsIgnoreCase(accion)) {
            try {
                int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud_adopcion"));
                int idEstadoNuevo = Integer.parseInt(request.getParameter("idEstado_solicitud"));
                String observacion = request.getParameter("observacion");

                Solicitud_adopcion solicitud = dao.ConsultarSolicitud_adopcion(idSolicitud);
                boolean ok = dao.actualizarEstadoSolicitud(idSolicitud, idEstadoNuevo, observacion);

                if (ok && solicitud != null) {
                    // Sincronizar el estado del perrito según el nuevo estado de la solicitud
                    String nuevoEstadoTexto = obtenerDescripcionEstado(idEstadoNuevo);
                    PerritoDAO perritoDao = new PerritoDAO();
                    if ("Aprobado".equalsIgnoreCase(nuevoEstadoTexto)) {
                        perritoDao.actualizarEstadoPerrito(solicitud.getPerrito_idPerrito(), ESTADO_PERRITO_ADOPTADO);
                    } else if ("Pendiente".equalsIgnoreCase(nuevoEstadoTexto) || "En proceso".equalsIgnoreCase(nuevoEstadoTexto)) {
                        perritoDao.actualizarEstadoPerrito(solicitud.getPerrito_idPerrito(), ESTADO_PERRITO_EN_PROCESO);
                    } else if ("Rechazado".equalsIgnoreCase(nuevoEstadoTexto)) {
                        perritoDao.actualizarEstadoPerrito(solicitud.getPerrito_idPerrito(), ESTADO_PERRITO_DISPONIBLE);
                    }

                    // Avisar al solicitante por correo
                    CorreoUtil.enviarCorreoCambioEstado(
                            solicitud.getCorreoUsuario(),
                            solicitud.getNombrePerrito(),
                            nuevoEstadoTexto
                    );
                    request.setAttribute("mensaje", "Estado actualizado y usuario notificado por correo.");
                } else {
                    request.setAttribute("mensaje", "Error al actualizar el estado de la solicitud.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "Datos inválidos.");
            }
        } else if ("programarEntrevista".equalsIgnoreCase(accion)) {
            try {
                int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud_adopcion"));
                String fechaTexto = request.getParameter("fecha");
                String horaTexto = request.getParameter("hora");
                String observaciones = request.getParameter("observaciones");

                Solicitud_adopcion solicitud = dao.ConsultarSolicitud_adopcion(idSolicitud);

                if (solicitud == null) {
                    request.setAttribute("mensaje", "No se encontró la solicitud.");
                } else if (fechaTexto == null || fechaTexto.trim().isEmpty()
                        || horaTexto == null || horaTexto.trim().isEmpty()) {
                    request.setAttribute("mensaje", "Debes indicar fecha y hora para la entrevista.");
                } else {
                    // El input type="time" del navegador manda "HH:MM", java.sql.Time necesita "HH:MM:SS"
                    String horaCompleta = horaTexto.length() == 5 ? horaTexto + ":00" : horaTexto;

                    Entrevista entrevista = new Entrevista();
                    entrevista.setSolicitud_adopcion_idSolicitud_adopcion(idSolicitud);
                    entrevista.setFecha(java.sql.Date.valueOf(fechaTexto));
                    entrevista.setHora(java.sql.Time.valueOf(horaCompleta));
                    entrevista.setObservaciones(observaciones);

                    EntrevistaDAO entrevistaDao = new EntrevistaDAO();
                    int idEntrevista = entrevistaDao.insertarEntrevista(entrevista);

                    if (idEntrevista != -1) {
                        int idEstadoEntrevista = obtenerIdEstadoPorDescripcion("Entrevista");
                        dao.actualizarEstadoSolicitud(idSolicitud, idEstadoEntrevista,
                                "Entrevista programada para " + fechaTexto + " a las " + horaTexto);

                        // El perrito pasa a "en proceso" mientras se define esta solicitud
                        new PerritoDAO().actualizarEstadoPerrito(solicitud.getPerrito_idPerrito(), ESTADO_PERRITO_EN_PROCESO);

                        CorreoUtil.enviarCorreoEntrevista(
                                solicitud.getCorreoUsuario(),
                                solicitud.getNombreUsuario(),
                                solicitud.getNombrePerrito(),
                                fechaTexto,
                                horaTexto
                        );
                        request.setAttribute("mensaje", "Entrevista programada y usuario notificado por correo.");
                    } else {
                        request.setAttribute("mensaje", "Error al registrar la entrevista.");
                    }
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("mensaje", "Datos inválidos para la entrevista.");
            }
        } else if ("eliminar".equalsIgnoreCase(accion)) {
            try {
                int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud_adopcion"));
                boolean ok = dao.eliminarSolicitud_adopcion(idSolicitud);
                request.setAttribute("mensaje", ok ? "Solicitud eliminada correctamente." : "Error al eliminar la solicitud.");
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "Datos inválidos.");
            }
        } else if ("reactivar".equalsIgnoreCase(accion)) {
            try {
                int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud_adopcion"));
                boolean ok = dao.reactivarSolicitud_adopcion(idSolicitud);
                request.setAttribute("mensaje", ok ? "Solicitud reactivada correctamente." : "Error al reactivar la solicitud.");
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "Datos inválidos.");
            }
        }

        cargarListas(request);
        request.getRequestDispatcher("/Vista/SolicitudAdopcionAdmi.jsp").forward(request, response);
    }

    private String obtenerDescripcionEstado(int idEstado) {
        for (Modelo.Estado_solicitud estado : new Estado_solicitudDAO().listarEstado_solicitud()) {
            if (estado.getIdEstado_solicitud() == idEstado) {
                return estado.getDescripcion_estado();
            }
        }
        return "";
    }

    // Busca el id a partir del texto (evita "hardcodear" el id 5, por si algún día cambia)
    private int obtenerIdEstadoPorDescripcion(String descripcion) {
        for (Modelo.Estado_solicitud estado : new Estado_solicitudDAO().listarEstado_solicitud()) {
            if (estado.getDescripcion_estado().equalsIgnoreCase(descripcion)) {
                return estado.getIdEstado_solicitud();
            }
        }
        return -1;
    }

    private void cargarListas(HttpServletRequest request) {
        Solicitud_adopcionDAO dao = new Solicitud_adopcionDAO();
        String textoBusqueda = request.getParameter("buscar");

        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            request.setAttribute("listaSolicitudes", dao.buscarSolicitud_adopcion(textoBusqueda.trim()));
            request.setAttribute("terminoBusqueda", textoBusqueda.trim());
        } else {
            request.setAttribute("listaSolicitudes", dao.listarSolicitud_adopcion());
        }

        request.setAttribute("listaEstadosSolicitud", new Estado_solicitudDAO().listarEstado_solicitud());
        request.setAttribute("listaSolicitudesInactivas", dao.listarInactivas());
    }
}
