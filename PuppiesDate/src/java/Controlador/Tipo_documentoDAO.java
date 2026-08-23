package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.Tipo_documento;

public class Tipo_documentoDAO {

    private final Conexion conexion = new Conexion();

    public boolean insertarTipo_documento(Tipo_documento documento) {
        boolean insertado = false;
        String sql = "INSERT INTO tipo_documento (idTipo_documento, descripcion_doc) VALUES (?, ?)";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, documento.getidTipo_documento());
            ps.setString(2, documento.getdescripcion_doc());
            ps.executeUpdate();
            insertado = true;
            System.out.println("Tipo de documento insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el Tipo de documento: " + e.getMessage());
        }
        return insertado;
    }

    public Tipo_documento ConsultarTipo_documento(int idTipo_documento) {
        Tipo_documento documento = null;
        String sql = "SELECT idTipo_documento, descripcion_doc, solo_numeros FROM tipo_documento WHERE idTipo_documento = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo_documento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    documento = new Tipo_documento();
                    documento.setidTipo_documento(rs.getInt("idTipo_documento"));
                    documento.setdescripcion_doc(rs.getString("descripcion_doc"));
                    documento.setSolo_numeros(rs.getBoolean("solo_numeros"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar Tipo de documento: " + e.getMessage());
        }
        return documento;
    }

    public boolean actualizarTipoDocumento(Tipo_documento documento) {
        boolean actualizado = false;
        String sql = "UPDATE tipo_documento SET descripcion_doc = ?, solo_numeros = ? WHERE idTipo_documento = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento.getdescripcion_doc());
            ps.setBoolean(2, documento.isSolo_numeros());
            ps.setInt(3, documento.getidTipo_documento());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Tipo de documento actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean eliminarTipoDocumento(int id) {
        boolean eliminado = false;
        String sql = "UPDATE tipo_documento SET activo = 0 WHERE idTipo_documento = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Tipo de documento eliminado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return eliminado;
    }

public List<Tipo_documento> listarTipoDocumento() {
    List<Tipo_documento> lista = new ArrayList<>();
    String sql = "SELECT idTipo_documento, descripcion_doc, solo_numeros FROM tipo_documento WHERE activo = 1";

    try (Connection con = conexion.getConn();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Tipo_documento doc = new Tipo_documento();
            doc.setidTipo_documento(rs.getInt("idTipo_documento"));
            doc.setdescripcion_doc(rs.getString("descripcion_doc"));
            doc.setSolo_numeros(rs.getBoolean("solo_numeros"));
            lista.add(doc);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar tipos de documento: " + e.getMessage());
    }
    return lista;
}

    public List<Tipo_documento> listarInactivos() {
        List<Tipo_documento> lista = new ArrayList<>();
        String sql = "SELECT idTipo_documento, descripcion_doc FROM tipo_documento WHERE activo = 0";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tipo_documento doc = new Tipo_documento();
                doc.setidTipo_documento(rs.getInt("idTipo_documento"));
                doc.setdescripcion_doc(rs.getString("descripcion_doc"));
                lista.add(doc);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar tipos de documento inactivos: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarTipoDocumento(int id) {
        boolean reactivado = false;
        String sql = "UPDATE tipo_documento SET activo = 1 WHERE idTipo_documento = ?";

        try (Connection con = conexion.getConn();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar tipo de documento: " + e.getMessage());
        }
        return reactivado;
    }
}