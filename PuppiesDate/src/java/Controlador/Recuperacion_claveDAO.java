package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import Modelo.Recuperacion_clave;

public class Recuperacion_claveDAO {

    Conexion conexion = new Conexion();

    public boolean insertarToken(Recuperacion_clave recuperacion) {
        boolean insertado = false;
        Connection con = conexion.getConn();
        String sql = "INSERT INTO recuperacion_clave (token, fecha_expiracion, usado, Usuarios_idUsuarios) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, recuperacion.getToken());
            ps.setTimestamp(2, recuperacion.getFecha_expiracion());
            ps.setBoolean(3, false);
            ps.setInt(4, recuperacion.getUsuarios_idUsuarios());
            ps.executeUpdate();
            insertado = true;
            System.out.println("Token de recuperación generado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar Recuperacion_clave: " + e.getMessage());
        }
        return insertado;
    }

    // Devuelve el token solo si existe, no ha sido usado, y no ha expirado. Si no, devuelve null.
    public Recuperacion_clave buscarTokenValido(String token) {
        Recuperacion_clave recuperacion = null;
        Connection con = conexion.getConn();
        String sql = "SELECT r.idRecuperacion_clave, r.token, r.fecha_creacion, r.fecha_expiracion, r.usado, "
                + "r.Usuarios_idUsuarios, u.correo AS correoUsuario, u.nombre AS nombreUsuario "
                + "FROM recuperacion_clave r "
                + "INNER JOIN Usuarios u ON r.Usuarios_idUsuarios = u.idUsuarios "
                + "WHERE r.token = ? AND r.usado = 0 AND r.fecha_expiracion > ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                recuperacion = new Recuperacion_clave();
                recuperacion.setIdRecuperacion_clave(rs.getInt("idRecuperacion_clave"));
                recuperacion.setToken(rs.getString("token"));
                recuperacion.setFecha_creacion(rs.getTimestamp("fecha_creacion"));
                recuperacion.setFecha_expiracion(rs.getTimestamp("fecha_expiracion"));
                recuperacion.setUsado(rs.getBoolean("usado"));
                recuperacion.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));
                recuperacion.setCorreoUsuario(rs.getString("correoUsuario"));
                recuperacion.setNombreUsuario(rs.getString("nombreUsuario"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar token: " + e.getMessage());
        }
        return recuperacion;
    }

    public boolean marcarTokenUsado(int idRecuperacion_clave) {
        boolean actualizado = false;
        Connection con = conexion.getConn();
        String sql = "UPDATE recuperacion_clave SET usado = 1 WHERE idRecuperacion_clave = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRecuperacion_clave);
            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al marcar token como usado: " + e.getMessage());
        }
        return actualizado;
    }

    // Invalida cualquier token anterior sin usar del mismo usuario, para que solo el más reciente sirva
    public boolean invalidarTokensAnteriores(int idUsuarios) {
        boolean actualizado = false;
        Connection con = conexion.getConn();
        String sql = "UPDATE recuperacion_clave SET usado = 1 WHERE Usuarios_idUsuarios = ? AND usado = 0";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuarios);
            ps.executeUpdate();
            actualizado = true;
        } catch (SQLException e) {
            System.out.println("Error al invalidar tokens anteriores: " + e.getMessage());
        }
        return actualizado;
    }
}
