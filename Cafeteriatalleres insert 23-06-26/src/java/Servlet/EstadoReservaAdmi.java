package Servlet;

import Controlador.Estado_reservaDAO;
import Modelo.Estado_reserva;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "EstadoReservaAdmi", urlPatterns = {"/EstadoReservaAdmi"})
public class EstadoReservaAdmi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarLista(request);
        request.getRequestDispatcher("/Vista/EstadoReserva_admi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Estado_reservaDAO dao = new Estado_reservaDAO();
        String accion = request.getParameter("accion");
        String msg;

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Estado_reserva estado = new Estado_reserva();
                estado.setdescripcion_esta(request.getParameter("descripcion_esta"));
                msg = dao.insertarEstadoReserva(estado) ? "Estado registrado exitosamente." : "Error al registrar estado.";
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Estado_reserva estado = new Estado_reserva();
                estado.setidEstado_reserva(Integer.parseInt(request.getParameter("idEstado_reserva")));
                estado.setdescripcion_esta(request.getParameter("descripcion_esta"));
                msg = dao.actualizarEstadoReserva(estado) ? "Estado actualizado exitosamente." : "Error al actualizar estado.";
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idEstado_reserva"));
                msg = dao.eliminarEstadoReserva(id) ? "Estado eliminado exitosamente." : "Error al eliminar estado.";
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idEstado_reserva"));
                msg = dao.reactivarEstadoReserva(id) ? "Estado reactivado exitosamente." : "Error al reactivar estado.";
            } else {
                msg = "Accion no reconocida.";
            }
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de EstadoReserva: " + e.getMessage(), e);
        }

        request.setAttribute("mensaje", msg);
        cargarLista(request);
        request.getRequestDispatcher("/Vista/EstadoReserva_admi.jsp").forward(request, response);
    }

    private void cargarLista(HttpServletRequest request) {
        Estado_reservaDAO dao = new Estado_reservaDAO();
        request.setAttribute("listaEstados", dao.Estado_reserva());
        request.setAttribute("listaEstadosInactivos", dao.listarInactivos());
    }
}
