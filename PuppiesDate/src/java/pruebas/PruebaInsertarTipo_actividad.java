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
 * @author USER
 */
public class PruebaInsertarTipo_actividad {
    
public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Tipo_Actividad Mitipoactividad = new Tipo_Actividad();
        Tipo_ActividadDAO dao = new Tipo_ActividadDAO();

        System.out.print("Ingrese el ID del tipo de actividad:");
        Mitipoactividad.setidTipo_Actividad(sc.nextInt());
        sc.nextLine();

        System.out.print("Ingrese la descripción del tipo actividad:");
        Mitipoactividad.setnombre_activi(sc.nextLine());

        boolean resultado = dao.insertarTipo_Actividad(Mitipoactividad);

        if (resultado) {
            System.out.println("\nEl Tipo de actividad se guardo con exito.");

        } else {
            System.out.println("\nNo se pudo guardar el Tipo de actividad.");
        }
    }
}
        