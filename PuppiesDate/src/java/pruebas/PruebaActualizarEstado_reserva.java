/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.Estado_reservaDAO;
import Modelo.Estado_reserva;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaActualizarEstado_reserva {
   
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Estado_reservaDAO dao = new Estado_reservaDAO(); 
        
        System.out.println("\nActualizar estado");
        System.out.print("ID del estado a modificar: ");
        int idMod = sc.nextInt();
        sc.nextLine();
        
        Estado_reserva estMod = new Estado_reserva();
        estMod.setidEstado_reserva(idMod);
        
        System.out.print("Nueva Descripción: ");
        estMod.setdescripcion_esta(sc.nextLine());
        
        if (dao.actualizarEstadoReserva(estMod)) {
            System.out.println("Estado actualizado correctamente.");
        }
        
    
    }
    
}
