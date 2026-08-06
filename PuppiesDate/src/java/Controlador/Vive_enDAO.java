/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelo.Vive_en;
import java.util.ArrayList;
import java.util.List;

public class Vive_enDAO {

    Conexion conexion = new Conexion();

    // Para llenar el <select> del formulario de solicitud de adopción
    public List<Vive_en> listarActivos() {
        List<Vive_en> lista = new ArrayList<>();
        String sql = "SELECT idvive_en, descripcion FROM vive_en WHERE activo = 1 ORDER BY descripcion";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vive_en opcion = new Vive_en();
                opcion.setIdVive_en(rs.getInt("idvive_en"));
                opcion.setDescripcion(rs.getString("descripcion"));
                opcion.setActivo(true);
                lista.add(opcion);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Vive_en: " + e.getMessage());
        }

        return lista;
    }
}