package Servlet;

import Controlador.DisponibilidadDAO;
import Controlador.HorariosDAO;
import Modelo.Disponibilidad;
import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Disponibilidaad", urlPatterns = {"/Disponibilidaad"})
public class Disponibilidaad extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        DisponibilidadDAO disponibilidadDao = new DisponibilidadDAO();

        try {
            if ("insertar".equalsIgnoreCase(accion) || "actualizar".equalsIgnoreCase(accion)) {
                Disponibilidad disponibilidad = crearDisponibilidad(request);
                boolean resultado;

                if ("actualizar".equalsIgnoreCase(accion)) {
                    disponibilidad.setidDisponibilidad(Integer.parseInt(request.getParameter("idDisponibilidad")));
                    resultado = disponibilidadDao.actualizarDisponibilidad(disponibilidad);
                    request.setAttribute("mensaje", resultado ? "Disponibilidad actualizada correctamente." : "Error al actualizar disponibilidad.");
                } else {
                    resultado = disponibilidadDao.insertarDisponibilidad(disponibilidad);
                    request.setAttribute("mensaje", resultado ? "Disponibilidad registrada correctamente." : "Error al registrar disponibilidad.");
                }
            } else if ("eliminar".equalsIgnoreCase(accion) || "inactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idDisponibilidad"));
                boolean resultado = disponibilidadDao.eliminarDisponibilidad(id);
                request.setAttribute("mensaje", resultado ? "Disponibilidad inactivada correctamente." : "Error al inactivar disponibilidad.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idDisponibilidad"));
                boolean resultado = disponibilidadDao.reactivarDisponibilidad(id);
                request.setAttribute("mensaje", resultado ? "Disponibilidad reactivada correctamente." : "Error al reactivar disponibilidad.");
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("mensaje", "Error: " + e.getMessage());
        }

        cargarListas(request, disponibilidadDao);
        request.getRequestDispatcher("/Vista/Disponibilidad_admi.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DisponibilidadDAO disponibilidadDao = new DisponibilidadDAO();
        cargarListas(request, disponibilidadDao);
        request.getRequestDispatcher("/Vista/Disponibilidad_admi.jsp").forward(request, response);
    }

    private Disponibilidad crearDisponibilidad(HttpServletRequest request) {
        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setfecha(java.sql.Date.valueOf(request.getParameter("fechaDisp")));
        disponibilidad.setcupo_total(Integer.parseInt(request.getParameter("cupoTotalDisp")));
        disponibilidad.setcupo_disponible(Integer.parseInt(request.getParameter("cupoDisponibleDisp")));
        disponibilidad.setHorarios_idHorarios(Integer.parseInt(request.getParameter("horarioIdDisp")));
        return disponibilidad;
    }

    private void cargarListas(HttpServletRequest request, DisponibilidadDAO disponibilidadDao) {
        request.setAttribute("listaDisponibilidades", disponibilidadDao.Disponibilidad());
        request.setAttribute("listaHorarios", new HorariosDAO().listarHorarios());
    }
}