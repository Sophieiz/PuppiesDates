package Servlet;

import Controlador.Solicitud_adopcionDAO;
import Controlador.Estado_solicitudDAO;
import Modelo.Estado_solicitud;
import Modelo.Solicitud_adopcion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MisSolicitudes", urlPatterns = {"/MisSolicitudes"})
public class MisSolicitudes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }

          int idUsuario = (int) sesion.getAttribute("idUsuario");
        Solicitud_adopcionDAO dao = new Solicitud_adopcionDAO();
        request.setAttribute("listaMisSolicitudes", dao.listarSolicitud_adopcionPorUsuario(idUsuario));
        request.getRequestDispatcher("/Vista/MisSolicitudes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");

        if ("cancelar".equalsIgnoreCase(request.getParameter("accion"))) {
            try {
                int idSolicitud = Integer.parseInt(request.getParameter("idSolicitud_adopcion"));
                Solicitud_adopcionDAO daoCancelar = new Solicitud_adopcionDAO();
                Solicitud_adopcion solicitud = daoCancelar.ConsultarSolicitud_adopcion(idSolicitud);

                // Seguridad: la solicitud debe existir y ser del usuario que está cancelando
                if (solicitud != null && solicitud.getUsuarios_idUsuarios() == idUsuario) {
                    int idCancelada = obtenerIdEstadoPorDescripcion("Cancelada");
                    if (idCancelada != -1) {
                        // El perrito no se toca: sigue "Disponible" para los demás candidatos,
                        // igual que estaba mientras esta solicitud seguía activa.
                        daoCancelar.actualizarEstadoSolicitud(idSolicitud, idCancelada, "Cancelada por el usuario.");
                    }
                }
            } catch (NumberFormatException e) {
                // id inválido: no hacemos nada, simplemente recargamos la lista
            }
        }

        response.sendRedirect(request.getContextPath() + "/MisSolicitudes");
    }

    private int obtenerIdEstadoPorDescripcion(String descripcion) {
        for (Estado_solicitud estado : new Estado_solicitudDAO().listarEstado_solicitud()) {
            if (estado.getDescripcion_estado().equalsIgnoreCase(descripcion)) {
                return estado.getIdEstado_solicitud();
            }
        }
        return -1;
    }
}
