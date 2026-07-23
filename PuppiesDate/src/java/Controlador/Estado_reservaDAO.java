/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;
import Modelo.Estado_reserva;
import Modelo.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author USER
 */
public class Estado_reservaDAO {
    Conexion conexion = new Conexion();
    
    public boolean insertarEstadoReserva(Estado_reserva estado) throws SQLException {
        boolean insertado = false;
        Connection con = conexion.getConn();
       
        String sql = "INSERT INTO estado_reserva (idEstado_reserva, descripcion_esta) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, estado.getidEstado_reserva());
            ps.setString(2, estado.getdescripcion_esta());

            if (ps.executeUpdate() > 0) {
                insertado = true;
                System.out.println("Estado de reserva insertado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar estado: " + e.getMessage());
        }
        return insertado;
    }

    
    public Estado_reserva ConsultarEstado_reserva(int idEstado_reserva) throws SQLException {
        Estado_reserva estado = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        try {
            String querySQL = "SELECT idEstado_reserva, descripcion_esta FROM estado_reserva WHERE idEstado_reserva = ?";
            PreparedStatement ps = con.prepareStatement(querySQL);
            ps.setInt(1, idEstado_reserva);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                estado = new Estado_reserva();

                estado.setidEstado_reserva(rs.getInt(1));
                estado.setdescripcion_esta(rs.getString(2));
            }
            return estado;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return estado;
        }
    }

   
    public boolean actualizarEstadoReserva(Estado_reserva estado) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE estado_reserva SET descripcion_esta=? WHERE idEstado_reserva=?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado.getdescripcion_esta());
            ps.setInt(2, estado.getidEstado_reserva());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
        }
        return actualizado;
    }

  
    public boolean eliminarEstadoReserva(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE estado_reserva SET activo = 0 WHERE idEstado_reserva = ?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar estado: " + e.getMessage());
        }
        return eliminado;
    }
    public List<Estado_reserva> Estado_reserva() {
        List<Estado_reserva> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idEstado_reserva, descripcion_esta FROM estado_reserva WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Estado_reserva reserva = new Estado_reserva();
                reserva.setidEstado_reserva(rs.getInt(1));
                reserva.setdescripcion_esta(rs.getString(2));
                lista.add(reserva);
            }
        } catch (Exception e) {
            System.out.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }

    public List<Estado_reserva> listarInactivos() {
        List<Estado_reserva> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idEstado_reserva, descripcion_esta FROM estado_reserva WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Estado_reserva reserva = new Estado_reserva();
                reserva.setidEstado_reserva(rs.getInt(1));
                reserva.setdescripcion_esta(rs.getString(2));
                lista.add(reserva);
            }
        } catch (Exception e) {
            System.out.println("Error al listar estados de reserva inactivos: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarEstadoReserva(int id) {
        boolean reactivado = false;
        String sql = "UPDATE estado_reserva SET activo = 1 WHERE idEstado_reserva = ?";
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar estado de reserva: " + e.getMessage());
        }
        return reactivado;
    }
}
    

