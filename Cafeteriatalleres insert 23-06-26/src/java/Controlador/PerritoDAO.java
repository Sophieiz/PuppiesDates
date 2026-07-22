package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Perrito;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PerritoDAO {

    Conexion conexion = new Conexion();

    private String ultimoError;

    public String getUltimoError() {
        return ultimoError;
    }

    public boolean insertarPerrito(Perrito perrito) {
        boolean insertado = false;
        Connection con = conexion.getConn();

        String sql = "INSERT INTO Perrito (nombre, especie, raza, fecha_nacimiento, sexo, microchip, "
                + "etapa_madurez, especialidad, condiciones_especiales, titulo_historia, historia, foto, "
                + "ciudad, Estado_perrito_idEstado_perrito) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, perrito.getNombre());
            ps.setString(2, perrito.getEspecie());
            ps.setString(3, perrito.getRaza());
            ps.setDate(4, perrito.getFecha_nacimiento());
            ps.setString(5, perrito.getSexo());
            ps.setString(6, perrito.getMicrochip());
            ps.setString(7, perrito.getEtapa_madurez());
            ps.setString(8, perrito.getEspecialidad());
            ps.setString(9, perrito.getCondiciones_especiales());
            ps.setString(10, perrito.getTitulo_historia());
            ps.setString(11, perrito.getHistoria());
            ps.setString(12, perrito.getFoto());
            ps.setString(13, perrito.getCiudad());
            ps.setInt(14, perrito.getEstado_perrito_idEstado_perrito());

            ps.executeUpdate();
            insertado = true;
            System.out.println("Perrito insertado con éxito.");
        } catch (SQLException e) {
            ultimoError = e.getMessage();
            System.out.println("Error al insertar Perrito: " + e.getMessage());
        }
        return insertado;
    }

    public Perrito ConsultarPerrito(int idPerrito) {
        Perrito perrito = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT p.idPerrito, p.nombre, p.especie, p.raza, p.fecha_nacimiento, p.sexo, "
                    + "p.microchip, p.etapa_madurez, p.especialidad, p.condiciones_especiales, "
                    + "p.titulo_historia, p.historia, p.foto, p.ciudad, p.Estado_perrito_idEstado_perrito, "
                    + "e.descripcion_estado AS descripcionEstado "
                    + "FROM Perrito p "
                    + "INNER JOIN Estado_perrito e ON p.Estado_perrito_idEstado_perrito = e.idEstado_perrito "
                    + "WHERE p.idPerrito = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idPerrito);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                perrito = mapearPerrito(rs);
            }
            return perrito;
        } catch (Exception ex) {
            System.out.println("Error al consultar Perrito: " + ex.getMessage());
            return perrito;
        }
    }

    public boolean actualizarPerrito(Perrito perrito) {
        boolean actualizado = false;
        String sql = "UPDATE Perrito SET nombre=?, especie=?, raza=?, fecha_nacimiento=?, sexo=?, "
                + "microchip=?, etapa_madurez=?, especialidad=?, condiciones_especiales=?, "
                + "titulo_historia=?, historia=?, foto=?, ciudad=?, Estado_perrito_idEstado_perrito=? "
                + "WHERE idPerrito=?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, perrito.getNombre());
            ps.setString(2, perrito.getEspecie());
            ps.setString(3, perrito.getRaza());
            ps.setDate(4, perrito.getFecha_nacimiento());
            ps.setString(5, perrito.getSexo());
            ps.setString(6, perrito.getMicrochip());
            ps.setString(7, perrito.getEtapa_madurez());
            ps.setString(8, perrito.getEspecialidad());
            ps.setString(9, perrito.getCondiciones_especiales());
            ps.setString(10, perrito.getTitulo_historia());
            ps.setString(11, perrito.getHistoria());
            ps.setString(12, perrito.getFoto());
            ps.setString(13, perrito.getCiudad());
            ps.setInt(14, perrito.getEstado_perrito_idEstado_perrito());
            ps.setInt(15, perrito.getIdPerrito());
            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Perrito actualizado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar Perrito: " + e.getMessage());
        }
        return actualizado;
    }

    // Se usa cuando la solicitud es aceptada, para marcar el perrito como adoptado
    public boolean actualizarEstadoPerrito(int idPerrito, int idEstado_perrito) {
        boolean actualizado = false;
        String sql = "UPDATE Perrito SET Estado_perrito_idEstado_perrito = ? WHERE idPerrito = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstado_perrito);
            ps.setInt(2, idPerrito);
            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Estado del perrito actualizado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado del Perrito: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean eliminarPerrito(int idPerrito) {
        boolean eliminado = false;
        String sql = "UPDATE Perrito SET activo = 0 WHERE idPerrito = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPerrito);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Perrito eliminado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar Perrito: " + e.getMessage());
        }
        return eliminado;
    }

    public List<Perrito> listarPerrito() {
        List<Perrito> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT p.idPerrito, p.nombre, p.especie, p.raza, p.fecha_nacimiento, p.sexo, "
                    + "p.microchip, p.etapa_madurez, p.especialidad, p.condiciones_especiales, "
                    + "p.titulo_historia, p.historia, p.foto, p.ciudad, p.Estado_perrito_idEstado_perrito, "
                    + "e.descripcion_estado AS descripcionEstado "
                    + "FROM Perrito p "
                    + "INNER JOIN Estado_perrito e ON p.Estado_perrito_idEstado_perrito = e.idEstado_perrito "
                    + "WHERE p.activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPerrito(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar Perritos: " + e.getMessage());
        }
        return lista;
    }

    // Solo los que están disponibles para adopción - para el catálogo público
    public List<Perrito> listarPerritoDisponible() {
        List<Perrito> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT p.idPerrito, p.nombre, p.especie, p.raza, p.fecha_nacimiento, p.sexo, "
                    + "p.microchip, p.etapa_madurez, p.especialidad, p.condiciones_especiales, "
                    + "p.titulo_historia, p.historia, p.foto, p.ciudad, p.Estado_perrito_idEstado_perrito, "
                    + "e.descripcion_estado AS descripcionEstado "
                    + "FROM Perrito p "
                    + "INNER JOIN Estado_perrito e ON p.Estado_perrito_idEstado_perrito = e.idEstado_perrito "
                    + "WHERE e.descripcion_estado = 'Disponible' AND p.activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPerrito(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar Perritos disponibles: " + e.getMessage());
        }
        return lista;
    }

    // Igual que listarPerritoDisponible, pero filtrando por nombre (buscador del catálogo)
    public List<Perrito> buscarPerritoDisponible(String textoBusqueda) {
        List<Perrito> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT p.idPerrito, p.nombre, p.especie, p.raza, p.fecha_nacimiento, p.sexo, "
                    + "p.microchip, p.etapa_madurez, p.especialidad, p.condiciones_especiales, "
                    + "p.titulo_historia, p.historia, p.foto, p.ciudad, p.Estado_perrito_idEstado_perrito, "
                    + "e.descripcion_estado AS descripcionEstado "
                    + "FROM Perrito p "
                    + "INNER JOIN Estado_perrito e ON p.Estado_perrito_idEstado_perrito = e.idEstado_perrito "
                    + "WHERE e.descripcion_estado = 'Disponible' AND p.activo = 1 "
                    + "AND p.nombre LIKE ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + textoBusqueda + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPerrito(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar Perritos disponibles: " + e.getMessage());
        }
        return lista;
    }

    // Valida que el microchip no esté repetido antes de insertar
    public boolean existeMicrochip(String microchip) {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT microchip FROM Perrito WHERE microchip = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, microchip);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al verificar microchip: " + e.getMessage());
            return false;
        }
    }

    // Igual que existeMicrochip pero excluyendo el propio registro (para cuando se edita un perrito)
    public boolean existeMicrochipEnOtroPerrito(String microchip, int idPerrito) {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT microchip FROM Perrito WHERE microchip = ? AND idPerrito != ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, microchip);
            ps.setInt(2, idPerrito);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al verificar microchip: " + e.getMessage());
            return false;
        }
    }

    public List<Perrito> listarInactivos() {
        List<Perrito> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT p.idPerrito, p.nombre, p.especie, p.raza, p.fecha_nacimiento, p.sexo, "
                    + "p.microchip, p.etapa_madurez, p.especialidad, p.condiciones_especiales, "
                    + "p.titulo_historia, p.historia, p.foto, p.ciudad, p.Estado_perrito_idEstado_perrito, "
                    + "e.descripcion_estado AS descripcionEstado "
                    + "FROM Perrito p "
                    + "INNER JOIN Estado_perrito e ON p.Estado_perrito_idEstado_perrito = e.idEstado_perrito "
                    + "WHERE p.activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapearPerrito(rs));
            }
        } catch (Exception e) {
            System.out.println("Error al listar Perritos inactivos: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarPerrito(int idPerrito) {
        boolean reactivado = false;
        String sql = "UPDATE Perrito SET activo = 1 WHERE idPerrito = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPerrito);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar Perrito: " + e.getMessage());
        }
        return reactivado;
    }

    private Perrito mapearPerrito(ResultSet rs) throws SQLException {
        Perrito perrito = new Perrito();
        perrito.setIdPerrito(rs.getInt("idPerrito"));
        perrito.setNombre(rs.getString("nombre"));
        perrito.setEspecie(rs.getString("especie"));
        perrito.setRaza(rs.getString("raza"));
        perrito.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
        perrito.setSexo(rs.getString("sexo"));
        perrito.setMicrochip(rs.getString("microchip"));
        perrito.setEtapa_madurez(rs.getString("etapa_madurez"));
        perrito.setEspecialidad(rs.getString("especialidad"));
        perrito.setCondiciones_especiales(rs.getString("condiciones_especiales"));
        perrito.setTitulo_historia(rs.getString("titulo_historia"));
        perrito.setHistoria(rs.getString("historia"));
        perrito.setFoto(rs.getString("foto"));
        perrito.setCiudad(rs.getString("ciudad"));
        perrito.setEstado_perrito_idEstado_perrito(rs.getInt("Estado_perrito_idEstado_perrito"));
        perrito.setDescripcionEstado_perrito(rs.getString("descripcionEstado"));
        return perrito;
    }
}
