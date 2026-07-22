/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;


import Controlador.ReservaDAO;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaEliminarReserva {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ReservaDAO dao = new ReservaDAO();

        System.out.println("\nEliminar estado de reserva");
        System.out.print("Ingrese ID de la reserva que desea eliminar: ");
        int idDel = sc.nextInt();

        if (dao.eliminarReserva(idDel)) {
            System.out.println("Reserva eliminada de VIVEROBD.");
        } else {
            System.out.println("No se encontro el ID.");
        }
    }
}