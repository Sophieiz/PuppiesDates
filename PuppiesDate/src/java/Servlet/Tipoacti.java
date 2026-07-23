package Servlet;

import Modelo.Tipo_Actividad;
import Controlador.Tipo_ActividadDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Tipoactividad", urlPatterns = {"/Tipoactividad"})
public class Tipoacti extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        Tipo_ActividadDAO dao = new Tipo_ActividadDAO();
        
        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                String nombre = request.getParameter("nombreActivi");
                
                Tipo_Actividad actividad = new Tipo_Actividad();
                actividad.setnombre_activi(nombre);
                
                boolean ok = dao.insertarTipo_Actividad(actividad);
                request.setAttribute("mensaje", ok ? "Actividad insertada correctamente." : "Error al insertar actividad.");
                
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_Actividad"));
                String nombre = request.getParameter("nombreActivi");
                
                Tipo_Actividad actividad = new Tipo_Actividad();
                actividad.setidTipo_Actividad(id);
                actividad.setnombre_activi(nombre);
                
                boolean ok = dao.actualizarTipoActividad(actividad);
                request.setAttribute("mensaje", ok ? "Actividad actualizada correctamente." : "Error al actualizar actividad.");
                
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_Actividad"));
                boolean ok = dao.eliminarTipoActividad(id);
                request.setAttribute("mensaje", ok ? "Actividad eliminada correctamente." : "Error al eliminar actividad.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idTipo_Actividad"));
                boolean ok = dao.reactivarTipoActividad(id);
                request.setAttribute("mensaje", ok ? "Actividad reactivada correctamente." : "Error al reactivar actividad.");
            }
            
            // Siempre recargar la lista
            List<Tipo_Actividad> lista = dao.listarTipoActividad();
            request.setAttribute("listaTiposActividad", lista);
            request.setAttribute("listaTiposActividadInactivos", dao.listarInactivas());
            
            request.getRequestDispatcher("/Vista/TipoActividad_admi.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de TipoActividad: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Tipo_ActividadDAO dao = new Tipo_ActividadDAO();
        List<Tipo_Actividad> lista = dao.listarTipoActividad();
        request.setAttribute("listaTiposActividad", lista);
        request.setAttribute("listaTiposActividadInactivos", dao.listarInactivas());
        request.getRequestDispatcher("/Vista/TipoActividad_admi.jsp").forward(request, response);
    }
}
