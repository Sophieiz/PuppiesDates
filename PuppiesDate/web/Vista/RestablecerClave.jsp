<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Nueva contraseña</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body class="pagina-formulario">
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Header.jsp" %>

        <a href="${ctx}/Vista/InicioSesion.jsp" class="btn-volver-inicio">&larr;</a>

        <c:choose>
            <c:when test="${tokenValido}">
                <form action="${ctx}/RestablecerClave" method="post" onsubmit="return validarNuevaClave()" novalidate>
                    <div class="Formulario">

                        <c:if test="${not empty mensaje}">
                            <p class="mensaje mensaje-error">${mensaje}</p>
                        </c:if>

                        <h2 class="titulo-form">Crea tu nueva contraseña</h2>
                        <hr>

                        <input type="hidden" name="token" value="${token}">

                        <div class="campo-reserva">
                            <label for="nuevaClave">Nueva contraseña</label>
                            <input type="password" name="nuevaClave" id="nuevaClave" placeholder="Mínimo 6 caracteres" minlength="6" required>
                            <span class="error-mensaje" id="error_nuevaClave"></span>
                        </div>

                        <div class="campo-reserva">
                            <label for="confirmarClave">Confirmar contraseña</label>
                            <input type="password" name="confirmarClave" id="confirmarClave" placeholder="Repite la contraseña" minlength="6" required>
                            <span class="error-mensaje" id="error_confirmarClave"></span>
                        </div>

                        <button type="submit">Guardar nueva contraseña</button>
                    </div>
                </form>

                <script>
                    function validarNuevaClave() {
                        const clave = document.getElementById('nuevaClave');
                        const confirmar = document.getElementById('confirmarClave');
                        const errorClave = document.getElementById('error_nuevaClave');
                        const errorConfirmar = document.getElementById('error_confirmarClave');
                        let valido = true;

                        errorClave.textContent = '';
                        errorConfirmar.textContent = '';
                        clave.classList.remove('input-error', 'input-ok');
                        confirmar.classList.remove('input-error', 'input-ok');

                        if (clave.value.length < 6) {
                            errorClave.textContent = 'Debe tener al menos 6 caracteres.';
                            clave.classList.add('input-error');
                            valido = false;
                        } else {
                            clave.classList.add('input-ok');
                        }

                        if (confirmar.value !== clave.value || confirmar.value === '') {
                            errorConfirmar.textContent = 'Las contraseñas no coinciden.';
                            confirmar.classList.add('input-error');
                            valido = false;
                        } else {
                            confirmar.classList.add('input-ok');
                        }

                        return valido;
                    }
                </script>
            </c:when>
            <c:otherwise>
                <div class="Formulario">
                    <p class="mensaje mensaje-error">${mensaje}</p>
                    <button type="button" onclick="window.location = '${ctx}/RecuperarClave'">Solicitar un nuevo enlace</button>
                </div>
            </c:otherwise>
        </c:choose>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
