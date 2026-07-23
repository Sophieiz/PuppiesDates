/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.ReservaDAO;
import Modelo.Reserva;
import java.sql.SQLException;
import java.util.Scanner;
/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarReserva {

    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner(System.in);
        ReservaDAO reservaDao = new ReservaDAO();

        System.out.print("Ingrese el ID de la Reserva: ");
        String idReservado = leer.nextLine();

        Reserva reserva = reservaDao.ConsultarReserva(1);

        if (reserva != null) {
            System.out.println("Reserva encontrada");
            System.out.println("fecha: " + reserva.getFecha());
            System.out.println("Hora: " + reserva.getHora());
            System.out.println("Numero de personas: " + reserva.getNum_personas());

        }

    }
}
