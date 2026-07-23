package Controlador;
import java.sql.Connection;
import java.sql.DriverManager;
public class Conexion {
    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String user = System.getenv("DB_USER") != null ? System.getenv("DB_USER").trim() : null;
    private String password = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD").trim() : null;
    private String baseDatos = System.getenv("DB_DATABASE") != null ? System.getenv("DB_DATABASE").trim() : null;
    private String host = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST").trim() : null;
    private String port = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT").trim() : null;
    private String url = "jdbc:mysql://" + host + ":" + port + "/" + baseDatos + "?useTimezone=true&serverTimezone=America/Bogota&useSSL=false&allowPublicKeyRetrieval=true";
     public Conexion() {
    conn = null;
    try {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
        if (conn == null) {
            System.out.println("No se estableció la conexion" + "\n" + url);
        } else {
            System.out.println("Conexión Establecida ");
        }
    } catch (Exception ex) {
        System.err.println(ex.getMessage());
    }
}
public Connection getConn() {
    return conn;
}
    
}