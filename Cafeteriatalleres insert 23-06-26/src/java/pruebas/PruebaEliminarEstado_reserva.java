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
public class PruebaEliminarEstado_reserva {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Estado_reservaDAO dao = new Estado_reservaDAO();

        System.out.println("\nEliminar Estado");
        System.out.print("ID del estado a eliminar: ");
        int idEstado_reserva = sc.nextInt();

        if (dao.eliminarEstadoReserva(idEstado_reserva)) {
            System.out.println("Estado eliminado con exito.");
        } else {
            System.out.println("No se encontró el estado.");
        }
    }

}

