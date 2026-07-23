<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Roles</title>
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
                <div class="admin-crud-toolbar">
                    <h3>Roles</h3>
                    <button type="button" class="admin-crud-btn-primary" data-open-create>Nuevo rol</button>
                </div>

                <p class="admin-crud-help">Haz clic sobre una fila para modificar o eliminar el registro.</p>

                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                               
                                <th>Descripción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rol" items="${listaRoles}">
                                <tr class="admin-crud-row" 
                                    data-admin-row 
                                    tabindex="0" 
                                    data-field-id="${rol.idRoles}" 
                                    data-field-idRoles="${rol.idRoles}" 
                                    data-field-descripcion_rol="${rol.descripcion_rol}" 
                                    data-duplicate-key="${rol.descripcion_rol}">
                                    
                                    <td>${rol.idRoles}</td>
                                    <td>${rol.descripcion_rol}</td>
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
                <h2 class="admin-crud-title" data-modal-title>Nuevo rol</h2>

                <form action="${ctx}/RolesAdmi" method="POST" class="admin-managed-form" 
                      data-id-name="idRoles" 
                      data-duplicate-keys="descripcion_rol" 
                      data-create-action="insertar" 
                      data-edit-action="actualizar" 
                      data-create-title="Nuevo rol" 
                      data-edit-title="Modificar rol">

                    <input type="hidden" id="modalAccion" name="accion" value="insertar">
                    <input type="hidden" id="modalId" name="id" value="">
                    <input type="hidden" name="idRoles">

                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="descripcion_rol">Descripción del rol:</label>
                        <input type="text" name="descripcion_rol" id="descripcion_rol" data-label="descripción del rol" data-required-message="La descripción del rol es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-danger admin-crud-only-edit" data-open-delete>Eliminar</button>
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-editar">Cancelar</button>
                        <button type="submit" class="admin-crud-btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>

        
        <div class="admin-crud-modal modal-overlay" id="modal-confirmar-eliminar" aria-hidden="true">
            <div class="admin-crud-modal-box admin-crud-confirm">
                <h2 class="admin-crud-title">¿Está seguro de eliminar este registro?</h2>
                <form action="${ctx}/RolesAdmi" method="POST">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idRoles">
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