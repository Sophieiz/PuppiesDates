/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Tipo_vivienda;
import java.util.ArrayList;
import java.util.List;

public class Tipo_viviendaDAO {

    Conexion conexion = new Conexion();

    // Para llenar el <select> del formulario de solicitud de adopción
    public List<Tipo_vivienda> listarActivos() {
        List<Tipo_vivienda> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idtipo_vivienda, descripcion FROM tipo_vivienda WHERE activo = 1 ORDER BY descripcion";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tipo_vivienda opcion = new Tipo_vivienda();
                opcion.setIdTipo_vivienda(rs.getInt("idtipo_vivienda"));
                opcion.setDescripcion(rs.getString("descripcion"));
                opcion.setActivo(true);
                lista.add(opcion);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Tipo_vivienda: " + e.getMessage());
        }
        return lista;
    }
}