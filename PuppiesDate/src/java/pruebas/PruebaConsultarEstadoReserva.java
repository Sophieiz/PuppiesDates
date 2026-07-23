/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import java.util.Scanner;
import java.sql.SQLException;
import Controlador.Estado_reservaDAO;
import Modelo.Estado_reserva;

/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarEstadoReserva {

    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner(System.in);
        Estado_reservaDAO estadoDao = new Estado_reservaDAO();

        System.out.print("Ingrese el ID del Estado_reserva: ");
        String idEstado_reserva = leer.nextLine();

        Estado_reserva estado = estadoDao.ConsultarEstado_reserva(1);

        if (estado != null) {
            System.out.println("Estado de reserva encontrada");
            System.out.println("Estado: " + estado.getdescripcion_esta());
        } else {
            System.out.println("Estado de reserva NO encontrado");
        }

    }

}
