package Servlet;

import Controlador.ActividadDAO;
import Controlador.CorreoUtil;
import Controlador.DisponibilidadDAO;
import Controlador.ReservaDAO;
import Controlador.UsuariosDAO;
import Modelo.Disponibilidad;
import Modelo.Reserva;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ReservaCliente", urlPatterns = {"/ReservaCliente"})
public class ReservaCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarCombosYRedirigir(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String documento = request.getParameter("documentoa");
            String numPersonasStr = request.getParameter("num_personasp");
            String fechaStr = request.getParameter("fechar");
            String horaStr = request.getParameter("horar");
            String idActividadStr = request.getParameter("actividada");

            if (documento == null || documento.trim().isEmpty()
                    || numPersonasStr == null || fechaStr == null
                    || horaStr == null || idActividadStr == null) {
                request.setAttribute("resultado", "Error: Todos los campos son obligatorios.");
                cargarCombosYRedirigir(request, response);
                return;
            }

            int numPersonas = Integer.parseInt(numPersonasStr);
            int idActividad = Integer.parseInt(idActividadStr);

            UsuariosDAO usuariosDao = new UsuariosDAO();
            ActividadDAO actividadDao = new ActividadDAO();

            if (!usuariosDao.existeUsuario(documento)) {
                request.setAttribute("resultado", "Error: El documento no está registrado.");
                cargarCombosYRedirigir(request, response);
                return;
            }

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
                    // "DISPONIBLE" -> continúa con la reserva
                    break;
            }

            Disponibilidad disponibilidad = disponibilidadDao.buscarDisponibilidad(fechaSql, horaSql);

            HttpSession sesion = request.getSession(false);
            int idUsuario = (int) sesion.getAttribute("idUsuario");

            Reserva reserva = new Reserva();
            reserva.setNum_personas(numPersonas);
            reserva.setFecha(fechaSql);
            reserva.setHora(horaSql);
            reserva.setActividad_idActividad(idActividad);
            reserva.setDisponibilidad_idDisponibilidad(disponibilidad.getIdDisponibilidad());
            reserva.setUsuarios_idUsuarios(idUsuario);
            reserva.setEstado_reserva_idEstado_reserva(1);
            reserva.setPagos_idPagos(1);

            ReservaDAO reservaDao = new ReservaDAO();
            boolean exito = reservaDao.insertarReserva(reserva);

            if (exito) {
                request.setAttribute("resultado", "¡Reserva registrada exitosamente!");

                Modelo.Usuarios usuarioReserva = usuariosDao.ConsultaUsuarios(documento);
                Modelo.Actividad actividadReserva = actividadDao.ConsultaUsuarios(idActividad);
                if (usuarioReserva != null && actividadReserva != null) {
                    // Notificación interna a la fundación
                    CorreoUtil.enviarCorreoNuevaReserva(
                            usuarioReserva.getnombre() + " " + usuarioReserva.getapellido(),
                            usuarioReserva.getcorreo(),
                            actividadReserva.getDescripcion_actividad(),
                            fechaStr, horaStr, numPersonas
                    );

                    // Ticket de confirmación para el usuario (fecha, hora y actividad reservada)
                    CorreoUtil.enviarCorreoConfirmacionReservaUsuario(
                            usuarioReserva.getnombre() + " " + usuarioReserva.getapellido(),
                            usuarioReserva.getcorreo(),
                            actividadReserva.getDescripcion_actividad(),
                            fechaStr, horaStr, numPersonas
                    );
                }
            } else {
                request.setAttribute("resultado", "Error al guardar la reserva.");
            }

        } catch (Exception e) {
            request.setAttribute("resultado", "Error inesperado: " + e.getMessage());
        }

        cargarCombosYRedirigir(request, response);
    }

    private void cargarCombosYRedirigir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActividadDAO actividadDao = new ActividadDAO();
        request.setAttribute("actividades", actividadDao.Actividad());
        request.getRequestDispatcher("/Vista/Reserva.jsp").forward(request, response);
    }
}
