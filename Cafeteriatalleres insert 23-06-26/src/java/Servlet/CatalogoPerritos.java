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
