<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Actividades</title>
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
            <!-- TABLA PRINCIPAL DE ACTIVIDADES -->
            <div class="admin-table-card">
                <div class="admin-crud-toolbar">
                    <h3>Actividades</h3>
                    <button type="button" class="admin-crud-btn-primary" data-open-create>Nueva actividad</button>
                </div>
                
                <p class="admin-crud-help">Haz clic sobre una fila para modificar o eliminar el registro.</p>
                
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Descripción</th>
                                <th>Tipo</th>
                                <th>Precio</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="actividad" items="${listaActividades}">
                                <tr class="admin-crud-row" 
                                    data-admin-row 
                                    tabindex="0"
                                    data-field-id="${actividad.idActividad}"
                                    data-field-idActividad="${actividad.idActividad}"
                                    data-field-descripcionAct="${actividad.descripcion_actividad}"
                                    data-field-tipoActi="${actividad.tipo_Actividad_idTipo_Actividad}"
                                    data-field-listaPrecioAct="${actividad.lista_Precios_idLista_Precios}"
                                    data-duplicate-key="${actividad.descripcion_actividad}|${actividad.tipo_Actividad_idTipo_Actividad}|${actividad.lista_Precios_idLista_Precios}">
                                    
                                    <td>${actividad.idActividad}</td>
                                    <td>${actividad.descripcion_actividad}</td>
                                    <td>
                                        <c:forEach var="tipo" items="${listaTiposActividad}">
                                            <c:if test="${actividad.tipo_Actividad_idTipo_Actividad == tipo.idTipo_Actividad}">
                                                ${tipo.nombre_activi}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="precio" items="${listaPrecios}">
                                            <c:if test="${actividad.lista_Precios_idLista_Precios == precio.idLista_Precio}">
                                                ${precio.descrip_precio}
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

        
        <div class="admin-crud-modal modal-overlay" id="modal-editar" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title" data-modal-title>Nueva actividad</h2>
                
                <form action="${ctx}/Actividad" method="POST" class="admin-managed-form"
                      data-id-name="idActividad" 
                      data-duplicate-keys="descripcionAct,tipoActi,listaPrecioAct"
                      data-create-action="insertar" 
                      data-edit-action="actualizar"
                      data-create-title="Nueva actividad" 
                      data-edit-title="Modificar actividad">
                    
                    <input type="hidden" id="modalAccion" name="accion" value="insertar">
                    <input type="hidden" id="modalId" name="id" value="">
                    <input type="hidden" name="idActividad">
                    
                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="descripcionAct">Descripción:</label>
                        <input type="text" name="descripcionAct" id="descripcionAct" data-label="descripción" data-required-message="La descripción es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="tipoActi">Tipo de actividad:</label>
                        <select name="tipoActi" id="tipoActi" data-label="tipo de actividad" data-required-message="El tipo de actividad es obligatorio." required>
                            <option value="">Seleccione un tipo</option>
                            <c:forEach var="tipo" items="${listaTiposActividad}">
                                <option value="${tipo.idTipo_Actividad}">${tipo.nombre_activi}</option>
                            </c:forEach>
                        </select>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="listaPrecioAct">Lista de precios:</label>
                        <select name="listaPrecioAct" id="listaPrecioAct" data-label="lista de precios" data-article="La" data-required-message="La lista de precios es obligatoria." required>
                            <option value="">Seleccione un precio</option>
                            <c:forEach var="precio" items="${listaPrecios}">
                                <option value="${precio.idLista_Precio}">${precio.descrip_precio}</option>
                            </c:forEach>
                        </select>
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
                <form action="${ctx}/Actividad" method="POST">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idActividad">
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