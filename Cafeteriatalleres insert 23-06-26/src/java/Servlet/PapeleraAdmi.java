package Servlet;

import Controlador.ActividadDAO;
import Controlador.DisponibilidadDAO;
import Controlador.Estado_reservaDAO;
import Controlador.HorariosDAO;
import Controlador.RolesDAO;
import Controlador.Lista_preciosDAO;
import Controlador.PagosDAO;
import Controlador.PerritoDAO;
import Controlador.ReservaDAO;
import Controlador.Solicitud_adopcionDAO;
import Controlador.Tipo_ActividadDAO;
import Controlador.Tipo_documentoDAO;
import Controlador.UsuariosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "PapeleraAdmi", urlPatterns = {"/PapeleraAdmi"})
public class PapeleraAdmi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("estadosReservaInactivos", new Estado_reservaDAO().listarInactivos());
        request.setAttribute("horariosInactivos", new HorariosDAO().listarInactivos());
        request.setAttribute("rolesInactivos", new RolesDAO().listarInactivos());
        request.setAttribute("listaActividadesInactivas", new ActividadDAO().listarInactivas());
        request.setAttribute("listaDisponibilidadesInactivas",new DisponibilidadDAO().listarInactivas());
        request.setAttribute("listaPreciosInactivos", new Lista_preciosDAO().listarInactivas());
        request.setAttribute("listaTiposActividadInactivos", new Tipo_ActividadDAO().listarInactivas());
        request.setAttribute("listaTiposDocumentoInactivos", new Tipo_documentoDAO().listarInactivos());
        request.setAttribute("listaPagosInactivos", new PagosDAO().listarInactivos());
        request.setAttribute("listaPerritosInactivos", new PerritoDAO().listarInactivos());
        request.setAttribute("listaReservasInactivas", new ReservaDAO().listarInactivas());
        request.setAttribute("listaSolicitudesInactivas", new Solicitud_adopcionDAO().listarInactivas());
        request.setAttribute("listaUsuariosInactivos", new UsuariosDAO().listarInactivos());

        request.getRequestDispatcher("/Vista/Papeleria.jsp").forward(request, response);
    }
}
