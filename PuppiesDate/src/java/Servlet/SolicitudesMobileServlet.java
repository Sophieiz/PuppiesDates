/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Puente entre el backend web (Java/JSP) y la app mobile (Flutter).
 * Expone en JSON las solicitudes de adopción de un usuario, con su
 * historial de estados, para la pantalla "Mis solicitudes" de Flutter.
 *
 * GET /api/solicitudes?idUsuario=5
 *
 * IMPORTANTE - esto es una version simplificada para el taller:
 * el idUsuario viaja como parametro de la URL sin validar que quien
 * llama realmente sea ese usuario. Antes de subir esto a produccion
 * de verdad, hay que reemplazar el parametro idUsuario por una sesion
 * o un token (login) que identifique al usuario del lado del servidor,
 * no del lado del cliente. Por ahora sirve para conectar el diseño de
 * Flutter con datos reales.
 */
@WebServlet("/api/solicitudes")
public class SolicitudesMobileServlet extends HttpServlet {

    private static final SimpleDateFormat FORMATO_FECHA =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Permite que la app Flutter (que corre en otro origen/puerto durante
        // desarrollo) pueda llamar este endpoint. En producción, restringe
        // esto al dominio real de tu app si aplica.
        response.setHeader("Access-Control-Allow-Origin", "*");

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

            List<Historial_estado_solicitud> historial =
                    dao.listarHistorialPorSolicitud(s.getIdSolicitud_adopcion());

            json.append("{")
                .append("\"id\":").append(s.getIdSolicitud_adopcion()).append(",")
                .append("\"nombrePerrito\":").append(jsonString(s.getNombrePerrito())).append(",")
                .append("\"fotoPerritoUrl\":null,")
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

    // El enum de Flutter (EstadoSolicitud) espera: pendiente, enProceso, aprobado, rechazado
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

    // Escapa comillas/backslashes/saltos de linea para que el texto no rompa el JSON
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
}
