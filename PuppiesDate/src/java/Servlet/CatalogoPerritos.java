package Servlet;

import Controlador.PerritoDAO;
import Modelo.Perrito;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

@WebServlet(name = "CatalogoPerritos", urlPatterns = {"/CatalogoPerritos"})
public class CatalogoPerritos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarSolicitud(request, response);
    }

    private void procesarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PerritoDAO dao = new PerritoDAO();
        List<Perrito> listaPerritos = dao.listarPerritoDisponible();

        // Razas únicas presentes en el catálogo actual, ordenadas alfabéticamente,
        // para poblar el <select> de filtro sin depender de una tabla aparte.
        TreeSet<String> razasUnicas = new TreeSet<>();
        for (Perrito p : listaPerritos) {
            if (p.getDescripcionRaza() != null && !p.getDescripcionRaza().isBlank()) {
                razasUnicas.add(p.getDescripcionRaza());
            }
        }

        request.setAttribute("listaPerritos", listaPerritos);
        request.setAttribute("listaRazas", razasUnicas);

        request.getRequestDispatcher("/Vista/Catalogo.jsp").forward(request, response);
    }
}