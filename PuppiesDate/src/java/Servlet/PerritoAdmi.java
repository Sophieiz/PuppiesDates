package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.List;

import Modelo.Perrito;
import Modelo.Estado_perrito;
import Controlador.PerritoDAO;
import Controlador.Estado_perritoDAO;

@WebServlet("/PerritoAdmi")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class PerritoAdmi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        PerritoDAO dao = new PerritoDAO();

        try {
            if ("insertar".equalsIgnoreCase(accion)) {

                String microchip = request.getParameter("microchip");
                Part filePart = request.getPart("fotoArchivo");
                if (filePart == null || filePart.getSize() == 0) {
                    request.getSession().setAttribute("mensajeFlash", "Debes subir una foto de la Mascota.");
                    response.sendRedirect(request.getContextPath() + "/PerritoAdmi");
                    return;
                }

                if (dao.existeMicrochip(microchip)) {
                    request.getSession().setAttribute("mensajeFlash", "Ya existe una mascota registrado con ese microchip.");
                } else {
                    Perrito perrito = armarPerrito(request, 0);
                    boolean ok = dao.insertarPerrito(perrito);
                    if (ok) {
                        request.getSession().setAttribute("mensajeFlash", "Mascota registrado correctamente.");
                    } else {
                        request.getSession().setAttribute("mensajeFlash", "Error al registrar la mascota: " + dao.getUltimoError());
                    }
                }

            } else if ("actualizar".equalsIgnoreCase(accion)) {

                int id = Integer.parseInt(request.getParameter("idPerrito"));
                String microchip = request.getParameter("microchip");

                if (dao.existeMicrochipEnOtroPerrito(microchip, id)) {
                    request.getSession().setAttribute("mensajeFlash", "Ese microchip ya pertenece a otra mascota.");
                } else {
                    Perrito perrito = armarPerrito(request, id);
                    boolean ok = dao.actualizarPerrito(perrito);
                    request.getSession().setAttribute("mensajeFlash", ok ? "Mascota actualizado correctamente." : "Error al actualizar el Mascota.");
                }

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idPerrito"));
                boolean ok = dao.eliminarPerrito(id);
                request.getSession().setAttribute("mensajeFlash", ok ? "Mascota eliminado correctamente." : "Error al eliminar el Mascota.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idPerrito"));
                boolean ok = dao.reactivarPerrito(id);
                request.getSession().setAttribute("mensajeFlash", ok ? "Mascota reactivado correctamente." : "Error al reactivar el Mascota.");
            }

            response.sendRedirect(request.getContextPath() + "/PerritoAdmi");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensajeFlash", "Datos inválidos en el formulario.");
            response.sendRedirect(request.getContextPath() + "/PerritoAdmi");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerritoDAO dao = new PerritoDAO();

        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("mensajeFlash") != null) {
            request.setAttribute("mensaje", sesion.getAttribute("mensajeFlash"));
            sesion.removeAttribute("mensajeFlash");
        }

        cargarListaYForward(request, response, dao);
    }

    private Perrito armarPerrito(HttpServletRequest request, int idPerrito) throws ServletException, IOException {
        Perrito perrito = new Perrito();
        if (idPerrito > 0) {
            perrito.setIdPerrito(idPerrito);
        }
        perrito.setNombre(request.getParameter("nombre"));
        perrito.setNombre(request.getParameter("nombre"));
        perrito.setEspecie_idEspecie(Integer.parseInt(request.getParameter("especie")));
        perrito.setRaza_idRaza(Integer.parseInt(request.getParameter("raza")));

        String fechaNac = request.getParameter("fecha_nacimiento");
        if (fechaNac != null && !fechaNac.trim().isEmpty()) {
            perrito.setFecha_nacimiento(Date.valueOf(fechaNac));
        }

        perrito.setSexo_perrito_idSexo_perrito(Integer.parseInt(request.getParameter("sexo")));
        perrito.setMicrochip(request.getParameter("microchip"));
        perrito.setEtapa_madurez(request.getParameter("etapa_madurez"));
        perrito.setEspecialidad(request.getParameter("especialidad"));
        perrito.setCondiciones_especiales(request.getParameter("condiciones_especiales"));
        perrito.setTitulo_historia(request.getParameter("titulo_historia"));
        perrito.setHistoria(request.getParameter("historia"));

        Part filePart = request.getPart("fotoArchivo");
        String rutaFotoFinal = request.getParameter("foto");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String extension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : ".jpg";
            String nuevoNombre = "perrito_" + System.currentTimeMillis() + extension;

            try (InputStream input = filePart.getInputStream()) {
                rutaFotoFinal = Controlador.CloudinaryUploader.subirImagen(input, nuevoNombre);
            }
        }
        perrito.setFoto(rutaFotoFinal);

        String idEstado = request.getParameter("Estado_perrito_idEstado_perrito");
        if (idEstado != null && !idEstado.isEmpty()) {
            perrito.setEstado_perrito_idEstado_perrito(Integer.parseInt(idEstado));
        }

        return perrito;
    }

    private String obtenerCarpetaUploads() {
        String desdeEntorno = System.getenv("UPLOADS_DIR");
        if (desdeEntorno != null && !desdeEntorno.trim().isEmpty()) {
            return desdeEntorno;
        }
        return System.getProperty("user.home") + File.separator
                + "PuppiesDatesUploads" + File.separator + "perritos";
    }

    private void cargarListaYForward(HttpServletRequest request, HttpServletResponse response, PerritoDAO dao)
            throws ServletException, IOException {
        List<Perrito> listaPerritos = dao.listarPerrito();
        List<Estado_perrito> listaEstados = new Estado_perritoDAO().listarEstado_perrito();
        request.setAttribute("listaPerritos", listaPerritos);
        request.setAttribute("listaEstados", listaEstados);
        request.setAttribute("listaPerritosInactivos", dao.listarInactivos());
        request.setAttribute("listaEspecies", new Controlador.EspecieDAO().listarEspecie());
        request.setAttribute("listaRazas", new Controlador.RazaDAO().listarRaza());
        request.setAttribute("listaSexos", new Controlador.Sexo_perritoDAO().listarSexo_perrito());
        request.setAttribute("listaEtapasMadurez", new Controlador.EtapaMadurezDAO().listarEtapa_madurez());
        request.getRequestDispatcher("/Vista/PerritoAdmi.jsp").forward(request, response);
    }
}
