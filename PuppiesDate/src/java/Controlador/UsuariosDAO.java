package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.Usuarios;

/**
 *
 * @author Aprendiz
 */
public class UsuariosDAO {

    Conexion conexion = new Conexion();

    public boolean insertarUsuarios(Usuarios usuarios) {
        boolean insertado = false;
        String sql = "INSERT INTO usuarios (idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarios.getidUsuarios());
            ps.setString(2, usuarios.getnombre());
            ps.setString(3, usuarios.getapellido());
            ps.setString(4, usuarios.getdocumento());
            ps.setString(5, usuarios.gettelefono());
            ps.setString(6, usuarios.getcorreo());
            ps.setString(7, usuarios.getclave());
            ps.setDate(8, new java.sql.Date(usuarios.getfecha_nac().getTime()));
            ps.setDate(9, new java.sql.Date(usuarios.getfecha_cad().getTime()));
            ps.setBoolean(10, usuarios.ischeckbox());
            ps.setInt(11, usuarios.getTipo_documento_idTipo_documento());
            ps.setInt(12, usuarios.getRoles_idRoles());

            ps.executeUpdate();
            insertado = true;
            System.out.println("Usuario insertado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }

        return insertado;
    }

    public Usuarios ConsultaUsuarios(String documento) {
        Usuarios usuario = null;
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE documento = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuarios();
                    usuario.setidUsuarios(rs.getInt("idUsuarios"));
                    usuario.setnombre(rs.getString("nombre"));
                    usuario.setapellido(rs.getString("apellido"));
                    usuario.setdocumento(rs.getString("documento"));
                    usuario.settelefono(rs.getString("telefono"));
                    usuario.setcorreo(rs.getString("correo"));
                    usuario.setclave(rs.getString("clave"));
                    usuario.setfecha_nac(rs.getDate("fecha_nac"));
                    usuario.setfecha_cad(rs.getDate("fecha_cad"));
                    usuario.setcheckbox(rs.getBoolean("checkbox"));
                    usuario.setTipo_documento_idTipo_documento(rs.getInt("Tipo_documento_idTipo_documento"));
                    usuario.setRoles_idRoles(rs.getInt("Roles_idRoles"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar usuario por documento: " + ex.getMessage());
        }

        return usuario;
    }

    public boolean actualizarUsuario(Usuarios usuarios) {
        boolean actualizado = false;
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, documento=?, telefono=?, correo=?, clave=?, fecha_nac=?, fecha_cad=?, checkbox=?, Tipo_documento_idTipo_documento=?, Roles_idRoles=? WHERE idUsuarios=?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuarios.getnombre());
            ps.setString(2, usuarios.getapellido());
            ps.setString(3, usuarios.getdocumento());
            ps.setString(4, usuarios.gettelefono());
            ps.setString(5, usuarios.getcorreo());
            ps.setString(6, usuarios.getclave());
            ps.setDate(7, new java.sql.Date(usuarios.getfecha_nac().getTime()));
            ps.setDate(8, new java.sql.Date(usuarios.getfecha_cad().getTime()));
            ps.setBoolean(9, usuarios.ischeckbox());
            ps.setInt(10, usuarios.getTipo_documento_idTipo_documento());
            ps.setInt(11, usuarios.getRoles_idRoles());
            ps.setInt(12, usuarios.getidUsuarios());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        }

        return actualizado;
    }

    public boolean eliminarUsuario(int id) {
        boolean eliminado = false;
        String sql = "UPDATE usuarios SET activo = 0 WHERE idUsuarios = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }

        return eliminado;
    }

    public List<Usuarios> listarUsuarios() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE checkbox = true";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setidUsuarios(rs.getInt("idUsuarios"));
                usuarios.setnombre(rs.getString("nombre"));
                usuarios.setapellido(rs.getString("apellido"));
                usuarios.setdocumento(rs.getString("documento"));
                usuarios.settelefono(rs.getString("telefono"));
                usuarios.setcorreo(rs.getString("correo"));
                usuarios.setclave(rs.getString("clave"));
                usuarios.setfecha_nac(rs.getDate("fecha_nac"));
                usuarios.setfecha_cad(rs.getDate("fecha_cad"));
                usuarios.setcheckbox(rs.getBoolean("checkbox"));
                usuarios.setTipo_documento_idTipo_documento(rs.getInt("Tipo_documento_idTipo_documento"));
                usuarios.setRoles_idRoles(rs.getInt("Roles_idRoles"));

                lista.add(usuarios);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    public List<Usuarios> listarInactivos() {
        List<Usuarios> lista = new ArrayList<>();
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE checkbox = false";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setidUsuarios(rs.getInt("idUsuarios"));
                usuarios.setnombre(rs.getString("nombre"));
                usuarios.setapellido(rs.getString("apellido"));
                usuarios.setdocumento(rs.getString("documento"));
                usuarios.settelefono(rs.getString("telefono"));
                usuarios.setcorreo(rs.getString("correo"));
                usuarios.setclave(rs.getString("clave"));
                usuarios.setfecha_nac(rs.getDate("fecha_nac"));
                usuarios.setfecha_cad(rs.getDate("fecha_cad"));
                usuarios.setcheckbox(rs.getBoolean("checkbox"));
                usuarios.setTipo_documento_idTipo_documento(rs.getInt("Tipo_documento_idTipo_documento"));
                usuarios.setRoles_idRoles(rs.getInt("Roles_idRoles"));

                lista.add(usuarios);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios inactivos: " + e.getMessage());
        }

        return lista;
    }

    public boolean reactivarUsuario(int id) {
        boolean reactivado = false;
        String sql = "UPDATE usuarios SET checkbox = true WHERE idUsuarios = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                reactivado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al reactivar el usuario por ID: " + e.getMessage());
        }

        return reactivado;
    }

    public boolean reactivarUsuario(Usuarios usuarios) {
        boolean reactivado = false;
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, telefono=?, correo=?, clave=?, fecha_nac=?, fecha_cad=?, checkbox = true WHERE documento=?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuarios.getnombre());
            ps.setString(2, usuarios.getapellido());
            ps.setString(3, usuarios.gettelefono());
            ps.setString(4, usuarios.getcorreo());
            ps.setString(5, usuarios.getclave());
            ps.setDate(6, new java.sql.Date(usuarios.getfecha_nac().getTime()));
            ps.setDate(7, new java.sql.Date(usuarios.getfecha_cad().getTime()));
            ps.setString(8, usuarios.getdocumento());

            if (ps.executeUpdate() > 0) {
                reactivado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al reactivar usuario con datos: " + e.getMessage());
        }

        return reactivado;
    }

    public boolean existeUsuario(String documento) {
        boolean existe = false;
        String sql = "SELECT documento FROM usuarios WHERE documento = ? AND checkbox = true";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
        }

        return existe;
    }

    public boolean existeUsuarioInactivo(String documento) {
        boolean existe = false;
        String sql = "SELECT documento FROM usuarios WHERE documento = ? AND checkbox = false";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar usuario inactivo: " + e.getMessage());
        }

        return existe;
    }

    // ======================================================================
    // MÉTODOS AGREGADOS PARA LA RECUPERACIÓN DE CONTRASEÑA
    // ======================================================================

    public Usuarios ConsultarUsuarioPorCorreo(String correo) {
        Usuarios usuario = null;
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE correo = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuarios();
                    usuario.setidUsuarios(rs.getInt("idUsuarios"));
                    usuario.setnombre(rs.getString("nombre"));
                    usuario.setapellido(rs.getString("apellido"));
                    usuario.setdocumento(rs.getString("documento"));
                    usuario.settelefono(rs.getString("telefono"));
                    usuario.setcorreo(rs.getString("correo"));
                    usuario.setclave(rs.getString("clave"));
                    usuario.setfecha_nac(rs.getDate("fecha_nac"));
                    usuario.setfecha_cad(rs.getDate("fecha_cad"));
                    usuario.setcheckbox(rs.getBoolean("checkbox"));
                    usuario.setTipo_documento_idTipo_documento(rs.getInt("Tipo_documento_idTipo_documento"));
                    usuario.setRoles_idRoles(rs.getInt("Roles_idRoles"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar usuario por correo: " + ex.getMessage());
        }

        return usuario;
    }

    public boolean actualizarClave(int idUsuarios, String nuevaClave) {
        boolean actualizado = false;
        String sql = "UPDATE usuarios SET clave = ? WHERE idUsuarios = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevaClave);
            ps.setInt(2, idUsuarios);

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Clave actualizada con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar clave: " + e.getMessage());
        }

        return actualizado;
    }



    public Usuarios ConsultarUsuarioPorId(int idUsuarios) {
        Usuarios usuario = null;
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE idUsuarios = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuarios);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuarios();
                    usuario.setidUsuarios(rs.getInt("idUsuarios"));
                    usuario.setnombre(rs.getString("nombre"));
                    usuario.setapellido(rs.getString("apellido"));
                    usuario.setdocumento(rs.getString("documento"));
                    usuario.settelefono(rs.getString("telefono"));
                    usuario.setcorreo(rs.getString("correo"));
                    usuario.setclave(rs.getString("clave"));
                    usuario.setfecha_nac(rs.getDate("fecha_nac"));
                    usuario.setfecha_cad(rs.getDate("fecha_cad"));
                    usuario.setcheckbox(rs.getBoolean("checkbox"));
                    usuario.setTipo_documento_idTipo_documento(rs.getInt("Tipo_documento_idTipo_documento"));
                    usuario.setRoles_idRoles(rs.getInt("Roles_idRoles"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar usuario por id: " + ex.getMessage());
        }

        return usuario;
}

    public boolean actualizarDatosPersonales(int idUsuarios, String nombre, String apellido, String telefono, String correo) {
        boolean actualizado = false;
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, telefono=?, correo=? WHERE idUsuarios=?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setInt(5, idUsuarios);

            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos personales: " + e.getMessage());
        }

        return actualizado;
    }

}