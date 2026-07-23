package Servlet;

import Controlador.CorreoUtil;
import Controlador.Opcion_formularioDAO;
import Controlador.PerritoDAO;
import Controlador.Solicitud_adopcionDAO;
import Modelo.Perrito;
import Modelo.Solicitud_adopcion;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "SolicitudAdopcionCliente", urlPatterns = {"/SolicitudAdopcionCliente"})
public class SolicitudAdopcionCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarFormularioYRedirigir(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String idPerritoStr = request.getParameter("idPerrito");
            String direccion = request.getParameter("direccion");
            String localidad = request.getParameter("localidad");
            String barrio = request.getParameter("barrio");
            String profesion = request.getParameter("profesion");
            String viveEn = request.getParameter("vive_en");
            String tipoVivienda = request.getParameter("tipo_vivienda");
            String nucleoFamiliar = request.getParameter("nucleo_familiar");
            String tieneMascotasStr = request.getParameter("tiene_mascotas");

            if (idPerritoStr == null || direccion == null || direccion.trim().isEmpty()
                    || localidad == null || localidad.trim().isEmpty()
                    || barrio == null || barrio.trim().isEmpty()
                    || profesion == null || profesion.trim().isEmpty()
                    || viveEn == null || viveEn.trim().isEmpty()
                    || tipoVivienda == null || tipoVivienda.trim().isEmpty()
                    || nucleoFamiliar == null || nucleoFamiliar.trim().isEmpty()) {
                request.setAttribute("resultado", "Error: Todos los campos son obligatorios.");
                cargarFormularioYRedirigir(request, response);
                return;
            }

            int idPerrito = Integer.parseInt(idPerritoStr);
            boolean tieneMascotas = "true".equalsIgnoreCase(tieneMascotasStr)
                    || "si".equalsIgnoreCase(tieneMascotasStr)
                    || "on".equalsIgnoreCase(tieneMascotasStr);

            HttpSession sesion = request.getSession(false);
            if (sesion == null || sesion.getAttribute("idUsuario") == null) {
                request.setAttribute("resultado", "Debes iniciar sesión para solicitar una adopción.");
                cargarFormularioYRedirigir(request, response);
                return;
            }
            int idUsuario = (int) sesion.getAttribute("idUsuario");

            PerritoDAO perritoDao = new PerritoDAO();
            Perrito perrito = perritoDao.ConsultarPerrito(idPerrito);

            if (perrito == null) {
                request.setAttribute("resultado", "Error: El perrito seleccionado no existe.");
                cargarFormularioYRedirigir(request, response);
                return;
            }

            if (!"Disponible".equals(perrito.getDescripcionEstado_perrito())) {
                request.setAttribute("resultado", "Este perrito ya no está disponible para adopción.");
                cargarFormularioYRedirigir(request, response);
                return;
            }

            Solicitud_adopcionDAO solicitudDao = new Solicitud_adopcionDAO();

            if (solicitudDao.existeSolicitudActiva(idUsuario, idPerrito)) {
                request.setAttribute("resultado", "Ya tienes una solicitud activa para este perrito. "
                        + "Espera la respuesta de la fundación antes de enviar otra.");
                cargarFormularioYRedirigir(request, response);
                return;
            }

            Solicitud_adopcion solicitud = new Solicitud_adopcion();
            solicitud.setDireccion(direccion);
            solicitud.setLocalidad(localidad);
            solicitud.setBarrio(barrio);
            solicitud.setProfesion(profesion);
            solicitud.setVive_en(viveEn);
            solicitud.setTipo_vivienda(tipoVivienda);
            solicitud.setNucleo_familiar(nucleoFamiliar);
            solicitud.setTiene_mascotas(tieneMascotas);
            solicitud.setUsuarios_idUsuarios(idUsuario);
            solicitud.setPerrito_idPerrito(idPerrito);

            int idSolicitudGenerada = solicitudDao.insertarSolicitud_adopcion(solicitud);

            if (idSolicitudGenerada != -1) {
                // Se vuelve a consultar ya con los joins (nombre de usuario, correo, nombre del perrito, etc.)
                // para armar el correo con todos los datos completos
                Solicitud_adopcion solicitudCompleta = solicitudDao.ConsultarSolicitud_adopcion(idSolicitudGenerada);

                // Notificación interna a la fundación
                CorreoUtil.enviarCorreoNuevaSolicitud(solicitudCompleta, perrito);

                // Confirmación al usuario de que su solicitud se envió correctamente
                CorreoUtil.enviarCorreoConfirmacionSolicitudUsuario(solicitudCompleta, perrito);

                request.setAttribute("resultado", "¡Solicitud de adopción enviada! "
                        + "La fundación revisará tu información y te contactará pronto.");
            } else {
                request.setAttribute("resultado", "Error al guardar la solicitud. Intenta de nuevo.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("resultado", "Error: Datos inválidos en el formulario.");
        } catch (Exception e) {
            request.setAttribute("resultado", "Error inesperado: " + e.getMessage());
        }

        cargarFormularioYRedirigir(request, response);
    }

    private void cargarFormularioYRedirigir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPerritoStr = request.getParameter("idPerrito");
        if (idPerritoStr != null) {
            try {
                PerritoDAO perritoDao = new PerritoDAO();
                request.setAttribute("perrito", perritoDao.ConsultarPerrito(Integer.parseInt(idPerritoStr)));
            } catch (NumberFormatException ignored) {
            }
        }
        Opcion_formularioDAO opcionDao = new Opcion_formularioDAO();
        request.setAttribute("listaViveEn", opcionDao.listarPorCategoria("vive_en"));
        request.setAttribute("listaTipoVivienda", opcionDao.listarPorCategoria("tipo_vivienda"));
        request.getRequestDispatcher("/Vista/SolicitudAdopcion.jsp").forward(request, response);
    }
}
