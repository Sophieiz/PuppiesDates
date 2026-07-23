/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Controlador.DisponibilidadDAO;
import Modelo.Disponibilidad;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Scanner;
import java.util.List;



/**
 *
 * @author USER
 */
public class PruebaInsertarDisponibilidad {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        DisponibilidadDAO dao = new DisponibilidadDAO();
        Disponibilidad disp = new Disponibilidad();

        System.out.print("Ingresa ID e cupo disponible: ");
        disp.setidDisponibilidad(sc.nextInt());
        sc.nextLine();
        System.out.print("Fecha (aaa-mm-dd): ");
        disp.setfecha(java.sql.Date.valueOf(sc.nextLine()));
        System.out.print("Cupo Total: ");
        disp.setcupo_total(sc.nextInt());
        System.out.print("Cupo Disponible: ");
        disp.setcupo_disponible(sc.nextInt());
        System.out.print("ID Horario: ");
        disp.setHorarios_idHorarios(sc.nextInt());

        if (dao.insertarDisponibilidad(disp)) {
            System.out.println("Guardado en viverobd");
        }

    }
}

      
       

        
        