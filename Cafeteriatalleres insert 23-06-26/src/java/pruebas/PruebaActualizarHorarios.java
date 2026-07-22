/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Controlador.HorariosDAO;
import Modelo.Horarios;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaActualizarHorarios {

  
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        HorariosDAO dao = new HorariosDAO();
        
        System.out.println("\nActualización de Horario");
        System.out.print("ID del horario a modificar: ");
        int idMod = sc.nextInt();
        sc.nextLine(); 

        Horarios horMod = new Horarios();
        horMod.setidHorarios(idMod);

        System.out.print("Nueva hora de inicio (hh:mm:ss): ");
        horMod.sethora_ini(Time.valueOf(sc.nextLine()));

        System.out.print("Nueva hora de fin (hh:mm:ss): ");
        horMod.sethora_fin(Time.valueOf(sc.nextLine()));

        if (dao.actualizarHorario(horMod)) {
            System.out.println("Horario actualizado correctamente.");
        }
    }
    
}
