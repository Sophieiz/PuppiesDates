/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Raza;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO {

    Conexion conexion = new Conexion();

    // Trae todas las razas activas, sin importar la especie
    public List<Raza> listarRaza() {
        List<Raza> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idRaza, nombre, Especie_idEspecie FROM raza WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearRaza(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar Raza: " + e.getMessage());
        }
        return lista;
    }

    // Util para cuando el formulario filtra razas dependiendo de la especie elegida (ej. con AJAX)
    public List<Raza> listarRazaPorEspecie(int idEspecie) {
        List<Raza> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idRaza, nombre, Especie_idEspecie FROM raza WHERE activo = 1 AND Especie_idEspecie = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEspecie);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearRaza(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar Raza por especie: " + e.getMessage());
        }
        return lista;
    }

    private Raza mapearRaza(ResultSet rs) throws java.sql.SQLException {
        Raza raza = new Raza();
        raza.setIdRaza(rs.getInt("idRaza"));
        raza.setNombre(rs.getString("nombre"));
        raza.setEspecie_idEspecie(rs.getInt("Especie_idEspecie"));
        return raza;
    }
}