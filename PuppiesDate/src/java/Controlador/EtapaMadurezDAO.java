/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.EtapaMadurez;
import java.util.ArrayList;
import java.util.List;

public class EtapaMadurezDAO {
    Conexion conexion = new Conexion();

    public List<EtapaMadurez> listarEtapa_madurez() {
        List<EtapaMadurez> lista = new ArrayList<>();
        String sql = "SELECT idEtapa_madurez, descripcion FROM etapa_madurez WHERE activo = 1";
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EtapaMadurez etapa = new EtapaMadurez();
                etapa.setIdEtapa_madurez(rs.getInt("idEtapa_madurez"));
                etapa.setDescripcion(rs.getString("descripcion"));
                lista.add(etapa);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Etapa_madurez: " + e.getMessage());
        }
        return lista;
    }
}