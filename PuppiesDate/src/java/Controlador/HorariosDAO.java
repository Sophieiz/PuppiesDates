/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Horarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


/**
 *
 * @author USER
 */

public class HorariosDAO {

    Conexion conexion = new Conexion();

   
    public boolean insertarHorarios(Horarios horario) throws SQLException {
        boolean insertado = false;
        Connection con = conexion.getConn();

        
        String sql = "INSERT INTO horarios (idHorarios, hora_ini, hora_fin) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, horario.getidHorarios());
            ps.setTime(2, horario.gethora_ini());
            ps.setTime(3, horario.gethora_fin());

            if (ps.executeUpdate() > 0) {
                insertado = true;
                System.out.println("Horario insertado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar horario: " + e.getMessage());
        }
        return insertado;
    }

    public Horarios ConsultarHorarios(int idHorarios) {
        Horarios horarios = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        try {
            String querySQL = "SELECT idHorarios, hora_ini, hora_fin FROM horarios WHERE idHorarios = ?";

            PreparedStatement ps = con.prepareStatement(querySQL);
            ps.setInt(1, idHorarios);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                horarios = new Horarios();
                horarios.setidHorarios(rs.getInt(1));
                horarios.sethora_ini(rs.getTime(2));
                horarios.sethora_fin(rs.getTime(3));
            }
            return horarios;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return horarios;
        }
    }


   
    public boolean actualizarHorario(Horarios horario) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE horarios SET hora_ini=?, hora_fin=? WHERE idHorarios=?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTime(1, horario.gethora_ini());
            ps.setTime(2, horario.gethora_fin());
            ps.setInt(3, horario.getidHorarios());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Horario actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el horario: " + e.getMessage());
        }
        return actualizado;
    }

    
    public boolean eliminarHorario(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE horarios SET activo = 0 WHERE idHorarios = ?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Horario eliminado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el horario: " + e.getMessage());
        }
        return eliminado;
    }
    
    public List<Horarios> listarHorarios() {
        List<Horarios> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idHorarios, hora_ini, hora_fin FROM horarios WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setidHorarios(rs.getInt(1));
                horario.sethora_ini(rs.getTime(2));
                horario.sethora_fin(rs.getTime(3));
                lista.add(horario);
            }
        } catch (Exception e) {
            System.out.println("Error al listar horarios: " + e.getMessage());
        }
        return lista;
    }

    public List<Horarios> listarInactivos() {
        List<Horarios> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idHorarios, hora_ini, hora_fin FROM horarios WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setidHorarios(rs.getInt(1));
                horario.sethora_ini(rs.getTime(2));
                horario.sethora_fin(rs.getTime(3));
                lista.add(horario);
            }
        } catch (Exception e) {
            System.out.println("Error al listar horarios inactivos: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarHorario(int id) {
        boolean reactivado = false;
        String sql = "UPDATE horarios SET activo = 1 WHERE idHorarios = ?";
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar horario: " + e.getMessage());
        }
        return reactivado;
    }
}
