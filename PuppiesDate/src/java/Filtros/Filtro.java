package Filtros;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class Filtro implements Filter {

    private static final String[] RUTAS_ADMIN = {
        "/PanelAdmin.jsp",
        "/Actividad_admi.jsp",
        "/Disponibilidad_admi.jsp",
        "/EstadoReserva_admi.jsp",
        "/Horario_admin.jsp",
        "/ListaPrecios_admi.jsp",
        "/Pagos_admi.jsp",
        "/Papeleria.jsp",
        "/ReservaAdmi.jsp",
        "/Roles_admi.jsp",
        "/SolicitudAdopcionAdmi.jsp",
        "/TipoActividad_admi.jsp",
        "/Tipodocumento_admin.jsp",
        "/UsuariosAdmi.jsp",
        "/Actividad",
        "/Disponibilidaad",
        "/EstadoReservaAdmi",
        "/Listaprecios",
        "/PagosAdmi",
        "/PapeleraAdmi",
        "/PerritoAdmi",
        "/ReservaAdmi",
        "/RolesAdmi",
        "/SolicitudAdopcionAdmi",
        "/Tipoactividad",
        "/Tipodocumento",
        "/UsuarioAdmi",
        "/Horarios"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.isEmpty() || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/api/")) {
            chain.doFilter(request, response);
            return;
        }

        if (path.endsWith("index.jsp")
                || path.endsWith("InicioSesion.jsp")
                || path.endsWith("Registrarse.jsp")
                || path.endsWith("Actividad.jsp")
                || path.endsWith("Historia.jsp")
                || path.endsWith("Menu.jsp")
                || path.endsWith("Reserva.jsp")
                || path.endsWith("Catalogo.jsp")
                || path.endsWith("SolicitudAdopcion.jsp")
                || path.contains("CargarRegistro")
                || path.contains("Registrarse")
                || path.contains("Iniciar")
                || path.contains("CerrarSesion")
                || path.contains("RecuperarClave")
                || path.contains("RestablecerClave")
                || path.contains("CatalogoPerritos")
                || path.contains("SolicitudAdopcionCliente")) {
            chain.doFilter(request, response);
            return;
        }

        // Validar que exista sesión activa para páginas privadas
        if (session == null || session.getAttribute("perfil") == null) {
            res.sendRedirect(req.getContextPath() + "/Iniciar");
            return;
        }

        // Validar que, si la ruta es de administrador, el usuario tenga perfil de admin
        boolean esRutaAdmin = false;
        for (String rutaAdmin : RUTAS_ADMIN) {
            if (path.equals(rutaAdmin) || path.startsWith(rutaAdmin + "?")) {
                esRutaAdmin = true;
                break;
            }
        }

        if (esRutaAdmin) {
            Object perfilObj = session.getAttribute("perfil");
            int rol = (perfilObj instanceof Integer)
                    ? (Integer) perfilObj
                    : Integer.parseInt(perfilObj.toString());

            boolean esAdmin = rol == 1;
            if (!esAdmin) {
                res.sendRedirect(req.getContextPath() + "/PanelUsuario.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
