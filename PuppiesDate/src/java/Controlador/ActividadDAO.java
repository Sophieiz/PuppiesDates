/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Controlador.Conexion;
import Modelo.Actividad;
import Modelo.Estado_reserva;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Aprendiz
 */
public class ActividadDAO {

    public boolean insertarActividad(Actividad actividad) throws SQLException {
        boolean insertado = false;
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        String sql = "INSERT INTO actividad (idActividad, descripcion_actividad, Tipo_Actividad_idTipo_Actividad, Lista_Precios_idLista_Precios) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, actividad.getIdActividad());
            ps.setString(2, actividad.getDescripcion_actividad());
            ps.setInt(3, actividad.getTipo_Actividad_idTipo_Actividad());
            ps.setInt(4, actividad.getLista_Precios_idLista_Precios());
            
            ps.executeUpdate(); 
            insertado = true;
            System.out.println("Actividad insertado con exito.");

        } catch (SQLException e) {
            System.out.println("Error al insertar actividad:" + e.getMessage());
        }

        return insertado;

    }

    public Actividad ConsultaUsuarios(int idActividad){
        Actividad actividad = null;
    Conexion conexion =  new Conexion();
    Connection con = conexion.getConn();
   
    try{
        String querySQL = "SELECT idActividad, descripcion_actividad, Tipo_Actividad_idTipo_Actividad, Lista_precios_idLista_Precios) FROM Actividad WHERE idActividad = ?";
        PreparedStatement ps = con.prepareStatement(querySQL);
        ps.setInt(1, idActividad);
       
        ResultSet rs = ps.executeQuery();
       
        if(rs.next()){
            actividad = new Actividad();
            
            actividad.setidActividad(rs.getInt(1));
            actividad.setdescripcion_actividad (rs.getString(2));
            
         }
       
        return actividad;
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return actividad;
        }
    }

    

    public boolean actualizarActividad(Actividad actividad) throws SQLException {
        boolean actualizado = false;

        String sql = "UPDATE actividad SET descripcion_actividad = ?, Tipo_Actividad_idTipo_Actividad = ?, Lista_Precios_idLista_Precios = ? WHERE idActividad = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, actividad.getDescripcion_actividad());
            ps.setInt(2, actividad.getTipo_Actividad_idTipo_Actividad());
            ps.setInt(3, actividad.getLista_Precios_idLista_Precios());
            ps.setInt(4, actividad.getIdActividad());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                actualizado = true;
                System.out.println("Actividad actualizada con éxito en la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar actividad: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean eliminarActividad(int id) throws SQLException {
        boolean eliminado = false;
        String sql = "UPDATE actividad SET activo = 0 WHERE idActividad = ?";
        Conexion conexion = new Conexion();
        Connection con = (Connection) conexion.getConn();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                eliminado = true;
                System.out.println("Actividad eliminada de la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return eliminado;
    }
    
        public List<Actividad> Actividad() {
        List<Actividad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idActividad, descripcion_actividad, Tipo_Actividad_idTipo_Actividad, Lista_precios_idLista_Precios FROM Actividad WHERE activo = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actividad activi = new Actividad();
                activi.setidActividad(rs.getInt(1));
                activi.setdescripcion_actividad(rs.getString(2));
                activi.setTipo_Actividad_idTipo_Actividad(rs.getInt(3));
                activi.setLista_Precios_idLista_Precios(rs.getInt(4));
                lista.add(activi);
            }
        } catch (Exception e) {
            System.out.println("Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }

    public List<Actividad> listarInactivas() {
        List<Actividad> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idActividad, descripcion_actividad, Tipo_Actividad_idTipo_Actividad, Lista_precios_idLista_Precios FROM Actividad WHERE activo = 0";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actividad activi = new Actividad();
                activi.setidActividad(rs.getInt(1));
                activi.setdescripcion_actividad(rs.getString(2));
                activi.setTipo_Actividad_idTipo_Actividad(rs.getInt(3));
                activi.setLista_Precios_idLista_Precios(rs.getInt(4));
                lista.add(activi);
            }
        } catch (Exception e) {
            System.out.println("Error al listar actividades inactivas: " + e.getMessage());
        }
        return lista;
    }

    public boolean reactivarActividad(int id) {
        boolean reactivado = false;
        String sql = "UPDATE actividad SET activo = 1 WHERE idActividad = ?";
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            reactivado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al reactivar actividad: " + e.getMessage());
        }
        return reactivado;
    }
}
