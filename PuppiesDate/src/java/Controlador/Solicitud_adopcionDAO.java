package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import Modelo.Solicitud_adopcion;
import Modelo.Historial_estado_solicitud;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Solicitud_adopcionDAO {

    public int insertarSolicitud_adopcion(Solicitud_adopcion solicitud) {
        int idGenerado = -1;
        String sql = "INSERT INTO solicitud_adopcion (direccion, Departamento_idDepartamento, "
                + "Municipio_idMunicipio, Localidad_idLocalidad, barrio, profesion, vive_en_idvive_en, "
                + "tipo_vivienda_idtipo_vivienda, nucleo_familiar, tiene_mascotas, Usuarios_idUsuarios, Perrito_idPerrito, "
                + "Estado_solicitud_idEstado_solicitud) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, solicitud.getDireccion());
            ps.setInt(2, solicitud.getDepartamentoId());

            if (solicitud.getMunicipioId() != null) {
                ps.setInt(3, solicitud.getMunicipioId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            if (solicitud.getLocalidadId() != null) {
                ps.setInt(4, solicitud.getLocalidadId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setString(5, solicitud.getBarrio());
            ps.setString(6, solicitud.getProfesion());
            ps.setInt(7, solicitud.getViveEnId());
            ps.setInt(8, solicitud.getTipoViviendaId());
            ps.setString(9, solicitud.getNucleo_familiar());
            ps.setBoolean(10, solicitud.isTiene_mascotas());
            ps.setInt(11, solicitud.getUsuarios_idUsuarios());
            ps.setInt(12, solicitud.getPerrito_idPerrito());
            ps.setInt(13, 1);

            ps.executeUpdate();

            try (ResultSet generadas = ps.getGeneratedKeys()) {
                if (generadas.next()) {
                    idGenerado = generadas.getInt(1);
                }
            }
            System.out.println("Solicitud de adopción insertada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar Solicitud_adopcion: " + e.getMessage());
        }
        return idGenerado;
    }

    public Solicitud_adopcion ConsultarSolicitud_adopcion(int idSolicitud_adopcion) {
        Solicitud_adopcion solicitud = null;
        String sql = sqlBaseConJoins() + " WHERE s.idSolicitud_adopcion = ?";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud_adopcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    solicitud = mapearSolicitud(rs);
                }
            }
            return solicitud;
        } catch (Exception ex) {
            System.out.println("Error al consultar Solicitud_adopcion: " + ex.getMessage());
            return solicitud;
        }
    }

    public List<Solicitud_adopcion> listarSolicitud_adopcion() {
        List<Solicitud_adopcion> lista = new ArrayList<>();
        String sql = sqlBaseConJoins() + " WHERE s.activo = 1 ORDER BY s.fecha_solicitud DESC";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        String sql = sqlBaseConJoins()
                + " WHERE s.activo = 1 AND (p.nombre LIKE ? OR u.nombre LIKE ? OR u.apellido LIKE ? OR u.documento LIKE ?) "
                + " ORDER BY s.fecha_solicitud DESC";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String comodin = "%" + textoBusqueda + "%";
            ps.setString(1, comodin);
            ps.setString(2, comodin);
            ps.setString(3, comodin);
            ps.setString(4, comodin);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearSolicitud(rs));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar Solicitud_adopcion: " + e.getMessage());
        }
        return lista;
    }

    // Para que el usuario consulte el estado de sus propias solicitudes
    public List<Solicitud_adopcion> listarSolicitud_adopcionPorUsuario(int idUsuarios) {
        List<Solicitud_adopcion> lista = new ArrayList<>();
        String sql = sqlBaseConJoins() + " WHERE s.activo = 1 AND s.Usuarios_idUsuarios = ? ORDER BY s.fecha_solicitud DESC";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuarios);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearSolicitud(rs));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar solicitudes del usuario: " + e.getMessage());
        }
        return lista;
    }

    // Valida que el usuario no tenga ya una solicitud activa (En revisión o En entrevista) para ese mismo perrito
    public boolean existeSolicitudActiva(int idUsuarios, int idPerrito) {
        String sql = "SELECT s.idSolicitud_adopcion FROM solicitud_adopcion s "
                + "INNER JOIN estado_solicitud e ON s.Estado_solicitud_idEstado_solicitud = e.idEstado_solicitud "
                + "WHERE s.Usuarios_idUsuarios = ? AND s.Perrito_idPerrito = ? "
                + "AND e.descripcion_estado IN ('Pendiente', 'En proceso')";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuarios);
            ps.setInt(2, idPerrito);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
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
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
        String sql = sqlBaseConJoins() + " WHERE s.activo = 0 ORDER BY s.fecha_solicitud DESC";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar solicitud: " + e.getMessage());
        }
        return reactivado;
    }

    public boolean insertarHistorial_estado_solicitud(Historial_estado_solicitud historial) {
        boolean insertado = false;
        String sql = "INSERT INTO historial_estado_solicitud (observacion, Solicitud_adopcion_idSolicitud_adopcion, "
                + "Estado_solicitud_idEstado_solicitud) VALUES (?, ?, ?)";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
        String sql = "SELECT h.idHistorial_estado_solicitud, h.fecha_cambio, h.observacion, "
                + "h.Solicitud_adopcion_idSolicitud_adopcion, h.Estado_solicitud_idEstado_solicitud, "
                + "e.descripcion_estado AS descripcionEstado "
                + "FROM historial_estado_solicitud h "
                + "INNER JOIN estado_solicitud e ON h.Estado_solicitud_idEstado_solicitud = e.idEstado_solicitud "
                + "WHERE h.Solicitud_adopcion_idSolicitud_adopcion = ? "
                + "ORDER BY h.fecha_cambio ASC";

        Conexion conexion = new Conexion();
        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud_adopcion);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (Exception e) {
            System.out.println("Error al listar historial: " + e.getMessage());
        }
        return lista;
    }

    private String sqlBaseConJoins() {
        return "SELECT s.idSolicitud_adopcion, s.direccion, s.Departamento_idDepartamento, "
                + "s.Municipio_idMunicipio, s.Localidad_idLocalidad, s.barrio, s.profesion, s.vive_en_idvive_en, "
                + "s.tipo_vivienda_idtipo_vivienda, s.nucleo_familiar, s.tiene_mascotas, s.fecha_solicitud, "
                + "s.Usuarios_idUsuarios, s.Perrito_idPerrito, s.Estado_solicitud_idEstado_solicitud, "
                + "u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, u.documento AS documentoUsuario, "
                + "u.correo AS correoUsuario, p.nombre AS nombrePerrito, e.descripcion_estado AS descripcionEstado, "
                + "dep.nombre AS nombreDepartamento, COALESCE(mun.nombre, loc.nombre) AS nombreUbicacion "
                + "FROM solicitud_adopcion s "
                + "INNER JOIN usuarios u ON s.Usuarios_idUsuarios = u.idUsuarios "
                + "INNER JOIN perrito p ON s.Perrito_idPerrito = p.idPerrito "
                + "INNER JOIN estado_solicitud e ON s.Estado_solicitud_idEstado_solicitud = e.idEstado_solicitud "
                + "LEFT JOIN departamentos dep ON s.Departamento_idDepartamento = dep.idDepartamento "
                + "LEFT JOIN municipios mun ON s.Municipio_idMunicipio = mun.idMunicipio "
                + "LEFT JOIN localidades loc ON s.Localidad_idLocalidad = loc.idLocalidad";
    }

    private Solicitud_adopcion mapearSolicitud(ResultSet rs) throws SQLException {
        Solicitud_adopcion solicitud = new Solicitud_adopcion();
        solicitud.setIdSolicitud_adopcion(rs.getInt("idSolicitud_adopcion"));
        solicitud.setDireccion(rs.getString("direccion"));
        solicitud.setDepartamentoId(rs.getInt("Departamento_idDepartamento"));
        solicitud.setMunicipioId(rs.getObject("Municipio_idMunicipio", Integer.class));
        solicitud.setLocalidadId(rs.getObject("Localidad_idLocalidad", Integer.class));
        solicitud.setBarrio(rs.getString("barrio"));
        solicitud.setProfesion(rs.getString("profesion"));
        solicitud.setViveEnId(rs.getInt("vive_en_idvive_en"));
        solicitud.setTipoViviendaId(rs.getInt("tipo_vivienda_idtipo_vivienda"));
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
        solicitud.setNombreDepartamento(rs.getString("nombreDepartamento"));
        solicitud.setNombreUbicacion(rs.getString("nombreUbicacion"));
        return solicitud;
    }
}