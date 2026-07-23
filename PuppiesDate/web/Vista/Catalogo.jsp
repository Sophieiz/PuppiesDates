<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Puppies Dates - Perritos en adopción</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body>
        <div class="header-cloud-wrapper">
            <header class="barrainicio main-container">
                <div class="brand-container">
                    <div class="logocorto">
                        <img src="${ctx}/Vista/Imagenes/image.png" alt="Logo Puppies Dates">
                    </div>
                    <h1 class="logo-texto">PUPPIES DATES</h1>
                </div>
                <nav class="navegacion">
                    <ul>
                        <li><a href="${ctx}/index.jsp" class="btn-menu">Inicio</a></li>
                        <li><a href="${ctx}/Vista/Actividad.jsp" class="btn-menu">Actividad</a></li>
                        <li><a href="${ctx}/CatalogoPerritos" class="btn-menu btn-verde-activo">Adopta</a></li>
                        <li><a href="${ctx}/CargarRegistro" class="btn-menu btn-rosa-sesion">Registrarse</a></li>
                    </ul>
                </nav>
                <div class="cloud-wave"></div>
            </header>
        </div>

        <h2 class="titulo-seccion">Perritos en adopción</h2>
        <p class="subtitulo-seccion">Cada uno tiene una historia distinta. Conoce la suya y dale un nuevo hogar.</p>

        <form action="${ctx}/CatalogoPerritos" method="GET" class="buscador-catalogo">
            <input type="text" name="buscar" placeholder="Buscar perrito por nombre..." value="${terminoBusqueda}">
            <button type="submit">Buscar</button>
            <c:if test="${not empty terminoBusqueda}">
                <a href="${ctx}/CatalogoPerritos" class="buscador-limpiar">Ver todos</a>
            </c:if>
        </form>

        <c:choose>
            <c:when test="${empty listaPerritos}">
                <p class="sin-perritos">
                    <c:choose>
                        <c:when test="${not empty terminoBusqueda}">No encontramos perritos con "${terminoBusqueda}". Intenta con otro nombre.</c:when>
                        <c:otherwise>Por ahora no hay perritos disponibles para adopción. ¡Vuelve pronto!</c:otherwise>
                    </c:choose>
                </p>
            </c:when>
            <c:otherwise>
                <div class="grid-adopcion grid-adopcion-catalogo">
                    <c:forEach var="perrito" items="${listaPerritos}" varStatus="i">
                        <c:set var="colorBorde" value="${i.index % 3 == 0 ? 'card-borde-rosa' : (i.index % 3 == 1 ? 'card-borde-verde' : 'card-borde-mostaza')}"/>
                        <c:set var="colorTag" value="${i.index % 3 == 0 ? 'bg-tag-rosa' : (i.index % 3 == 1 ? 'bg-tag-verde' : 'bg-tag-mostaza')}"/>
                        <div class="tarjeta-perrito ${colorBorde}">

                            <!-- 1. CONTENEDOR DE IMAGEN CORREGIDO -->
                            <div class="contenedor-foto-catalogo">
                                <c:choose>
                                    <c:when test="${not empty perrito.foto}">
                                        <img src="${ctx}/${perrito.foto}" 
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
                                <p>${perrito.raza}${not empty perrito.raza ? ' · ' : ''}${perrito.sexo}</p>

                                <!-- Asegúrate de usar el atributo ID correcto de tu clase Perrito -->
                                <a href="${ctx}/SolicitudAdopcionCliente?idPerrito=${perrito.idPerrito}" 
                                   class="estado-adopcion js-adoption-modal-link"
                                   data-id="${perrito.idPerrito}">
                                    Quiero adoptarlo
                                </a>
                            </div>

                        </div>
                    </c:forEach>
                </div>
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
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
