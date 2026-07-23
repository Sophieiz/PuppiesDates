/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.ActividadDAO;
import java.sql.SQLException;
import java.util.Scanner;
import Modelo.Actividad;
import Controlador.DisponibilidadDAO;
/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarActividad {


    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner (System.in);
        ActividadDAO  actividadDao  = new ActividadDAO();
        
        
        System.out.print("Ingrese el ID de la actividad: ");
        String idActividad = leer.nextLine();
        
        Actividad actividad = actividadDao.ConsultaUsuarios(1);
        
        if (actividad != null){
            System.out.println("Actividad encontrada");
            System.out.println("Actividad: " + actividad.getDescripcion_actividad());
        }else {
            System.out.println("Actividad NO encontrada");
        }
        
        
     
    }
    
}
