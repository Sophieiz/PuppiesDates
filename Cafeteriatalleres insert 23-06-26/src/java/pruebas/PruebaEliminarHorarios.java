/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.HorariosDAO;
import Modelo.Horarios;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author isabe
 */
public class PruebaEliminarHorarios {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        HorariosDAO dao = new HorariosDAO();

        System.out.println("\nElminar Horario");
        System.out.print("ID del horario a eliminar: ");
        int idDel = sc.nextInt();

        if (dao.eliminarHorario(idDel)) {
            System.out.println("Horario eliminado de viverobd.");
        } else {
            System.out.println("No se encontró el ID del horario.");
        }
    }

}
