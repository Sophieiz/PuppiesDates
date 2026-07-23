<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Disponibilidad</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    </head>
    <body class="admin-form-body">
        <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>

        <c:if test="${not empty mensaje}">
            <div class="mensaje-bienvenida">
                <p>${mensaje}</p>
            </div>
        </c:if>

        <div class="admin-form-wrap">
            <div class="admin-table-card">
                <div class="disponibilidad-toolbar">
                    <h3>Disponibilidad</h3>
                    <button type="button" class="btn-admin-primario" data-open-create="true">Nueva disponibilidad</button>
                </div>

                <div class="tabla-responsive-admin">
                    <table class="tabla-lista-admin" id="tablaDisponibilidad">
                        <thead>
                            <tr>
                                <th>Fecha</th>
                                <th>Cupo total</th>
                                <th>Cupo disponible</th>
                                <th>Horario</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="disp" items="${listaDisponibilidades}">
                                <!-- Lee base de datos -->
                                <tr class="fila-admin"
                                    tabindex="0"
                                    data-admin-row="true"
                                    data-field-id="${disp.idDisponibilidad}"
                                    data-field-idDisponibilidad="${disp.idDisponibilidad}"
                                    data-field-fechaDisp="${disp.fecha}"
                                    data-field-cupoTotalDisp="${disp.cupo_total}"
                                    data-field-cupoDisponibleDisp="${disp.cupo_disponible}"
                                    data-field-horarioIdDisp="${disp.horarios_idHorarios}">
                                    
                                    <td>${disp.idDisponibilidad}</td>
                                    <td>${disp.fecha}</td>
                                    <td>${disp.cupo_total}</td>
                                    <td>${disp.cupo_disponible}</td>
                                    <td>
                                        <c:forEach var="horario" items="${listaHorarios}">
                                            <c:if test="${horario.idHorarios == disp.horarios_idHorarios}">
                                                ${horario.hora_ini} - ${horario.hora_fin}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>  
        </div>

        <!-- Editar -->
        <div class="admin-modal-overlay modal-overlay" id="modal-editar" aria-hidden="true">
            <div class="admin-modal-caja" role="dialog" aria-modal="true">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-modal-titulo" data-modal-title>Nueva disponibilidad</h2>
                <p class="admin-modal-texto">Completa los datos obligatorios para guardar el registro.</p>

                <!-- Caja de alertas enlazada con el JS -->
                <div class="admin-alerta" data-form-alert role="alert"></div>

                <form action="${ctx}/Disponibilidaad" method="POST" id="formDisponibilidad" class="admin-managed-form" 
                      data-create-action="insertar" 
                      data-edit-action="actualizar"
                      data-delete-action="inactivar"
                      data-create-title="Nueva disponibilidad" 
                      data-edit-title="Modificar disponibilidad">

                    <input type="hidden" name="accion" id="modalAccion" value="insertar">
                    <input type="hidden" name="idDisponibilidad" id="modalId">

                    <div class="campo-grupo admin-crud-field">
                        <label for="fechaDisp">Fecha:</label>
                        <input type="date" name="fechaDisp" id="fechaDisp" data-label="fecha" required>
                    </div>

                    <div class="campo-grupo admin-crud-field">
                        <label for="cupoTotalDisp">Cupo total:</label>
                        <input type="number" name="cupoTotalDisp" id="cupoTotalDisp" min="0" data-label="cupo total" required>
                    </div>

                    <div class="campo-grupo admin-crud-field">
                        <label for="cupoDisponibleDisp">Cupo disponible:</label>
                        <input type="number" name="cupoDisponibleDisp" id="cupoDisponibleDisp" min="0" data-label="cupo disponible" required>
                    </div>

                    <div class="campo-grupo admin-crud-field">
                        <label for="horarioIdDisp">Horario:</label>
                        <select name="horarioIdDisp" id="horarioIdDisp" data-label="horario" required>
                            <option value="">Seleccione un horario</option>
                            <c:forEach var="horario" items="${listaHorarios}">
                                <option value="${horario.idHorarios}">${horario.hora_ini} - ${horario.hora_fin}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="modal-acciones">
                        <button type="button" class="btn-admin-peligro solo-edicion" data-open-delete="true">Eliminar</button>
                        <button type="button" class="btn-admin-secundario" data-cerrar="modal-editar">Cancelar</button>
                        <button type="submit" class="btn-admin-primario">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal Confirmación Eliminar -->
        <div class="admin-modal-overlay modal-overlay" id="modal-confirmar-eliminar" aria-hidden="true">
            <div class="admin-modal-caja admin-modal-confirmar" role="dialog" aria-modal="true">
                <h2 class="admin-modal-titulo">¿Está seguro de eliminar este registro?</h2>
                <div class="modal-acciones">
                    <button type="button" class="btn-admin-secundario" data-cerrar="modal-confirmar-eliminar">No</button>
                    <button type="button" class="btn-admin-peligro" data-confirm-delete="true">Sí</button>
                </div>
            </div>
        </div>

        <script src="${ctx}/Vista/JavaScript/Admi_modales.js"></script>
    </body>
</html>