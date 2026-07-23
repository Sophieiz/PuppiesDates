<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Solicitud de adopción</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <c:set var="activePage" value="adopta" scope="request"/>
        <%@ include file="Header.jsp" %>

        <a href="${ctx}/CatalogoPerritos" class="btn-volver-inicio">&larr;</a>

        <c:choose>
            <c:when test="${empty perrito}">
                <p class="sin-perritos">No encontramos ese perrito. <a href="${ctx}/CatalogoPerritos">Vuelve al catálogo</a>.</p>
            </c:when>
            <c:otherwise>
                <div class="adopcion-wrap">
                    <div class="ficha-perrito">
                        <c:choose>
                            <c:when test="${not empty perrito.foto}">
                                <img src="${perrito.foto}" alt="Foto de ${perrito.nombre}">
                            </c:when>
                        </c:choose>
                        <h2>${perrito.nombre}</h2>
                        <div class="ficha-dato"><span>Especie</span><span>${perrito.especie}</span></div>
                        <div class="ficha-dato"><span>Raza</span><span>${perrito.raza}</span></div>
                        <div class="ficha-dato"><span>Fecha nacimiento</span><span>${perrito.fecha_nacimiento}</span></div>
                        <div class="ficha-dato"><span>Sexo</span><span>${perrito.sexo}</span></div>
                        <div class="ficha-dato"><span>Microchip</span><span>${perrito.microchip}</span></div>
                        <div class="ficha-dato"><span>Etapa de madurez</span><span>${perrito.etapa_madurez}</span></div>
                        <div class="ficha-dato"><span>Especialidad</span><span>${perrito.especialidad}</span></div>
                        <div class="ficha-dato"><span>Condiciones especiales</span><span>${perrito.condiciones_especiales}</span></div>
                        <c:if test="${not empty perrito.titulo_historia}">
                            <h3 style="margin-top:15px;color:var(--wild-brown);">${perrito.titulo_historia}</h3>
                        </c:if>
                        <p class="ficha-historia">${perrito.historia}</p>
                    </div>

                    <div class="adopcion-form">
                        <form action="${ctx}/SolicitudAdopcionCliente" method="post" onsubmit="return validarSolicitudAdopcion()" novalidate>
                            <div class="Formulario">
                                <c:if test="${not empty resultado}">
                                    <p class="mensaje ${resultado.startsWith('¡') ? 'mensaje-exito' : 'mensaje-error'}">${resultado}</p>
                                </c:if>

                                <h2 class="titulo-form">Formulario de adopción</h2>
                                <hr>

                                <input type="hidden" name="idPerrito" value="${perrito.idPerrito}">

                                <div class="campo-reserva">
                                    <label for="direccion">Dirección</label>
                                    <input type="text" name="direccion" id="direccion" placeholder="Ej: Calle 45 #12-30">
                                    <span class="error-mensaje" id="error_direccion"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="localidad">Localidad</label>
                                    <input type="text" name="localidad" id="localidad" placeholder="Ej: Suba">
                                    <span class="error-mensaje" id="error_localidad"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="barrio">Barrio</label>
                                    <input type="text" name="barrio" id="barrio">
                                    <span class="error-mensaje" id="error_barrio"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="profesion">Profesión</label>
                                    <input type="text" name="profesion" id="profesion">
                                    <span class="error-mensaje" id="error_profesion"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="vive_en">Vive en</label>
                                    <select name="vive_en" id="vive_en">
                                        <option value="">Seleccione...</option>
                                        <c:forEach var="opcion" items="${listaViveEn}">
                                            <option value="${opcion.descripcion}">${opcion.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="error-mensaje" id="error_vive_en"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="tipo_vivienda">Tipo de vivienda</label>
                                    <select name="tipo_vivienda" id="tipo_vivienda">
                                        <option value="">Seleccione...</option>
                                        <c:forEach var="opcion" items="${listaTipoVivienda}">
                                            <option value="${opcion.descripcion}">${opcion.descripcion}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="error-mensaje" id="error_tipo_vivienda"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="nucleo_familiar">Núcleo familiar</label>
                                    <textarea name="nucleo_familiar" id="nucleo_familiar" rows="3" placeholder="Explica brevemente cómo está conformada tu familia"></textarea>
                                    <span class="error-mensaje" id="error_nucleo_familiar"></span>
                                </div>

                                <div class="campo-reserva">
                                    <label for="tiene_mascotas">¿Tienes mascotas actualmente?</label>
                                    <select name="tiene_mascotas" id="tiene_mascotas">
                                        <option value="false">No</option>
                                        <option value="true">Sí</option>
                                    </select>
                                </div>

                                <button type="submit">Enviar solicitud</button>
                            </div>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>

        <script src="${ctx}/Vista/JavaScript/validarSolicitudAdopcion.js" defer></script>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
