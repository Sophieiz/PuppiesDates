package Servlet;

import Controlador.ActividadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Actividades", urlPatterns = {"/Actividades"})
public class Actividades extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActividadDAO dao = new ActividadDAO();
        request.setAttribute("actividades", dao.Actividad());
        request.getRequestDispatcher("/Vista/Actividad.jsp").forward(request, response);
    }
}