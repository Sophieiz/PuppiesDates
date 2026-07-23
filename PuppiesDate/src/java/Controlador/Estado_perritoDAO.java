package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Estado_perrito;
import java.util.ArrayList;
import java.util.List;

public class Estado_perritoDAO {

    Conexion conexion = new Conexion();

    public List<Estado_perrito> listarEstado_perrito() {
        List<Estado_perrito> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idEstado_perrito, descripcion_estado FROM Estado_perrito";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Estado_perrito estado = new Estado_perrito();
                estado.setIdEstado_perrito(rs.getInt("idEstado_perrito"));
                estado.setDescripcion_estado(rs.getString("descripcion_estado"));
                lista.add(estado);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Estado_perrito: " + e.getMessage());
        }
        return lista;
    }
}
