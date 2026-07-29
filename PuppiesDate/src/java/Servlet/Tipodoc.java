package Servlet;
import Modelo.Tipo_documento;
import Controlador.Tipo_documentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Tipodocumento", urlPatterns = {"/Tipodocumento"})
public class Tipodoc extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        Tipo_documentoDAO dao = new Tipo_documentoDAO();

        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                String descripcion = request.getParameter("descripcion_doc");

                Tipo_documento doc = new Tipo_documento();
                doc.setdescripcion_doc(descripcion);

                boolean ok = dao.insertarTipo_documento(doc);
                request.getSession().setAttribute("mensajeFlash", ok ? "Documento insertado correctamente." : "Error al insertar documento.");

            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_documento")); // solo se usa para actualizar
                String descripcion = request.getParameter("descripcion_doc");

                Tipo_documento doc = new Tipo_documento();
                doc.setidTipo_documento(id);
                doc.setdescripcion_doc(descripcion);

                boolean ok = dao.actualizarTipoDocumento(doc);
                request.getSession().setAttribute("mensajeFlash", ok ? "Documento actualizado correctamente." : "Error al actualizar documento.");

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_documento"));
                boolean ok = dao.eliminarTipoDocumento(id);
                request.getSession().setAttribute("mensajeFlash", ok ? "Documento eliminado correctamente." : "Error al eliminar documento.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_documento"));
                boolean ok = dao.reactivarTipoDocumento(id);
                request.getSession().setAttribute("mensajeFlash", ok ? "Documento reactivado correctamente." : "Error al reactivar documento.");
            }

            response.sendRedirect(request.getContextPath() + "/Tipodocumento");

        } catch (SQLException e) {
            request.getSession().setAttribute("mensajeFlash", "Error en operaciones de TipoDocumento: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/Tipodocumento");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tipo_documentoDAO dao = new Tipo_documentoDAO();

        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("mensajeFlash") != null) {
            request.setAttribute("mensaje", sesion.getAttribute("mensajeFlash"));
            sesion.removeAttribute("mensajeFlash");
        }

        List<Tipo_documento> lista = dao.listarTipoDocumento();
        request.setAttribute("listaTiposDocumento", lista);
        request.setAttribute("listaTiposDocumentoInactivos", dao.listarInactivos());
        request.getRequestDispatcher("/Vista/Tipodocumento_admin.jsp").forward(request, response);
    }
}