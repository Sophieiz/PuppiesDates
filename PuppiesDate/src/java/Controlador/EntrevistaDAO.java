/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Entrevista;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntrevistaDAO {

    
    private String sqlBaseConJoins() {
        return "SELECT en.identrevista, en.fecha, en.hora, en.observaciones, "
                + "en.solicitud_adopcion_idsolicitud_adopcion, en.activo, "
                + "u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, u.correo AS correoUsuario, "
                + "p.nombre AS nombrePerrito "
                + "FROM entrevista en "
                + "INNER JOIN solicitud_adopcion s ON en.solicitud_adopcion_idsolicitud_adopcion = s.idsolicitud_adopcion "
                + "INNER JOIN usuarios u ON s.usuarios_idusuarios = u.idusuarios "
                + "INNER JOIN perrito p ON s.perrito_idperrito = p.idperrito ";
    }

    public int insertarEntrevista(Entrevista entrevista) {
        int idGenerado = -1;
        String sql = "INSERT INTO entrevista (fecha, hora, observaciones, "
                + "solicitud_adopcion_idsolicitud_adopcion) VALUES (?, ?, ?, ?)";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, entrevista.getFecha());
            ps.setTime(2, entrevista.getHora());
            ps.setString(3, entrevista.getObservaciones());
            ps.setInt(4, entrevista.getSolicitud_adopcion_idSolicitud_adopcion());

            ps.executeUpdate();

            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    idGenerado = generadas.getInt(1);
                }
            }
            System.out.println("Entrevista registrada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar Entrevista: " + e.getMessage());
        }
        return idGenerado;
    }

    // Todas las entrevistas que se le han programado a una solicitud, de la más reciente a la más vieja
    public List<Entrevista> listarEntrevistaPorSolicitud(int idSolicitud_adopcion) {
        List<Entrevista> lista = new ArrayList<>();
        String sql = sqlBaseConJoins() + " WHERE en.solicitud_adopcion_idsolicitud_adopcion = ? "
                + "AND en.activo = 1 ORDER BY en.fecha DESC, en.hora DESC";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud_adopcion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEntrevista(rs));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar entrevistas de la solicitud: " + e.getMessage());
        }
        return lista;
    }

    // La entrevista más reciente de una solicitud (útil para el "ticket" que ve el usuario)
    public Entrevista consultarUltimaEntrevista(int idSolicitud_adopcion) {
        Entrevista entrevista = null;
        String sql = sqlBaseConJoins() + " WHERE en.solicitud_adopcion_idsolicitud_adopcion = ? "
                + "AND en.activo = 1 ORDER BY en.fecha DESC, en.hora DESC LIMIT 1";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud_adopcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entrevista = mapearEntrevista(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar última entrevista: " + e.getMessage());
        }
        return entrevista;
    }

    private Entrevista mapearEntrevista(ResultSet rs) throws SQLException {
        Entrevista entrevista = new Entrevista();
        entrevista.setIdEntrevista(rs.getInt("identrevista"));
        entrevista.setFecha(rs.getDate("fecha"));
        entrevista.setHora(rs.getTime("hora"));
        entrevista.setObservaciones(rs.getString("observaciones"));
        entrevista.setSolicitud_adopcion_idSolicitud_adopcion(rs.getInt("solicitud_adopcion_idsolicitud_adopcion"));
        entrevista.setActivo(rs.getBoolean("activo"));
        entrevista.setNombreUsuario(rs.getString("nombreUsuario"));
        entrevista.setApellidoUsuario(rs.getString("apellidoUsuario"));
        entrevista.setCorreoUsuario(rs.getString("correoUsuario"));
        entrevista.setNombrePerrito(rs.getString("nombrePerrito"));
        return entrevista;
    }
}
