/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Lista_precios;
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
public class Lista_preciosDAO {

    public Lista_preciosDAO() {
    }

    Conexion conexion = new Conexion();

    public boolean insertarLista_precios(Lista_precios precio) throws SQLException {
        boolean insertado = false;
        Connection con = conexion.getConn();

        String sql = "INSERT INTO lista_precios (descrip_precio) VALUES (?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, precio.getdescrip_precio());

            if (ps.executeUpdate() > 0) {
                insertado = true;
                System.out.println("Precio insertado con éxito en la lista.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar en lista de precios: " + e.getMessage());
        }
        return insertado;
    }

    public Lista_precios ConsultarLista_precios(int idLista_precios) throws SQLException {
        Lista_precios precios = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        try {
            String querySQL = "SELECT idLista_precios, descrip_precio FROM lista_precios WHERE idLista_precios = ?";

            PreparedStatement ps = con.prepareStatement(querySQL);
            ps.setInt(1, idLista_precios);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                precios = new Lista_precios();

                precios.setidLista_Precios(rs.getInt(1));
                precios.setdescrip_precio(rs.getString(2));
            }

            return precios;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return precios;
        }
    }

    public boolean actualizarListaPrecios(Lista_precios precio) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE lista_precios SET descrip_precio=? WHERE idLista_Precios=?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, precio.getdescrip_precio());
            ps.setInt(2, precio.getidLista_Precio());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Lista de precios actualizada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la lista de precios: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean eliminarListaPrecios(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE lista_precios SET activo = 0 WHERE idLista_Precios = ?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Registro eliminado de la lista de precios.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar de la lista de precios: " + e.getMessage());
        }
        return eliminado;
    }

    public List<Lista_precios> listarLista_precios() {
        List<Lista_precios> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idLista_precios, descrip_precio FROM lista_precios WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lista_precios precio = new Lista_precios();
                precio.setidLista_Precios(rs.getInt(1));
                precio.setdescrip_precio(rs.getString(2));
                lista.add(precio);
            }
        } catch (Exception e) {
            System.out.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }

    public List<Lista_precios> listarInactivas() {
        List<Lista_precios> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idLista_precios, descrip_precio FROM lista_precios WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lista_precios precio = new Lista_precios();
                precio.setidLista_Precios(rs.getInt(1));
                precio.setdescrip_precio(rs.getString(2));
                lista.add(precio);
            }
        } catch (Exception e) {
            System.out.println("Error al listar precios inactivos: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarListaPrecios(int id) {
        boolean reactivado = false;
        String sql = "UPDATE lista_precios SET activo = 1 WHERE idLista_Precios = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar precio: " + e.getMessage());
        }
        return reactivado;
    }
}
