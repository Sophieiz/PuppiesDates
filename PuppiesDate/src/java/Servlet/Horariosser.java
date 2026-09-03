package Servlet;

import Modelo.Horarios;
import Controlador.HorariosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

@WebServlet(name = "Horarios", urlPatterns = {"/Horarios"})
public class Horariosser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HorariosDAO dao = new HorariosDAO();

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Time horaIni = parseTime(request.getParameter("hora_ini"));
                Time horaFin = parseTime(request.getParameter("hora_fin"));
                if (!horaFin.after(horaIni)) {                                          // ← NUEVO
                    throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio."); // ← NUEVO
                }
                Horarios horario = new Horarios();
                horario.sethora_ini(horaIni);
                horario.sethora_fin(horaFin);
                boolean ok = dao.insertarHorarios(horario);
                request.getSession().setAttribute("mensajeFlash", ok ? "Horario insertado correctamente." : "Error al insertar horario.");
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = parseId(request.getParameter("idHorarios"));
                Time horaIni = parseTime(request.getParameter("hora_ini"));
                Time horaFin = parseTime(request.getParameter("hora_fin"));
                if (!horaFin.after(horaIni)) {                                          // ← NUEVO
                    throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio."); // ← NUEVO
                }
                Horarios horario = new Horarios();
                horario.setidHorarios(id);
                horario.sethora_ini(horaIni);
                horario.sethora_fin(horaFin);
                boolean ok = dao.actualizarHorario(horario);
                request.getSession().setAttribute("mensajeFlash", ok ? "Horario actualizado correctamente." : "Error al actualizar horario.");
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = parseId(request.getParameter("idHorarios"));
                boolean ok = dao.eliminarHorario(id);
                request.getSession().setAttribute("mensajeFlash", ok ? "Horario inactivado correctamente." : "Error al inactivar horario.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = parseId(request.getParameter("idHorarios"));
                boolean ok = dao.reactivarHorario(id);
                request.getSession().setAttribute("mensajeFlash", ok ? "Horario reactivado correctamente." : "Error al reactivar horario.");
            }

        } catch (SQLException | IllegalArgumentException e) {
            request.getSession().setAttribute("mensajeFlash", "Error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/Horarios");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HorariosDAO dao = new HorariosDAO();
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("mensajeFlash") != null) {
            request.setAttribute("mensaje", sesion.getAttribute("mensajeFlash"));
            sesion.removeAttribute("mensajeFlash");
        }
        List<Horarios> lista = dao.listarHorarios();
        request.setAttribute("listaHorarios", lista);
        request.setAttribute("listaHorariosInactivos", dao.listarInactivos());
        request.getRequestDispatcher("/Vista/Horario_admin.jsp").forward(request, response);
    }

    private Time parseTime(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora no puede estar vacía.");
        }
        Time hora = Time.valueOf(value.length() == 5 ? value + ":00" : value);
        Time apertura = Time.valueOf("08:00:00");
        Time cierre = Time.valueOf("17:00:00");
        if (hora.before(apertura) || hora.after(cierre)) {
            throw new IllegalArgumentException("La hora debe estar entre 8:00 AM y 5:00 PM.");
        }
        return hora;

    }

    private int parseId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El identificador del horario es obligatorio.");
        }
        return Integer.parseInt(value);
    }
}
