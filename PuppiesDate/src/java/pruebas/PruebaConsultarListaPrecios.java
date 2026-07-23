/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;
import Controlador.Lista_preciosDAO;
import Modelo.Lista_precios;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Aprendiz
 */
public class PruebaConsultarListaPrecios {

    public static void main(String[] args) throws SQLException {
        Scanner leer = new Scanner(System.in);
        Lista_preciosDAO preciosDao = new Lista_preciosDAO();

        System.out.print("Ingrese el ID del precio: ");
        String idLista_precios = leer.nextLine();

        Lista_precios precios = preciosDao.ConsultarLista_precios(1);

        if (precios != null) {

            System.out.println("Precio encontrado");
            System.out.println("Precio: " + precios.getdescrip_precio());
        } else {
            System.out.println("Precio NO encontrado");
        }

    }

}
