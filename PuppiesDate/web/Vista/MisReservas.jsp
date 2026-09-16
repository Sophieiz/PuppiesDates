<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mis Reservas - SOUL PAWS</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    
        <link rel="icon" type="image/png" href="${ctx}/Vista/Imagenes/image.png">
    </head>
    <body>
        <c:set var="activePage" value="reservas" scope="request"/>
        <%@ include file="Header.jsp" %>

        <c:if test="${not empty sessionScope.resultado}">
            <c:set var="resultado" value="${sessionScope.resultado}"/>
            <c:remove var="resultado" scope="session"/>
        </c:if>

        <main class="main-container" style="padding: 40px 20px;">
            <h2 class="titulo-apartado">Mis reservas</h2>

            <c:if test="${not empty resultado}">
                <p class="mensaje ${fn:startsWith(resultado, '¡') ? 'mensaje-exito' : 'mensaje-error'}">${resultado}</p>
            </c:if>

            <%-- ===================== RESERVAS ACTIVAS ===================== --%>
            <c:set var="hayActivas" value="false"/>
            <c:forEach var="r" items="${listaMisReservas}">
                <c:if test="${!r.cancelada && !r.realizada}"><c:set var="hayActivas" value="true"/></c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${!hayActivas}">
                    <p class="sin-perritos sin-reservas">No tienes reservas activas. <a href="${ctx}/ReservaCliente">Reserva una actividad</a>.</p>
                </c:when>
                <c:otherwise>
                    <div class="mis-registros-table-wrap">
                        <table class="mis-registros-table">
                            <thead>
                                <tr>
                                    <th>Actividad</th>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Personas</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="r" items="${listaMisReservas}">
                                    <c:if test="${!r.cancelada && !r.realizada}">
                                        <tr>
                                            <td data-label="Actividad">${r.nombreActividad}</td>
                                            <td data-label="Fecha"><fmt:formatDate value="${r.fecha}" pattern="dd/MM/yyyy"/></td>
                                            <td data-label="Hora"><fmt:formatDate value="${r.hora}" pattern="HH:mm"/></td>
                                            <td data-label="Personas">${r.num_personas}</td>
                                            <td data-label="Estado">${r.descripcionEstadoReserva}</td>
                                            <td data-label="Acciones">
                                                <c:choose>
                                                    <c:when test="${r.modificable}">
                                                        <div class="acciones-reserva">
                                                            <a class="admin-crud-btn-secondary admin-crud-btn-sm" href="${ctx}/GestionarReserva?accion=editar&id=${r.idReserva}">Editar</a>
                                                            <button type="button" class="admin-crud-btn-danger admin-crud-btn-sm"
                                                                    onclick="abrirModalCancelar(${r.idReserva})">
                                                                Cancelar
                                                            </button>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="texto-bloqueado">No modificable (menos de 7 días)</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

            <%-- ===================== HISTORIAL (canceladas / realizadas) ===================== --%>
            <h2 class="titulo-apartado" style="margin-top:40px;">Historial</h2>
            <c:set var="hayHistorial" value="false"/>
            <c:forEach var="r" items="${listaMisReservas}">
                <c:if test="${r.cancelada || r.realizada}"><c:set var="hayHistorial" value="true"/></c:if>
            </c:forEach>

            <c:choose>
                <c:when test="${!hayHistorial}">
                    <p class="sin-perritos sin-reservas">Aún no tienes reservas canceladas ni realizadas.</p>
                </c:when>
                <c:otherwise>
                    <div class="mis-registros-table-wrap">
                        <table class="mis-registros-table">
                            <thead>
                                <tr>
                                    <th>Actividad</th>
                                    <th>Fecha</th>
                                    <th>Hora</th>
                                    <th>Personas</th>
                                    <th>Estado</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="r" items="${listaMisReservas}">
                                    <c:if test="${r.cancelada || r.realizada}">
                                        <tr>
                                            <td data-label="Actividad">${r.nombreActividad}</td>
                                            <td data-label="Fecha"><fmt:formatDate value="${r.fecha}" pattern="dd/MM/yyyy"/></td>
                                            <td data-label="Hora"><fmt:formatDate value="${r.hora}" pattern="HH:mm"/></td>
                                            <td data-label="Personas">${r.num_personas}</td>
                                            <td data-label="Estado">
                                                <c:choose>
                                                    <c:when test="${r.cancelada}">Cancelada</c:when>
                                                    <c:otherwise>Realizada</c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

        <!-- Modal de confirmación para cancelar -->
        <div class="admin-modal-overlay" id="modalCancelar" style="display:none;">
            <div class="admin-modal-caja">
                <button type="button" class="admin-modal-cerrar" onclick="cerrarModalCancelar()">&times;</button>
                <h3>¿Estás seguro?</h3>
                <p>Esta acción cancelará tu reserva y no se puede deshacer.</p>
                <div class="modal-acciones-centradas">
                    <button type="button" class="admin-crud-btn-secondary" onclick="cerrarModalCancelar()">No, volver</button>
                    <form id="formCancelar" action="${ctx}/GestionarReserva" method="get" style="display:inline;">
                        <input type="hidden" name="accion" value="cancelar">
                        <input type="hidden" name="id" id="idReservaCancelar" value="">
                        <button type="submit" class="admin-crud-btn-danger">Sí, cancelar reserva</button>
                    </form>
                </div>
            </div>
        </div>



        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
