/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Controlador.Conexion;
import Modelo.Tipo_Actividad;
import Modelo.Tipo_documento;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author USER
 */
public class Tipo_ActividadDAO {
    public boolean insertarTipo_Actividad(Tipo_Actividad Mitipoactividad) throws SQLException {
        boolean insertado = false;
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        String sql = "INSERT INTO tipo_actividad (idTipo_Actividad, nombre_activi) VALUES (?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Mitipoactividad.getidTipo_Actividad());
            ps.setString(2, Mitipoactividad.getnombre_activi());
            ps.executeUpdate();

            insertado = true;

            System.out.println("Tipo de actividad insertado correctamente en VIVEROBD.");

        } catch (SQLException e) {
            System.out.println("Error al insertar el Tipo de actividad." + e.getMessage());
        }
        return insertado;
    }

    public Tipo_Actividad ConsultarTipo_Actividad(int idTipo_Actividad) throws SQLException{
        Tipo_Actividad Mitipoactividad = null;
      Conexion conexion = new Conexion();
      Connection con = conexion.getConn();
      
      try{ 
          String querySQL = "select idTipo_Actividad, nombre_activi FROM tipo_actividad WHERE idTipo_Actividad = ?";
          
          PreparedStatement ps = con.prepareStatement(querySQL);
          ps.setInt(1, idTipo_Actividad);
          
          ResultSet rs = ps.executeQuery();
          
        if(rs.next()){
            Mitipoactividad = new Tipo_Actividad();
            
            Mitipoactividad.setidTipo_Actividad(idTipo_Actividad);
            Mitipoactividad.setnombre_activi(rs.getString(2));
        }
       return Mitipoactividad;
       
      } catch(Exception ex){
          System.out.println(ex.getMessage());
          return Mitipoactividad;
      }
    }

    public boolean actualizarTipoActividad(Tipo_Actividad Mitipoactividad) throws SQLException {
        boolean actualizado = false;
        String sql = "UPDATE tipo_actividad SET nombre_activi = ? WHERE idtipo_actividad = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, Mitipoactividad.getnombre_activi());
            ps.setInt(2, Mitipoactividad.getidTipo_Actividad());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Tipo de actividad actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
        return actualizado;
    }


    public boolean eliminarTipoActividad (int idTipo_Actividad) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE tipo_actividad SET activo = 0 WHERE idTipo_Actividad = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTipo_Actividad);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
                System.out.println("Tipo de actividad eliminado de VIVEROBD.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return eliminado;
    }

    public List<Tipo_Actividad> listarTipoActividad() {
        List<Tipo_Actividad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idTipo_Actividad, nombre_activi FROM tipo_actividad WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tipo_Actividad actividad = new Tipo_Actividad();
                actividad.setidTipo_Actividad(rs.getInt(1));
                actividad.setnombre_activi(rs.getString(2));
                lista.add(actividad);
            }
        } catch (Exception e) {
            System.out.println("Error al listar tipos de actividades: " + e.getMessage());
        }
        return lista;
    }

    public List<Tipo_Actividad> listarInactivas() {
        List<Tipo_Actividad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idTipo_Actividad, nombre_activi FROM tipo_actividad WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tipo_Actividad actividad = new Tipo_Actividad();
                actividad.setidTipo_Actividad(rs.getInt(1));
                actividad.setnombre_activi(rs.getString(2));
                lista.add(actividad);
            }
        } catch (Exception e) {
            System.out.println("Error al listar tipos de actividades inactivas: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarTipoActividad(int id) {
        boolean reactivado = false;
        String sql = "UPDATE tipo_actividad SET activo = 1 WHERE idTipo_Actividad = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar tipo de actividad: " + e.getMessage());
        }
        return reactivado;
    }

}
    

