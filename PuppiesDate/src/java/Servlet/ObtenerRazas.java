package Servlet;

import Controlador.RazaDAO;
import Modelo.Raza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ObtenerRazas", urlPatterns = {"/ObtenerRazas"})
public class ObtenerRazas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        String idEspecieStr = request.getParameter("idEspecie");
        List<Raza> razas;

        try {
            int idEspecie = Integer.parseInt(idEspecieStr);
            razas = new RazaDAO().listarRazaPorEspecie(idEspecie);
        } catch (NumberFormatException e) {
            razas = new java.util.ArrayList<>();
        }

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < razas.size(); i++) {
            Raza r = razas.get(i);
            if (i > 0) json.append(",");
            json.append("{\"id\":").append(r.getIdRaza())
                .append(",\"nombre\":\"").append(escapeJson(r.getNombre())).append("\"}");
        }
        json.append("]");

        try (PrintWriter out = response.getWriter()) {
            out.write(json.toString());
        }
    }

    private String escapeJson(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}