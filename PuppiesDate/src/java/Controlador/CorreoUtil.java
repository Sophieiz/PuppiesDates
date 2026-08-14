package Controlador;

import Modelo.Perrito;
import Modelo.Solicitud_adopcion;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class CorreoUtil {

    private static final String CORREO_REMITENTE = "puppiesdates@gmail.com";
    private static final String NOMBRE_REMITENTE = "Puppies Dates";
    private static final String BREVO_API_KEY = System.getenv("BREVO_API_KEY");
    private static final String BREVO_URL = "https://api.brevo.com/v3/smtp/email";
    private static final String CORREO_FUNDACION = "puppiesdates@gmail.com";

    // Colores de marca (coinciden con las tarjetas del sitio: rosa / azul / mostaza)
    private static final String COLOR_ROSA = "#ff8fab";
    private static final String COLOR_AZUL = "#6fb3e0";
    private static final String COLOR_MOSTAZA = "#f2b134";
    private static final String COLOR_TEXTO = "#3a3a3a";
    private static final String COLOR_FONDO = "#fff8f3";

    // ==========================================================
    //  INFRAESTRUCTURA COMÚN DE ENVÍO (API HTTP de Brevo) Y PLANTILLA HTML
    // ==========================================================
    private static boolean enviar(String destino, String asunto, String cuerpoHtml, String logTag) {
        try {
            String json = "{"
                    + "\"sender\":{\"name\":\"" + jsonEscapar(NOMBRE_REMITENTE) + "\",\"email\":\"" + jsonEscapar(CORREO_REMITENTE) + "\"},"
                    + "\"to\":[{\"email\":\"" + jsonEscapar(limpiar(destino)) + "\"}],"
                    + "\"subject\":\"" + jsonEscapar(asunto) + "\","
                    + "\"htmlContent\":\"" + jsonEscapar(cuerpoHtml) + "\""
                    + "}";

            HttpClient cliente = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15))
                    .build();

            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(BREVO_URL))
                    .timeout(Duration.ofSeconds(15))
                    .header("accept", "application/json")
                    .header("api-key", BREVO_API_KEY)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() >= 200 && respuesta.statusCode() < 300) {
                System.out.println("Correo (" + logTag + ") enviado con éxito.");
                return true;
            } else {
                System.out.println("Error al enviar correo (" + logTag + "): HTTP "
                        + respuesta.statusCode() + " - " + respuesta.body());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al enviar correo (" + logTag + "): " + e.getMessage());
            return false;
        }
    }

    /**
     * Plantilla base con la identidad visual de Puppies Dates. Se reutiliza en
     * TODOS los correos para que siempre salgan decorados igual.
     */
    private static String plantilla(String colorBanda, String titulo, String cuerpoHtml) {
        return "<!DOCTYPE html>"
                + "<html lang=\"es\"><head><meta charset=\"UTF-8\"></head>"
                + "<body style=\"margin:0;padding:0;background-color:#f0ede8;font-family:'Trebuchet MS',Verdana,Arial,sans-serif;\">"
                + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#f0ede8;padding:24px 0;\">"
                + "<tr><td align=\"center\">"
                + "<table role=\"presentation\" width=\"100%\" style=\"max-width:520px;background-color:" + COLOR_FONDO + ";border-radius:18px;overflow:hidden;box-shadow:0 4px 14px rgba(0,0,0,0.08);\" cellpadding=\"0\" cellspacing=\"0\">"
                + "<tr><td style=\"background-color:" + colorBanda + ";padding:22px 28px;text-align:center;\">"
                + "<h1 style=\"margin:0;color:#ffffff;font-size:20px;font-family:'Trebuchet MS',Verdana,Arial,sans-serif;letter-spacing:0.5px;\">" + titulo + "</h1>"
                + "</td></tr>"
                + "<tr><td style=\"padding:26px 28px;color:" + COLOR_TEXTO + ";font-size:15px;line-height:1.55;\">"
                + cuerpoHtml
                + "</td></tr>"
                + "<tr><td style=\"background-color:#efe6dd;padding:16px 28px;text-align:center;\">"
                + "<p style=\"margin:0;font-size:12px;color:#8a8a8a;\">Puppies Dates &middot; Rescatando y conectando corazones peludos</p>"
                + "</td></tr>"
                + "</table></td></tr></table></body></html>";
    }

    private static String filaDato(String etiqueta, String valor) {
        return "<tr>"
                + "<td style=\"padding:6px 10px;color:#888;font-size:13px;white-space:nowrap;\">" + etiqueta + "</td>"
                + "<td style=\"padding:6px 10px;font-weight:bold;\">" + valor + "</td>"
                + "</tr>";
    }

    // ==========================================================
    //  CORREOS PARA LA FUNDACIÓN (ADMINISTRADOR)
    // ==========================================================
    public static boolean enviarCorreoNuevaSolicitud(Solicitud_adopcion solicitud, Perrito perrito) {
        String nombreUsuario = escapar(solicitud.getNombreUsuario() + " " + solicitud.getApellidoUsuario());
        String documentoUsuario = escapar(solicitud.getDocumentoUsuario());
        String correoUsuario = escapar(solicitud.getCorreoUsuario());
        String nombrePerrito = escapar(perrito.getNombre());
        String direccion = escapar(solicitud.getDireccion());
        String departamento = escapar(solicitud.getNombreDepartamento());
        String ubicacion = escapar(solicitud.getNombreUbicacion());
        String barrio = escapar(solicitud.getBarrio());
        String profesion = escapar(solicitud.getProfesion());
        String viveEn = escapar(solicitud.getDescripcionViveEn());
        String tipoVivienda = escapar(solicitud.getDescripcionTipoVivienda());
        String nucleoFamiliar = escapar(solicitud.getNucleo_familiar());
        String tieneMascotas = solicitud.isTiene_mascotas() ? "Sí" : "No";

        String asunto = "Nueva solicitud de adopción - " + limpiar(perrito.getNombre());

        String cuerpo = "<p>Ha llegado una nueva solicitud de adopción para <strong>" + nombrePerrito + "</strong>.</p>"
                + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:12px;background-color:#ffffff;border-radius:10px;\">"
                + filaDato("Nombre", nombreUsuario)
                + filaDato("Documento", documentoUsuario)
                + filaDato("Correo", correoUsuario)
                + filaDato("Dirección", direccion)
                + filaDato("Departamento", departamento)
                + filaDato("Municipio/Localidad", ubicacion)
                + filaDato("Barrio", barrio)
                + filaDato("Profesión", profesion)
                + filaDato("Vive en", viveEn)
                + filaDato("Tipo de vivienda", tipoVivienda)
                + filaDato("Núcleo familiar", nucleoFamiliar)
                + filaDato("¿Tiene mascotas?", tieneMascotas)
                + "</table>"
                + "<p style=\"margin-top:18px;\">Ingresa al panel de administrador para revisar la solicitud completa.</p>";

        String html = plantilla(COLOR_ROSA, "Nueva solicitud de adopción", cuerpo);
        return enviar(CORREO_FUNDACION, asunto, html, "nueva solicitud - admin");
    }

    public static boolean enviarCorreoNuevaReserva(String nombreUsuario, String correoUsuario,
            String nombreActividad, String fecha, String hora, int numPersonas) {
        String nombre = escapar(nombreUsuario);
        String correo = escapar(correoUsuario);
        String actividad = escapar(nombreActividad);

        String asunto = "Nueva reserva - " + limpiar(nombreActividad);
        String cuerpo = "<p>Ha llegado una nueva reserva.</p>"
                + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:12px;background-color:#ffffff;border-radius:10px;\">"
                + filaDato("Actividad", actividad)
                + filaDato("Fecha", escapar(fecha))
                + filaDato("Hora", escapar(hora))
                + filaDato("Personas", String.valueOf(numPersonas))
                + filaDato("Solicitante", nombre)
                + filaDato("Correo", correo)
                + "</table>"
                + "<p style=\"margin-top:18px;\">Ingresa al panel de administrador para ver el detalle completo.</p>";

        String html = plantilla(COLOR_MOSTAZA, "Nueva reserva recibida", cuerpo);
        return enviar(CORREO_FUNDACION, asunto, html, "nueva reserva - admin");
    }

    // ==========================================================
    //  CORREOS PARA EL USUARIO
    // ==========================================================
    public static boolean enviarCorreoCambioEstado(String correoDestino, String nombrePerrito, String nuevoEstado) {
        String perrito = escapar(nombrePerrito);
        String estado = escapar(nuevoEstado);

        String asunto = "Actualización de tu solicitud de adopción - " + limpiar(nombrePerrito);
        String cuerpo = "<p>Hola,</p>"
                + "<p>El estado de tu solicitud de adopción para <strong>" + perrito + "</strong> ha cambiado a:</p>"
                + "<div style=\"background-color:#ffffff;border-radius:10px;padding:14px 18px;margin:14px 0;border-left:5px solid " + COLOR_AZUL + ";text-align:center;\">"
                + "<strong style=\"font-size:17px;\">" + estado + "</strong>"
                + "</div>"
                + "<p>Puedes ingresar a la página para ver el detalle.</p>";

        String html = plantilla(COLOR_AZUL, "Actualización de tu solicitud", cuerpo);
        return enviar(correoDestino, asunto, html, "cambio de estado");
    }

    public static boolean enviarCorreoRecuperacion(String correoDestino, String nombreUsuario, String linkRestablecer) {
        String nombre = escapar(nombreUsuario);

        String asunto = "Recupera tu contraseña - Puppies Dates";
        String cuerpo = "<p>Hola <strong>" + nombre + "</strong>,</p>"
                + "<p>Recibimos una solicitud para restablecer tu contraseña. "
                + "Haz clic en el siguiente botón para crear una nueva (válido por 30 minutos):</p>"
                + "<p style=\"text-align:center;margin:22px 0;\">"
                + "<a href=\"" + linkRestablecer + "\" style=\"background-color:" + COLOR_AZUL + ";color:#ffffff;text-decoration:none;padding:12px 26px;border-radius:24px;font-weight:bold;display:inline-block;\">Restablecer contraseña</a>"
                + "</p>"
                + "<p style=\"font-size:12px;color:#888;\">Si el botón no funciona, copia y pega este enlace en tu navegador:<br>" + linkRestablecer + "</p>"
                + "<p style=\"margin-top:18px;\">Si tú no solicitaste esto, puedes ignorar este correo.</p>";

        String html = plantilla(COLOR_AZUL, "Recupera tu contraseña", cuerpo);
        return enviar(correoDestino, asunto, html, "recuperacion de clave");
    }

    public static boolean enviarCorreoConfirmacionSolicitudUsuario(Solicitud_adopcion solicitud, Perrito perrito) {
        String correoUsuario = solicitud.getCorreoUsuario();
        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {
            return false;
        }
        String nombreUsuario = escapar(solicitud.getNombreUsuario());
        String nombrePerrito = escapar(perrito.getNombre());

        String asunto = "Recibimos tu solicitud de adopción - " + limpiar(perrito.getNombre());

        String cuerpo = "<p>Hola <strong>" + nombreUsuario + "</strong>,</p>"
                + "<p>¡Gracias por dar el primer paso para darle un hogar a <strong>" + nombrePerrito + "</strong>! "
                + "Tu solicitud de adopción fue <strong>enviada correctamente</strong> y ya está en manos de nuestro equipo.</p>"
                + "<div style=\"background-color:#ffffff;border-radius:10px;padding:14px 18px;margin-top:14px;border-left:5px solid " + COLOR_ROSA + ";\">"
                + "<p style=\"margin:0;\">Estado actual: <strong>En revisión</strong></p>"
                + "</div>"
                + "<p style=\"margin-top:18px;\">Nuestro equipo revisará tu información y se pondrá en contacto contigo muy pronto. "
                + "Puedes consultar el estado de tu solicitud desde la sección <em>Mis Solicitudes</em> en tu cuenta.</p>"
                + "<p style=\"margin-top:18px;\">¡Gracias por querer cambiarle la vida a uno de nuestros peluditos!</p>";

        String html = plantilla(COLOR_ROSA, "¡Solicitud enviada con éxito!", cuerpo);
        return enviar(correoUsuario, asunto, html, "confirmacion solicitud - usuario");
    }

    public static boolean enviarCorreoConfirmacionReservaUsuario(String nombreUsuario, String correoUsuario,
            String nombreActividad, String fecha, String hora, int numPersonas) {
        if (correoUsuario == null || correoUsuario.trim().isEmpty()) {
            return false;
        }
        String nombre = escapar(nombreUsuario);
        String actividad = escapar(nombreActividad);
        String fechaEsc = escapar(fecha);
        String horaEsc = escapar(hora);

        String asunto = "Tu ticket de reserva - " + limpiar(nombreActividad);

        String cuerpo = "<p>Hola <strong>" + nombre + "</strong>,</p>"
                + "<p>¡Tu reserva fue confirmada! Aquí está tu ticket para presentar el día de tu actividad:</p>"
                + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" "
                + "style=\"margin-top:16px;background-color:#ffffff;border-radius:14px;border:2px dashed " + COLOR_MOSTAZA + ";\">"
                + "<tr><td style=\"padding:18px 20px;\">"
                + "<p style=\"margin:0 0 10px;text-align:center;font-size:13px;letter-spacing:2px;color:#b08b1b;text-transform:uppercase;\">Ticket de reserva &middot; Puppies Dates</p>"
                + "<table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">"
                + filaDato("Actividad", actividad)
                + filaDato("Fecha", fechaEsc)
                + filaDato("Hora", horaEsc)
                + filaDato("Personas", String.valueOf(numPersonas))
                + "</table>"
                + "</td></tr>"
                + "</table>"
                + "<p style=\"margin-top:18px;\">Te esperamos con muchas colas felices moviéndose. Si necesitas cambiar o cancelar tu reserva, ingresa a <em>Mis Reservas</em> en tu cuenta.</p>";

        String html = plantilla(COLOR_MOSTAZA, "¡Reserva confirmada!", cuerpo);
        return enviar(correoUsuario, asunto, html, "confirmacion reserva - usuario");
    }

    private static String limpiar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replaceAll("[\r\n]", " ").trim();
    }

    private static String escapar(String valor) {
        String limpio = limpiar(valor);
        return limpio.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    // Escapa comillas, backslashes y saltos de línea para que el texto sea válido dentro de un JSON
    private static String jsonEscapar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }
}