/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Municipio;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {
    Conexion conexion = new Conexion();

    // Para llenar el <select> de Municipio en el formulario de solicitud de adopción
    public List<Municipio> listarPorDepartamento(int idDepartamento) {
        List<Municipio> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idmunicipio, nombre FROM municipios "
                       + "WHERE departamento_iddepartamento = ? AND activo = 1 ORDER BY nombre";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idDepartamento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Municipio m = new Municipio();
                m.setIdMunicipio(rs.getInt("idmunicipio"));
                m.setNombre(rs.getString("nombre"));
                m.setDepartamento_idDepartamento(idDepartamento);
                m.setActivo(true);
                lista.add(m);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Municipios: " + e.getMessage());
        }
        return lista;
    }
}
