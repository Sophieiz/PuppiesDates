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
import java.util.List;

/**
 *
 * @author USER
 */
public class PruebaInsertarHorarios {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Horarios horario = new Horarios();
        HorariosDAO dao = new HorariosDAO();

        System.out.print("Ingrese el ID del Horario: ");
        horario.setidHorarios(sc.nextInt());
        sc.nextLine();

        System.out.print("Ingrese la hora de inicio (Formato hh:mm:ss): ");
        horario.sethora_ini(Time.valueOf(sc.nextLine() + ":00"));

        System.out.print("Ingrese la hora de fin (Formato hh:mm:ss): ");
        horario.sethora_fin(Time.valueOf(sc.nextLine() + ":00"));

        if (dao.insertarHorarios(horario)) {
            System.out.println("\nEl horario se guardó correctamente.");
        } else {
            System.out.println("\nEl horario no se pudo guardar.");
        }
    }
}

      
      
        
        

        
       

