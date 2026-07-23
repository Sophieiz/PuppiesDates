/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Pagos;
import Modelo.Roles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Paula Gisedt
 */
public class PagosDAO {
    
    
    public boolean insertarPago (Pagos pago) throws SQLException {
        boolean insertado = false;
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        String sql = "INSERT INTO pagos (idPagos, estado_pago) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pago.getidPagos());
            ps.setString(2, pago.getestado_pago());
            ps.executeUpdate();
            insertado = true;
            System.out.println("Estado del pago insertado correctamente en la base de datos viverobd.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el estado del pago:" + e.getMessage());
        }
        return insertado;
    }

    public Pagos consultarPagos(int idPagos) {
        Pagos pagos = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        try {
            String querySQL = "SELECT idPagos, estado_pago FROM Pagos WHERE idPagos = ? ";

            PreparedStatement ps = con.prepareStatement(querySQL);
            ps.setInt(1, idPagos);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                pagos = new Pagos();
                pagos.setidPagos(rs.getInt(1));
                pagos.setestado_pago(rs.getString(2));
            }
            return pagos;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return pagos;
        }
    }

    public boolean actualizarPagos(Pagos pago) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE pagos SET estado_pago = ? WHERE idPagos = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pago.getestado_pago());
            ps.setInt(2, pago.getidPagos());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Estado de pago actualizado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado de pago: " + e.getMessage());
        }
        return actualizado;
    }


    public boolean eliminarPagos(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE pagos SET activo = 0 WHERE idPagos = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Estado de pago eliminado de VIVEROBD.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el estado de pago: " + e.getMessage());
        }
        return eliminado;
    }
        public List<Pagos> listarPagos() {
    List<Pagos> lista = new ArrayList<>();
    Conexion conexion = new Conexion();
    Connection con = conexion.getConn();
    try {
        String sql = "SELECT idPagos, estado_pago FROM Pagos WHERE activo = 1";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Pagos pago = new Pagos();
            pago.setidPagos(rs.getInt(1));
            pago.setestado_pago(rs.getString(2));
            lista.add(pago);
        }
    } catch (Exception e) {
        System.out.println("Error al listar roles: " + e.getMessage());
    }
    return lista;
}

    public List<Pagos> listarInactivos() {
        List<Pagos> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idPagos, estado_pago FROM Pagos WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pagos pago = new Pagos();
                pago.setidPagos(rs.getInt(1));
                pago.setestado_pago(rs.getString(2));
                lista.add(pago);
            }
        } catch (Exception e) {
            System.out.println("Error al listar pagos inactivos: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarPago(int id) {
        boolean reactivado = false;
        String sql = "UPDATE pagos SET activo = 1 WHERE idPagos = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar el pago: " + e.getMessage());
        }
        return reactivado;
    }
}
