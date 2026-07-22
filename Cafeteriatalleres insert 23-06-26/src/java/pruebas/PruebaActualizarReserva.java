/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Controlador.ReservaDAO;
import Modelo.Reserva;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaActualizarReserva {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        ReservaDAO dao = new ReservaDAO();

        System.out.println("\nModificar reserva existente");
        System.out.print("Ingrese el ID de la reserva que desea modificar: ");
        int idMod = sc.nextInt();
        sc.nextLine();

        Reserva resAct = new Reserva();
        resAct.setidReserva(idMod);

        System.out.print("Ingrese la nueva cantidad de personas: ");
        resAct.setNum_personas(sc.nextInt());

        System.out.print("Ingrese la nueva hora: ");
        resAct.setHora(Time.valueOf(sc.nextLine()));

        System.out.print("Ingrese la nueva fecha: ");
        resAct.setFecha(Date.valueOf(sc.nextLine()));

        System.out.print("Ingrese el nuevo ID de Usuario asignado: ");
        resAct.setUsuarios_idUsuarios(sc.nextInt());

        System.out.print("Ingrese el nuevo ID de la nueva disponibilidad: ");
        resAct.setDisponibilidad_idDisponibilidad(sc.nextInt());

        System.out.print("Ingrese el nuevo ID del nuevo estado de reserva: ");
        resAct.setEstado_reserva_idEstado_reserva(sc.nextInt());

        System.out.print("Ingrese el nuevo ID de la nueva actividad: ");
        resAct.setActividad_idActividad(sc.nextInt());

        System.out.print("Ingrese el nuevo ID del nuevo estado de pago : ");
        resAct.setPagos_idPagos(sc.nextInt());

        if (dao.actualizarReserva(resAct)) {
            System.out.println(" La reserva ha sido actualizada correctamente.");
        } else {
            System.out.println("Error: No se encontró ninguna reserva con el ID " + idMod);
        }
    }

}
