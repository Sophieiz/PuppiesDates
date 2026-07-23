<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Registro</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
        <script src="${ctx}/Vista/JavaScript/validarReg.js" defer></script>
    </head>
    <body class="pagina-formulario">   
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Header.jsp" %>

        <a href="${ctx}/index.jsp" class="btn-volver-inicio">&larr;</a>

        <form action="${ctx}/Registrarse" method="post" onsubmit="return validarRegistro()" novalidate>
            <div class="Formulario">

                <c:if test="${not empty resultado}">
                    <p class="mensaje mensaje-error">${resultado}</p>
                </c:if>

                <h2 class="titulo-form">Crear una cuenta</h2>
                <hr>

                <div class="campo-reserva">
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombrep" id="nombre" placeholder="Ej: María">
                    <span class="error-mensaje" id="error_nombre"></span>
                </div>

                <div class="campo-reserva">
                    <label for="apellido">Apellido</label>
                    <input type="text" name="apellidoa" id="apellido" placeholder="Ej: González">
                    <span class="error-mensaje" id="error_apellido"></span>
                </div>

                <div class="campo-reserva">
                    <label for="tipodoc">Tipo de documento</label>
                    <select id="tipodoc" name="tipodocs">
                        <option value="">-- Selecciona un tipo --</option>
                        <c:forEach var="tipo" items="${tiposDoc}">
                            <option value="${tipo.idTipo_documento}">${tipo.descripcion_doc}</option>
                        </c:forEach>
                    </select>
                    <span class="error-mensaje" id="error_tipodoc"></span>
                </div>

                <div class="campo-reserva">
                    <label for="documento">Número de documento</label>
                    <input type="text" name="documentoa" id="documento" placeholder="Ej: 1023456789" maxlength="12">
                    <span class="error-mensaje" id="error_documento"></span>
                </div>

                <div class="campo-reserva">
                    <label for="telefono">Teléfono</label>
                    <input type="text" name="telefonoi" id="telefono" placeholder="Ej: 3001234567" maxlength="10">
                    <span class="error-mensaje" id="error_telefono"></span>
                </div>

                <div class="campo-reserva">
                    <label for="correo">Correo electrónico</label>
                    <input type="email" name="correoz" id="correo" placeholder="Ej: maria@correo.com">
                    <span class="error-mensaje" id="error_correo"></span>
                </div>

                <div class="campo-reserva">
                    <label for="clave">Contraseña</label>
                    <input type="password" name="clavev" id="clave" placeholder="Mínimo 6 caracteres">
                    <span class="error-mensaje" id="error_clave"></span>
                </div>

                <div class="campo-reserva">
                    <label for="fecha_nac">Fecha de nacimiento</label>
                    <input type="date" name="fecha_nac" id="fecha_nac">
                    <span class="error-mensaje" id="error_fecha_nac"></span>
                </div>

                <div class="campo-reserva campo-checkbox">
                    <label class="label-checkbox">
                        <input type="checkbox" name="checkbox" id="checkbox">
                        Acepto el tratamiento de mis datos personales
                    </label>
                    <span class="error-mensaje" id="error_checkbox"></span>
                </div>

                <button type="submit">Registrarse</button>
                <button type="button" onclick="window.location = '${ctx}/Iniciar'">Iniciar sesión</button>
            </div>
        </form>
        <c:if test="${not empty mensajeExito}">
            <div class="modal-exito-shell is-open" id="modalExito">
                <div class="modal-exito-backdrop"></div>
                <section class="modal-exito-box">
                    <h3 class="modal-exito-titulo">¡Registro Exitoso! 🐾</h3>
                    <p class="modal-exito-texto">${mensajeExito}</p>
                    <button type="button" 
                            id="btnIrIniciarSesion" 
                            class="modal-exito-btn" 
                            data-url="${ctx}/Iniciar">
                        Ir a Iniciar Sesión
                    </button>
                </section>
            </div>
        </c:if>


        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
