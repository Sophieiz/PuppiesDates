package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.Tipo_Actividad;

public class Tipo_ActividadDAO {

    private final Conexion conexion = new Conexion();

    public boolean insertarTipo_Actividad(Tipo_Actividad mitipoactividad) {
        boolean insertado = false;
        String sql = "INSERT INTO tipo_actividad (idTipo_Actividad, nombre_activi) VALUES (?, ?)";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mitipoactividad.getidTipo_Actividad());
            ps.setString(2, mitipoactividad.getnombre_activi());
            ps.executeUpdate();

            insertado = true;
            System.out.println("Tipo de actividad insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar el Tipo de actividad: " + e.getMessage());
        }
        return insertado;
    }

    public Tipo_Actividad ConsultarTipo_Actividad(int idTipo_Actividad) {
        Tipo_Actividad mitipoactividad = null;
        String sql = "SELECT idTipo_Actividad, nombre_activi FROM tipo_actividad WHERE idTipo_Actividad = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo_Actividad);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mitipoactividad = new Tipo_Actividad();
                    mitipoactividad.setidTipo_Actividad(rs.getInt("idTipo_Actividad"));
                    mitipoactividad.setnombre_activi(rs.getString("nombre_activi"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar Tipo de actividad: " + e.getMessage());
        }
        return mitipoactividad;
    }

    public boolean actualizarTipoActividad(Tipo_Actividad mitipoactividad) {
        boolean actualizado = false;
        String sql = "UPDATE tipo_actividad SET nombre_activi = ? WHERE idtipo_actividad = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mitipoactividad.getnombre_activi());
            ps.setInt(2, mitipoactividad.getidTipo_Actividad());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Tipo de actividad actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean eliminarTipoActividad(int idTipo_Actividad) {
        boolean eliminado = false;
        String sql = "UPDATE tipo_actividad SET activo = 0 WHERE idTipo_Actividad = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo_Actividad);

            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Tipo de actividad eliminado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return eliminado;
    }

    public List<Tipo_Actividad> listarTipoActividad() {
        List<Tipo_Actividad> lista = new ArrayList<>();
        String sql = "SELECT idTipo_Actividad, nombre_activi FROM tipo_actividad WHERE activo = 1";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_Actividad actividad = new Tipo_Actividad();
                actividad.setidTipo_Actividad(rs.getInt("idTipo_Actividad"));
                actividad.setnombre_activi(rs.getString("nombre_activi"));
                lista.add(actividad);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar tipos de actividades: " + e.getMessage());
        }
        return lista;
    }

    public List<Tipo_Actividad> listarInactivas() {
        List<Tipo_Actividad> lista = new ArrayList<>();
        String sql = "SELECT idTipo_Actividad, nombre_activi FROM tipo_actividad WHERE activo = 0";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_Actividad actividad = new Tipo_Actividad();
                actividad.setidTipo_Actividad(rs.getInt("idTipo_Actividad"));
                actividad.setnombre_activi(rs.getString("nombre_activi"));
                lista.add(actividad);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar tipos de actividades inactivas: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarTipoActividad(int id) {
        boolean reactivado = false;
        String sql = "UPDATE tipo_actividad SET activo = 1 WHERE idTipo_Actividad = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar tipo de actividad: " + e.getMessage());
        }
        return reactivado;
    }
}