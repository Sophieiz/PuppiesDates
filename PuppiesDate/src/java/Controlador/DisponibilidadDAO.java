/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Disponibilidad;
import Modelo.Estado_reserva;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Time;

/**
 *
 * @author USER
 */
public class DisponibilidadDAO {

   public boolean insertarDisponibilidad(Disponibilidad disp) throws SQLException {
        boolean insertado = false;
        
        String sql = "INSERT INTO disponibilidad (fecha, cupo_total, cupo_disponible, Horarios_idHorarios) VALUES (?, ?, ?, ?)";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(disp.getFecha().getTime()));
            ps.setInt(2, disp.getCupo_total());
            ps.setInt(3, disp.getCupo_disponible());
            ps.setInt(4, disp.getHorarios_idHorarios());

            if (ps.executeUpdate() > 0) {
                insertado = true;
            }
        }
        return insertado;
    }

    public Disponibilidad ConsultarDisponibilidad(int idDisponibilidad) throws SQLException {
        Disponibilidad dispo = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        try {
            String querySQL = "SELECT idDisponibilidad, fecha, cupo_total, cupo_disponible, Horarios_idHorarios FROM Disponibilidad WHERE idDisponibilidad = ? ";

            PreparedStatement ps = con.prepareStatement(querySQL);
            ps.setInt(1, idDisponibilidad);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dispo = new Disponibilidad();
                dispo.setidDisponibilidad(rs.getInt(1));
                dispo.setfecha(rs.getDate(2));
                dispo.setcupo_total(rs.getInt(3));
                dispo.setcupo_disponible(rs.getInt(4));
                dispo.setHorarios_idHorarios(rs.getInt(5));

            }
            return dispo;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return dispo;

        }

    }

    public boolean actualizarDisponibilidad(Disponibilidad disp) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE disponibilidad SET fecha=?, cupo_total=?, cupo_disponible=?, Horarios_idHorarios=? WHERE idDisponibilidad=?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(disp.getFecha().getTime()));
            ps.setInt(2, disp.getCupo_total());
            ps.setInt(3, disp.getCupo_disponible());
            ps.setInt(4, disp.getHorarios_idHorarios());
            ps.setInt(5, disp.getIdDisponibilidad());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
        }
        return actualizado;
    }

    public boolean eliminarDisponibilidad(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE disponibilidad SET activo = 0 WHERE idDisponibilidad = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
        }
        return eliminado;
    }

    public List<Disponibilidad> Disponibilidad() {
        List<Disponibilidad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idDisponibilidad, fecha, cupo_total, cupo_disponible, Horarios_idHorarios FROM Disponibilidad WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Disponibilidad dispo = new Disponibilidad();
                dispo.setidDisponibilidad(rs.getInt(1));
                dispo.setfecha(rs.getDate(2));
                dispo.setcupo_total(rs.getInt(3));
                dispo.setcupo_disponible(rs.getInt(4));
                dispo.setHorarios_idHorarios(rs.getInt(5));
                lista.add(dispo);
            }
        } catch (Exception e) {
            System.out.println("Error al listar disponibilidad: " + e.getMessage());
        }
        return lista;
    }

    public List<Disponibilidad> listarInactivas() {
        List<Disponibilidad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idDisponibilidad, fecha, cupo_total, cupo_disponible, Horarios_idHorarios FROM Disponibilidad WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Disponibilidad dispo = new Disponibilidad();
                dispo.setidDisponibilidad(rs.getInt(1));
                dispo.setfecha(rs.getDate(2));
                dispo.setcupo_total(rs.getInt(3));
                dispo.setcupo_disponible(rs.getInt(4));
                dispo.setHorarios_idHorarios(rs.getInt(5));
                lista.add(dispo);
            }
        } catch (Exception e) {
            System.out.println("Error al listar disponibilidad inactiva: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarDisponibilidad(int id) {
        boolean reactivado = false;
        String sql = "UPDATE disponibilidad SET activo = 1 WHERE idDisponibilidad = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar disponibilidad: " + e.getMessage());
        }
        return reactivado;
    }

    public String verificarEstadoDisponibilidad(java.sql.Date fecha, java.sql.Time hora) {
        String estado = "NO_EXISTE";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        String sql = "SELECT d.cupo_disponible "
                + "FROM disponibilidad d "
                + "INNER JOIN horarios h ON d.Horarios_idHorarios = h.idHorarios "
                + "WHERE d.fecha = ? AND ? BETWEEN h.hora_ini AND h.hora_fin";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, fecha);
            ps.setTime(2, hora);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                estado = rs.getInt("cupo_disponible") > 0 ? "DISPONIBLE" : "SIN_CUPO";
            }
        } catch (Exception e) {
            System.out.println("Error al verificar estado de disponibilidad: " + e.getMessage());
        }
        return estado;
    }

    public Disponibilidad buscarDisponibilidad(java.sql.Date fecha, java.sql.Time hora) {
        Disponibilidad dispo = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT d.idDisponibilidad, d.fecha, d.cupo_total, d.cupo_disponible, d.Horarios_idHorarios FROM disponibilidad d INNER JOIN horarios h ON d.Horarios_idHorarios = h.idHorarios WHERE d.fecha = ? AND ? BETWEEN h.hora_ini AND h.hora_fin AND d.cupo_disponible > 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, fecha);
            ps.setTime(2, hora);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dispo = new Disponibilidad();
                dispo.setidDisponibilidad(rs.getInt(1));
                dispo.setfecha(rs.getDate(2));
                dispo.setcupo_total(rs.getInt(3));
                dispo.setcupo_disponible(rs.getInt(4));
                dispo.setHorarios_idHorarios(rs.getInt(5));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar disponibilidad: " + e.getMessage());
        }
        return dispo;
    }
}
