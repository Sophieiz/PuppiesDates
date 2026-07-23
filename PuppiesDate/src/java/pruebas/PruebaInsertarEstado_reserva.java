/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.Estado_reservaDAO;
import Modelo.Estado_reserva;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

/**
 *
 * @author USER
 */
public class PruebaInsertarEstado_reserva {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Estado_reserva estado = new Estado_reserva();
        Estado_reservaDAO dao = new Estado_reservaDAO();

        System.out.print("Ingrese el ID del Estado de Reserva: ");
        estado.setidEstado_reserva(sc.nextInt());
        sc.nextLine();

        System.out.print("Ingrese la descripción del Estado (ej: Pendiente): ");
        estado.setdescripcion_esta(sc.nextLine());

        boolean resultado = dao.insertarEstadoReserva(estado);
        if (resultado) {
            System.out.println("\nEl Estado se guardo correctamente en VIVEROBD.");
        } else {
            System.out.println("\nEl estado no se guardo.");
        }

    }
}
       

        
