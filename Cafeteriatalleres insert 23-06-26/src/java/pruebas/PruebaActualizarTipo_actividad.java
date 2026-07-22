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
public class PruebaActualizarTipo_actividad {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Tipo_ActividadDAO dao = new Tipo_ActividadDAO();

        System.out.println("\nActualizar tipo de actividad");
        System.out.print("ID del tipo de actividad a modificar: ");
        int idMod = sc.nextInt();
        sc.nextLine();

        Tipo_Actividad docMod = new Tipo_Actividad();
        docMod.setidTipo_Actividad(idMod);
        System.out.print("Nueva descripción del tipo de documeno: ");
        docMod.setnombre_activi(sc.nextLine());

        if (dao.actualizarTipoActividad(docMod)) {
            System.out.println("Cambio guardado en viverodb.");
        }
    }
}
    
    

