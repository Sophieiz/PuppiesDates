/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Departamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    public List<Departamento> listarActivos() {
        List<Departamento> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        String sql = "SELECT idDepartamento, nombre, tipo_division FROM departamentos "
                + "WHERE activo = 1 ORDER BY nombre";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Departamento d = new Departamento();
                d.setIdDepartamento(rs.getInt("idDepartamento"));
                d.setNombre(rs.getString("nombre"));
                d.setTipoDivision(rs.getString("tipo_division"));
                lista.add(d);
            }
        } catch (Exception e) {
            System.out.println("Error al listar departamentos: " + e.getMessage());
        }
        return lista;
    }
}