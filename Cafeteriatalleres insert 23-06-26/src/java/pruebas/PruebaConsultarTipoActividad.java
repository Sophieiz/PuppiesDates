/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.Tipo_ActividadDAO;
import Modelo.Tipo_Actividad;
import java.sql.SQLException;
import java.util.Scanner;
/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarTipoActividad {

  
    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner (System.in);
        Tipo_ActividadDAO MitipoactividadDao = new Tipo_ActividadDAO();
        
        System.out.println("Ingrese el ID del tipo de actividad: ");
        String idTipo_Actividad = leer.nextLine();
        
        Tipo_Actividad Mitipoactividad = MitipoactividadDao.ConsultarTipo_Actividad(1);
        
        if (Mitipoactividad != null) {
            System.out.println("Tipo de actividad encontrada");
            System.out.println("Tipo de Actividad: " + Mitipoactividad.getnombre_activi());
            
        }else {
            System.out.println("Tipo de actividad NO encontrada");
        }
        
        
        
    }
    
}
