<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PUPPIES DATES</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="stylesheet" href="${ctx}/Vista/Css/formulario-reserva-styles.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body class="reserva-page">
        <c:set var="activePage" value="reservas" scope="request"/>
        <%@ include file="Header.jsp" %>

        <a href="${ctx}/PanelUsuario.jsp" class="btn-volver-inicio">&larr;</a>

        <c:if test="${empty sessionScope.nombreUsuario}">
            <c:redirect url="/Iniciar"/>
        </c:if>

        <div class="Formulario">

            <c:if test="${not empty resultado}">
                <c:choose>
                    <c:when test="${fn:startsWith(resultado, '¡')}">
                        <p class="mensaje mensaje-exito">${resultado}</p>
                    </c:when>
                    <c:otherwise>
                        <p class="mensaje mensaje-error">${resultado}</p>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <h2 class="titulo-form">Confirma tu reserva</h2>
            <hr>

            <c:choose>
                <c:when test="${not empty sessionScope.resActividad and empty resultado}">

                    <div class="campo-reserva">
                        <label>Número de personas</label>
                        <p>${sessionScope.resNumPersonas}</p>
                    </div>

                    <div class="campo-reserva">
                        <label>Fecha</label>
                        <p>${sessionScope.resFecha}</p>
                    </div>

                    <div class="campo-reserva">
                        <label>Hora</label>
                        <p>${sessionScope.resHora}</p>
                    </div>

                    <div class="campo-reserva">
                        <label>Pago</label>
                        <p>Se paga en el lugar</p>
                    </div>

                    <form action="${ctx}/ConfirmarReserva" method="post">
                        <button type="submit">Confirmar reserva</button>
                    </form>

                </c:when>
                <c:otherwise>
                    <p>No hay ninguna reserva pendiente de confirmar.</p>
                    <a href="${ctx}/ReservaCliente">Hacer una nueva reserva</a>
                </c:otherwise>
            </c:choose>

        </div>

        <%@ include file="Footer.jsp" %>
    </body>
</html>