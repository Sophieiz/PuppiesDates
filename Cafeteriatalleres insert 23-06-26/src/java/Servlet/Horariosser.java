package Servlet;

import Modelo.Horarios;
import Controlador.HorariosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                
                Horarios horario = new Horarios();
                horario.sethora_ini(horaIni);
                horario.sethora_fin(horaFin);
                
                boolean ok = dao.insertarHorarios(horario);
                request.setAttribute("mensaje", ok ? "Horario insertado correctamente." : "Error al insertar horario.");
                
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idHorarios"));
                Time horaIni = parseTime(request.getParameter("hora_ini"));
                Time horaFin = parseTime(request.getParameter("hora_fin"));
                
                Horarios horario = new Horarios();
                horario.setidHorarios(id);
                horario.sethora_ini(horaIni);
                horario.sethora_fin(horaFin);
                
                boolean ok = dao.actualizarHorario(horario);
                request.setAttribute("mensaje", ok ? "Horario actualizado correctamente." : "Error al actualizar horario.");
                
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idHorarios"));
                boolean ok = dao.eliminarHorario(id);
                request.setAttribute("mensaje", ok ? "Horario inactivado correctamente." : "Error al inactivar horario.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idHorarios"));
                boolean ok = dao.reactivarHorario(id);
                request.setAttribute("mensaje", ok ? "Horario reactivado correctamente." : "Error al reactivar horario.");
            }
            
            // Siempre recargar la lista
            List<Horarios> lista = dao.listarHorarios();
            request.setAttribute("listaHorarios", lista);
            request.setAttribute("listaHorariosInactivos", dao.listarInactivos());
            
            request.getRequestDispatcher("/Vista/Horario_admin.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de Horarios: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HorariosDAO dao = new HorariosDAO();
        List<Horarios> lista = dao.listarHorarios();
        request.setAttribute("listaHorarios", lista);
        request.setAttribute("listaHorariosInactivos", dao.listarInactivos());
        request.getRequestDispatcher("/Vista/Horario_admin.jsp").forward(request, response);
    }

    private Time parseTime(String value) {
        return Time.valueOf(value.length() == 5 ? value + ":00" : value);
    }
}
