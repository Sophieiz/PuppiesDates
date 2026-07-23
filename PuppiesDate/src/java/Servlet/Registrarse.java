package Servlet;

import Controlador.RolesDAO;
import Controlador.Tipo_documentoDAO;
import Controlador.UsuariosDAO;
import Modelo.Usuarios;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Registrarse", urlPatterns = {"/Registrarse"})
public class Registrarse extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datos del formulario
        String nombre = request.getParameter("nombrep");
        String apellido = request.getParameter("apellidoa");
        String documento = request.getParameter("documentoa");
        String telefono = request.getParameter("telefonoi");
        String correo = request.getParameter("correoz");
        String clave = request.getParameter("clavev");
        int idTipoDocumento = Integer.parseInt(request.getParameter("tipodocs"));

        RolesDAO rolesDao = new RolesDAO();
        int idRolCliente = rolesDao.obtenerIdRolClientePorDefecto();

        Usuarios usuario = new Usuarios();
        usuario.setnombre(nombre);
        usuario.setapellido(apellido);
        usuario.setdocumento(documento);
        usuario.settelefono(telefono);
        usuario.setcorreo(correo);
        usuario.setfecha_nac(Date.valueOf(request.getParameter("fecha_nac")));
        usuario.setclave(clave);
        usuario.setTipo_documento_idTipo_documento(idTipoDocumento);
        usuario.setRoles_idRoles(idRolCliente);

        LocalDate fechaCad = LocalDate.now().plusYears(1);
        usuario.setfecha_cad(Date.valueOf(fechaCad));
        usuario.setcheckbox(request.getParameter("checkbox") != null);

        UsuariosDAO usuariosDao = new UsuariosDAO();

        if (idRolCliente == 0) {
            request.setAttribute("resultado", "No hay un rol de cliente configurado. Contacta al administrador.");
        } else if (usuariosDao.existeUsuario(documento)) {
            request.setAttribute("resultado", "El documento ya está registrado.");
        } else if (usuariosDao.existeUsuarioInactivo(documento)) {
            try {
                usuariosDao.reactivarUsuario(usuario);
                request.setAttribute("resultado", "Cuenta reactivada exitosamente.");
            } catch (Exception e) {
                request.setAttribute("resultado", "Error al reactivar: " + e.getMessage());
            }
        } else {
            try {
                boolean resultado = usuariosDao.insertarUsuarios(usuario);
                request.setAttribute("resultado", resultado ? "Usuario registrado exitosamente." : "Error al registrar usuario.");
            } catch (Exception e) {
                request.setAttribute("resultado", "Error: " + e.getMessage());
            }
        }

        cargarCombosYRedirigir(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cargarCombosYRedirigir(request, response);
    }

    private void cargarCombosYRedirigir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tipo_documentoDAO tipoDao = new Tipo_documentoDAO();
        request.setAttribute("tiposDoc", tipoDao.listarTipoDocumento());
        request.getRequestDispatcher("/Vista/Registrarse.jsp").forward(request, response);
    }
}
