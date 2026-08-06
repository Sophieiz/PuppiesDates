package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelo.Sexo_perrito;
import java.util.ArrayList;
import java.util.List;

public class Sexo_perritoDAO {

    private final Conexion conexion = new Conexion();

    public List<Sexo_perrito> listarSexo_perrito() {
        List<Sexo_perrito> lista = new ArrayList<>();
        String sql = "SELECT idSexo_perrito, descripcion FROM sexo_perrito ORDER BY idSexo_perrito";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sexo_perrito sexo = new Sexo_perrito();
                sexo.setIdSexo_perrito(rs.getInt("idSexo_perrito"));
                sexo.setDescripcion(rs.getString("descripcion"));
                lista.add(sexo);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Sexo_perrito: " + e.getMessage());
        }
        return lista;
    }
}