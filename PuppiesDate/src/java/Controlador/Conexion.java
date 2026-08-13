package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    static {
        // Fuerza que la JVM use la zona horaria de Bogotá
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("America/Bogota"));
    }

    private String driver = "com.mysql.cj.jdbc.Driver";

    public Conexion() {
    }

    public Connection getConn() {
        Connection conn = null;
        try {
            // Leer las variables de entorno en el momento que se solicita la conexión
            String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST").trim() : "";
            String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT").trim() : "3306";
            String baseDatos = System.getenv("DB_DATABASE") != null ? System.getenv("DB_DATABASE").trim() : "";
            String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER").trim() : "";
            String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD").trim() : "";

            // Construir la URL con parámetros SSL compatibles con Aiven
            String url = "jdbc:mysql://" + host + ":" + port + "/" + baseDatos
                    + "?serverTimezone=America/Bogota"
                    + "&useSSL=true"
                    + "&requireSSL=true"
                    + "&trustServerCertificate=true"
                    + "&allowPublicKeyRetrieval=true";

            // Imprimir la URL en la consola de Render para verificar que no tenga partes nulas
            System.out.println("Intentando conectar a: " + "jdbc:mysql://" + host + ":" + port + "/" + baseDatos);

            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión Establecida con éxito a la BD de Aiven.");
            
        } catch (Exception ex) {
            System.err.println("Error al establecer la conexión con la base de datos:");
            ex.printStackTrace();
        }
        return conn;
    }
}