/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
    private Connection conn;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String user = "root";
    private String password = "";
    private String baseDatos = "PuppiesBd";
    private String url = "jdbc:mysql://localhost:3308/" + baseDatos + "?useTimezone=true&serverTimezone=America/Bogota&useSSL=false&allowPublicKeyRetrieval=true";
    private String PuppiesBd;

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
