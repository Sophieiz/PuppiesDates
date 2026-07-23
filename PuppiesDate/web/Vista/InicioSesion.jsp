<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Iniciar Sesión</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
        <script src="${ctx}/Vista/JavaScript/validarInicio.js" defer></script>
    </head>
    <body class="pagina-formulario">
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Header.jsp" %>

        <a href="${ctx}/index.jsp" class="btn-volver-inicio">&larr;</a>

        <form action="${ctx}/Iniciar" method="post" onsubmit="return validarLogin()" novalidate>
            <div class="Formulario">
                
                <%-- Alerta de Error General --%>
                <c:if test="${not empty mensaje}">
                    <p class="mensaje mensaje-error">${mensaje}</p>
                </c:if>

                <%-- Alerta de Contraseña Actualizada --%>
                <c:if test="${param.claveActualizada == 'true'}">
                    <p class="mensaje mensaje-exito">¡Tu contraseña fue actualizada! Ya puedes iniciar sesión.</p>
                </c:if>

                <%-- Alerta de Sesión Expirada (Movida aquí adentro) --%>
                <c:if test="${param.mensaje == 'sesion_expirada'}">
                    <div class="alerta-sesion-expirada">
                        <p>Tu sesión ha expirado por inactividad. Por favor, ingresa nuevamente por seguridad.</p>
                    </div>
                </c:if>

                <h2 class="titulo-form">Iniciar sesión</h2>
                <hr>

                <div class="campo-reserva">
                    <label for="correo">Correo electrónico</label>
                    <input type="email" name="correo" id="correo" placeholder="Ej: correo@ejemplo.com">
                    <span class="error-mensaje" id="error_correo"></span>
                </div>

                <div class="campo-reserva">
                    <label for="pass">Contraseña</label>
                    <input type="password" name="pass" id="pass" placeholder="Ingresa tu contraseña">
                    <span class="error-mensaje" id="error_pass"></span>
                </div>

                <a href="${ctx}/RecuperarClave" class="link-olvido-clave">¿Olvidaste tu contraseña?</a>
                <button type="submit">Iniciar sesión</button>
                <button type="button" onclick="window.location = '${ctx}/CargarRegistro'">Registrarse</button>
            </div>
        </form>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>