/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Especie;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO {

    Conexion conexion = new Conexion();

    public List<Especie> listarEspecie() {
        List<Especie> lista = new ArrayList<>();
        String sql = "SELECT idEspecie, descripcion FROM especie WHERE activo = 1";


        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Especie especie = new Especie();
                especie.setIdEspecie(rs.getInt("idEspecie"));
                especie.setDescripcion(rs.getString("descripcion"));
                lista.add(especie);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Especie: " + e.getMessage());
        }
        return lista;
    }
}