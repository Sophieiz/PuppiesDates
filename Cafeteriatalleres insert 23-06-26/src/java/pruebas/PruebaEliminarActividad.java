/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.ActividadDAO;
import Modelo.Actividad;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaEliminarActividad {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ActividadDAO dao = new ActividadDAO();
        
        System.out.println("\n Eliminar una actividad");
        System.out.print("Ingrese el ID de la actividad que va a eliminar: ");
        int idEliminar = sc.nextInt();
        
        if (dao.eliminarActividad(idEliminar)) {
            System.out.println("Actividad eliminada de VIVEROBD.");
        } else {
            System.out.println("No se encontro ninguna actividad con el ID: " + idEliminar);
        }
        sc.close();
    }
}

       
    

