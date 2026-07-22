package Servlet;

import Controlador.ReservaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MisReservas", urlPatterns = {"/MisReservas"})
public class MisReservas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");
        ReservaDAO dao = new ReservaDAO();
        request.setAttribute("listaMisReservas", dao.listarReservaPorUsuario(idUsuario));
        request.getRequestDispatcher("/Vista/MisReservas.jsp").forward(request, response);
    }
}
