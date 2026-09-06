package Servlet;

import Controlador.ReservaDAO;
import Controlador.CorreoUtil;
import Modelo.Reserva;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "GestionarReserva", urlPatterns = {"/GestionarReserva"})
public class GestionarReserva extends HttpServlet {

    private static final int ESTADO_CANCELADA = 3;

    private final ReservaDAO reservaDao = new ReservaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }
        int idUsuario = (int) sesion.getAttribute("idUsuario");

        String accion = request.getParameter("accion");
        int idReserva = Integer.parseInt(request.getParameter("id"));

        try {
            Reserva reserva = reservaDao.ConsultarReserva(idReserva);

            if (reserva == null || reserva.getUsuarios_idUsuarios() != idUsuario) {
                sesion.setAttribute("resultado", "No se encontró la reserva o no tienes permiso sobre ella.");
                response.sendRedirect(request.getContextPath() + "/MisReservas");
                return;
            }

            if (!reserva.isModificable()) {
                sesion.setAttribute("resultado",
                    "Ya no es posible modificar o cancelar esta reserva (mínimo 7 días de anticipación, "
                    + "o ya está cancelada/realizada).");
                response.sendRedirect(request.getContextPath() + "/MisReservas");
                return;
            }

            if ("cancelar".equalsIgnoreCase(accion)) {
                boolean ok = reservaDao.actualizarEstadoReserva(idReserva, ESTADO_CANCELADA);

                if (ok) {
                    CorreoUtil.enviarCorreoNuevaReserva(
                        String.valueOf(sesion.getAttribute("nombreUsuario")),
                        obtenerCorreoUsuario(sesion),
                        "[CANCELADA] " + nombreActividadOFallback(reserva),
                        String.valueOf(reserva.getFecha()),
                        String.valueOf(reserva.getHora()),
                        reserva.getNum_personas()
                    );
                    sesion.setAttribute("resultado", "¡Reserva cancelada con éxito!");
                } else {
                    sesion.setAttribute("resultado", "No se pudo cancelar la reserva. Intenta de nuevo.");
                }
                response.sendRedirect(request.getContextPath() + "/MisReservas");
                return;
            }

            if ("editar".equalsIgnoreCase(accion)) {
                request.setAttribute("reserva", reserva);
                request.getRequestDispatcher("/Vista/EditarReserva.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/MisReservas");

        } catch (SQLException e) {
            throw new ServletException("Error de base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }
        int idUsuario = (int) sesion.getAttribute("idUsuario");
        int idReserva = Integer.parseInt(request.getParameter("id"));

        try {
            Reserva reserva = reservaDao.ConsultarReserva(idReserva);

            if (reserva == null || reserva.getUsuarios_idUsuarios() != idUsuario) {
                response.sendRedirect(request.getContextPath() + "/MisReservas");
                return;
            }

            if (!reserva.isModificable()) {
                sesion.setAttribute("resultado",
                    "Ya no es posible modificar esta reserva (mínimo 7 días de anticipación).");
                response.sendRedirect(request.getContextPath() + "/MisReservas");
                return;
            }

            String nuevaFecha = request.getParameter("fecha");
            String nuevaHora = request.getParameter("hora");
            int nuevasPersonas = Integer.parseInt(request.getParameter("num_personas"));

            reserva.setFecha(Date.valueOf(nuevaFecha));
            reserva.setHora(Time.valueOf(nuevaHora + ":00"));
            reserva.setNum_personas(nuevasPersonas);

            boolean ok = reservaDao.actualizarReserva(reserva);

            if (ok) {
                CorreoUtil.enviarCorreoNuevaReserva(
                    String.valueOf(sesion.getAttribute("nombreUsuario")),
                    obtenerCorreoUsuario(sesion),
                    "[MODIFICADA] " + nombreActividadOFallback(reserva),
                    nuevaFecha,
                    nuevaHora,
                    nuevasPersonas
                );
                sesion.setAttribute("resultado", "¡Reserva modificada con éxito!");
            } else {
                sesion.setAttribute("resultado", "No se pudo actualizar la reserva. Intenta de nuevo.");
            }

            response.sendRedirect(request.getContextPath() + "/MisReservas");

        } catch (SQLException e) {
            throw new ServletException("Error de base de datos", e);
        }
    }

    // ConsultarReserva no trae el nombre de la actividad (no hace JOIN)
    private String nombreActividadOFallback(Reserva reserva) {
        String nombre = reserva.getNombreActividad();
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Actividad #" + reserva.getActividad_idActividad();
        }
        return nombre;
    }

    private String obtenerCorreoUsuario(HttpSession sesion) {
        Object correo = sesion.getAttribute("correoUsuario");
        return correo != null ? correo.toString() : "";
    }
}