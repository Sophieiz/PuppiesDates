package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Solicitud_adopcion;
import Modelo.Historial_estado_solicitud;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Solicitud_adopcionDAO {

    Conexion conexion = new Conexion();

    public int insertarSolicitud_adopcion(Solicitud_adopcion solicitud) {
        int idGenerado = -1;
        Connection con = conexion.getConn();

        String sql = "INSERT INTO solicitud_adopcion (direccion, localidad, barrio, profesion, vive_en, "
                + "tipo_vivienda, nucleo_familiar, tiene_mascotas, Usuarios_idUsuarios, Perrito_idPerrito, "
                + "Estado_solicitud_idEstado_solicitud) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, solicitud.getDireccion());
            ps.setString(2, solicitud.getLocalidad());
            ps.setString(3, solicitud.getBarrio());
            ps.setString(4, solicitud.getProfesion());
            ps.setString(5, solicitud.getVive_en());
            ps.setString(6, solicitud.getTipo_vivienda());
            ps.setString(7, solicitud.getNucleo_familiar());
            ps.setBoolean(8, solicitud.isTiene_mascotas());
            ps.setInt(9, solicitud.getUsuarios_idUsuarios());
            ps.setInt(10, solicitud.getPerrito_idPerrito());
            // Toda solicitud nueva arranca en "En revisión" (idEstado_solicitud = 1)
            ps.setInt(11, 1);

            ps.executeUpdate();

            ResultSet generadas = ps.getGeneratedKeys();
            if (generadas.next()) {
                idGenerado = generadas.getInt(1);
            }
            System.out.println("Solicitud de adopción insertada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar Solicitud_adopcion: " + e.getMessage());
        }
        return idGenerado;
    }

    public Solicitud_adopcion ConsultarSolicitud_adopcion(int idSolicitud_adopcion) {
        Solicitud_adopcion solicitud = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = sqlBaseConJoins() + " WHERE s.idSolicitud_adopcion = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idSolicitud_adopcion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                solicitud = mapearSolicitud(rs);
            }
            return solicitud;
        } catch (Exception ex) {
            System.out.println("Error al consultar Solicitud_adopcion: " + ex.getMessage());
            return solicitud;
        }
    }

    public List<Solicitud_adopcion> listarSolicitud_adopcion() {
        List<Solicitud_adopcion> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = sqlBaseConJoins() + " WHERE s.activo = 1 ORDER BY s.fecha_solicitud DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearSolicitud(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar Solicitud_adopcion: " + e.getMessage());
        }
        return lista;
    }

    // Busca por nombre del perrito o nombre/apellido/documento del solicitante
    public List<Solicitud_adopcion> buscarSolicitud_adopcion(String textoBusqueda) {
        List<Solicitud_adopcion> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = sqlBaseConJoins()
                    + " WHERE s.activo = 1 AND (p.nombre LIKE ? OR u.nombre LIKE ? OR u.apellido LIKE ? OR u.documento LIKE ?) "
                    + " ORDER BY s.fecha_solicitud DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            String comodin = "%" + textoBusqueda + "%";
            ps.setString(1, comodin);
            ps.setString(2, comodin);
            ps.setString(3, comodin);
            ps.setString(4, comodin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearSolicitud(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar Solicitud_adopcion: " + e.getMessage());
        }
        return lista;
    }

    // Para que el usuario consulte el estado de sus propias solicitudes
    public List<Solicitud_adopcion> listarSolicitud_adopcionPorUsuario(int idUsuarios) {
        List<Solicitud_adopcion> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = sqlBaseConJoins() + " WHERE s.activo = 1 AND s.Usuarios_idUsuarios = ? ORDER BY s.fecha_solicitud DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuarios);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearSolicitud(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar solicitudes del usuario: " + e.getMessage());
        }
        return lista;
    }

    // Valida que el usuario no tenga ya una solicitud activa (En revisión o En entrevista) para ese mismo perrito
    public boolean existeSolicitudActiva(int idUsuarios, int idPerrito) {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT s.idSolicitud_adopcion FROM solicitud_adopcion s "
                    + "INNER JOIN estado_solicitud e ON s.Estado_solicitud_idEstado_solicitud = e.idEstado_solicitud "
                    + "WHERE s.Usuarios_idUsuarios = ? AND s.Perrito_idPerrito = ? "
                    + "AND e.descripcion_estado IN ('Pendiente', 'En proceso')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuarios);
            ps.setInt(2, idPerrito);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al verificar solicitud activa: " + e.getMessage());
            return false;
        }
    }

    // Cambia el estado de la solicitud Y deja registro en Historial_estado_solicitud
    public boolean actualizarEstadoSolicitud(int idSolicitud_adopcion, int idEstado_solicitud, String observacion) {
        boolean actualizado = false;
        String sql = "UPDATE solicitud_adopcion SET Estado_solicitud_idEstado_solicitud = ? WHERE idSolicitud_adopcion = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstado_solicitud);
            ps.setInt(2, idSolicitud_adopcion);
            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Estado de la solicitud actualizado con éxito.");

                Historial_estado_solicitud historial = new Historial_estado_solicitud();
                historial.setSolicitud_adopcion_idSolicitud_adopcion(idSolicitud_adopcion);
                historial.setEstado_solicitud_idEstado_solicitud(idEstado_solicitud);
                historial.setObservacion(observacion);
                insertarHistorial_estado_solicitud(historial);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado de Solicitud_adopcion: " + e.getMessage());
        }
        return actualizado;
    }

    // Borrado suave: nunca se elimina de verdad, solo se inactiva
    public boolean eliminarSolicitud_adopcion(int id) {
        boolean eliminado = false;
        String sql = "UPDATE solicitud_adopcion SET activo = 0 WHERE idSolicitud_adopcion = ?";
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Solicitud de adopción inactivada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al inactivar solicitud: " + e.getMessage());
        }
        return eliminado;
    }

    public List<Solicitud_adopcion> listarInactivas() {
        List<Solicitud_adopcion> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = sqlBaseConJoins() + " WHERE s.activo = 0 ORDER BY s.fecha_solicitud DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearSolicitud(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar solicitudes inactivas: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarSolicitud_adopcion(int id) {
        boolean reactivado = false;
        String sql = "UPDATE solicitud_adopcion SET activo = 1 WHERE idSolicitud_adopcion = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar solicitud: " + e.getMessage());
        }
        return reactivado;
    }

    public boolean insertarHistorial_estado_solicitud(Historial_estado_solicitud historial) {
        boolean insertado = false;
        Connection con = conexion.getConn();
        String sql = "INSERT INTO historial_estado_solicitud (observacion, Solicitud_adopcion_idSolicitud_adopcion, "
                + "Estado_solicitud_idEstado_solicitud) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, historial.getObservacion());
            ps.setInt(2, historial.getSolicitud_adopcion_idSolicitud_adopcion());
            ps.setInt(3, historial.getEstado_solicitud_idEstado_solicitud());
            ps.executeUpdate();
            insertado = true;
            System.out.println("Historial de estado registrado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar Historial_estado_solicitud: " + e.getMessage());
        }
        return insertado;
    }

    public List<Historial_estado_solicitud> listarHistorialPorSolicitud(int idSolicitud_adopcion) {
        List<Historial_estado_solicitud> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT h.idHistorial_estado_solicitud, h.fecha_cambio, h.observacion, "
                    + "h.Solicitud_adopcion_idSolicitud_adopcion, h.Estado_solicitud_idEstado_solicitud, "
                    + "e.descripcion_estado AS descripcionEstado "
                    + "FROM historial_estado_solicitud h "
                    + "INNER JOIN estado_solicitud e ON h.Estado_solicitud_idEstado_solicitud = e.idEstado_solicitud "
                    + "WHERE h.Solicitud_adopcion_idSolicitud_adopcion = ? "
                    + "ORDER BY h.fecha_cambio ASC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idSolicitud_adopcion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Historial_estado_solicitud historial = new Historial_estado_solicitud();
                historial.setIdHistorial_estado_solicitud(rs.getInt("idHistorial_estado_solicitud"));
                historial.setFecha_cambio(rs.getTimestamp("fecha_cambio"));
                historial.setObservacion(rs.getString("observacion"));
                historial.setSolicitud_adopcion_idSolicitud_adopcion(rs.getInt("Solicitud_adopcion_idSolicitud_adopcion"));
                historial.setEstado_solicitud_idEstado_solicitud(rs.getInt("Estado_solicitud_idEstado_solicitud"));
                historial.setDescripcionEstado_solicitud(rs.getString("descripcionEstado"));
                lista.add(historial);
            }
        } catch (Exception e) {
            System.out.println("Error al listar historial: " + e.getMessage());
        }
        return lista;
    }

    private String sqlBaseConJoins() {
        return "SELECT s.idSolicitud_adopcion, s.direccion, s.localidad, s.barrio, s.profesion, s.vive_en, "
                + "s.tipo_vivienda, s.nucleo_familiar, s.tiene_mascotas, s.fecha_solicitud, "
                + "s.Usuarios_idUsuarios, s.Perrito_idPerrito, s.Estado_solicitud_idEstado_solicitud, "
                + "u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, u.documento AS documentoUsuario, "
                + "u.correo AS correoUsuario, p.nombre AS nombrePerrito, e.descripcion_estado AS descripcionEstado "
                + "FROM solicitud_adopcion s "
                + "INNER JOIN usuarios u ON s.Usuarios_idUsuarios = u.idUsuarios "
                + "INNER JOIN perrito p ON s.Perrito_idPerrito = p.idPerrito "
                + "INNER JOIN estado_solicitud e ON s.Estado_solicitud_idEstado_solicitud = e.idEstado_solicitud";
    }

    private Solicitud_adopcion mapearSolicitud(ResultSet rs) throws SQLException {
        Solicitud_adopcion solicitud = new Solicitud_adopcion();
        solicitud.setIdSolicitud_adopcion(rs.getInt("idSolicitud_adopcion"));
        solicitud.setDireccion(rs.getString("direccion"));
        solicitud.setLocalidad(rs.getString("localidad"));
        solicitud.setBarrio(rs.getString("barrio"));
        solicitud.setProfesion(rs.getString("profesion"));
        solicitud.setVive_en(rs.getString("vive_en"));
        solicitud.setTipo_vivienda(rs.getString("tipo_vivienda"));
        solicitud.setNucleo_familiar(rs.getString("nucleo_familiar"));
        solicitud.setTiene_mascotas(rs.getBoolean("tiene_mascotas"));
        Timestamp fecha = rs.getTimestamp("fecha_solicitud");
        solicitud.setFecha_solicitud(fecha);
        solicitud.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));
        solicitud.setPerrito_idPerrito(rs.getInt("Perrito_idPerrito"));
        solicitud.setEstado_solicitud_idEstado_solicitud(rs.getInt("Estado_solicitud_idEstado_solicitud"));
        solicitud.setNombreUsuario(rs.getString("nombreUsuario"));
        solicitud.setApellidoUsuario(rs.getString("apellidoUsuario"));
        solicitud.setDocumentoUsuario(rs.getString("documentoUsuario"));
        solicitud.setCorreoUsuario(rs.getString("correoUsuario"));
        solicitud.setNombrePerrito(rs.getString("nombrePerrito"));
        solicitud.setDescripcionEstado_solicitud(rs.getString("descripcionEstado"));
        return solicitud;
    }
}