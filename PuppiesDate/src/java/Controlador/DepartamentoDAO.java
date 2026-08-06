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
        String sql = "SELECT idDepartamento, nombre, tipo_division FROM departamentos "
                + "WHERE activo = 1 ORDER BY nombre";

        try (Connection con = new Conexion().getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
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