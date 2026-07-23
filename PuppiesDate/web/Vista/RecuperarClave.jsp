<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Recuperar contraseña</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body class="pagina-formulario">
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Header.jsp" %>

        <a href="${ctx}/Vista/InicioSesion.jsp" class="btn-volver-inicio">&larr;</a>

        <form action="${ctx}/RecuperarClave" method="post" novalidate>
            <div class="Formulario">

                <c:if test="${not empty mensaje}">
                    <p class="mensaje mensaje-error">${mensaje}</p>
                </c:if>

                <h2 class="titulo-form">Recuperar contraseña</h2>
                <hr>

                <p>Escribe el correo con el que te registraste. Si está en nuestra base de datos, te enviaremos un enlace para crear una contraseña nueva.</p>

                <div class="campo-reserva">
                    <label for="correo">Correo electrónico</label>
                    <input type="email" name="correo" id="correo" placeholder="tucorreo@ejemplo.com" required>
                </div>

                <button type="submit">Enviar enlace</button>
                <button type="button" onclick="window.location = '${ctx}/Vista/InicioSesion.jsp'">Volver a iniciar sesión</button>
            </div>
        </form>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
