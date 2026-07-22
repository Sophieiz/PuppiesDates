<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Casa y Jardin - Vivero Café</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="stylesheet" href="${ctx}/Vista/Css/formulario-reserva-styles.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
        <script src="${ctx}/Vista/JavaScript/validarReserva.js" defer></script>
    </head>
    <body class="reserva-page">   
        <c:set var="activePage" value="reservas" scope="request"/>
        <%@ include file="Header.jsp" %>

        <c:choose>
            <c:when test="${not empty sessionScope.nombreUsuario}">
                <a href="${ctx}/PanelUsuario.jsp" class="btn-volver-inicio">&larr;</a>
            </c:when>
            <c:otherwise>
                <a href="${ctx}/index.jsp" class="btn-volver-inicio">&larr;</a>
            </c:otherwise>
        </c:choose>

        <form action="${ctx}/ReservaCliente" method="post" id="formReserva" novalidate>
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

                <h2 class="titulo-form">Reserva tu actividad</h2>
                <hr>

                <div class="campo-reserva">
                    <label for="documento">Número de documento</label>
                    <input type="text" name="documentoa" id="documento" placeholder="Ej: 1023456789" maxlength="12">
                    <span class="error-mensaje" id="error_documento"></span>
                </div>

                <div class="campo-reserva">
                    <label for="num_personas">Número de personas</label>
                    <input type="number" name="num_personasp" id="num_personas" placeholder="Ej: 2" min="1" max="20">
                    <span class="error-mensaje" id="error_num_personas"></span>
                </div>

                <div class="campo-reserva">
                    <label for="fecha">Fecha de la reserva</label>
                    <input type="date" name="fechar" id="fecha">
                    <span class="error-mensaje" id="error_fecha"></span>
                </div>

                <div class="campo-reserva">
                    <label for="hora">Hora de la reserva</label>
                    <input type="time" name="horar" id="hora" min="08:00" max="17:00">
                    <span class="hint-hora">Horario de atención: 8:00 AM a 5:00 PM</span>
                    <span class="error-mensaje" id="error_hora"></span>
                </div>

                <div class="campo-reserva">
                    <label for="actividad">Actividad</label>
                    <select id="actividad" name="actividada">
                        <option value="">-- Selecciona una actividad --</option>
                        <c:forEach var="act" items="${actividades}">
                            <option value="${act.idActividad}">${act.descripcion_actividad}</option>
                        </c:forEach>
                    </select>
                    <span class="error-mensaje" id="error_actividad"></span>
                </div>

                <button type="button" id="btnAbrirConfirmarReserva">Confirmar reserva</button>
            </div>
        </form>

        <div class="modal-overlay" id="modal-confirmar-reserva" aria-hidden="true">
            <div class="admin-crud-modal-box admin-crud-confirm">
                <h2 class="admin-crud-title">¿Deseas confirmar esta reserva?</h2>
                <div class="admin-crud-actions">
                    <button type="button" class="admin-crud-btn-secondary" id="btnCancelarReserva">No</button>
                    <button type="button" class="admin-crud-btn-primary" id="btnConfirmarReserva">Sí</button>
                </div>
            </div>
        </div>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
