/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Localidad;
import java.util.ArrayList;
import java.util.List;

public class LocalidadDAO {
    Conexion conexion = new Conexion();

    // Para llenar el <select> de Localidad en el formulario de solicitud de adopción
    public List<Localidad> listarPorDepartamento(int idDepartamento) {
        List<Localidad> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idlocalidad, nombre FROM localidades "
                       + "WHERE departamento_iddepartamento = ? AND activo = 1 ORDER BY nombre";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idDepartamento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Localidad l = new Localidad();
                l.setIdLocalidad(rs.getInt("idlocalidad"));
                l.setNombre(rs.getString("nombre"));
                l.setDepartamento_idDepartamento(idDepartamento);
                l.setActivo(true);
                lista.add(l);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Localidades: " + e.getMessage());
        }
        return lista;
    }
}