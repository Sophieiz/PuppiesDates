<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="estadosCancelables" value=",Pendiente,Entrevista," />
<c:set var="estadosFinalizados" value=",Cancelada,Rechazada,No seleccionado," />
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mis Solicitudes - SOUL PAWS</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    
        <link rel="icon" type="image/png" href="${ctx}/Vista/Imagenes/image.png">
    </head>

    <body>
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Header.jsp" %>

        <main class="main-container main-container-padded">
            <h2 class="titulo-apartado">Mis solicitudes de adopción</h2>

            <c:choose>
                <c:when test="${empty listaMisSolicitudes}">
                    <p class="sin-perritos">Todavía no has enviado ninguna solicitud de adopción. <a href="${ctx}/CatalogoPerritos">Conoce a nuestros perritos</a>.</p>
                </c:when>
                <c:otherwise>

                    <div class="filtro-catalogo">
                        <label for="filtroEstadoSolicitud" class="filtro-etiqueta">Filtrar por estado:</label>
                        <select id="filtroEstadoSolicitud" class="filtro-select">
                            <option value="todas">Todas</option>
                            <option value="pendiente">Pendiente</option>
                            <option value="entrevista">Entrevista</option>
                            <option value="no-seleccionado">No seleccionado</option>
                            <option value="rechazada">Rechazada</option>
                            <option value="cancelada">Cancelada</option>
                        </select>
                    </div>

                    <%-- ===================== SOLICITUDES ACTIVAS ===================== --%>
                    <c:set var="hayActivas" value="false"/>
                    <c:forEach var="s" items="${listaMisSolicitudes}">
                        <c:set var="estadoConComas" value=",${s.descripcionEstado_solicitud},"/>
                        <c:if test="${!fn:contains(estadosFinalizados, estadoConComas)}"><c:set var="hayActivas" value="true"/></c:if>
                    </c:forEach>

                    <div class="seccion-solicitudes" id="seccionActivas" data-seccion="activas">
                        <c:choose>
                            <c:when test="${!hayActivas}">
                                <p class="sin-perritos">No tienes solicitudes activas en este momento.</p>
                            </c:when>
                            <c:otherwise>
                                <div class="mis-registros-table-wrap">
                                    <table class="mis-registros-table" data-tabla-solicitudes>
                                        <thead>
                                            <tr>
                                                <th>Perrito</th>
                                                <th>Fecha de solicitud</th>
                                                <th>Estado</th>
                                                <th>Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="s" items="${listaMisSolicitudes}">
                                                <c:set var="estadoConComas" value=",${s.descripcionEstado_solicitud},"/>
                                                <c:set var="estadoSlug" value="${fn:toLowerCase(fn:replace(s.descripcionEstado_solicitud, ' ', '-'))}"/>
                                                <c:if test="${!fn:contains(estadosFinalizados, estadoConComas)}">
                                                    <tr data-estado="${estadoSlug}">
                                                        <td data-label="Perrito">${s.nombrePerrito}</td>
                                                        <td data-label="Fecha de solicitud"><fmt:formatDate value="${s.fecha_solicitud}" pattern="dd/MM/yyyy 'a las' HH:mm"/></td>
                                                        <td data-label="Estado">
                                                            <span class="estado-badge estado-${estadoSlug}">${s.descripcionEstado_solicitud}</span>
                                                        </td>
                                                        <td data-label="Acciones">
                                                            <c:if test="${fn:contains(estadosCancelables, estadoConComas)}">
                                                                <form action="${ctx}/MisSolicitudes" method="POST"
                                                                      onsubmit="return confirm('¿Seguro que quieres cancelar tu solicitud de adopción para ${s.nombrePerrito}? Esta acción no se puede deshacer.');">
                                                                    <input type="hidden" name="accion" value="cancelar">
                                                                    <input type="hidden" name="idSolicitud_adopcion" value="${s.idSolicitud_adopcion}">
                                                                    <button type="submit" class="btn-cancelar-solicitud">Cancelar solicitud</button>
                                                                </form>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <%-- ===================== HISTORIAL ===================== --%>
                    <c:set var="hayHistorial" value="false"/>
                    <c:forEach var="s" items="${listaMisSolicitudes}">
                        <c:set var="estadoConComas" value=",${s.descripcionEstado_solicitud},"/>
                        <c:if test="${fn:contains(estadosFinalizados, estadoConComas)}"><c:set var="hayHistorial" value="true"/></c:if>
                    </c:forEach>

                    <c:if test="${hayHistorial}">
                        <div class="seccion-solicitudes" id="seccionHistorial" data-seccion="historial">
                            <h2 class="titulo-apartado titulo-historial">Historial</h2>
                            <div class="mis-registros-table-wrap">
                                <table class="mis-registros-table" data-tabla-solicitudes>
                                    <thead>
                                        <tr>
                                            <th>Perrito</th>
                                            <th>Fecha de solicitud</th>
                                            <th>Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="s" items="${listaMisSolicitudes}">
                                            <c:set var="estadoConComas" value=",${s.descripcionEstado_solicitud},"/>
                                            <c:set var="estadoSlug" value="${fn:toLowerCase(fn:replace(s.descripcionEstado_solicitud, ' ', '-'))}"/>
                                            <c:if test="${fn:contains(estadosFinalizados, estadoConComas)}">
                                                <tr data-estado="${estadoSlug}">
                                                    <td data-label="Perrito">${s.nombrePerrito}</td>
                                                    <td data-label="Fecha de solicitud"><fmt:formatDate value="${s.fecha_solicitud}" pattern="dd/MM/yyyy 'a las' HH:mm"/></td>
                                                    <td data-label="Estado">
                                                        <span class="estado-badge estado-${estadoSlug}">${s.descripcionEstado_solicitud}</span>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>

                    <p class="sin-resultados-filtro oculto" id="sinResultadosFiltro">No hay solicitudes con ese estado.</p>

                </c:otherwise>
            </c:choose>
        </main>
        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
        
    </body>
</html>