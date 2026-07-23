package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.Roles;

public class RolesDAO {

    Conexion conexion = new Conexion();

    public boolean insertarRol(Roles rol) throws SQLException {
        boolean insertado = false;
        Connection con = conexion.getConn();
        String sql = "INSERT INTO roles (idRoles, descripcion_rol) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, rol.getidRoles());
            ps.setString(2, rol.getdescripcion_rol());
            ps.executeUpdate();
            insertado = true;
            System.out.println("Rol insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar rol: " + e.getMessage());
        }
        return insertado;
    }

    public Roles ConsultarRoles(int idRoles) {
        Roles roles = null;
        Connection con = conexion.getConn();
        String sql = "SELECT idRoles, descripcion_rol FROM roles WHERE idRoles = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRoles);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                roles = new Roles();
                roles.setidRoles(rs.getInt(1));
                roles.setdescripcion_rol(rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println("Error al consultar rol: " + e.getMessage());
        }
        return roles;
    }

    public boolean actualizarRol(Roles rol) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE roles SET descripcion_rol = ? WHERE idRoles = ?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getdescripcion_rol());
            ps.setInt(2, rol.getidRoles());
            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Rol actualizado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
        }
        return actualizado;
    }

    // Borrado suave: nunca se elimina de verdad, solo se inactiva
    public boolean eliminarRol(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE roles SET activo = 0 WHERE idRoles = ?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Rol inactivado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al inactivar rol: " + e.getMessage());
        }
        return eliminado;
    }

    public boolean reactivarRol(int id) {
        boolean reactivado = false;
        String sql = "UPDATE roles SET activo = 1 WHERE idRoles = ?";
        Connection con = conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar rol: " + e.getMessage());
        }
        return reactivado;
    }

    public List<Roles> listarRoles() {
        List<Roles> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        String sql = "SELECT idRoles, descripcion_rol FROM roles WHERE activo = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles rol = new Roles();
                rol.setidRoles(rs.getInt(1));
                rol.setdescripcion_rol(rs.getString(2));
                lista.add(rol);
            }
        } catch (Exception e) {
            System.out.println("Error al listar roles: " + e.getMessage());
        }
        return lista;
    }

    public List<Roles> listarInactivos() {
        List<Roles> lista = new ArrayList<>();
        Connection con = conexion.getConn();
        String sql = "SELECT idRoles, descripcion_rol FROM roles WHERE activo = 0";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roles rol = new Roles();
                rol.setidRoles(rs.getInt(1));
                rol.setdescripcion_rol(rs.getString(2));
                lista.add(rol);
            }
        } catch (Exception e) {
            System.out.println("Error al listar roles inactivos: " + e.getMessage());
        }
        return lista;
    }

    // Rol asignado automáticamente a todo registro público (nunca Admin = idRoles 1)
    public int obtenerIdRolClientePorDefecto() {
        int idRolCliente = 0;
        Connection con = conexion.getConn();
        String sql = "SELECT idRoles FROM roles WHERE idRoles <> 1 AND activo = 1 ORDER BY idRoles LIMIT 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idRolCliente = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener rol cliente por defecto: " + e.getMessage());
        }
        return idRolCliente;
    }
}
