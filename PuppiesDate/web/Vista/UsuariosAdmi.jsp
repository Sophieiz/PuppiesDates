<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Usuarios</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    </head>
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
                    <h3>Usuarios</h3>
                </div>

                <p class="admin-crud-help">Haz clic sobre una fila para modificar o eliminar el usuario.</p>

                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table usuarios-admin-table">
                        <thead>
                            <tr>
                               
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Documento</th>
                                <th>Teléfono</th>
                                <th>Correo</th>
                                <th>F. nacimiento</th>
                                <th>F. expedición</th>
                                <th>Activo</th>
                                <th>Rol</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${listaUsuarios}">
                                <tr class="admin-crud-row" 
                                    data-admin-row 
                                    tabindex="0"
                                    data-field-id="${u.idUsuarios}"
                                    data-field-idUsuarios="${u.idUsuarios}"
                                    data-field-nombre="${u.nombre}"
                                    data-field-apellido="${u.apellido}"
                                    data-field-documento="${u.documento}"
                                    data-field-telefono="${u.telefono}"
                                    data-field-correo="${u.correo}"
                                    data-field-clave="${u.clave}"
                                    data-field-fecha_nac="${u.fecha_nac}"
                                    data-field-fecha_cad="${u.fecha_cad}"
                                    data-field-checkbox="${u.checkbox ? '1' : '0'}"
                                    data-field-Tipo_documento_idTipo_documento="${u.tipo_documento_idTipo_documento}"
                                    data-field-Roles_idRoles="${u.roles_idRoles}"
                                    data-duplicate-key="${u.documento}|${u.correo}">

                                    <td>${u.idUsuarios}</td>
                                    <td>${u.nombre}</td>
                                    <td>${u.apellido}</td>
                                    <td>${u.documento}</td>
                                    <td>${u.telefono}</td>
                                    <td>${u.correo}</td>
                                    <td>${u.fecha_nac}</td>
                                    <td>${u.fecha_cad}</td>
                                    <td>${u.checkbox ? 'Sí' : 'No'}</td>
                                    <td>
                                        <c:forEach var="rol" items="${listaRoles}">
                                            <c:if test="${u.roles_idRoles == rol.idRoles}">
                                                ${rol.descripcion_rol}
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

        <!-- Modal Modificar Usuario -->
        <div class="admin-crud-modal modal-overlay" id="modal-editar" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title" data-modal-title>Modificar usuario</h2>

                <form action="${ctx}/UsuarioAdmi" method="POST" class="admin-managed-form"
                      data-id-name="idUsuarios" 
                      data-duplicate-keys="documento,correo"
                      data-edit-action="modificar" 
                      data-edit-title="Modificar usuario">

                    <input type="hidden" id="modalAccion" name="accion" value="modificar">
                    <input type="hidden" id="modalId" name="id" value="">
                    <input type="hidden" name="idUsuarios">
                    <input type="hidden" name="clave">
                    <input type="hidden" name="Tipo_documento_idTipo_documento">

                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="nombre">Nombre:</label>
                        <input type="text" name="nombre" id="nombre" data-label="nombre" data-required-message="El nombre es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="apellido">Apellido:</label>
                        <input type="text" name="apellido" id="apellido" data-label="apellido" data-required-message="El apellido es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="documento">Documento:</label>
                        <input type="text" name="documento" id="documento" data-label="documento" data-required-message="El documento es obligatorio." data-pattern="^[0-9]+$" data-pattern-message="El documento solo puede contener números." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="telefono">Teléfono:</label>
                        <input type="text" name="telefono" id="telefono" data-label="teléfono" data-required-message="El teléfono es obligatorio." data-pattern="^[0-9]+$" data-pattern-message="El teléfono solo puede contener números." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="correo">Correo:</label>
                        <input type="email" name="correo" id="correo" data-label="correo" data-required-message="El correo es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="fecha_nac">Fecha de nacimiento:</label>
                        <input type="date" name="fecha_nac" id="fecha_nac" data-label="fecha de nacimiento" data-article="La" data-required-message="La fecha de nacimiento es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="fecha_cad">Fecha de expedición:</label>
                        <input type="date" name="fecha_cad" id="fecha_cad" data-label="fecha de expedición" data-article="La" data-required-message="La fecha de expedición es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="checkbox">Activo:</label>
                        <select name="checkbox" id="checkbox" data-label="estado activo" data-required-message="El estado activo es obligatorio." required>
                            <option value="1">Sí</option>
                            <option value="0">No</option>
                        </select>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="Roles_idRoles">Rol:</label>
                        <select name="Roles_idRoles" id="Roles_idRoles" data-label="rol" data-required-message="El rol es obligatorio." required>
                            <option value="">Seleccione un rol</option>
                            <c:forEach var="rol" items="${listaRoles}">
                                <option value="${rol.idRoles}">${rol.descripcion_rol}</option>
                            </c:forEach>
                        </select>
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

        <!-- Modal Confirmación Inactivar -->
        <div class="admin-crud-modal modal-overlay" id="modal-confirmar-eliminar" aria-hidden="true">
            <div class="admin-crud-modal-box admin-crud-confirm">
                <h2 class="admin-crud-title">¿Está seguro de inactivar este registro? No se eliminará, solo dejará de estar disponible.</h2>
                <form action="${ctx}/UsuarioAdmi" method="POST">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idUsuarios">
                    
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