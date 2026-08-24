<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Perritos en adopción</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    <body>
        <c:set var="activePage" value="adopta" scope="request"/>
        <%@ include file="Header.jsp" %>

        <h2 class="titulo-seccion">Mascotas en adopción</h2>
        <p class="subtitulo-seccion">Cada uno tiene una historia distinta. Conoce la suya y dale un nuevo hogar.</p>

        <div class="filtro-catalogo">
            <label for="filtroEspecie" class="filtro-etiqueta">Especie:</label>
            <select id="filtroEspecie" class="filtro-select">
                <option value="todas">Todas</option>
                <c:forEach var="entry" items="${razasPorEspecie}">
                    <option value="${fn:toLowerCase(entry.key)}">${entry.key}</option>
                </c:forEach>
            </select>

            <label for="filtroRaza" class="filtro-etiqueta">Raza:</label>
            <select id="filtroRaza" class="filtro-select">
                <option value="todas">Todas las razas</option>
                <c:forEach var="raza" items="${listaRazas}">
                    <option value="${fn:toLowerCase(raza)}">${raza}</option>
                </c:forEach>
            </select>
        </div>

        <c:choose>
            <c:when test="${empty listaPerritos}">
                <p class="sin-perritos">Por ahora no hay perritos disponibles para adopción. ¡Vuelve pronto!</p>
            </c:when>
            <c:otherwise>
                <div class="grid-adopcion grid-adopcion-catalogo" id="gridPerritos">
                    <c:forEach var="perrito" items="${listaPerritos}" varStatus="i">
                        <c:set var="colorBorde" value="${i.index % 3 == 0 ? 'card-borde-rosa' : (i.index % 3 == 1 ? 'card-borde-verde' : 'card-borde-mostaza')}"/>
                        <c:set var="colorTag" value="${i.index % 3 == 0 ? 'bg-tag-rosa' : (i.index % 3 == 1 ? 'bg-tag-verde' : 'bg-tag-mostaza')}"/>
                        <div class="tarjeta-perrito ${colorBorde}" data-raza="${fn:toLowerCase(perrito.descripcionRaza)}" data-especie="${fn:toLowerCase(perrito.descripcionEspecie)}">

                            <!-- 1. CONTENEDOR DE IMAGEN -->
                            <div class="contenedor-foto-catalogo">
                                <c:choose>
                                    <c:when test="${not empty perrito.foto}">
                                        <img src="${fn:startsWith(perrito.foto, 'http') ? perrito.foto : ctx.concat('/').concat(perrito.foto)}"
                                             alt="Foto de ${perrito.nombre}"
                                             onerror="this.onerror=null; this.src='${ctx}/Vista/Imagenes/Perrito1.jpg';" />
                                    </c:when>
                                    <c:otherwise>
                                        <div class="avatar-perrito"></div>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${not empty perrito.etapa_madurez}">
                                    <span class="tag-edad ${colorTag}">${perrito.etapa_madurez}</span>
                                </c:if>
                            </div>

                            <!-- 2. INFORMACIÓN Y BOTÓN DE ADOPCIÓN -->
                            <div class="info-card-body">
                                <h3>${perrito.nombre}</h3>
                                <p>${perrito.descripcionRaza}${not empty perrito.descripcionRaza ? ' · ' : ''}${perrito.descripcionSexo}</p>

                                <a href="${ctx}/SolicitudAdopcionCliente?idPerrito=${perrito.idPerrito}" 
                                   class="estado-adopcion js-adoption-modal-link"
                                   data-id="${perrito.idPerrito}">
                                    Quiero adoptarlo
                                </a>
                            </div>

                        </div>
                    </c:forEach>
                </div>
                <p class="sin-perritos sin-resultados-filtro" id="sinResultadosFiltro" style="display:none;">
                    No encontramos perritos de esa raza. Prueba con otra.
                </p>
            </c:otherwise>
        </c:choose>

        <%@ include file="Footer.jsp" %>
        <div class="adoption-modal-shell" id="adoptionModal" aria-hidden="true">
            <div class="adoption-modal-backdrop" data-adoption-close></div>
            <section class="adoption-modal-box" role="dialog" aria-modal="true" aria-labelledby="adoptionModalTitle">
                <div class="adoption-modal-content" id="adoptionModalContent">
                    <p class="adoption-modal-loading">Cargando formulario...</p>
                </div>
            </section>
        </div>

        <script>window.ctxApp = "${ctx}";</script>
        <script>
            window.razasPorEspecie = {
            <c:forEach var="entry" items="${razasPorEspecie}" varStatus="loopEspecie">
            "${fn:toLowerCase(entry.key)}": [
                <c:forEach var="raza" items="${entry.value}" varStatus="loopRaza">
            "${fn:toLowerCase(raza)}"<c:if test="${!loopRaza.last}">,</c:if>
                </c:forEach>
            ]<c:if test="${!loopEspecie.last}">,</c:if>
            </c:forEach>
            };
        </script>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
        <script src="${ctx}/Vista/JavaScript/funciones.js"></script>
        <script src="${ctx}/Vista/JavaScript/ubicacionAdopcion.js"></script>

    </body>
</html>
