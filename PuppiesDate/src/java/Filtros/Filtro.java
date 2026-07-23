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
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        // Ruta relativa al contexto (sin el nombre del proyecto)
        String path = req.getRequestURI().substring(req.getContextPath().length());
        // Si la ruta está vacía o es solo "/", es la raíz -> tratar como index.jsp
        if (path.isEmpty() || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }
        // Excluir recursos estáticos
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }
        // Excluir páginas públicas y servlets de acceso
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
        // Validar sesión para páginas privadas
        if (session == null || session.getAttribute("perfil") == null) {
            res.sendRedirect(req.getContextPath() + "/Vista/InicioSesion.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}