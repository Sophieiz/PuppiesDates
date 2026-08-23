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
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    </head>
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

        <c:if test="${empty sessionScope.nombreUsuario}">
            <c:redirect url="/Iniciar"/>
        </c:if>

        <!-- Ahora apunta al Servlet que verifica disponibilidad, no directo a ReservaCliente -->
        <form action="${ctx}/VerificarDisponibilidad" method="post" id="formReserva" data-ctx="${ctx}" novalidate>
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

                <!-- Campo "documento" eliminado: el usuario ya está identificado por la sesión -->

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
                            <option value="${act.idActividad}" data-precio="${act.precioTexto}">${act.tipoActividadNombre}</option>
                        </c:forEach>
                    </select>
                    <span class="error-mensaje" id="error_actividad"></span>
                    <p id="precioActividadTexto" class="precio-actividad-texto" style="display:none;"></p>
                </div>

                <div class="aviso-fechas-disponibles">
                    Antes de reservar, revisa qué fechas tenemos disponibles.
                </div>
                <button type="button" id="btnVerFechas">Ver fechas disponibles</button>

                <button type="submit">Verificar disponibilidad</button>

                <!-- Ya no abre el modal directamente: ahora es un submit normal del form -->
                <button type="submit">Verificar disponibilidad</button>
            </div>
        </form>
        <div class="admin-modal-overlay" id="fechasModal">
            <div class="admin-modal-caja">
                <button type="button" class="admin-modal-cerrar" id="cerrarFechasModal">&times;</button>
                <div id="fechasModalContent">
                    <div class="adoption-modal-loading"><span></span><span></span><span></span></div>
                </div>
            </div>
        </div>
        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>