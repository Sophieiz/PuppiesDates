package Servlet;

import Controlador.ActividadDAO;
import Controlador.DisponibilidadDAO;
import Modelo.Disponibilidad;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "VerificarDisponibilidad", urlPatterns = {"/VerificarDisponibilidad"})
public class VerificarDisponibilidad extends HttpServlet {

    // Horario de atención del negocio
    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(17, 0);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            String numPersonasStr = request.getParameter("num_personasp");
            String fechaStr = request.getParameter("fechar");
            String horaStr = request.getParameter("horar");
            String idActividadStr = request.getParameter("actividada");

            if (numPersonasStr == null || numPersonasStr.trim().isEmpty()
                    || fechaStr == null || fechaStr.trim().isEmpty()
                    || horaStr == null || horaStr.trim().isEmpty()
                    || idActividadStr == null || idActividadStr.trim().isEmpty()) {
                request.setAttribute("resultado", "Error: Todos los campos son obligatorios.");
                cargarCombosYRedirigir(request, response);
                return;
            }

            int numPersonas = Integer.parseInt(numPersonasStr);
            int idActividad = Integer.parseInt(idActividadStr);

            if (numPersonas < 1 || numPersonas > 20) {
                request.setAttribute("resultado", "Error: El número de personas debe estar entre 1 y 20.");
                cargarCombosYRedirigir(request, response);
                return;
            }

            String horaCompleta = horaStr.length() == 5 ? horaStr + ":00" : horaStr;
            Date fechaSql = Date.valueOf(fechaStr);
            Time horaSql = Time.valueOf(horaCompleta);

            LocalDate fechaSeleccionada = fechaSql.toLocalDate();
            LocalTime horaSeleccionada = horaSql.toLocalTime();
            LocalDate hoy = LocalDate.now();
            LocalTime ahora = LocalTime.now();

            // No permitir fechas pasadas
            if (fechaSeleccionada.isBefore(hoy)) {
                request.setAttribute("resultado", "Error: No se permiten fechas anteriores a hoy.");
                cargarCombosYRedirigir(request, response);
                return;
            }

            // No permitir horas fuera del horario de atención
            if (horaSeleccionada.isBefore(HORA_APERTURA) || horaSeleccionada.isAfter(HORA_CIERRE)) {
                request.setAttribute("resultado", "Error: El horario de atención es de 8:00 AM a 5:00 PM.");
                cargarCombosYRedirigir(request, response);
                return;
            }

            // Si la fecha es hoy, no permitir horas que ya pasaron
            if (fechaSeleccionada.isEqual(hoy) && horaSeleccionada.isBefore(ahora)) {
                request.setAttribute("resultado", "Error: Esa hora ya pasó. Elige una hora posterior a la actual.");
                cargarCombosYRedirigir(request, response);
                return;
            }

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

        } catch (IllegalArgumentException e) {
            // Cubre: numeros invalidos, fecha/hora con formato incorrecto (Date.valueOf, Time.valueOf)
            request.setAttribute("resultado", "Revisa los datos ingresados: verifica el número de personas, la fecha y la hora.");
            cargarCombosYRedirigir(request, response);
        } catch (Exception e) {
            // Cualquier otro error inesperado (ej. fallo de conexión a la base de datos)
            request.setAttribute("resultado", "Ocurrió un error inesperado. Intenta de nuevo en unos minutos.");
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