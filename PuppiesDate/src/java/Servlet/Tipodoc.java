package Servlet;

import Modelo.Tipo_documento;
import Controlador.Tipo_documentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                request.setAttribute("mensaje", ok ? "Documento insertado correctamente." : "Error al insertar documento.");
                
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_documento")); // solo se usa para actualizar
                String descripcion = request.getParameter("descripcion_doc");
                
                Tipo_documento doc = new Tipo_documento();
                doc.setidTipo_documento(id);
                doc.setdescripcion_doc(descripcion);
                
                boolean ok = dao.actualizarTipoDocumento(doc);
                request.setAttribute("mensaje", ok ? "Documento actualizado correctamente." : "Error al actualizar documento.");
                
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_documento"));
                boolean ok = dao.eliminarTipoDocumento(id);
                request.setAttribute("mensaje", ok ? "Documento eliminado correctamente." : "Error al eliminar documento.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_documento"));
                boolean ok = dao.reactivarTipoDocumento(id);
                request.setAttribute("mensaje", ok ? "Documento reactivado correctamente." : "Error al reactivar documento.");
            }
            
            // Siempre recargar la lista
            List<Tipo_documento> lista = dao.listarTipoDocumento();
            request.setAttribute("listaTiposDocumento", lista);
            request.setAttribute("listaTiposDocumentoInactivos", dao.listarInactivos());
            
            request.getRequestDispatcher("/Vista/Tipodocumento_admin.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de TipoDocumento: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tipo_documentoDAO dao = new Tipo_documentoDAO();
        List<Tipo_documento> lista = dao.listarTipoDocumento();
        request.setAttribute("listaTiposDocumento", lista);
        request.setAttribute("listaTiposDocumentoInactivos", dao.listarInactivos());
        request.getRequestDispatcher("/Vista/Tipodocumento_admin.jsp").forward(request, response);
    }
}
