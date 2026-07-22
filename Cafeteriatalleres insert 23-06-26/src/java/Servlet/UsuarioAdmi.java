package Servlet;

import Controlador.RolesDAO;
import Controlador.UsuariosDAO;
import Modelo.Usuarios;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioAdmi", urlPatterns = {"/UsuarioAdmi"})
public class UsuarioAdmi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        UsuariosDAO usuariosDao = new UsuariosDAO();

        try {
            String accion = request.getParameter("accion");

            if ("modificar".equalsIgnoreCase(accion)) {
                Usuarios usuario = crearUsuario(request);
                boolean resultado = usuariosDao.actualizarUsuario(usuario);
                request.setAttribute("resultado", resultado ? "Usuario modificado correctamente." : "Error al modificar el usuario.");
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int idUsuarios = Integer.parseInt(request.getParameter("idUsuarios"));
                boolean resultado = usuariosDao.eliminarUsuario(idUsuarios);
                request.setAttribute("resultado", resultado ? "Usuario eliminado correctamente." : "Error al eliminar el usuario.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int idUsuarios = Integer.parseInt(request.getParameter("idUsuarios"));
                boolean resultado = usuariosDao.reactivarUsuario(idUsuarios);
                request.setAttribute("resultado", resultado ? "Usuario reactivado correctamente." : "Error al reactivar el usuario.");
            }
        } catch (Exception e) {
            request.setAttribute("resultado", "Error: " + e.getMessage());
        }

        cargarLista(request, usuariosDao);
        request.getRequestDispatcher("/Vista/UsuariosAdmi.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuariosDAO usuariosDao = new UsuariosDAO();
        cargarLista(request, usuariosDao);
        request.getRequestDispatcher("/Vista/UsuariosAdmi.jsp").forward(request, response);
    }

    private Usuarios crearUsuario(HttpServletRequest request) {
        Usuarios usuario = new Usuarios();
        usuario.setidUsuarios(Integer.parseInt(request.getParameter("idUsuarios")));
        usuario.setnombre(request.getParameter("nombre"));
        usuario.setapellido(request.getParameter("apellido"));
        usuario.setdocumento(request.getParameter("documento"));
        usuario.settelefono(request.getParameter("telefono"));
        usuario.setcorreo(request.getParameter("correo"));
        usuario.setclave(request.getParameter("clave"));
        usuario.setfecha_nac(java.sql.Date.valueOf(request.getParameter("fecha_nac")));
        usuario.setfecha_cad(java.sql.Date.valueOf(request.getParameter("fecha_cad")));
        usuario.setcheckbox("1".equals(request.getParameter("checkbox")));
        usuario.setTipo_documento_idTipo_documento(Integer.parseInt(request.getParameter("Tipo_documento_idTipo_documento")));
        usuario.setRoles_idRoles(Integer.parseInt(request.getParameter("Roles_idRoles")));
        return usuario;
    }

    private void cargarLista(HttpServletRequest request, UsuariosDAO usuariosDao) {
        request.setAttribute("listaUsuarios", usuariosDao.listarUsuarios());
        request.setAttribute("listaRoles", new RolesDAO().listarRoles());
        request.setAttribute("listaUsuariosInactivos", usuariosDao.listarInactivos());
    }
}
