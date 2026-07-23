<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Reservas</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    </head>
    <body class="admin-form-body">
        <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>

        <c:if test="${not empty resultado}">
            <div class="mensaje-bienvenida">
                <p>${resultado}</p>
            </div>
        </c:if>

        <div class="admin-form-wrap">
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

                <p class="admin-crud-help">Haz clic sobre una fila para modificar o eliminar el registro.</p>

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
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="reserva" items="${listaReservas}">
                                <tr class="admin-crud-row" 
                                    data-admin-row 
                                    tabindex="0"
                                    data-field-id="${reserva.idReserva}"
                                    data-field-idReserva="${reserva.idReserva}"
                                    data-field-num_personas="${reserva.num_personas}"
                                    data-field-hora="${reserva.hora}"
                                    data-field-fecha="${reserva.fecha}"
                                    data-field-Usuarios_idUsuarios="${reserva.usuarios_idUsuarios}"
                                    data-field-Disponibilidad_idDisponibilidad="${reserva.disponibilidad_idDisponibilidad}"
                                    data-field-Estado_reserva_idEstado_reserva="${reserva.estado_reserva_idEstado_reserva}"
                                    data-field-Actividad_idActividad="${reserva.actividad_idActividad}"
                                    data-field-Pagos_idPagos="${reserva.pagos_idPagos}"
                                    data-duplicate-key="${reserva.num_personas}|${reserva.hora}|${reserva.fecha}|${reserva.usuarios_idUsuarios}|${reserva.disponibilidad_idDisponibilidad}|${reserva.estado_reserva_idEstado_reserva}|${reserva.actividad_idActividad}|${reserva.pagos_idPagos}">
                                    
                                    <td>${reserva.idReserva}</td>
                                    <td>${reserva.num_personas}</td>
                                    <td>${reserva.hora}</td>
                                    <td>${reserva.fecha}</td>
                                    <td>${reserva.usuarios_idUsuarios}</td>
                                    <td>${reserva.disponibilidad_idDisponibilidad}</td>
                                    <td>${reserva.estado_reserva_idEstado_reserva}</td>
                                    <td>${reserva.nombreActividad}</td>
                                    <td>${reserva.pagos_idPagos}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- MODAL DE EDICIÓN -->
        <div class="admin-crud-modal modal-overlay" id="modal-editar" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title" data-modal-title>Modificar reserva</h2>

                <form action="${ctx}/ReservaAdmi" method="POST" class="admin-managed-form"
                      data-id-name="idReserva"
                      data-duplicate-keys="num_personas,hora,fecha,Usuarios_idUsuarios,Disponibilidad_idDisponibilidad,Estado_reserva_idEstado_reserva,Actividad_idActividad,Pagos_idPagos"
                      data-edit-action="modificar" 
                      data-edit-title="Modificar reserva">

                    <input type="hidden" id="modalAccion" name="accion" value="modificar">
                    <input type="hidden" id="modalId" name="id" value="">
                    <input type="hidden" name="idReserva">
                    <input type="hidden" name="Actividad_idActividad">

                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="num_personas">Personas:</label>
                        <input type="number" min="1" name="num_personas" id="num_personas" data-label="número de personas" data-required-message="El número de personas es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="hora">Hora:</label>
                        <input type="time" name="hora" id="hora" data-label="hora" data-article="La" data-required-message="La hora es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="fecha">Fecha:</label>
                        <input type="date" name="fecha" id="fecha" data-label="fecha" data-article="La" data-required-message="La fecha es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="Usuarios_idUsuarios">Usuario:</label>
                        <input type="number" min="1" name="Usuarios_idUsuarios" id="Usuarios_idUsuarios" data-label="usuario" data-required-message="El usuario es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="Disponibilidad_idDisponibilidad">Disponibilidad:</label>
                        <input type="number" min="1" name="Disponibilidad_idDisponibilidad" id="Disponibilidad_idDisponibilidad" data-label="disponibilidad" data-article="La" data-required-message="La disponibilidad es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="Estado_reserva_idEstado_reserva">Estado:</label>
                        <input type="number" min="1" name="Estado_reserva_idEstado_reserva" id="Estado_reserva_idEstado_reserva" data-label="estado" data-required-message="El estado es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="Pagos_idPagos">Pago:</label>
                        <input type="number" min="1" name="Pagos_idPagos" id="Pagos_idPagos" data-label="pago" data-required-message="El pago es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-danger admin-crud-only-edit" data-open-delete>Inactivar</button>
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-editar">Cancelar</button>
                        <button type="submit" class="admin-crud-btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

      
        <div class="admin-crud-modal modal-overlay" id="modal-confirmar-eliminar" aria-hidden="true">
            <div class="admin-crud-modal-box admin-crud-confirm">
                <h2 class="admin-crud-title">¿Está seguro de inactivar este registro? No se eliminará, solo dejará de estar disponible.</h2>
                <form action="${ctx}/ReservaAdmi" method="POST">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idReserva">
                    <div class="admin-crud-actions">
                        <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                        <button type="submit" class="admin-crud-btn-danger">Sí</button>
                    </div>
                </form>
            </div>
        </div>

        <script src="${ctx}/Vista/JavaScript/Admi_modales.js"></script>
    </body>
</html>