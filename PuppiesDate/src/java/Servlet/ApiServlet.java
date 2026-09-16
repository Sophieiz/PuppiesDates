/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

import Controlador.CorreoUtil;
import Controlador.EntrevistaDAO;
import Controlador.PasswordUtil;
import Controlador.PerritoDAO;
import Controlador.Solicitud_adopcionDAO;
import Controlador.UsuariosDAO;
import Modelo.Entrevista;
import Modelo.Perrito;
import Modelo.Solicitud_adopcion;
import Modelo.Historial_estado_solicitud;
import Modelo.Usuarios;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

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
            case "/solicitudes":
                handleCrearSolicitud(request, response);
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

        try {
            UsuariosDAO usuariosDao = new UsuariosDAO();
            Usuarios usuarioBD = usuariosDao.ConsultarUsuarioPorCorreo(correo.trim());

            if (usuarioBD == null || !PasswordUtil.verificarPassword(clave, usuarioBD.getclave())) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,"
                            + "\"mensaje\":\"Correo o clave incorrectos\"}");
                }
                return;
            }

            java.util.Date hoy = new java.util.Date();
            java.util.Date fechaCad = usuarioBD.getfecha_cad();
            if (fechaCad != null && hoy.after(fechaCad)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,"
                            + "\"mensaje\":\"Tu cuenta ha expirado. Por favor registrate nuevamente.\"}");
                }
                return;
            }

            if (!usuarioBD.ischeckbox()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,"
                            + "\"mensaje\":\"Tu cuenta esta inactiva. Por favor registrate nuevamente.\"}");
                }
                return;
            }

            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":true,"
                        + "\"idUsuario\":" + usuarioBD.getidUsuarios() + ","
                        + "\"nombre\":" + jsonString(usuarioBD.getnombre()) + ","
                        + "\"apellido\":" + jsonString(usuarioBD.getapellido()) + ","
                        + "\"correo\":" + jsonString(usuarioBD.getcorreo()) + ","
                        + "\"idRol\":" + usuarioBD.getRoles_idRoles() + "}");
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

            String estadoActualMapeado = mapearEstado(s.getDescripcionEstado_solicitud());
            Entrevista ultimaEntrevista = new EntrevistaDAO().consultarUltimaEntrevista(s.getIdSolicitud_adopcion());

            json.append("{")
                    .append("\"id\":").append(s.getIdSolicitud_adopcion()).append(",")
                    .append("\"nombrePerrito\":").append(jsonString(s.getNombrePerrito())).append(",")
                    .append("\"fotoPerritoUrl\":").append(jsonStringNullable(construirUrlFoto(request, s.getFotoPerrito()))).append(",")
                    .append("\"fechaSolicitud\":").append(jsonString(formatearFecha(s.getFecha_solicitud()))).append(",")
                    .append("\"estadoActual\":").append(jsonString(estadoActualMapeado)).append(",")
                    .append("\"entrevista\":").append(construirJsonEntrevista(ultimaEntrevista)).append(",")
                    .append("\"perritoAdoptado\":").append(
                    "aprobado".equals(estadoActualMapeado)
                    ? construirJsonPerritoAdoptado(request, s.getPerrito_idPerrito())
                    : "null"
            ).append(",")
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

   
    private void handleCrearSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idUsuarioStr = request.getParameter("idUsuario");
        String idPerritoStr = request.getParameter("idPerrito");
        String direccion = request.getParameter("direccion");
        String departamentoIdStr = request.getParameter("departamentoId");
        String ubicacionIdStr = request.getParameter("ubicacionId");
        String tipoDivision = request.getParameter("tipoDivision");
        String barrio = request.getParameter("barrio");
        String profesion = request.getParameter("profesion");
        String viveEnIdStr = request.getParameter("viveEnId");
        String tipoViviendaIdStr = request.getParameter("tipoViviendaId");
        String nucleoFamiliar = request.getParameter("nucleo_familiar");
        String tieneMascotasStr = request.getParameter("tiene_mascotas");

        if (idUsuarioStr == null || idUsuarioStr.trim().isEmpty()
                || idPerritoStr == null || idPerritoStr.trim().isEmpty()
                || direccion == null || direccion.trim().isEmpty()
                || departamentoIdStr == null || departamentoIdStr.trim().isEmpty()
                || ubicacionIdStr == null || ubicacionIdStr.trim().isEmpty()
                || tipoDivision == null || tipoDivision.trim().isEmpty()
                || barrio == null || barrio.trim().isEmpty()
                || profesion == null || profesion.trim().isEmpty()
                || viveEnIdStr == null || viveEnIdStr.trim().isEmpty()
                || tipoViviendaIdStr == null || tipoViviendaIdStr.trim().isEmpty()
                || nucleoFamiliar == null || nucleoFamiliar.trim().isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,\"mensaje\":\"Todos los campos son obligatorios\"}");
            }
            return;
        }

        try {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            int idPerrito = Integer.parseInt(idPerritoStr);
            int departamentoId = Integer.parseInt(departamentoIdStr);
            int ubicacionId = Integer.parseInt(ubicacionIdStr);
            int viveEnId = Integer.parseInt(viveEnIdStr);
            int tipoViviendaId = Integer.parseInt(tipoViviendaIdStr);
            boolean tieneMascotas = "true".equalsIgnoreCase(tieneMascotasStr)
                    || "si".equalsIgnoreCase(tieneMascotasStr);

            PerritoDAO perritoDao = new PerritoDAO();
            Perrito perrito = perritoDao.ConsultarPerrito(idPerrito);

            if (perrito == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,\"mensaje\":\"El perrito seleccionado no existe\"}");
                }
                return;
            }

            if (!"Disponible".equals(perrito.getDescripcionEstado_perrito())) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,\"mensaje\":\"Este perrito ya no esta disponible para adopcion\"}");
                }
                return;
            }

            Solicitud_adopcionDAO solicitudDao = new Solicitud_adopcionDAO();

            if (solicitudDao.existeSolicitudActiva(idUsuario, idPerrito)) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,\"mensaje\":\"Ya tienes una solicitud activa para este perrito\"}");
                }
                return;
            }

            Solicitud_adopcion solicitud = new Solicitud_adopcion();
            solicitud.setDireccion(direccion);
            solicitud.setDepartamentoId(departamentoId);

            if ("LOCALIDAD".equals(tipoDivision)) {
                solicitud.setLocalidadId(ubicacionId);
                solicitud.setMunicipioId(null);
            } else {
                solicitud.setMunicipioId(ubicacionId);
                solicitud.setLocalidadId(null);
            }

            solicitud.setBarrio(barrio);
            solicitud.setProfesion(profesion);
            solicitud.setViveEnId(viveEnId);
            solicitud.setTipoViviendaId(tipoViviendaId);
            solicitud.setNucleo_familiar(nucleoFamiliar);
            solicitud.setTiene_mascotas(tieneMascotas);
            solicitud.setUsuarios_idUsuarios(idUsuario);
            solicitud.setPerrito_idPerrito(idPerrito);

            int idSolicitudGenerada = solicitudDao.insertarSolicitud_adopcion(solicitud);

            if (idSolicitudGenerada != -1) {
                try {
                    Solicitud_adopcion solicitudCompleta = solicitudDao.ConsultarSolicitud_adopcion(idSolicitudGenerada);
                    CorreoUtil.enviarCorreoNuevaSolicitud(solicitudCompleta, perrito);
                    CorreoUtil.enviarCorreoConfirmacionSolicitudUsuario(solicitudCompleta, perrito);
                } catch (Exception exCorreo) {
                    System.out.println("Error enviando correos de adopcion: " + exCorreo.getMessage());
                }

                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":true,"
                            + "\"mensaje\":\"Solicitud de adopcion enviada\","
                            + "\"idSolicitud\":" + idSolicitudGenerada + "}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,\"mensaje\":\"Error al guardar la solicitud\"}");
                }
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,\"mensaje\":\"Datos invalidos en el formulario\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,\"mensaje\":\"Error inesperado: " + escapar(e.getMessage()) + "\"}");
            }
        }
    }

    private void prepararRespuesta(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    // Fecha/hora de la entrevista más reciente de la solicitud, o null si todavía no le han programado ninguna
    private String construirJsonEntrevista(Entrevista entrevista) {
        if (entrevista == null) {
            return "null";
        }
        return "{"
                + "\"fecha\":" + jsonStringNullable(entrevista.getFecha() != null ? entrevista.getFecha().toString() : null) + ","
                + "\"hora\":" + jsonStringNullable(entrevista.getHora() != null ? entrevista.getHora().toString() : null) + ","
                + "\"observaciones\":" + jsonStringNullable(entrevista.getObservaciones())
                + "}";
    }

    // Ficha completa del perrito, para mostrarla cuando la solicitud ya quedó "Aprobada" (adoptado)
    private String construirJsonPerritoAdoptado(HttpServletRequest request, int idPerrito) {
        Perrito perrito = new PerritoDAO().ConsultarPerrito(idPerrito);
        if (perrito == null) {
            return "null";
        }
        return "{"
                + "\"nombre\":" + jsonString(perrito.getNombre()) + ","
                + "\"fotoUrl\":" + jsonStringNullable(construirUrlFoto(request, perrito.getFoto())) + ","
                + "\"especie\":" + jsonStringNullable(perrito.getDescripcionEspecie()) + ","
                + "\"raza\":" + jsonStringNullable(perrito.getDescripcionRaza()) + ","
                + "\"sexo\":" + jsonStringNullable(perrito.getDescripcionSexo()) + ","
                + "\"etapaMadurez\":" + jsonStringNullable(perrito.getEtapa_madurez()) + ","
                + "\"especialidad\":" + jsonStringNullable(perrito.getEspecialidad()) + ","
                + "\"condicionesEspeciales\":" + jsonStringNullable(perrito.getCondiciones_especiales()) + ","
                + "\"historia\":" + jsonStringNullable(perrito.getHistoria())
                + "}";
    }

private String construirUrlFoto(HttpServletRequest request, String rutaFoto) {
    if (rutaFoto == null || rutaFoto.trim().isEmpty()) {
        return null;
    }

    if (rutaFoto.startsWith("http://") || rutaFoto.startsWith("https://")) {
        return rutaFoto;
    }

  
    String proto = request.getHeader("X-Forwarded-Proto");
    if (proto == null || proto.isEmpty()) {
        proto = request.getScheme();
    }

    String host = request.getHeader("X-Forwarded-Host");
    if (host == null || host.isEmpty()) {
        host = request.getServerName();
        boolean puertoEstandar = ("http".equals(proto) && request.getServerPort() == 80)
                || ("https".equals(proto) && request.getServerPort() == 443);
        if (!puertoEstandar) {
            host += ":" + request.getServerPort();
        }
    }

    String base = proto + "://" + host + request.getContextPath();
    String ruta = rutaFoto.startsWith("/") ? rutaFoto : "/" + rutaFoto;
    return base + ruta;
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
            case "Entrevista":
                return "entrevista";
            case "Aprobado":
                return "aprobado";
            case "Rechazado":
                return "rechazado";
            case "Cancelada":
                return "cancelada";
            case "No seleccionado":
                return "noSeleccionado";
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
