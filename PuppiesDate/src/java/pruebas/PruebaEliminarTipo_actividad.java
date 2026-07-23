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
 * @author isabe
 */
public class PruebaEliminarTipo_actividad {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Tipo_ActividadDAO dao = new Tipo_ActividadDAO();
        
        
        System.out.println("\nEliminar Tipo de actividad");
        System.out.print("ID del Tipo de actividad  que va a eliminar: ");
        int idDel = sc.nextInt();

        if (dao.eliminarTipoActividad(idDel)) {
            System.out.println("Tipo documento eliminado de viverodb.");
        }
    
    }
    
}
