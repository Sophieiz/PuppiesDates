package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Modelo.Raza;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO {

    private final Conexion conexion = new Conexion();

    // Trae todas las razas activas, sin importar la especie
    public List<Raza> listarRaza() {
        List<Raza> lista = new ArrayList<>();
        String sql = "SELECT idRaza, nombre, Especie_idEspecie FROM raza WHERE activo = 1";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearRaza(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Raza: " + e.getMessage());
        }
        return lista;
    }

    // Útil para cuando el formulario filtra razas dependiendo de la especie elegida (ej. con AJAX)
    public List<Raza> listarRazaPorEspecie(int idEspecie) {
        List<Raza> lista = new ArrayList<>();
        String sql = "SELECT idRaza, nombre, Especie_idEspecie FROM raza WHERE activo = 1 AND Especie_idEspecie = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEspecie);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearRaza(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar Raza por especie: " + e.getMessage());
        }
        return lista;
    }

    private Raza mapearRaza(ResultSet rs) throws SQLException {
        Raza raza = new Raza();
        raza.setIdRaza(rs.getInt("idRaza"));
        raza.setNombre(rs.getString("nombre"));
        raza.setEspecie_idEspecie(rs.getInt("Especie_idEspecie"));
        return raza;
    }
}