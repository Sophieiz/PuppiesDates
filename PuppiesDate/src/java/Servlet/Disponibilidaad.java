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
import jakarta.servlet.http.HttpSession;

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
                    request.getSession().setAttribute("mensajeFlash", resultado ? "Disponibilidad actualizada correctamente." : "Error al actualizar disponibilidad.");
                } else {
                    resultado = disponibilidadDao.insertarDisponibilidad(disponibilidad);
                    request.getSession().setAttribute("mensajeFlash", resultado ? "Disponibilidad registrada correctamente." : "Error al registrar disponibilidad.");
                }
            } else if ("eliminar".equalsIgnoreCase(accion) || "inactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idDisponibilidad"));
                boolean resultado = disponibilidadDao.eliminarDisponibilidad(id);
                request.getSession().setAttribute("mensajeFlash", resultado ? "Disponibilidad inactivada correctamente." : "Error al inactivar disponibilidad.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idDisponibilidad"));
                boolean resultado = disponibilidadDao.reactivarDisponibilidad(id);
                request.getSession().setAttribute("mensajeFlash", resultado ? "Disponibilidad reactivada correctamente." : "Error al reactivar disponibilidad.");
            }
        } catch (SQLException | IllegalArgumentException e) {
            request.getSession().setAttribute("mensajeFlash", "Error: revisa los datos ingresados.");
            response.sendRedirect(request.getContextPath() + "/Disponibilidaad");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/Disponibilidaad");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DisponibilidadDAO disponibilidadDao = new DisponibilidadDAO();

        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("mensajeFlash") != null) {
            request.setAttribute("mensaje", sesion.getAttribute("mensajeFlash"));
            sesion.removeAttribute("mensajeFlash");
        }

        cargarListas(request, disponibilidadDao);
        request.getRequestDispatcher("/Vista/Disponibilidad_admi.jsp").forward(request, response);
    }

    private Disponibilidad crearDisponibilidad(HttpServletRequest request) {
        String fechaStr = request.getParameter("fechaDisp");
        String cupoTotalStr = request.getParameter("cupoTotalDisp");
        String cupoDisponibleStr = request.getParameter("cupoDisponibleDisp");
        String horarioIdStr = request.getParameter("horarioIdDisp");

        if (fechaStr == null || fechaStr.trim().isEmpty()
                || cupoTotalStr == null || cupoTotalStr.trim().isEmpty()
                || cupoDisponibleStr == null || cupoDisponibleStr.trim().isEmpty()
                || horarioIdStr == null || horarioIdStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        Disponibilidad disponibilidad = new Disponibilidad();
        disponibilidad.setfecha(java.sql.Date.valueOf(fechaStr));
        disponibilidad.setcupo_total(Integer.parseInt(cupoTotalStr));
        disponibilidad.setcupo_disponible(Integer.parseInt(cupoDisponibleStr));
        disponibilidad.setHorarios_idHorarios(Integer.parseInt(horarioIdStr));
        return disponibilidad;
    }

    private void cargarListas(HttpServletRequest request, DisponibilidadDAO disponibilidadDao) {
        request.setAttribute("listaDisponibilidades", disponibilidadDao.Disponibilidad());
        request.setAttribute("listaHorarios", new HorariosDAO().listarHorarios());
    }
}