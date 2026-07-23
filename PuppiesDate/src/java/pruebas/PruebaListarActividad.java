/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Modelo.Actividad;
import java.util.List;
import Controlador.ActividadDAO;
import java.sql.SQLException;
/**
 *
 * @author isabe
 */
public class PruebaListarActividad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        System.out.println("\n=== Listado de Actividades ===");

        ActividadDAO dao = new ActividadDAO();
        List<Actividad> lista = dao.Actividad();


        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay actividades registradas o hubo un error en la consulta.");
        } else {
            
            for (Actividad a : lista) {
                System.out.println("ID: " + a.getIdActividad() + " | Actividad: " + a.getDescripcion_actividad());
            }
        }
    }
}
