package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Reserva;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    Conexion conexion = new Conexion();

    public boolean insertarReserva(Reserva Mireserva) {
        boolean insertado = false;
        Connection con = conexion.getConn();

        String sql = "INSERT INTO Reserva (num_personas, hora, fecha, Usuarios_idUsuarios, Disponibilidad_idDisponibilidad, Estado_reserva_idEstado_reserva, Actividad_idActividad, Pagos_idPagos) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Mireserva.getNum_personas());
            ps.setTime(2, Mireserva.getHora());
            ps.setDate(3, Mireserva.getFecha());
            ps.setInt(4, Mireserva.getUsuarios_idUsuarios());
            ps.setInt(5, Mireserva.getDisponibilidad_idDisponibilidad());
            ps.setInt(6, Mireserva.getEstado_reserva_idEstado_reserva());
            ps.setInt(7, Mireserva.getActividad_idActividad());
            ps.setInt(8, Mireserva.getPagos_idPagos());

            ps.executeUpdate();
            insertado = true;
            System.out.println("Reserva insertada con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar Reserva: " + e.getMessage());
        }
        return insertado;
    }

    public Reserva ConsultarReserva(int idReserva) throws SQLException {
        Reserva reserva = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idReserva, num_personas, hora, fecha, Usuarios_idUsuarios, Disponibilidad_idDisponibilidad, Estado_reserva_idEstado_reserva, Actividad_idActividad, Pagos_idPagos FROM Reserva WHERE idReserva = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idReserva);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reserva = new Reserva();
                reserva.setidReserva(rs.getInt(1));
                reserva.setNum_personas(rs.getInt(2));
                reserva.setHora(rs.getTime(3));
                reserva.setFecha(rs.getDate(4));
                reserva.setUsuarios_idUsuarios(rs.getInt(5));
                reserva.setDisponibilidad_idDisponibilidad(rs.getInt(6));
                reserva.setEstado_reserva_idEstado_reserva(rs.getInt(7));
                reserva.setActividad_idActividad(rs.getInt(8));
                reserva.setPagos_idPagos(rs.getInt(9));
            }
            return reserva;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return reserva;
        }
    }

    public boolean actualizarReserva(Reserva reserva) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE Reserva SET num_personas=?, hora=?, fecha=?, Usuarios_idUsuarios=?, Disponibilidad_idDisponibilidad=?, Estado_reserva_idEstado_reserva=?, Actividad_idActividad=?, Pagos_idPagos=? WHERE idReserva=?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reserva.getNum_personas());
            ps.setTime(2, reserva.getHora());
            ps.setDate(3, reserva.getFecha());
            ps.setInt(4, reserva.getUsuarios_idUsuarios());
            ps.setInt(5, reserva.getDisponibilidad_idDisponibilidad());
            ps.setInt(6, reserva.getEstado_reserva_idEstado_reserva());
            ps.setInt(7, reserva.getActividad_idActividad());
            ps.setInt(8, reserva.getPagos_idPagos());
            ps.setInt(9, reserva.getidReserva());
            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar reserva: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean eliminarReserva(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE Reserva SET activo = 0 WHERE idReserva = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar reserva: " + e.getMessage());
        }
        return eliminado;
    }

    public List<Reserva> listarReserva() {
        List<Reserva> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT r.idReserva, r.num_personas, r.hora, r.fecha, r.Usuarios_idUsuarios, "
                    + "r.Disponibilidad_idDisponibilidad, r.Estado_reserva_idEstado_reserva, "
                    + "r.Actividad_idActividad, r.Pagos_idPagos, a.descripcion_actividad AS nombreActividad "
                    + "FROM Reserva r "
                    + "INNER JOIN Actividad a ON r.Actividad_idActividad = a.idActividad "
                    + "WHERE r.activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReserva(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }
        return lista;
    }

    // Busca reservas por nombre/apellido/documento del usuario o por fecha (formato yyyy-mm-dd)
    public List<Reserva> buscarReserva(String textoBusqueda) {
        List<Reserva> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT r.idReserva, r.num_personas, r.hora, r.fecha, r.Usuarios_idUsuarios, "
                    + "r.Disponibilidad_idDisponibilidad, r.Estado_reserva_idEstado_reserva, "
                    + "r.Actividad_idActividad, r.Pagos_idPagos, a.descripcion_actividad AS nombreActividad, "
                    + "u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario "
                    + "FROM Reserva r "
                    + "INNER JOIN Actividad a ON r.Actividad_idActividad = a.idActividad "
                    + "INNER JOIN Usuarios u ON r.Usuarios_idUsuarios = u.idUsuarios "
                    + "WHERE r.activo = 1 AND ("
                    + "u.nombre LIKE ? OR u.apellido LIKE ? OR u.documento LIKE ? "
                    + "OR CAST(r.fecha AS CHAR) LIKE ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            String comodin = "%" + textoBusqueda + "%";
            ps.setString(1, comodin);
            ps.setString(2, comodin);
            ps.setString(3, comodin);
            ps.setString(4, comodin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = mapearReserva(rs);
                reserva.setNombreUsuario(rs.getString("nombreUsuario") + " " + rs.getString("apellidoUsuario"));
                lista.add(reserva);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar reservas: " + e.getMessage());
        }
        return lista;
    }

    // Para la vista "Mis Reservas" del usuario: solo las suyas, con el nombre
    // de la actividad y la descripción del estado ya resueltos.
    public List<Reserva> listarReservaPorUsuario(int idUsuarios) {
        List<Reserva> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT r.idReserva, r.num_personas, r.hora, r.fecha, r.Usuarios_idUsuarios, "
                    + "r.Disponibilidad_idDisponibilidad, r.Estado_reserva_idEstado_reserva, "
                    + "r.Actividad_idActividad, r.Pagos_idPagos, a.descripcion_actividad AS nombreActividad, "
                    + "er.descripcion_esta AS descripcionEstadoReserva "
                    + "FROM Reserva r "
                    + "INNER JOIN Actividad a ON r.Actividad_idActividad = a.idActividad "
                    + "INNER JOIN Estado_reserva er ON r.Estado_reserva_idEstado_reserva = er.idEstado_reserva "
                    + "WHERE r.activo = 1 AND r.Usuarios_idUsuarios = ? "
                    + "ORDER BY r.fecha DESC, r.hora DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuarios);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = mapearReserva(rs);
                reserva.setDescripcionEstadoReserva(rs.getString("descripcionEstadoReserva"));
                lista.add(reserva);
            }
        } catch (Exception e) {
            System.out.println("Error al listar reservas del usuario: " + e.getMessage());
        }
        return lista;
    }

    public List<Reserva> listarInactivas() {
        List<Reserva> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT r.idReserva, r.num_personas, r.hora, r.fecha, r.Usuarios_idUsuarios, "
                    + "r.Disponibilidad_idDisponibilidad, r.Estado_reserva_idEstado_reserva, "
                    + "r.Actividad_idActividad, r.Pagos_idPagos, a.descripcion_actividad AS nombreActividad "
                    + "FROM Reserva r "
                    + "INNER JOIN Actividad a ON r.Actividad_idActividad = a.idActividad "
                    + "WHERE r.activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearReserva(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar reservas inactivas: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarReserva(int id) {
        boolean reactivado = false;
        String sql = "UPDATE Reserva SET activo = 1 WHERE idReserva = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar reserva: " + e.getMessage());
        }
        return reactivado;
    }

    private Reserva mapearReserva(ResultSet rs) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setidReserva(rs.getInt("idReserva"));
        reserva.setNum_personas(rs.getInt("num_personas"));
        reserva.setHora(rs.getTime("hora"));
        reserva.setFecha(rs.getDate("fecha"));
        reserva.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));
        reserva.setDisponibilidad_idDisponibilidad(rs.getInt("Disponibilidad_idDisponibilidad"));
        reserva.setEstado_reserva_idEstado_reserva(rs.getInt("Estado_reserva_idEstado_reserva"));
        reserva.setActividad_idActividad(rs.getInt("Actividad_idActividad"));
        reserva.setPagos_idPagos(rs.getInt("Pagos_idPagos"));
        reserva.setNombreActividad(rs.getString("nombreActividad"));
        return reserva;
    }
}
