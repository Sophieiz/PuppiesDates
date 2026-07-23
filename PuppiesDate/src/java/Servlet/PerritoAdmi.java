package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

                if (dao.existeMicrochip(microchip)) {
                    request.setAttribute("mensaje", "Ya existe un perrito registrado con ese microchip.");
                } else {
                    Perrito perrito = armarPerrito(request, 0);
                    boolean ok = dao.insertarPerrito(perrito);
                    if (ok) {
                        request.setAttribute("mensaje", "Perrito registrado correctamente.");
                    } else {
                        request.setAttribute("mensaje", "Error al registrar el perrito: " + dao.getUltimoError());
                    }
                }

            } else if ("actualizar".equalsIgnoreCase(accion)) {

                int id = Integer.parseInt(request.getParameter("idPerrito"));
                String microchip = request.getParameter("microchip");

                if (dao.existeMicrochipEnOtroPerrito(microchip, id)) {
                    request.setAttribute("mensaje", "Ese microchip ya pertenece a otro perrito.");
                } else {
                    Perrito perrito = armarPerrito(request, id);
                    boolean ok = dao.actualizarPerrito(perrito);
                    request.setAttribute("mensaje", ok ? "Perrito actualizado correctamente." : "Error al actualizar el perrito.");
                }

            } else if ("eliminar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idPerrito"));
                boolean ok = dao.eliminarPerrito(id);
                request.setAttribute("mensaje", ok ? "Perrito eliminado correctamente." : "Error al eliminar el perrito.");
            } else if ("reactivar".equalsIgnoreCase(accion)) {
                int id = Integer.parseInt(request.getParameter("idPerrito"));
                boolean ok = dao.reactivarPerrito(id);
                request.setAttribute("mensaje", ok ? "Perrito reactivado correctamente." : "Error al reactivar el perrito.");
            }

            cargarListaYForward(request, response, dao);

        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "Datos inválidos en el formulario.");
            cargarListaYForward(request, response, dao);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PerritoDAO dao = new PerritoDAO();
        cargarListaYForward(request, response, dao);
    }

    private Perrito armarPerrito(HttpServletRequest request, int idPerrito) throws ServletException, IOException {
        Perrito perrito = new Perrito();
        if (idPerrito > 0) {
            perrito.setIdPerrito(idPerrito);
        }
        perrito.setNombre(request.getParameter("nombre"));
        perrito.setEspecie(request.getParameter("especie"));
        perrito.setRaza(request.getParameter("raza"));

        String fechaNac = request.getParameter("fecha_nacimiento");
        if (fechaNac != null && !fechaNac.trim().isEmpty()) {
            perrito.setFecha_nacimiento(Date.valueOf(fechaNac));
        }

        perrito.setSexo(request.getParameter("sexo"));
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

            // Carpeta de subida: configurable por variable de entorno UPLOADS_DIR
            // (en Railway, esta debe apuntar a la ruta donde montas el Volume, ej: /data/uploads).
            // Si no está definida (ej. desarrollo local), usa una carpeta fija en el home del usuario.
            String uploadPath = obtenerCarpetaUploads();
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File fileToSave = new File(uploadPath + File.separator + nuevoNombre);
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            rutaFotoFinal = "uploads/" + nuevoNombre;
        }

        perrito.setFoto(rutaFotoFinal);
        perrito.setCiudad(request.getParameter("ciudad"));
        
        String idEstado = request.getParameter("Estado_perrito_idEstado_perrito");
        if (idEstado != null && !idEstado.isEmpty()) {
            perrito.setEstado_perrito_idEstado_perrito(Integer.parseInt(idEstado));
        }
        
        return perrito;
    }

    /**
     * Resuelve la carpeta donde se guardan las fotos de los perritos.
     * - En Railway: define la variable de entorno UPLOADS_DIR apuntando a la ruta
     *   donde montaste el Volume (ej: /data/uploads), asi las fotos sobreviven a cada deploy.
     * - En local (sin la variable definida): usa una carpeta fija en el home del usuario,
     *   para que no se borre al recompilar.
     */
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
        request.setAttribute("listaSexos", new Controlador.Sexo_perritoDAO().listarSexo_perrito());
        request.getRequestDispatcher("/Vista/PerritoAdmi.jsp").forward(request, response);
    }
}