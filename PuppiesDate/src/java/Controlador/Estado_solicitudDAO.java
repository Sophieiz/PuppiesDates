package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Modelo.Estado_solicitud;
import java.util.ArrayList;
import java.util.List;

public class Estado_solicitudDAO {

    Conexion conexion = new Conexion();

    public List<Estado_solicitud> listarEstado_solicitud() {
        List<Estado_solicitud> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idEstado_solicitud, descripcion_estado FROM Estado_solicitud";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Estado_solicitud estado = new Estado_solicitud();
                estado.setIdEstado_solicitud(rs.getInt("idEstado_solicitud"));
                estado.setDescripcion_estado(rs.getString("descripcion_estado"));
                lista.add(estado);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Estado_solicitud: " + e.getMessage());
        }
        return lista;
    }
}
