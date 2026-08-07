package Servlet;

import Controlador.ActividadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaCliente", urlPatterns = {"/ReservaCliente"})
public class ReservaCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarCombosYRedirigir(request, response);
    }

    private void cargarCombosYRedirigir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActividadDAO actividadDao = new ActividadDAO();
        request.setAttribute("actividades", actividadDao.Actividad());
        request.getRequestDispatcher("/Vista/Reserva.jsp").forward(request, response);
    }
}
