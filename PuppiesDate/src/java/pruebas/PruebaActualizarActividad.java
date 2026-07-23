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
public class PruebaActualizarActividad {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ActividadDAO dao = new ActividadDAO();

        System.out.println("\nActualizar una actividad");
        System.out.print("Ingrese el ID de la actividad que desea modificar: ");
        int idModificar = sc.nextInt();
        sc.nextLine();

        System.out.print("Ingrese la NUEVA descripción para esta actividad: ");
        String nuevaDesc = sc.nextLine();

        Actividad actEditada = new Actividad();
        actEditada.setidActividad(idModificar);
        actEditada.setdescripcion_actividad(nuevaDesc);

        if (dao.actualizarActividad(actEditada)) {
            System.out.println("Actividad actualizada con éxito.");
        } else {
            System.out.println("Error: No se encontró actividad con ID: " + idModificar);
        }
    }

}
