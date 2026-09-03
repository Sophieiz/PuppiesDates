<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Reservas</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    </head>
    <body class="admin-form-body">
        <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>

        <c:if test="${not empty resultado}">
            <div class="mensaje-bienvenida">
                <p>${resultado}</p>
            </div>
        </c:if>

        <div class="admin-form-wrap admin-users-wrap">
            <div class="admin-table-card">
                <div class="admin-crud-toolbar">
                    <h3>Reservas</h3>
                </div>

                <form action="${ctx}/ReservaAdmi" method="GET" class="admin-buscador">
                    <span class="icono-buscar" aria-hidden="true"></span>
                    <input type="text" name="buscar" placeholder="Buscar por usuario, documento o fecha (yyyy-mm-dd)..." value="${terminoBusqueda}">
                    <button type="submit">Buscar</button>
                    <c:if test="${not empty terminoBusqueda}">
                        <a href="${ctx}/ReservaAdmi" class="admin-buscador-limpiar">Ver todas</a>
                    </c:if>
                </form>

                <p class="admin-crud-help">Usa el botón "Cambiar estado" para actualizar el estado de una reserva.</p>

                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                <th>Personas</th>
                                <th>Hora</th>
                                <th>Fecha</th>
                                <th>Usuario</th>
                                <th>Disponibilidad</th>
                                <th>Estado</th>
                                <th>Actividad</th>
                                <th>Pago</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reserva" items="${listaReservas}">
                                <tr class="admin-crud-row">
                                    <td data-label="Personas">${reserva.num_personas}</td>
                                    <td data-label="Hora">${reserva.hora}</td>
                                    <td data-label="Fecha">${reserva.fecha}</td>
                                    <td data-label="Usuario">${reserva.nombreUsuario}</td>
                                    <td data-label="Disponibilidad">${reserva.cupoDisponible}/${reserva.cupoTotal} cupos</td>
                                    <td data-label="Estado">${reserva.descripcionEstadoReserva}</td>
                                    <td data-label="Actividad">
                                        <details class="detalle-actividad">
                                            <summary>Ver actividad</summary>
                                            <p>${reserva.nombreActividad}</p>
                                        </details>
                                    </td>
                                    <td data-label="Pago">${reserva.estadoPago}</td>
                                    <td data-label="Acciones">
                                        <button type="button" class="admin-crud-btn-primary admin-crud-btn-sm"
                                                data-abrir-cambio-estado-reserva
                                                data-id="${reserva.idReserva}"
                                                data-estado-actual="${reserva.estado_reserva_idEstado_reserva}">
                                            Cambiar estado
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- MODAL CAMBIAR ESTADO (independiente, al mismo nivel que el resto) -->
        <div class="admin-crud-modal modal-overlay" id="modal-cambiar-estado-reserva" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar-estado-reserva aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title">Cambiar estado de la reserva</h2>
                <form action="${ctx}/ReservaAdmi" method="POST">
                    <input type="hidden" name="accion" value="actualizarEstado">
                    <input type="hidden" name="idReserva" id="idReservaEstadoModal">
                    <div class="admin-crud-field">
                        <label for="Estado_reserva_idEstado_reserva_modal">Nuevo estado:</label>
                        <select name="Estado_reserva_idEstado_reserva" id="Estado_reserva_idEstado_reserva_modal" required>
                            <c:forEach var="estado" items="${listaEstadosReserva}">
                                <option value="${estado.idEstado_reserva}">${estado.descripcion_esta}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar-estado-reserva>Cancelar</button>
                        <button type="submit" class="admin-crud-btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        <script src="${ctx}/Vista/JavaScript/Admi_modales.js"></script>
    </body>
</html>
