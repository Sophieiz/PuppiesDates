package Servlet;

import Controlador.ReservaDAO;
import Modelo.Reserva;
import java.io.IOException;
import java.sql.Time;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaAdmi", urlPatterns = {"/ReservaAdmi"})
public class ReservaAdmi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ReservaDAO reservaDao = new ReservaDAO();

        try {
            String accion = request.getParameter("accion");

            if ("modificar".equalsIgnoreCase(accion)) {
                Reserva reserva = crearReserva(request);
                boolean resultado = reservaDao.actualizarReserva(reserva);
                request.setAttribute("resultado", resultado ? "Reserva modificada exitosamente." : "Error al modificar la reserva.");
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int idReserva = Integer.parseInt(request.getParameter("idReserva"));
                boolean resultado = reservaDao.eliminarReserva(idReserva);
                request.setAttribute("resultado", resultado ? "Reserva eliminada exitosamente." : "Error al eliminar la reserva.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int idReserva = Integer.parseInt(request.getParameter("idReserva"));
                boolean resultado = reservaDao.reactivarReserva(idReserva);
                request.setAttribute("resultado", resultado ? "Reserva reactivada exitosamente." : "Error al reactivar la reserva.");
            }
        } catch (Exception e) {
            request.setAttribute("resultado", "Error: " + e.getMessage());
        }

        cargarLista(request, reservaDao);
        request.getRequestDispatcher("/Vista/ReservaAdmi.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ReservaDAO reservaDao = new ReservaDAO();
        cargarLista(request, reservaDao);
        request.getRequestDispatcher("/Vista/ReservaAdmi.jsp").forward(request, response);
    }

    private Reserva crearReserva(HttpServletRequest request) {
        Reserva reserva = new Reserva();
        reserva.setidReserva(Integer.parseInt(request.getParameter("idReserva")));
        reserva.setNum_personas(Integer.parseInt(request.getParameter("num_personas")));
        reserva.setHora(parseTime(request.getParameter("hora")));
        reserva.setFecha(java.sql.Date.valueOf(request.getParameter("fecha")));
        reserva.setUsuarios_idUsuarios(Integer.parseInt(request.getParameter("Usuarios_idUsuarios")));
        reserva.setDisponibilidad_idDisponibilidad(Integer.parseInt(request.getParameter("Disponibilidad_idDisponibilidad")));
        reserva.setEstado_reserva_idEstado_reserva(Integer.parseInt(request.getParameter("Estado_reserva_idEstado_reserva")));
        reserva.setActividad_idActividad(Integer.parseInt(request.getParameter("Actividad_idActividad")));
        reserva.setPagos_idPagos(Integer.parseInt(request.getParameter("Pagos_idPagos")));
        return reserva;
    }

    private void cargarLista(HttpServletRequest request, ReservaDAO reservaDao) {
        String textoBusqueda = request.getParameter("buscar");
        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            request.setAttribute("listaReservas", reservaDao.buscarReserva(textoBusqueda.trim()));
            request.setAttribute("terminoBusqueda", textoBusqueda.trim());
        } else {
            request.setAttribute("listaReservas", reservaDao.listarReserva());
        }
        request.setAttribute("listaReservasInactivas", reservaDao.listarInactivas());
    }

    private Time parseTime(String value) {
        return Time.valueOf(value.length() == 5 ? value + ":00" : value);
    }
}
