package Servlet;

import Controlador.UsuariosDAO;
import Modelo.Usuarios;
import java.io.IOException;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Iniciar")
public class InicioSesion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String password = request.getParameter("pass");

        UsuariosDAO midao = new UsuariosDAO();
        Usuarios usuarioBD = midao.ConsultarUsuarioPorCorreo(correo);

        if (usuarioBD == null) {
            request.setAttribute("mensaje", "El correo no existe");
            request.getRequestDispatcher("/Vista/InicioSesion.jsp").forward(request, response);

        } else if (!Controlador.PasswordUtil.verificarPassword(password, usuarioBD.getclave())) {
            request.setAttribute("mensaje", "Clave incorrecta");
            request.getRequestDispatcher("/Vista/InicioSesion.jsp").forward(request, response);

        } else {

            Date hoy = new Date();
            Date fechaCad = usuarioBD.getfecha_cad();

            if (fechaCad != null && hoy.after(fechaCad)) {

                usuarioBD.setcheckbox(false);
                try {
                    midao.actualizarUsuario(usuarioBD);
                } catch (Exception e) {
                    System.out.println("Error al inactivar cuenta: " + e.getMessage());
                }
                request.setAttribute("mensaje", "Tu cuenta ha expirado. Por favor regístrate nuevamente..");
                request.getRequestDispatcher("/Vista/InicioSesion.jsp").forward(request, response);
                return;
            }

            if (!usuarioBD.ischeckbox()) {
                request.setAttribute("mensaje", "Tu cuenta está inactiva. Por favor regístrate nuevamente.");
                request.getRequestDispatcher("/Vista/InicioSesion.jsp").forward(request, response);
                return;
            }

            // Login exitoso
            request.getSession().invalidate();
            HttpSession sesion = request.getSession(true);
            sesion.setAttribute("nombreUsuario", usuarioBD.getnombre());
            sesion.setAttribute("perfil", usuarioBD.getRoles_idRoles());
            sesion.setAttribute("idUsuario", usuarioBD.getidUsuarios());
            sesion.setAttribute("correoUsuario", usuarioBD.getcorreo());

            if (usuarioBD.getRoles_idRoles() == 1) {
                request.getRequestDispatcher("/PanelAdmin.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/PanelUsuario.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion = request.getSession(false);
        if (sesion != null && sesion.getAttribute("perfil") != null) {
            int rol = Integer.parseInt(sesion.getAttribute("perfil").toString());
            if (rol == 1) {
                request.getRequestDispatcher("/PanelAdmin.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/PanelUsuario.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("/Vista/InicioSesion.jsp").forward(request, response);
        }
    }
}
