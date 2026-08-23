package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;


@WebServlet(name = "ImagenPerritoServlet", urlPatterns = {"/uploads/*"})
public class ImagenPerritoServlet extends HttpServlet {

   
    private static String carpetaBase() {
        String desdeEntorno = System.getenv("UPLOADS_DIR");
        if (desdeEntorno != null && !desdeEntorno.trim().isEmpty()) {
            return desdeEntorno;
        }
        return System.getProperty("user.home") + File.separator
                + "PuppiesDatesUploads" + File.separator + "perritos";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombreArchivo = request.getPathInfo();
        if (nombreArchivo == null || nombreArchivo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Se queda solo con el nombre del archivo (evita recorrer carpetas con ../)
        String soloNombre = new File(nombreArchivo).getName();
        File archivo = new File(carpetaBase(), soloNombre);

        if (!archivo.exists() || !archivo.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String tipoContenido = getServletContext().getMimeType(archivo.getName());
        if (tipoContenido == null) {
            tipoContenido = "application/octet-stream";
        }

        response.setContentType(tipoContenido);
        response.setContentLengthLong(archivo.length());

        try (OutputStream salida = response.getOutputStream()) {
            Files.copy(archivo.toPath(), salida);
        }
    }
}
