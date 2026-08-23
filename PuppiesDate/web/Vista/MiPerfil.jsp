<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mi Perfil - Puppies Dates</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body class="pagina-formulario">
        <c:set var="activePage" value="perfil" scope="request"/>
        <%@ include file="Header.jsp" %>

        <c:if test="${not empty mensaje}">
            <div class="mensaje-bienvenida">
                <p>${mensaje}</p>
            </div>
        </c:if>

        <form action="${ctx}/MiPerfil" method="POST" novalidate>
            <div class="Formulario">
                <h2 class="titulo-form">Mi perfil</h2>
                <hr>

                <div class="campo-reserva">
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombre" id="nombre" value="${usuario.nombre}" required>
                </div>

                <div class="campo-reserva">
                    <label for="apellido">Apellido</label>
                    <input type="text" name="apellido" id="apellido" value="${usuario.apellido}" required>
                </div>

                <div class="campo-reserva">
                    <label>Documento</label>
                    <input type="text" value="${usuario.documento}" disabled>
                    <span class="hint-hora">El documento no se puede modificar.</span>
                </div>

                <div class="campo-reserva">
                    <label for="telefono">Teléfono</label>
                    <input type="text" name="telefono" id="telefono" value="${usuario.telefono}">
                </div>

                <div class="campo-reserva">
                    <label for="correo">Correo</label>
                    <input type="email" name="correo" id="correo" value="${usuario.correo}" required>
                </div>

                <button type="submit">Guardar cambios</button>
            </div>
        </form>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>