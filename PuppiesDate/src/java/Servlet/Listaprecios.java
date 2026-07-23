package Servlet;

import Modelo.Lista_precios;
import Controlador.Lista_preciosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Listaprecios", urlPatterns = {"/Listaprecios"})
public class Listaprecios extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        Lista_preciosDAO dao = new Lista_preciosDAO();
        
        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                String descripcion = normalizarPrecio(request.getParameter("descripPrecio"));
                
                Lista_precios precio = new Lista_precios();
                precio.setdescrip_precio(descripcion);
                
                boolean ok = dao.insertarLista_precios(precio);
                request.setAttribute("mensaje", ok ? "Precio insertado correctamente." : "Error al insertar precio.");
                
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idLista_Precios"));
                String descripcion = normalizarPrecio(request.getParameter("descripPrecio"));
                
                Lista_precios precio = new Lista_precios();
                precio.setidLista_Precios(id);
                precio.setdescrip_precio(descripcion);
                
                boolean ok = dao.actualizarListaPrecios(precio);
                request.setAttribute("mensaje", ok ? "Precio actualizado correctamente." : "Error al actualizar precio.");
                
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idLista_Precios"));
                boolean ok = dao.eliminarListaPrecios(id);
                request.setAttribute("mensaje", ok ? "Precio eliminado correctamente." : "Error al eliminar precio.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idLista_Precios"));
                boolean ok = dao.reactivarListaPrecios(id);
                request.setAttribute("mensaje", ok ? "Precio reactivado correctamente." : "Error al reactivar precio.");
            }
            
            // Siempre recargar la lista
            List<Lista_precios> lista = dao.listarLista_precios();
            request.setAttribute("listaPrecios", lista);
            request.setAttribute("listaPreciosInactivos", dao.listarInactivas());
            
            request.getRequestDispatcher("/Vista/ListaPrecios_admi.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de ListaPrecios: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Lista_preciosDAO dao = new Lista_preciosDAO();
        List<Lista_precios> lista = dao.listarLista_precios();
        request.setAttribute("listaPrecios", lista);
        request.setAttribute("listaPreciosInactivos", dao.listarInactivas());
        request.getRequestDispatcher("/Vista/ListaPrecios_admi.jsp").forward(request, response);
    }

    private String normalizarPrecio(String precio) {
        if (precio == null) {
            return "";
        }
        return precio.trim().replace(".", "").replace(",", "");
    }
}
