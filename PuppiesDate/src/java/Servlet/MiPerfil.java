package Servlet;

import Controlador.UsuariosDAO;
import Modelo.Usuarios;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "MiPerfil", urlPatterns = {"/MiPerfil"})
public class MiPerfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }

        if (sesion.getAttribute("mensajeFlash") != null) {
            request.setAttribute("mensaje", sesion.getAttribute("mensajeFlash"));
            sesion.removeAttribute("mensajeFlash");
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");
        UsuariosDAO dao = new UsuariosDAO();
        Usuarios usuario = dao.ConsultarUsuarioPorId(idUsuario);
        request.setAttribute("usuario", usuario);
        request.getRequestDispatcher("/Vista/MiPerfil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("idUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/Iniciar");
            return;
        }

        int idUsuario = (int) sesion.getAttribute("idUsuario");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");

        UsuariosDAO dao = new UsuariosDAO();
        boolean ok = dao.actualizarDatosPersonales(idUsuario, nombre, apellido, telefono, correo);

        if (ok) {
            sesion.setAttribute("nombreUsuario", nombre);
            sesion.setAttribute("correoUsuario", correo);
            sesion.setAttribute("mensajeFlash", "Tus datos se actualizaron correctamente.");
        } else {
            sesion.setAttribute("mensajeFlash", "No pudimos actualizar tus datos. Intenta de nuevo.");
        }

        response.sendRedirect(request.getContextPath() + "/MiPerfil");
    }
}