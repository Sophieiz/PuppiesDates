package Servlet;

import Controlador.RolesDAO;
import Modelo.Roles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RolesAdmi", urlPatterns = {"/RolesAdmi"})
public class RolesAdmi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarLista(request);
        request.getRequestDispatcher("/Vista/Roles_admi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RolesDAO dao = new RolesDAO();
        String accion = request.getParameter("accion");
        String msg;

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                Roles rol = new Roles();
                rol.setdescripcion_rol(request.getParameter("descripcion_rol"));
                msg = dao.insertarRol(rol) ? "Rol registrado exitosamente." : "Error al registrar rol.";
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                Roles rol = new Roles();
                rol.setidRoles(Integer.parseInt(request.getParameter("idRoles")));
                rol.setdescripcion_rol(request.getParameter("descripcion_rol"));
                msg = dao.actualizarRol(rol) ? "Rol actualizado exitosamente." : "Error al actualizar rol.";
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idRoles"));
                msg = dao.eliminarRol(id) ? "Rol inactivado exitosamente." : "Error al inactivar rol.";
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idRoles"));
                msg = dao.reactivarRol(id) ? "Rol reactivado exitosamente." : "Error al reactivar rol.";
            } else {
                msg = "Accion no reconocida.";
            }
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de Roles: " + e.getMessage(), e);
        }

        request.setAttribute("mensaje", msg);
        cargarLista(request);
        request.getRequestDispatcher("/Vista/Roles_admi.jsp").forward(request, response);
    }

    private void cargarLista(HttpServletRequest request) {
        RolesDAO dao = new RolesDAO();
        request.setAttribute("listaRoles", dao.listarRoles());
        request.setAttribute("listaRolesInactivos", dao.listarInactivos());
    }
}
