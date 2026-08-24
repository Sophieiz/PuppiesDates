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

     
        java.util.Map<String, java.util.TreeSet<String>> razasPorEspecie = new java.util.LinkedHashMap<>();
        TreeSet<String> razasUnicas = new TreeSet<>();

        for (Perrito p : listaPerritos) {
            String especie = p.getDescripcionEspecie();
            String raza = p.getDescripcionRaza();

            if (raza != null && !raza.isBlank()) {
                razasUnicas.add(raza);
            }

            if (especie != null && !especie.isBlank() && raza != null && !raza.isBlank()) {
                razasPorEspecie.computeIfAbsent(especie, k -> new TreeSet<>()).add(raza);
            }
        }

        request.setAttribute("listaPerritos", listaPerritos);
        request.setAttribute("listaRazas", razasUnicas);
        request.setAttribute("razasPorEspecie", razasPorEspecie);
        request.getRequestDispatcher("/Vista/Catalogo.jsp").forward(request, response);
    }
}