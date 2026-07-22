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
    <title>Mis Reservas - Puppies Dates</title>
    <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
</head>
<body>
    <c:set var="activePage" value="reservas" scope="request"/>
    <%@ include file="Header.jsp" %>

    <main class="main-container" style="padding: 40px 20px;">
        <h2 class="titulo-apartado">Mis reservas</h2>

        <c:choose>
            <c:when test="${empty listaMisReservas}">
                <p class="sin-perritos">Todavía no has hecho ninguna reserva. <a href="${ctx}/ReservaCliente">Reserva una actividad</a>.</p>
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
                                <tr>
                                    <td data-label="Actividad">${r.nombreActividad}</td>
                                    <td data-label="Fecha"><fmt:formatDate value="${r.fecha}" pattern="dd/MM/yyyy"/></td>
                                    <td data-label="Hora"><fmt:formatDate value="${r.hora}" pattern="HH:mm"/></td>
                                    <td data-label="Personas">${r.num_personas}</td>
                                    <td data-label="Estado">
                                        <span class="estado-badge estado-${fn:toLowerCase(fn:replace(r.descripcionEstadoReserva, ' ', '-'))}">${r.descripcionEstadoReserva}</span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </main>
    <%@ include file="Footer.jsp" %>
    <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
</body>
</html>
