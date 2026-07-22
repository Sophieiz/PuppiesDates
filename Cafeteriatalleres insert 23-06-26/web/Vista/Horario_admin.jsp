<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Horarios</title>
    <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
</head>
<body class="admin-form-body">
    <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>
    <c:if test="${not empty mensaje}"><div class="mensaje-bienvenida"><p>${mensaje}</p></div></c:if>

    <div class="admin-form-wrap">
        <div class="admin-table-card">
            <div class="admin-crud-toolbar">
                <h3>Horarios</h3>
                <button type="button" class="admin-crud-btn-primary" data-open-create>Nuevo horario</button>
            </div>
            <p class="admin-crud-help">Haz clic sobre una fila para modificar o eliminar el registro.</p>
            <div class="admin-crud-table-wrap">
                <table class="admin-crud-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Hora inicio</th>
                            <th>Hora fin</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="horario" items="${listaHorarios}">
                            <tr class="admin-crud-row" data-admin-row tabindex="0"
                                data-field-id="${horario.idHorarios}"
                                data-field-idHorarios="${horario.idHorarios}"
                                data-field-hora_ini="${horario.hora_ini}"
                                data-field-hora_fin="${horario.hora_fin}"
                                data-duplicate-key="${horario.hora_ini}|${horario.hora_fin}">
                                <td>${horario.idHorarios}</td>
                                <td>${horario.hora_ini}</td>
                                <td>${horario.hora_fin}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="admin-crud-modal modal-overlay" id="modal-editar" aria-hidden="true">
        <div class="admin-crud-modal-box">
            <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
            <h2 class="admin-crud-title" data-modal-title>Nuevo horario</h2>
            <form action="${ctx}/Horarios" method="POST" class="admin-managed-form"
                  data-id-name="idHorarios" data-duplicate-keys="hora_ini,hora_fin"
                  data-create-action="insertar" data-edit-action="actualizar"
                  data-create-title="Nuevo horario" data-edit-title="Modificar horario">
                <input type="hidden" id="modalAccion" name="accion" value="insertar">
                <input type="hidden" id="modalId" name="id" value="">
                <input type="hidden" name="idHorarios">
                <div class="admin-crud-alert" data-form-alert></div>
                <div class="admin-crud-field">
                    <label for="hora_ini">Hora inicio:</label>
                    <input type="time" name="hora_ini" id="hora_ini" data-label="hora inicio" data-article="La" data-required-message="La hora inicio es obligatoria." required>
                    <span class="admin-crud-error"></span>
                </div>
                <div class="admin-crud-field">
                    <label for="hora_fin">Hora fin:</label>
                    <input type="time" name="hora_fin" id="hora_fin" data-label="hora fin" data-article="La" data-required-message="La hora fin es obligatoria." required>
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
            <form action="${ctx}/Horarios" method="POST">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="idHorarios">
                <div class="admin-crud-actions">
                    <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-confirmar-eliminar">No</button>
                    <button type="submit" class="admin-crud-btn-danger">Sí</button>
                </div>
            </form>
        </div>
    </div>
    <script src="${ctx}/Vista/JavaScript/Admi_modales.js"></script>
</body>
</html>
