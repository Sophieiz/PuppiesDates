/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CloudinaryUploader {

    private static final String CLOUD_NAME = "ck03dztt";
    private static final String UPLOAD_PRESET = "puppiesdate_unsigned";

    private static final String UPLOAD_URL
            = "https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/image/upload";

    private static final String BOUNDARY = "----PuppiesDateBoundary";

    public static String subirImagen(InputStream imagen, String nombreArchivo) throws IOException {
        URL url = new URL(UPLOAD_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        try (OutputStream out = conn.getOutputStream()) {
            escribirCampoTexto(out, "upload_preset", UPLOAD_PRESET);

            out.write(("--" + BOUNDARY + "\r\n").getBytes(StandardCharsets.UTF_8));
            out.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + nombreArchivo + "\"\r\n")
                    .getBytes(StandardCharsets.UTF_8));
            out.write("Content-Type: application/octet-stream\r\n\r\n".getBytes(StandardCharsets.UTF_8));

            byte[] buffer = new byte[4096];
            int bytesLeidos;
            while ((bytesLeidos = imagen.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLeidos);
            }
            out.write("\r\n".getBytes(StandardCharsets.UTF_8));

            out.write(("--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8));
        }

        int status = conn.getResponseCode();
        InputStream respuestaStream = (status >= 200 && status < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        String respuesta = new String(respuestaStream.readAllBytes(), StandardCharsets.UTF_8);

        if (status < 200 || status >= 300) {
            throw new IOException("Error al subir a Cloudinary (HTTP " + status + "): " + respuesta);
        }

        String marcador = "\"secure_url\":\"";
        int inicio = respuesta.indexOf(marcador);
        if (inicio == -1) {
            throw new IOException("No se encontró secure_url en la respuesta de Cloudinary: " + respuesta);
        }
        inicio += marcador.length();
        int fin = respuesta.indexOf("\"", inicio);
        return respuesta.substring(inicio, fin).replace("\\/", "/");
    }

    private static void escribirCampoTexto(OutputStream out, String nombre, String valor) throws IOException {
        out.write(("--" + BOUNDARY + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Disposition: form-data; name=\"" + nombre + "\"\r\n\r\n")
                .getBytes(StandardCharsets.UTF_8));
        out.write((valor + "\r\n").getBytes(StandardCharsets.UTF_8));
    }
}
