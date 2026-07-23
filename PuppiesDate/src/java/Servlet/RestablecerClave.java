package Servlet;

import Controlador.Recuperacion_claveDAO;
import Controlador.UsuariosDAO;
import Modelo.Recuperacion_clave;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RestablecerClave", urlPatterns = {"/RestablecerClave"})
public class RestablecerClave extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        Recuperacion_claveDAO recuperacionDao = new Recuperacion_claveDAO();
        Recuperacion_clave recuperacion = recuperacionDao.buscarTokenValido(token);

        if (recuperacion == null) {
            request.setAttribute("mensaje", "Este enlace no es válido o ya expiró. Solicita uno nuevo.");
            request.setAttribute("tokenValido", false);
        } else {
            request.setAttribute("token", token);
            request.setAttribute("tokenValido", true);
        }

        request.getRequestDispatcher("/Vista/RestablecerClave.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String token = request.getParameter("token");
        String nuevaClave = request.getParameter("nuevaClave");
        String confirmarClave = request.getParameter("confirmarClave");

        Recuperacion_claveDAO recuperacionDao = new Recuperacion_claveDAO();
        Recuperacion_clave recuperacion = recuperacionDao.buscarTokenValido(token);

        if (recuperacion == null) {
            request.setAttribute("mensaje", "Este enlace no es válido o ya expiró. Solicita uno nuevo.");
            request.setAttribute("tokenValido", false);
            request.getRequestDispatcher("/Vista/RestablecerClave.jsp").forward(request, response);
            return;
        }

        if (nuevaClave == null || nuevaClave.trim().isEmpty() || nuevaClave.length() < 6) {
            request.setAttribute("mensaje", "La contraseña debe tener al menos 6 caracteres.");
            request.setAttribute("token", token);
            request.setAttribute("tokenValido", true);
            request.getRequestDispatcher("/Vista/RestablecerClave.jsp").forward(request, response);
            return;
        }

        if (!nuevaClave.equals(confirmarClave)) {
            request.setAttribute("mensaje", "Las contraseñas no coinciden.");
            request.setAttribute("token", token);
            request.setAttribute("tokenValido", true);
            request.getRequestDispatcher("/Vista/RestablecerClave.jsp").forward(request, response);
            return;
        }

        UsuariosDAO usuariosDao = new UsuariosDAO();
        boolean actualizado = usuariosDao.actualizarClave(recuperacion.getUsuarios_idUsuarios(), nuevaClave);

        if (actualizado) {
            recuperacionDao.marcarTokenUsado(recuperacion.getIdRecuperacion_clave());
            response.sendRedirect(request.getContextPath() + "/Vista/InicioSesion.jsp?claveActualizada=true");
        } else {
            request.setAttribute("mensaje", "Ocurrió un error al actualizar tu contraseña. Intenta de nuevo.");
            request.setAttribute("token", token);
            request.setAttribute("tokenValido", true);
            request.getRequestDispatcher("/Vista/RestablecerClave.jsp").forward(request, response);
        }
    }
}
