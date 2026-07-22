/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import java.sql.SQLException;
import java.util.Scanner;
import Controlador.HorariosDAO;
import Modelo.Horarios;

/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarHorarios {

    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner(System.in);
        HorariosDAO horariosDao = new HorariosDAO();

        System.out.print("Ingrese el ID del horario: ");
        String idHorarios = leer.nextLine();

        Horarios horarios = horariosDao.ConsultarHorarios(1);

        if (horarios != null) {
            System.out.println("Horario encontrado");
            System.out.println("Hora de Inicio: " + horarios.gethora_ini());
            System.out.println("Hora de Fin: " + horarios.gethora_fin());
        }

    }

}
