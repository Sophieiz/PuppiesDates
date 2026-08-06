package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.Tipo_vivienda;

public class Tipo_viviendaDAO {

    private final Conexion conexion = new Conexion();


    public List<Tipo_vivienda> listarActivos() {
        List<Tipo_vivienda> lista = new ArrayList<>();
        String sql = "SELECT idtipo_vivienda, descripcion FROM tipo_vivienda WHERE activo = 1 ORDER BY descripcion";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_vivienda opcion = new Tipo_vivienda();
                opcion.setIdTipo_vivienda(rs.getInt("idtipo_vivienda"));
                opcion.setDescripcion(rs.getString("descripcion"));
                opcion.setActivo(true);
                lista.add(opcion);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Tipo_vivienda: " + e.getMessage());
        }
        return lista;
    }
}