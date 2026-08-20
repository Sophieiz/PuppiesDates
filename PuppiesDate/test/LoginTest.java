import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {

    private static final String LOGIN_URL =
            "https://puppiesdates.onrender.com/Iniciar";

    @Test
    public void loginCorreoInexistente() throws Exception {

        String correo = "correoquenoexiste999@gmail.com";
        String password = "ClavePrueba123";

        String parametros =
                "correo=" + URLEncoder.encode(correo, StandardCharsets.UTF_8)
                + "&pass=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

        URL url = new URL(LOGIN_URL);

        HttpURLConnection conexion =
                (HttpURLConnection) url.openConnection();

        conexion.setRequestMethod("POST");
        conexion.setDoOutput(true);
        conexion.setInstanceFollowRedirects(false);

        conexion.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
        );

        try (OutputStream salida = conexion.getOutputStream()) {
            salida.write(parametros.getBytes(StandardCharsets.UTF_8));
        }

        int codigo = conexion.getResponseCode();

        InputStream flujo = codigo >= 400
                ? conexion.getErrorStream()
                : conexion.getInputStream();

        String respuesta = "";

        if (flujo != null) {
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(flujo, StandardCharsets.UTF_8))) {

                String linea;

                while ((linea = lector.readLine()) != null) {
                    respuesta += linea;
                }
            }
        }

        Assert.assertEquals(codigo, 200);

        Assert.assertTrue(
                respuesta.contains("El correo no existe"),
                "No apareció el mensaje esperado."
        );

        conexion.disconnect();
    }

    @Test
    public void loginClaveIncorrecta() throws Exception {

        String correo = "ortegaobandoisabelsofia@gmail.com";
        String password = "ClaveIncorrecta123";

        String parametros =
                "correo=" + URLEncoder.encode(correo, StandardCharsets.UTF_8)
                + "&pass=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

        URL url = new URL(LOGIN_URL);

        HttpURLConnection conexion =
                (HttpURLConnection) url.openConnection();

        conexion.setRequestMethod("POST");
        conexion.setDoOutput(true);
        conexion.setInstanceFollowRedirects(false);

        conexion.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
        );

        try (OutputStream salida = conexion.getOutputStream()) {
            salida.write(parametros.getBytes(StandardCharsets.UTF_8));
        }

        int codigo = conexion.getResponseCode();

        InputStream flujo = codigo >= 400
                ? conexion.getErrorStream()
                : conexion.getInputStream();

        String respuesta = "";

        if (flujo != null) {
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(flujo, StandardCharsets.UTF_8))) {

                String linea;

                while ((linea = lector.readLine()) != null) {
                    respuesta += linea;
                }
            }
        }

        Assert.assertEquals(codigo, 200);

        Assert.assertTrue(
                respuesta.contains("Clave incorrecta"),
                "No apareció el mensaje de contraseña incorrecta."
        );

        conexion.disconnect();
    }

    @Test
    public void loginExitoso() throws Exception {

        String correo = "ortegaobandoisabelsofia@gmail.com";
        String password = "123456";

        String parametros =
                "correo=" + URLEncoder.encode(correo, StandardCharsets.UTF_8)
                + "&pass=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

        URL url = new URL(LOGIN_URL);

        HttpURLConnection conexion =
                (HttpURLConnection) url.openConnection();

        conexion.setRequestMethod("POST");
        conexion.setDoOutput(true);
        conexion.setInstanceFollowRedirects(false);

        conexion.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
        );

        try (OutputStream salida = conexion.getOutputStream()) {
            salida.write(parametros.getBytes(StandardCharsets.UTF_8));
        }

        int codigo = conexion.getResponseCode();

        // IMPORTANTE: el login exitoso ahora usa forward() en vez de sendRedirect()
        // (para que la URL /PanelAdmin.jsp o /PanelUsuario.jsp no se vea en el navegador),
        // así que la respuesta ya viene con el panel cargado directamente, con código 200,
        // sin encabezado "Location" ni redirección 302.
        InputStream flujo = codigo >= 400
                ? conexion.getErrorStream()
                : conexion.getInputStream();

        String respuesta = "";

        if (flujo != null) {
            try (BufferedReader lector = new BufferedReader(
                    new InputStreamReader(flujo, StandardCharsets.UTF_8))) {

                String linea;

                while ((linea = lector.readLine()) != null) {
                    respuesta += linea;
                }
            }
        }

        System.out.println("Código HTTP: " + codigo);

        Assert.assertEquals(
                codigo,
                200,
                "El login correcto no devolvió 200 (ya no hay redirección, se usa forward)."
        );

        Assert.assertFalse(
                respuesta.contains("Clave incorrecta") || respuesta.contains("El correo no existe"),
                "El login no fue exitoso, apareció un mensaje de error."
        );

        conexion.disconnect();
    }
}