<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Editar Reserva - SOUL PAWS</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="stylesheet" href="${ctx}/Vista/Css/formulario-reserva-styles.css">
    </head>
    <body class="reserva-page">
        <c:set var="activePage" value="reservas" scope="request"/>
        <%@ include file="Header.jsp" %>
        <a href="${ctx}/MisReservas" class="btn-volver-inicio">&larr;</a>

        <form action="${ctx}/GestionarReserva" method="post" id="formEditarReserva"
              onsubmit="return false;">
            <div class="Formulario">
                <h2 class="titulo-form">Editar reserva</h2>
                <hr>
                <input type="hidden" name="id" value="${reserva.idReserva}">

                <div class="campo-reserva">
                    <label for="num_personas">Número de personas</label>
                    <input type="number" name="num_personas" id="num_personas"
                           value="${reserva.num_personas}" min="1" max="20" required>
                </div>

                <div class="campo-reserva">
                    <label for="fecha">Fecha de la reserva</label>
                    <input type="date" name="fecha" id="fecha"
                           value="<fmt:formatDate value="${reserva.fecha}" pattern="yyyy-MM-dd"/>" required>
                </div>

                <div class="campo-reserva">
                    <label for="hora">Hora de la reserva</label>
                    <input type="time" name="hora" id="hora" min="08:00" max="17:00"
                           value="<fmt:formatDate value="${reserva.hora}" pattern="HH:mm"/>" required>
                    <span class="hint-hora">Horario de atención: 8:00 AM a 5:00 PM</span>
                </div>

                <button type="button" onclick="abrirModalGuardar()">Guardar cambios</button>
            </div>
        </form>

        <!-- Modal de confirmación antes de guardar -->
        <div class="admin-modal-overlay" id="modalGuardar" style="display:none;">
            <div class="admin-modal-caja">
                <button type="button" class="admin-modal-cerrar" onclick="cerrarModalGuardar()">&times;</button>
                <h3>¿Estás seguro?</h3>
                <p>Vas a modificar tu reserva con los nuevos datos ingresados.</p>
                <div style="display:flex; gap:10px; justify-content:center; margin-top:16px;">
                    <button type="button" class="btn-mini" onclick="cerrarModalGuardar()">No, revisar de nuevo</button>
                    <button type="button" class="btn-mini" onclick="confirmarGuardar()">Sí, guardar cambios</button>
                </div>
            </div>
        </div>

        <script>
            function abrirModalGuardar() {
                var form = document.getElementById('formEditarReserva');
                if (!form.reportValidity()) {
                    return;
                }
                document.getElementById('modalGuardar').style.display = 'flex';
            }
            function cerrarModalGuardar() {
                document.getElementById('modalGuardar').style.display = 'none';
            }
            function confirmarGuardar() {
                document.getElementById('formEditarReserva').submit();
            }
        </script>

        <%@ include file="Footer.jsp" %>
    </body>
</html>
