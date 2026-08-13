package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    static {
        // Fuerza que TODO el JVM (contenedor de Railway) piense en hora de Bogotá,
        // así no hay desfase entre la zona del contenedor (normalmente UTC) y la
        // zona que el driver de MySQL usa para convertir fechas/horas.
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("America/Bogota"));
    }

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER").trim() : null;
    private String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD").trim() : null;
    private String baseDatos = System.getenv("DB_DATABASE") != null ? System.getenv("DB_DATABASE").trim() : null;
    private String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST").trim() : null;
    private String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT").trim() : null;

    // Aiven exige conexión SSL/TLS obligatoria (a diferencia de Railway).
    // useSSL=true + requireSSL=true cifra la conexión; es el par correcto para
    // mysql-connector-java 8.0.12 (el parámetro "sslMode" solo existe desde 8.0.13).
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + baseDatos
            + "?serverTimezone=America/Bogota&useSSL=true&requireSSL=true&allowPublicKeyRetrieval=true";

    // Ya no abrimos conexión aquí, solo dejamos todo listo para cuando la pidan
    public Conexion() {
    }

    // Cada llamada a getConn() crea y devuelve una conexión NUEVA
    public Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión Establecida");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return conn;
    }
}