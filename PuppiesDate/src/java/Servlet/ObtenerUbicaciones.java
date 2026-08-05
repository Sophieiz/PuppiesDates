/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

import Controlador.MunicipioDAO;
import Controlador.LocalidadDAO;
import Modelo.Municipio;
import Modelo.Localidad;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ObtenerUbicaciones", urlPatterns = {"/ObtenerUbicaciones"})
public class ObtenerUbicaciones extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");

        String depIdStr = request.getParameter("departamentoId");
        String tipoDivision = request.getParameter("tipoDivision");

        if (depIdStr == null || depIdStr.trim().isEmpty()
                || tipoDivision == null || tipoDivision.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("[]");
            return;
        }

        try {
            int idDepartamento = Integer.parseInt(depIdStr);
            StringBuilder json = new StringBuilder("[");

            if ("MUNICIPIO".equalsIgnoreCase(tipoDivision)) {
                MunicipioDAO dao = new MunicipioDAO();
                List<Municipio> lista = dao.listarPorDepartamento(idDepartamento);
                for (int i = 0; i < lista.size(); i++) {
                    Municipio m = lista.get(i);
                    if (i > 0) json.append(",");
                    json.append("{\"id\":").append(m.getIdMunicipio())
                        .append(",\"nombre\":\"").append(escapar(m.getNombre())).append("\"}");
                }
            } else {
                LocalidadDAO dao = new LocalidadDAO();
                List<Localidad> lista = dao.listarPorDepartamento(idDepartamento);
                for (int i = 0; i < lista.size(); i++) {
                    Localidad l = lista.get(i);
                    if (i > 0) json.append(",");
                    json.append("{\"id\":").append(l.getIdLocalidad())
                        .append(",\"nombre\":\"").append(escapar(l.getNombre())).append("\"}");
                }
            }

            json.append("]");
            response.getWriter().write(json.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("[]");
        }
    }

    private String escapar(String texto) {
        return texto == null ? "" : texto.replace("\"", "\\\"");
    }
}