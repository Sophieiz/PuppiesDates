<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Tipo de documento</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    </head>
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
                    <h3>Tipo de documento</h3>
                </div>

                <p class="admin-crud-help">Haz clic sobre una fila para inactivar registro.</p>

                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Descripción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="doc" items="${listaTiposDocumento}">
                                <tr class="admin-crud-row" 
                                    data-admin-row 
                                    tabindex="0"
                                    data-field-id="${doc.idTipo_documento}"
                                    data-field-idTipo_documento="${doc.idTipo_documento}"
                                    data-field-descripcion_doc="${doc.descripcion_doc}"
                                    data-duplicate-key="${doc.descripcion_doc}">
                                    
                                    <td>${doc.idTipo_documento}</td>
                                    <td>${doc.descripcion_doc}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- MODAL DE DETALLE (solo lectura + inactivar) -->
        <div class="admin-crud-modal modal-overlay" id="modal-editar" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title" data-modal-title>Tipo de documento</h2>

                <form action="${ctx}/Tipodocumento" method="POST" class="admin-managed-form"
                      data-id-name="idTipo_documento" 
                      data-duplicate-keys="descripcion_doc"
                      data-create-title="Tipo de documento" 
                      data-edit-title="Tipo de documento">

                    <input type="hidden" id="modalId" name="id" value="">
                    <input type="hidden" name="idTipo_documento">

                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="descripcion_doc">Descripción:</label>
                        <input type="text" name="descripcion_doc" id="descripcion_doc" data-label="descripción" readonly>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-danger admin-crud-only-edit" data-open-delete>Inactivar</button>
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-editar">Cerrar</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- MODAL DE CONFIRMACIÓN DE ELIMINACIÓN -->
        <div class="admin-crud-modal modal-overlay" id="modal-confirmar-eliminar" aria-hidden="true">
            <div class="admin-crud-modal-box admin-crud-confirm">
                <h2 class="admin-crud-title">¿Está seguro de inactivar este registro? No se eliminará, solo dejará de estar disponible.</h2>
                <form action="${ctx}/Tipodocumento" method="POST">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idTipo_documento">

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