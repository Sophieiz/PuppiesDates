/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

import Controlador.ActividadDAO;
import Controlador.DisponibilidadDAO;
import Modelo.Disponibilidad;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "VerificarDisponibilidad", urlPatterns = {"/VerificarDisponibilidad"})
public class VerificarDisponibilidad extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String numPersonasStr = request.getParameter("num_personasp");
            String fechaStr = request.getParameter("fechar");
            String horaStr = request.getParameter("horar");
            String idActividadStr = request.getParameter("actividada");

            if (numPersonasStr == null || fechaStr == null
                    || horaStr == null || idActividadStr == null) {
                request.setAttribute("resultado", "Error: Todos los campos son obligatorios.");
                cargarCombosYRedirigir(request, response);
                return;
            }

            int numPersonas = Integer.parseInt(numPersonasStr);
            int idActividad = Integer.parseInt(idActividadStr);

            String horaCompleta = horaStr.length() == 5 ? horaStr + ":00" : horaStr;
            Date fechaSql = Date.valueOf(fechaStr);
            Time horaSql = Time.valueOf(horaCompleta);

            DisponibilidadDAO disponibilidadDao = new DisponibilidadDAO();
            String estadoDisponibilidad = disponibilidadDao.verificarEstadoDisponibilidad(fechaSql, horaSql);

            switch (estadoDisponibilidad) {
                case "NO_EXISTE":
                    request.setAttribute("resultado", "Fecha no disponible para esta actividad.");
                    cargarCombosYRedirigir(request, response);
                    return;
                case "SIN_CUPO":
                    request.setAttribute("resultado", "Sin cupos disponibles para esta fecha.");
                    cargarCombosYRedirigir(request, response);
                    return;
                default:
                    break;
            }

            Disponibilidad disponibilidad = disponibilidadDao.buscarDisponibilidad(fechaSql, horaSql);

            HttpSession sesion = request.getSession();
            sesion.setAttribute("resActividad", idActividad);
            sesion.setAttribute("resDisponibilidad", disponibilidad.getIdDisponibilidad());
            sesion.setAttribute("resNumPersonas", numPersonas);
            sesion.setAttribute("resFecha", fechaStr);
            sesion.setAttribute("resHora", horaStr);

            response.sendRedirect(request.getContextPath() + "/ConfirmarReserva");

        } catch (Exception e) {
            request.setAttribute("resultado", "Revisa los datos ingresados: " + e.getMessage());
            cargarCombosYRedirigir(request, response);
        }
    }

    private void cargarCombosYRedirigir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActividadDAO actividadDao = new ActividadDAO();
        request.setAttribute("actividades", actividadDao.Actividad());
        request.getRequestDispatcher("/Vista/Reserva.jsp").forward(request, response);
    }
}
