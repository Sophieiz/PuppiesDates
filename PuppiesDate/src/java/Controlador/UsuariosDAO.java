package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Usuarios;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author Aprendiz
 */
public class UsuariosDAO {

    Conexion conexion = new Conexion();

    public boolean insertarUsuarios(Usuarios usuarios) throws SQLException {
        boolean insertado = false;
        Connection con = conexion.getConn();

        String sql = "INSERT INTO usuarios (idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
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
            System.out.println("Error al insertar usuario." + e.getMessage());
        }
        return insertado;
    }

    public Usuarios ConsultaUsuarios(String documento) {
        Usuarios usuario = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();

        try {
            String querySQL = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE documento = ? ";
            PreparedStatement ps = con.prepareStatement(querySQL);
            ps.setString(1, documento);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setidUsuarios(rs.getInt(1)); 
                usuario.setnombre(rs.getString(2));
                usuario.setapellido(rs.getString(3));
                usuario.setdocumento(rs.getString(4));
                usuario.settelefono(rs.getString(5));
                usuario.setcorreo(rs.getString(6));
                usuario.setclave(rs.getString(7));
                usuario.setfecha_nac(rs.getDate(8));
                usuario.setfecha_cad(rs.getDate(9));
                usuario.setcheckbox(rs.getBoolean(10));
                usuario.setTipo_documento_idTipo_documento(rs.getInt(11));
                usuario.setRoles_idRoles(rs.getInt(12));

            }

            return usuario;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return usuario;
        }
    }

    public boolean actualizarUsuario(Usuarios usuarios) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, documento=?, telefono=?, correo=?, clave=?, fecha_nac=?, fecha_cad=?, checkbox=?, Tipo_documento_idTipo_documento=?, Roles_idRoles=? WHERE idUsuarios=?";        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
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

    public boolean eliminarUsuario(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE usuarios SET activo = 0 WHERE idUsuarios = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
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
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE activo = 1";  

        try (PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setidUsuarios(rs.getInt(1));
                usuario.setnombre(rs.getString(2));
                usuario.setapellido(rs.getString(3));
                usuario.setdocumento(rs.getString(4));
                usuario.settelefono(rs.getString(5));
                usuario.setcorreo(rs.getString(6));
                usuario.setclave(rs.getString(7));
                usuario.setfecha_nac(rs.getDate(8));
                usuario.setfecha_cad(rs.getDate(9));
                usuario.setcheckbox(rs.getBoolean(10));
                usuario.setTipo_documento_idTipo_documento(rs.getInt(11));
                usuario.setRoles_idRoles(rs.getInt(12));
                

                lista.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Usuarios> listarInactivos() {
        List<Usuarios> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles FROM usuarios WHERE activo = 0";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setidUsuarios(rs.getInt(1));
                usuario.setnombre(rs.getString(2));
                usuario.setapellido(rs.getString(3));
                usuario.setdocumento(rs.getString(4));
                usuario.settelefono(rs.getString(5));
                usuario.setcorreo(rs.getString(6));
                usuario.setclave(rs.getString(7));
                usuario.setfecha_nac(rs.getDate(8));
                usuario.setfecha_cad(rs.getDate(9));
                usuario.setcheckbox(rs.getBoolean(10));
                usuario.setTipo_documento_idTipo_documento(rs.getInt(11));
                usuario.setRoles_idRoles(rs.getInt(12));

                lista.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error al listar usuarios inactivos: " + e.getMessage());
        }
        return lista;
    }

    // Reactivación desde la Papelería del admin (por id, sin reescribir el resto de los datos)
    public boolean reactivarUsuario(int id) {
        boolean reactivado = false;
        String sql = "UPDATE usuarios SET activo = 1 WHERE idUsuarios = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar el usuario: " + e.getMessage());
        }
        return reactivado;
    }

    public boolean existeUsuario(String documento) {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT documento FROM usuarios WHERE documento = ? AND checkbox = true";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al verificar usuario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean existeUsuarioInactivo(String documento) {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT documento FROM usuarios WHERE documento = ? AND checkbox = false";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al verificar usuario inactivo: " + e.getMessage());
            return false;
        }
    }
    
    public boolean reactivarUsuario(Usuarios usuarios) {
    Conexion conexion = new Conexion();
    Connection con = conexion.getConn();
    String sql = "UPDATE usuarios SET nombre=?, apellido=?, telefono=?, correo=?, clave=?, fecha_nac=?, fecha_cad=?, checkbox=true WHERE documento=?";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, usuarios.getnombre());
        ps.setString(2, usuarios.getapellido());
        ps.setString(3, usuarios.gettelefono());
        ps.setString(4, usuarios.getcorreo());
        ps.setString(5, usuarios.getclave());
        ps.setDate(6, new java.sql.Date(usuarios.getfecha_nac().getTime()));
        ps.setDate(7, new java.sql.Date(usuarios.getfecha_cad().getTime()));
        ps.setString(8, usuarios.getdocumento());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.out.println("Error al reactivar usuario: " + e.getMessage());
        return false;
    }
}

    // ======================================================================
    // MÉTODOS NUEVOS PARA "¿OLVIDASTE TU CONTRASEÑA?"
    // ======================================================================

    public Usuarios ConsultarUsuarioPorCorreo(String correo) {
        Usuarios usuario = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idUsuarios, nombre, apellido, documento, telefono, correo, clave, fecha_nac, "
                    + "fecha_cad, checkbox, Tipo_documento_idTipo_documento, Roles_idRoles "
                    + "FROM usuarios WHERE correo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setidUsuarios(rs.getInt(1));
                usuario.setnombre(rs.getString(2));
                usuario.setapellido(rs.getString(3));
                usuario.setdocumento(rs.getString(4));
                usuario.settelefono(rs.getString(5));
                usuario.setcorreo(rs.getString(6));
                usuario.setclave(rs.getString(7));
                usuario.setfecha_nac(rs.getDate(8));
                usuario.setfecha_cad(rs.getDate(9));
                usuario.setcheckbox(rs.getBoolean(10));
                usuario.setTipo_documento_idTipo_documento(rs.getInt(11));
                usuario.setRoles_idRoles(rs.getInt(12));
            }
            return usuario;
        } catch (Exception ex) {
            System.out.println("Error al consultar usuario por correo: " + ex.getMessage());
            return usuario;
        }
    }

    public boolean actualizarClave(int idUsuarios, String nuevaClave) {
        boolean actualizado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        String sql = "UPDATE usuarios SET clave = ? WHERE idUsuarios = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
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
}