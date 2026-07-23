/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Modelo.Disponibilidad;
import java.util.Scanner;
import Controlador.DisponibilidadDAO;
import java.sql.SQLException;

/**
 *
 * @author isabe
 */
public class PruebaActualizarDisponibilidad {

    public static void main(String[] args) throws SQLException {
        
        Scanner sc = new Scanner(System.in);
        DisponibilidadDAO dao = new DisponibilidadDAO();
        
        
        System.out.println("\nActualizacion de cupos disponibles");
        System.out.print("ID para modificar: ");
        int idMod = sc.nextInt();
        sc.nextLine();
        Disponibilidad dispMod = new Disponibilidad();
        dispMod.setidDisponibilidad(idMod);
        System.out.print("Nueva Fecha (aaaa-mm-dd): ");
        dispMod.setfecha(java.sql.Date.valueOf(sc.nextLine()));
       
        
        if (dao.actualizarDisponibilidad(dispMod)) System.out.println("Actualizado");
    
    }
    
}
