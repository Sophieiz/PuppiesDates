package Servlet;

import Controlador.CorreoUtil;
import Controlador.DepartamentoDAO;
import Controlador.Vive_enDAO;
import Controlador.Tipo_viviendaDAO;
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

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            
            
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        cargarFormularioYRedirigir(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String idPerritoStr = request.getParameter("idPerrito");
            String direccion = request.getParameter("direccion");
            String departamentoIdStr = request.getParameter("departamentoId");
            String ubicacionIdStr = request.getParameter("ubicacionId");
            String tipoDivision = request.getParameter("tipoDivision");
            String barrio = request.getParameter("barrio");
            String profesion = request.getParameter("profesion");
            String viveEnIdStr = request.getParameter("viveEnId");
            String tipoViviendaIdStr = request.getParameter("tipoViviendaId");
            String nucleoFamiliar = request.getParameter("nucleo_familiar");
            String tieneMascotasStr = request.getParameter("tiene_mascotas");

            if (idPerritoStr == null || direccion == null || direccion.trim().isEmpty()
                    || departamentoIdStr == null || departamentoIdStr.trim().isEmpty()
                    || ubicacionIdStr == null || ubicacionIdStr.trim().isEmpty()
                    || tipoDivision == null || tipoDivision.trim().isEmpty()
                    || barrio == null || barrio.trim().isEmpty()
                    || profesion == null || profesion.trim().isEmpty()
                    || viveEnIdStr == null || viveEnIdStr.trim().isEmpty()
                    || tipoViviendaIdStr == null || tipoViviendaIdStr.trim().isEmpty()
                    || nucleoFamiliar == null || nucleoFamiliar.trim().isEmpty()) {
                request.setAttribute("resultado", "Error: Todos los campos son obligatorios.");
                cargarFormularioYRedirigir(request, response);
                return;
            }

            int idPerrito = Integer.parseInt(idPerritoStr);
            int departamentoId = Integer.parseInt(departamentoIdStr);
            int ubicacionId = Integer.parseInt(ubicacionIdStr);
            int viveEnId = Integer.parseInt(viveEnIdStr);
            int tipoViviendaId = Integer.parseInt(tipoViviendaIdStr);

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
            solicitud.setDepartamentoId(departamentoId);

            if ("LOCALIDAD".equals(tipoDivision)) {
                solicitud.setLocalidadId(ubicacionId);
                solicitud.setMunicipioId(null);
            } else {
                solicitud.setMunicipioId(ubicacionId);
                solicitud.setLocalidadId(null);
            }

            solicitud.setBarrio(barrio);
            solicitud.setProfesion(profesion);
            solicitud.setViveEnId(viveEnId);
            solicitud.setTipoViviendaId(tipoViviendaId);
            solicitud.setNucleo_familiar(nucleoFamiliar);
            solicitud.setTiene_mascotas(tieneMascotas);
            solicitud.setUsuarios_idUsuarios(idUsuario);
            solicitud.setPerrito_idPerrito(idPerrito);

            int idSolicitudGenerada = solicitudDao.insertarSolicitud_adopcion(solicitud);

            if (idSolicitudGenerada != -1) {

                Solicitud_adopcion solicitudCompleta = solicitudDao.ConsultarSolicitud_adopcion(idSolicitudGenerada);

                CorreoUtil.enviarCorreoNuevaSolicitud(solicitudCompleta, perrito);

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
        try {
            String idPerritoStr = request.getParameter("idPerrito");
            if (idPerritoStr != null) {
                try {
                    PerritoDAO perritoDao = new PerritoDAO();
                    request.setAttribute("perrito", perritoDao.ConsultarPerrito(Integer.parseInt(idPerritoStr)));
                } catch (NumberFormatException ignored) {
                }
            }

            DepartamentoDAO departamentoDao = new DepartamentoDAO();
            request.setAttribute("departamentos", departamentoDao.listarActivos());

            Vive_enDAO viveEnDao = new Vive_enDAO();
            request.setAttribute("listaViveEn", viveEnDao.listarActivos());

            Tipo_viviendaDAO tipoViviendaDao = new Tipo_viviendaDAO();
            request.setAttribute("listaTipoVivienda", tipoViviendaDao.listarActivos());

        } catch (Exception e) {
            request.setAttribute("resultado", "Error al cargar datos: " + e.getMessage());
        }


        String requestedWith = request.getHeader("X-Requested-With");
        boolean esPeticionAjax = "XMLHttpRequest".equals(requestedWith);
        String vista = esPeticionAjax
                ? "/Vista/SolicitudAdopcionFragmento.jsp"
                : "/Vista/SolicitudAdopcion.jsp";

        request.getRequestDispatcher(vista).forward(request, response);
    }
}
