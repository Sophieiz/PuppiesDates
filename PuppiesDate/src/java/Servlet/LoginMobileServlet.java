/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

import Controlador.Conexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/api/login")
public class LoginMobileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");

        if (correo == null || clave == null || correo.trim().isEmpty() || clave.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,\"mensaje\":\"Correo y clave son obligatorios\"}");
            }
            return;
        }

        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        String sql = "SELECT idUsuarios, nombre, apellido FROM usuarios "
                + "WHERE correo = ? AND clave = ? AND activo = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("idUsuarios");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":true,"
                            + "\"idUsuario\":" + idUsuario + ","
                            + "\"nombre\":\"" + escapar(nombre) + "\","
                            + "\"apellido\":\"" + escapar(apellido) + "\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"success\":false,\"mensaje\":\"Correo o clave incorrectos\"}");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\":false,\"mensaje\":\"Error del servidor\"}");
            }
            System.out.println("Error en login mobile: " + e.getMessage());
        }
    }

    private String escapar(String valor) {
        if (valor == null) return "";
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}