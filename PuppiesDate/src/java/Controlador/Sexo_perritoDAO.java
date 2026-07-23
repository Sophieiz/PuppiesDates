package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Sexo_perrito;
import java.util.ArrayList;
import java.util.List;

public class Sexo_perritoDAO {

    Conexion conexion = new Conexion();

    public List<Sexo_perrito> listarSexo_perrito() {
        List<Sexo_perrito> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idSexo_perrito, descripcion FROM sexo_perrito ORDER BY idSexo_perrito";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sexo_perrito sexo = new Sexo_perrito();
                sexo.setIdSexo_perrito(rs.getInt("idSexo_perrito"));
                sexo.setDescripcion(rs.getString("descripcion"));
                lista.add(sexo);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Sexo_perrito: " + e.getMessage());
        }
        return lista;
    }
}
