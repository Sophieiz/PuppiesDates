package Servlet;

import Controlador.PerritoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CatalogoPerritos", urlPatterns = {"/CatalogoPerritos"})
public class CatalogoPerritos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Maneja las peticiones GET (al entrar directamente a la URL o recargar la página)
        procesarSolicitud(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Maneja las peticiones POST (al enviar el formulario de búsqueda)
        procesarSolicitud(request, response);
    }

    private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String textoBusqueda = request.getParameter("buscar");
        PerritoDAO dao = new PerritoDAO();

        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            request.setAttribute("listaPerritos", dao.buscarPerritoDisponible(textoBusqueda.trim()));
            request.setAttribute("terminoBusqueda", textoBusqueda.trim());
        } else {
            request.setAttribute("listaPerritos", dao.listarPerritoDisponible());
        }

        request.getRequestDispatcher("/Vista/Catalogo.jsp").forward(request, response);
    }
}