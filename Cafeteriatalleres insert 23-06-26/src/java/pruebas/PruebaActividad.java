/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Controlador.ActividadDAO;
import Modelo.Actividad;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USER
 */
public class PruebaActividad {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Actividad actividad = new Actividad();
        ActividadDAO dao = new ActividadDAO();

        System.out.print("Ingrese el ID de la Actividad:");
        actividad.setidActividad(sc.nextInt());

        System.out.print("Ingrese la descripción de la Actividad:");
        actividad.setdescripcion_actividad(sc.nextLine());
        
        System.out.print("Ingrese el ID del tipo de actividad:");
        actividad.setTipo_Actividad_idTipo_Actividad (sc.nextInt());

        System.out.print("Ingrese el ID de la lista de precios:");
        actividad.setLista_Precios_idLista_Precios (sc.nextInt());
        
        boolean resultado = dao.insertarActividad(actividad);
        if (resultado) {
            System.out.println("\n La actividad se guardo correctamente en VIVEROBD.");
        } else {
            System.out.println("\nNo se pudo guardar correctamente la actividad.");
        }

        System.out.println("\nListado de actividades");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
