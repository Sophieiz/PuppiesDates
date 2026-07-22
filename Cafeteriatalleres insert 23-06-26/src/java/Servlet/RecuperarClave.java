package Servlet;

import Controlador.CorreoUtil;
import Controlador.Recuperacion_claveDAO;
import Controlador.UsuariosDAO;
import Modelo.Recuperacion_clave;
import Modelo.Usuarios;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RecuperarClave", urlPatterns = {"/RecuperarClave"})
public class RecuperarClave extends HttpServlet {

    // El link es válido por 30 minutos
    private static final int MINUTOS_VALIDEZ = 30;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Vista/RecuperarClave.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String correo = request.getParameter("correo");

        // Por seguridad, siempre se muestra el mismo mensaje exista o no el correo,
        // para no revelar si un correo está registrado o no en el sistema
        String mensajeGenerico = "Si el correo está registrado, te enviamos un enlace para restablecer tu contraseña.";

        try {
            if (correo == null || correo.trim().isEmpty()) {
                request.setAttribute("mensaje", "Ingresa tu correo electrónico.");
                request.getRequestDispatcher("/Vista/RecuperarClave.jsp").forward(request, response);
                return;
            }

            UsuariosDAO usuariosDao = new UsuariosDAO();
            Usuarios usuario = usuariosDao.ConsultarUsuarioPorCorreo(correo.trim());

            if (usuario != null) {
                Recuperacion_claveDAO recuperacionDao = new Recuperacion_claveDAO();

                // Invalida cualquier link anterior que no se haya usado
                recuperacionDao.invalidarTokensAnteriores(usuario.getidUsuarios());

                String token = UUID.randomUUID().toString();
                Timestamp expiracion = new Timestamp(System.currentTimeMillis() + (MINUTOS_VALIDEZ * 60 * 1000L));

                Recuperacion_clave recuperacion = new Recuperacion_clave();
                recuperacion.setToken(token);
                recuperacion.setFecha_expiracion(expiracion);
                recuperacion.setUsuarios_idUsuarios(usuario.getidUsuarios());

                recuperacionDao.insertarToken(recuperacion);

                String baseUrl = request.getScheme() + "://" + request.getServerName()
                        + ":" + request.getServerPort() + request.getContextPath();
                String linkRestablecer = baseUrl + "/RestablecerClave?token=" + token;

                CorreoUtil.enviarCorreoRecuperacion(usuario.getcorreo(), usuario.getnombre(), linkRestablecer);
            }

            request.setAttribute("mensaje", mensajeGenerico);

        } catch (Exception e) {
            request.setAttribute("mensaje", "Ocurrió un error. Intenta de nuevo más tarde.");
        }

        request.getRequestDispatcher("/Vista/RecuperarClave.jsp").forward(request, response);
    }
}
