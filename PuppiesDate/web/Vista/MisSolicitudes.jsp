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
    <title>Mis Solicitudes - Puppies Dates</title>
    <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
</head>
<body>
    <c:set var="activePage" value="inicio" scope="request"/>
    <%@ include file="Header.jsp" %>

    <main class="main-container" style="padding: 40px 20px;">
        <h2 class="titulo-apartado">Mis solicitudes de adopción</h2>

        <c:choose>
            <c:when test="${empty listaMisSolicitudes}">
                <p class="sin-perritos">Todavía no has enviado ninguna solicitud de adopción. <a href="${ctx}/CatalogoPerritos">Conoce a nuestros perritos</a>.</p>
            </c:when>
            <c:otherwise>
                <div class="mis-registros-table-wrap">
                    <table class="mis-registros-table">
                        <thead>
                            <tr>
                                <th>Perrito</th>
                                <th>Fecha de solicitud</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${listaMisSolicitudes}">
                                <tr>
                                    <td data-label="Perrito">${s.nombrePerrito}</td>
                                    <td data-label="Fecha de solicitud"><fmt:formatDate value="${s.fecha_solicitud}" pattern="dd/MM/yyyy 'a las' HH:mm"/></td>
                                    <td data-label="Estado">
                                        <span class="estado-badge estado-${fn:toLowerCase(fn:replace(s.descripcionEstado_solicitud, ' ', '-'))}">${s.descripcionEstado_solicitud}</span>
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
