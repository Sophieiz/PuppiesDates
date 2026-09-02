/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servlet;

import Controlador.CorreoUtil;
import Controlador.DisponibilidadDAO;
import Controlador.PagosDAO;
import Controlador.ReservaDAO;
import Modelo.Reserva;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ConfirmarReserva", urlPatterns = {"/ConfirmarReserva"})
public class ConfirmarReserva extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Vista/ConfirmarReserva.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null) {
            response.sendRedirect(request.getContextPath() + "/ReservaCliente");
            return;
        }

        // Declarados fuera del try para poder revertir el cupo desde el catch
        // si algo falla DESPUES de haberlo descontado (ver bloque catch abajo).
        DisponibilidadDAO disponibilidadDao = new DisponibilidadDAO();
        Integer idDisponibilidad = null;
        boolean cupoDescontado = false;

        try {
            Integer idActividad = (Integer) sesion.getAttribute("resActividad");
            idDisponibilidad = (Integer) sesion.getAttribute("resDisponibilidad");
            Integer numPersonas = (Integer) sesion.getAttribute("resNumPersonas");
            String fechaStr = (String) sesion.getAttribute("resFecha");
            String horaStr = (String) sesion.getAttribute("resHora");
            Integer idUsuario = (Integer) sesion.getAttribute("idUsuario");
            String nombreUsuario = (String) sesion.getAttribute("nombreUsuario");
            String correoUsuario = (String) sesion.getAttribute("correoUsuario");

            if (idActividad == null || idDisponibilidad == null || idUsuario == null
                    || fechaStr == null || horaStr == null) {
                response.sendRedirect(request.getContextPath() + "/ReservaCliente");
                return;
            }
            String horaCompleta = horaStr.length() == 5 ? horaStr + ":00" : horaStr;

            // IMPORTANTE: se descuenta el cupo ANTES de insertar la reserva.
            // descontarCupo() solo tiene éxito si cupo_disponible > 0 en ese momento
            // (la condición está en el propio UPDATE, a nivel de fila), así que esto
            // actúa como el "seguro" real contra overbooking cuando dos personas
            // reservan el último cupo casi al mismo tiempo.
            cupoDescontado = disponibilidadDao.descontarCupo(idDisponibilidad);

            if (!cupoDescontado) {
                request.setAttribute("resultado", "Lo sentimos, el cupo para esa fecha y hora ya no está disponible.");
                request.getRequestDispatcher("/Vista/ConfirmarReserva.jsp").forward(request, response);
                return;
            }

            PagosDAO pagosDao = new PagosDAO();
            int idPago = pagosDao.insertarPagoPendiente();

            if (idPago == -1) {
                // No se pudo generar el pago: hay que devolver el cupo que ya se descontó.
                disponibilidadDao.reponerCupo(idDisponibilidad);
                cupoDescontado = false;
                request.setAttribute("resultado", "No se pudo generar el pago, intenta de nuevo.");
                request.getRequestDispatcher("/Vista/ConfirmarReserva.jsp").forward(request, response);
                return;
            }

            Reserva reserva = new Reserva();
            reserva.setNum_personas(numPersonas);
            reserva.setFecha(java.sql.Date.valueOf(fechaStr));
            reserva.setHora(java.sql.Time.valueOf(horaCompleta));
            reserva.setActividad_idActividad(idActividad);
            reserva.setDisponibilidad_idDisponibilidad(idDisponibilidad);
            reserva.setUsuarios_idUsuarios(idUsuario);
            reserva.setEstado_reserva_idEstado_reserva(1);
            reserva.setPagos_idPagos(idPago);

            ReservaDAO reservaDao = new ReservaDAO();
            boolean exito = reservaDao.insertarReserva(reserva);

            if (exito) {
                cupoDescontado = false; // ya quedó reflejado correctamente, no hay nada que revertir

                CorreoUtil.enviarCorreoConfirmacionReservaUsuario(
                        nombreUsuario, correoUsuario, "Actividad reservada", fechaStr, horaStr, numPersonas
                );

                sesion.removeAttribute("resActividad");
                sesion.removeAttribute("resDisponibilidad");
                sesion.removeAttribute("resNumPersonas");
                sesion.removeAttribute("resFecha");
                sesion.removeAttribute("resHora");

                request.setAttribute("resultado", "¡Reserva registrada exitosamente!");
            } else {
                // La reserva no se pudo guardar: hay que devolver el cupo que ya se
                // había descontado, si no queda "perdido" para siempre.
                disponibilidadDao.reponerCupo(idDisponibilidad);
                cupoDescontado = false;
                request.setAttribute("resultado", "Error al guardar la reserva.");
            }

        } catch (IllegalArgumentException e) {
            // Fecha u hora guardadas en sesion con formato invalido.
            // Si ya se había descontado el cupo antes del error, se devuelve.
            if (cupoDescontado && idDisponibilidad != null) {
                disponibilidadDao.reponerCupo(idDisponibilidad);
            }
            request.setAttribute("resultado", "Error: los datos de la reserva no son válidos. Intenta hacer la reserva de nuevo.");
        } catch (Exception e) {
            // Cualquier otro error inesperado (ej. fallo de conexion a la base de datos, correo,
            // numPersonas nulo, etc.). Si ya se había descontado el cupo antes del error, se devuelve.
            if (cupoDescontado && idDisponibilidad != null) {
                disponibilidadDao.reponerCupo(idDisponibilidad);
            }
            request.setAttribute("resultado", "Ocurrió un error inesperado al confirmar tu reserva. Intenta de nuevo en unos minutos.");
        }

        request.getRequestDispatcher("/Vista/ConfirmarReserva.jsp").forward(request, response);
    }
}