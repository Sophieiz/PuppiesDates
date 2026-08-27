/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

import Controlador.Conexion;
import Controlador.Solicitud_adopcionDAO;
import Modelo.Solicitud_adopcion;
import Modelo.Historial_estado_solicitud;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "ApiServlet", urlPatterns = {"/api/*"})
public class ApiServlet extends HttpServlet {

    private static final SimpleDateFormat FORMATO_FECHA
            = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararRespuesta(response);
        String ruta = obtenerRuta(request);

        switch (ruta) {
            case "/solicitudes":
                handleSolicitudes(request, response);
                break;
            default:
                enviar404(response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        prepararRespuesta(response);
        String ruta = obtenerRuta(request);

        switch (ruta) {
            case "/login":
                handleLogin(request, response);
                break;
            default:
                enviar404(response);
                break;
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        prepararRespuesta(response);
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void handleLogin(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        if (correo == null || clave == null
                || correo.trim().isEmpty()
                || clave.trim().isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,"
                        + "\"mensaje\":\"Correo y clave son obligatorios\"}");
            }
            return;
        }

        String sql = "SELECT idUsuarios, nombre, apellido, clave "
                + "FROM usuarios "
                + "WHERE correo = ? AND activo = 1";

        try (Connection con = new Conexion().getConn(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo.trim());

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    String hashGuardado = rs.getString("clave");

                    if (BCrypt.checkpw(clave, hashGuardado)) {
                        int idUsuario = rs.getInt("idUsuarios");
                        String nombre = rs.getString("nombre");
                        String apellido = rs.getString("apellido");

                        try (PrintWriter out = response.getWriter()) {
                            out.print("{\"success\":true,"
                                    + "\"idUsuario\":" + idUsuario + ","
                                    + "\"nombre\":\"" + escapar(nombre) + "\","
                                    + "\"apellido\":\"" + escapar(apellido) + "\"}");
                        }
                        return;
                    }
                }

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,"
                            + "\"mensaje\":\"Correo o clave incorrectos\"}");
                }
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,"
                        + "\"mensaje\":\"Error del servidor\"}");
            }

            System.out.println("Error en /api/login: " + e.getMessage());
        }
    }

    private void handleSolicitudes(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idUsuarioParam = request.getParameter("idUsuario");

        if (idUsuarioParam == null || idUsuarioParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Falta el parametro idUsuario\"}");
            }
            return;
        }

        int idUsuario;
        try {
            idUsuario = Integer.parseInt(idUsuarioParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"idUsuario invalido\"}");
            }
            return;
        }

        Solicitud_adopcionDAO dao = new Solicitud_adopcionDAO();
        List<Solicitud_adopcion> solicitudes = dao.listarSolicitud_adopcionPorUsuario(idUsuario);

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < solicitudes.size(); i++) {
            Solicitud_adopcion s = solicitudes.get(i);
            if (i > 0) {
                json.append(",");
            }

            List<Historial_estado_solicitud> historial
                    = dao.listarHistorialPorSolicitud(s.getIdSolicitud_adopcion());

            json.append("{")
                    .append("\"id\":").append(s.getIdSolicitud_adopcion()).append(",")
                    .append("\"nombrePerrito\":").append(jsonString(s.getNombrePerrito())).append(",")
                    .append("\"fotoPerritoUrl\":").append(jsonStringNullable(construirUrlFoto(request, s.getFotoPerrito()))).append(",")
                    .append("\"fechaSolicitud\":").append(jsonString(formatearFecha(s.getFecha_solicitud()))).append(",")
                    .append("\"estadoActual\":").append(jsonString(mapearEstado(s.getDescripcionEstado_solicitud()))).append(",")
                    .append("\"historial\":[");

            for (int j = 0; j < historial.size(); j++) {
                Historial_estado_solicitud h = historial.get(j);
                if (j > 0) {
                    json.append(",");
                }
                json.append("{")
                        .append("\"estado\":").append(jsonString(mapearEstado(h.getDescripcionEstado_solicitud()))).append(",")
                        .append("\"fecha\":").append(jsonString(formatearFecha(h.getFecha_cambio()))).append(",")
                        .append("\"observacion\":").append(jsonStringNullable(h.getObservacion()))
                        .append("}");
            }

            json.append("]}");
        }

        json.append("]");

        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
        }
    }

    private void prepararRespuesta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    private String construirUrlFoto(HttpServletRequest request, String rutaFoto) {
        if (rutaFoto == null || rutaFoto.trim().isEmpty()) {
            return null;
        }
        String base = request.getScheme() + "://" + request.getServerName()
                + (esPuertoEstandar(request) ? "" : ":" + request.getServerPort())
                + request.getContextPath();
        String ruta = rutaFoto.startsWith("/") ? rutaFoto : "/" + rutaFoto;
        return base + ruta;
    }

    private boolean esPuertoEstandar(HttpServletRequest request) {
        return ("http".equals(request.getScheme()) && request.getServerPort() == 80)
                || ("https".equals(request.getScheme()) && request.getServerPort() == 443);
    }

    
    private String obtenerRuta(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        return pathInfo == null ? "" : pathInfo;
    }

    private void enviar404(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"success\":false,\"mensaje\":\"Ruta de API no encontrada\"}");
        }
    }


    private String mapearEstado(String descripcionEstadoBD) {
        if (descripcionEstadoBD == null) {
            return "pendiente";
        }
        switch (descripcionEstadoBD) {
            case "Pendiente":
                return "pendiente";
            case "En proceso":
                return "enProceso";
            case "Aprobado":
                return "aprobado";
            case "Rechazado":
                return "rechazado";
            default:
                return "pendiente";
        }
    }

    private String formatearFecha(java.util.Date fecha) {
        if (fecha == null) {
            return null;
        }
        return FORMATO_FECHA.format(fecha);
    }


    private String jsonString(String valor) {
        if (valor == null) {
            return "\"\"";
        }
        String escapado = valor
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
        return "\"" + escapado + "\"";
    }

    private String jsonStringNullable(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return "null";
        }
        return jsonString(valor);
    }

    private String escapar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
