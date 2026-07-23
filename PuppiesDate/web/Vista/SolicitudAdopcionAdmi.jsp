<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Solicitudes de adopción</title>
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
                    <h3>Solicitudes de adopción</h3>
                </div>

                <form action="${ctx}/SolicitudAdopcionAdmi" method="GET" class="admin-buscador">
                    <span class="icono-buscar" aria-hidden="true"></span>
                    <input type="text" name="buscar" placeholder="Buscar por perrito, usuario o documento..." value="${terminoBusqueda}">
                    <button type="submit">Buscar</button>
                    <c:if test="${not empty terminoBusqueda}">
                        <a href="${ctx}/SolicitudAdopcionAdmi" class="admin-buscador-limpiar">Ver todas</a>
                    </c:if>
                </form>

                <p class="admin-crud-help">Haz clic sobre una fila para cambiar el estado de la solicitud. El solicitante recibirá un correo automático avisándole del cambio.</p>

                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Perrito</th>
                                <th>Solicitante</th>
                                <th>Correo</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${listaSolicitudes}">
                                <tr class="admin-crud-row" 
                                    data-abrir-cambio
                                    data-id="${s.idSolicitud_adopcion}"
                                    data-perrito="${s.nombrePerrito}"
                                    data-estado-actual="${s.estado_solicitud_idEstado_solicitud}"
                                    tabindex="0">
                                    
                                    <td>${s.idSolicitud_adopcion}</td>
                                    <td>${s.nombrePerrito}</td>
                                    <td>${s.nombreUsuario} ${s.apellidoUsuario}</td>
                                    <td>${s.correoUsuario}</td>
                                    <td><fmt:formatDate value="${s.fecha_solicitud}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>${s.descripcionEstado_solicitud}</td>
                                    <td>
                                        <form action="${ctx}/SolicitudAdopcionAdmi" method="POST"
                                              onsubmit="return confirm('¿Está seguro de inactivar esta solicitud? No se eliminará, solo dejará de estar disponible.');">
                                            <input type="hidden" name="accion" value="eliminar">
                                            <input type="hidden" name="idSolicitud_adopcion" value="${s.idSolicitud_adopcion}">
                                            <button type="submit" class="admin-crud-btn-danger admin-crud-btn-sm" onclick="event.stopPropagation();">Inactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaSolicitudes}">
                                <tr>
                                    <td colspan="7" class="admin-empty">Todavía no hay solicitudes de adopción.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        
        <div class="admin-crud-modal modal-overlay" id="modal-cambiar-estado" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title">Cambiar estado de la solicitud (<span id="nombrePerritoModal"></span>)</h2>
                
                <form action="${ctx}/SolicitudAdopcionAdmi" method="POST">
                    <input type="hidden" name="accion" value="actualizarEstado">
                    <input type="hidden" name="idSolicitud_adopcion" id="idSolicitudModal">

                    <div class="admin-crud-field">
                        <label for="idEstado_solicitud">Nuevo estado:</label>
                        <select name="idEstado_solicitud" id="idEstado_solicitud" required>
                            <c:forEach var="estado" items="${listaEstadosSolicitud}">
                                <option value="${estado.idEstado_solicitud}">${estado.descripcion_estado}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="admin-crud-field">
                        <label for="observacion">Observación (opcional):</label>
                        <textarea name="observacion" id="observacion" rows="3" placeholder="Ej: Pendiente visita domiciliaria..."></textarea>
                    </div>

                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-cambiar-estado">Cancelar</button>
                        <button type="submit" class="admin-crud-btn-primary">Guardar y notificar</button>
                    </div>
                </form>
            </div>
        </div>

    <script src="${ctx}/Vista/JavaScript/funciones.js"></script>
    </body>
</html>