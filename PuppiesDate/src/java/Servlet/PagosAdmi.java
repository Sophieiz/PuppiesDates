package Servlet;

import Controlador.PagosDAO;
import Modelo.Pagos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "PagosAdmi", urlPatterns = {"/PagosAdmi"})
public class PagosAdmi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarLista(request);
        request.getRequestDispatcher("/Vista/Pagos_admi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PagosDAO dao = new PagosDAO();
        String accion = request.getParameter("accion");
        String msg;

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Pagos pago = new Pagos();
                pago.setestado_pago(request.getParameter("estado_pago"));
                msg = dao.insertarPago(pago) ? "Pago registrado exitosamente." : "Error al registrar pago.";
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Pagos pago = new Pagos();
                pago.setidPagos(Integer.parseInt(request.getParameter("idPagos")));
                pago.setestado_pago(request.getParameter("estado_pago"));
                msg = dao.actualizarPagos(pago) ? "Pago actualizado exitosamente." : "Error al actualizar pago.";
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idPagos"));
                msg = dao.eliminarPagos(id) ? "Pago eliminado exitosamente." : "Error al eliminar pago.";
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idPagos"));
                msg = dao.reactivarPago(id) ? "Pago reactivado exitosamente." : "Error al reactivar pago.";
            } else {
                msg = "Accion no reconocida.";
            }
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de Pagos: " + e.getMessage(), e);
        }

        request.setAttribute("mensaje", msg);
        cargarLista(request);
        request.getRequestDispatcher("/Vista/Pagos_admi.jsp").forward(request, response);
    }

    private void cargarLista(HttpServletRequest request) {
        PagosDAO dao = new PagosDAO();
        request.setAttribute("listaPagos", dao.listarPagos());
        request.setAttribute("listaPagosInactivos", dao.listarInactivos());
    }
}
