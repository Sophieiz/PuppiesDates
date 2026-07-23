package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import Modelo.Opcion_formulario;

public class Opcion_formularioDAO {

    Conexion conexion = new Conexion();

    // Trae las opciones de una categoría (por ejemplo "vive_en" o "tipo_vivienda")
    // ordenadas por idOpcion_formulario, para llenar los <select> del formulario.
    public List<Opcion_formulario> listarPorCategoria(String categoria) {
        List<Opcion_formulario> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection con = conexion.getConn();
        try {
            String sql = "SELECT idOpcion_formulario, categoria, descripcion "
                    + "FROM opcion_formulario WHERE categoria = ? ORDER BY idOpcion_formulario";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Opcion_formulario opcion = new Opcion_formulario();
                opcion.setIdOpcion_formulario(rs.getInt("idOpcion_formulario"));
                opcion.setCategoria(rs.getString("categoria"));
                opcion.setDescripcion(rs.getString("descripcion"));
                lista.add(opcion);
            }
        } catch (Exception e) {
            System.out.println("Error al listar Opcion_formulario: " + e.getMessage());
        }
        return lista;
    }
}
