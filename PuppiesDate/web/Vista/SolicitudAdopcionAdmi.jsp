<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Solicitudes de adopción</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    </head>
    <body class="admin-form-body">
        <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>

        <c:if test="${not empty mensaje}">
            <div class="mensaje-bienvenida">
                <p>${mensaje}</p>
            </div>
        </c:if>

        <div class="admin-form-wrap admin-users-wrap">
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

                <c:set var="perritosProcesados" value="," />

                <c:forEach var="s" items="${listaSolicitudes}">
                    <c:set var="marcadorPerrito" value=",${s.nombrePerrito}," />

                    <c:if test="${not fn:contains(perritosProcesados, marcadorPerrito)}">
                        <c:set var="perritosProcesados" value="${perritosProcesados}${s.nombrePerrito}," />

                        <div class="admin-perrito-card">
                            <h4 class="admin-perrito-card-title">${s.nombrePerrito}</h4>

                            <div class="admin-crud-table-wrap">
                                <table class="admin-crud-table">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Solicitante</th>
                                            <th>Correo</th>
                                            <th>Fecha</th>
                                            <th>Estado</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="s2" items="${listaSolicitudes}" varStatus="i">
                                            <c:if test="${s2.nombrePerrito == s.nombrePerrito}">
                                                <tr class="admin-crud-row"
                                                    data-abrir-cambio
                                                    data-id="${s2.idSolicitud_adopcion}"
                                                    data-perrito="${s2.nombrePerrito}"
                                                    data-estado-actual="${s2.estado_solicitud_idEstado_solicitud}"
                                                    tabindex="0">

                                                    <td data-label="#">${i.count}</td>
                                                    <td data-label="Solicitante">${s2.nombreUsuario} ${s2.apellidoUsuario}</td>
                                                    <td data-label="Correo">${s2.correoUsuario}</td>
                                                    <td data-label="Fecha"><fmt:formatDate value="${s2.fecha_solicitud}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                    <td data-label="Estado">${s2.descripcionEstado_solicitud}</td>
                                                    <td data-label="Acciones">
                                                        <div class="admin-crud-acciones">
                                                            <button type="button" class="admin-crud-btn-primary admin-crud-btn-sm"
                                                                    data-abrir-entrevista
                                                                    data-id="${s2.idSolicitud_adopcion}"
                                                                    data-perrito="${s2.nombrePerrito}"
                                                                    onclick="event.stopPropagation();">
                                                                Entrevista
                                                            </button>
                                                            <form action="${ctx}/SolicitudAdopcionAdmi" method="POST"
                                                                  onsubmit="return confirm('¿Está seguro de inactivar esta solicitud? No se eliminará, solo dejará de estar disponible.');">
                                                                <input type="hidden" name="accion" value="eliminar">
                                                                <input type="hidden" name="idSolicitud_adopcion" value="${s2.idSolicitud_adopcion}">
                                                                <button type="submit" class="admin-crud-btn-danger admin-crud-btn-sm" onclick="event.stopPropagation();">Inactivar</button>
                                                            </form>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>

                <c:if test="${empty listaSolicitudes}">
                    <div class="admin-empty">Todavía no hay solicitudes de adopción.</div>
                </c:if>
            </div>
        </div>


        <div class="admin-crud-modal modal-overlay" id="modal-cambiar-estado" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title">Cambiar estado de la solicitud (<span id="nombrePerritoModal"></span>)</h2>

                <form action="${ctx}/SolicitudAdopcionAdmi" method="POST" class="admin-managed-form">
                    <input type="hidden" name="accion" value="actualizarEstado">
                    <input type="hidden" name="idSolicitud_adopcion" id="idSolicitudModal">
                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="idEstado_solicitud">Nuevo estado:</label>
                        <select name="idEstado_solicitud" id="idEstado_solicitud"
                                data-label="nuevo estado" data-required-message="Debes seleccionar un nuevo estado." required>
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

        <div class="admin-crud-modal modal-overlay" id="modal-programar-entrevista" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-programar-entrevista" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title">Programar entrevista (<span id="nombrePerritoEntrevistaModal"></span>)</h2>

                <form action="${ctx}/SolicitudAdopcionAdmi" method="POST" onsubmit="return validarEntrevista()">
                    <input type="hidden" name="accion" value="programarEntrevista">
                    <input type="hidden" name="idSolicitud_adopcion" id="idSolicitudEntrevistaModal">


                    <div class="admin-crud-field">
                        <label for="fecha">Fecha:</label>
                        <input type="date" name="fecha" id="fecha" required>
                        <span class="admin-crud-error" id="error_fecha"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="hora">Hora:</label>
                        <input type="time" name="hora" id="hora" min="08:00" max="17:00" required>
                        <span class="admin-crud-error" id="error_hora"></span>
                    </div>

                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-programar-entrevista">Cancelar</button>
                        <button type="submit" class="admin-crud-btn-primary">Programar y notificar</button>
                    </div>
                </form>
            </div>
        </div>

        <script src="${ctx}/Vista/JavaScript/funciones.js"></script>
        <script src="${ctx}/Vista/JavaScript/ubicacionAdopcion.js"></script>
    </body>
</html>
