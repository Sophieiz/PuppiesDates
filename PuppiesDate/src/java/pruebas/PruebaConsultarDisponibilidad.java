/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Modelo.Disponibilidad;
import Controlador.DisponibilidadDAO;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarDisponibilidad {

    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner(System.in);
        DisponibilidadDAO dispoDao = new DisponibilidadDAO();

        System.out.print("Ingrse el ID de Disponibilidad: ");
        String Disponibilidad = leer.nextLine();

        Disponibilidad dispo = dispoDao.ConsultarDisponibilidad(1);

        if (dispo != null) {
            System.out.println("Dsponibilidad encontrada");
            System.out.println("fecha: " + dispo.getFecha());
            System.out.println("Cupo total: " + dispo.getCupo_total());
            System.out.println("Cupo Disponible: " + dispo.getCupo_disponible());
        } else {
            System.out.println("Disponibilidad NO encontrada");
        }

    }

}
