/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import Controlador.DisponibilidadDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ConsultarFechasDisponibles", urlPatterns = {"/ConsultarFechasDisponibles"})
public class ConsultarFechasDisponibles extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DisponibilidadDAO disponibilidadDao = new DisponibilidadDAO();
        request.setAttribute("fechasDisponibles", disponibilidadDao.listarFechasDisponiblesFuturas());
        request.getRequestDispatcher("/Vista/FechasDisponiblesFragment.jsp").forward(request, response);
    }
}