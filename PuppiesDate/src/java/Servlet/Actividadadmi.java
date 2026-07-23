                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            package Servlet;

import Modelo.Actividad;
import Controlador.ActividadDAO;
import Controlador.Lista_preciosDAO;
import Controlador.Tipo_ActividadDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "Actividad", urlPatterns = {"/Actividad"})
public class Actividadadmi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        ActividadDAO dao = new ActividadDAO();
        
        try {
            if ("insertar".equalsIgnoreCase(accion)) {
                String descripcion = request.getParameter("descripcionAct");
                int tipoActi = Integer.parseInt(request.getParameter("tipoActi"));
                int listaPrecio = Integer.parseInt(request.getParameter("listaPrecioAct"));
                
                Actividad actividad = new Actividad();
                actividad.setdescripcion_actividad(descripcion);
                actividad.setTipo_Actividad_idTipo_Actividad(tipoActi);
                actividad.setLista_Precios_idLista_Precios(listaPrecio);
                
                boolean ok = dao.insertarActividad(actividad);
                request.setAttribute("mensaje", ok ? "Actividad insertada correctamente." : "Error al insertar actividad.");
                
            } else if ("actualizar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idActividad"));
                String descripcion = request.getParameter("descripcionAct");
                int tipoActi = Integer.parseInt(request.getParameter("tipoActi"));
                int listaPrecio = Integer.parseInt(request.getParameter("listaPrecioAct"));
                
                Actividad actividad = new Actividad();
                actividad.setidActividad(id);
                actividad.setdescripcion_actividad(descripcion);
                actividad.setTipo_Actividad_idTipo_Actividad(tipoActi);
                actividad.setLista_Precios_idLista_Precios(listaPrecio);
                
                boolean ok = dao.actualizarActividad(actividad);
                request.setAttribute("mensaje", ok ? "Actividad actualizada correctamente." : "Error al actualizar actividad.");
                
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idActividad"));
                boolean ok = dao.eliminarActividad(id);
                request.setAttribute("mensaje", ok ? "Actividad eliminada  correctamente." : "Error al eliminar actividad.");

            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idActividad"));
                boolean ok = dao.reactivarActividad(id);
                request.setAttribute("mensaje", ok ? "Actividad reactivada correctamente." : "Error al reactivar actividad.");
            }
            
            cargarListas(request, dao);
            request.getRequestDispatcher("/Vista/Actividad_admi.jsp").forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException("Error en operaciones de Actividad: " + e.getMessage(), e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActividadDAO dao = new ActividadDAO();
        cargarListas(request, dao);
        request.getRequestDispatcher("/Vista/Actividad_admi.jsp").forward(request, response);
    }

    private void cargarListas(HttpServletRequest request, ActividadDAO dao) {
        request.setAttribute("listaActividades", dao.Actividad());        
        request.setAttribute("listaTiposActividad", new Tipo_ActividadDAO().listarTipoActividad());
        request.setAttribute("listaPrecios", new Lista_preciosDAO().listarLista_precios());
    }
}
