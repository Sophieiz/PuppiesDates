package Servlet;

import Controlador.Solicitud_adopcionDAO;
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
}
