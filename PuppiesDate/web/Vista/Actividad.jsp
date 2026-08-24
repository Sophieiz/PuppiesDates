<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:choose>
    <c:when test="${not empty sessionScope.nombreUsuario}">
        <c:set var="actividadReservaUrl" value="${ctx}/ReservaCliente"/>
    </c:when>
    <c:otherwise>
        <c:set var="actividadReservaUrl" value="${ctx}/Iniciar"/>
    </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Actividades - Puppies Dates</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    <body>
        <c:set var="activePage" value="actividades" scope="request"/>
        <%@ include file="Header.jsp" %>

        <main class="actividad-page">
            <section class="actividad-page-hero main-container">
                <div>
                    <span class="actividad-tag">Experiencias</span>
                    <h2>Actividades con perritos</h2>
                    <p>Dos planes pensados para crear, respirar y compartir con perritos rescatados en un espacio amoroso.</p>
                </div>
                <img src="${ctx}/Vista/Imagenes/Perrito5.jpg" alt="Perrito feliz en Puppies Dates">
            </section>

            <section class="seccion-actividades actividad-page-body">
                <div class="main-container">
                    <c:set var="imagenesActividad" value="perrito3_1.jpg,Perrito4.jpg,Perrito5.jpg,Perrito6.jpg,Perrito7.jpg,Gatito1.jpg,Gatito2.jpg,Gatito3.jpg,Gatito4.jpg,Gatito5.jpg,Gatito6.jpg"/>
                    <c:set var="listaImagenesActividad" value="${fn:split(imagenesActividad, ',')}"/>
                    <div class="actividades-showcase">
                        <c:forEach var="act" items="${actividades}" varStatus="loop">
                            <article class="actividad-card ${loop.index % 2 == 0 ? 'actividad-pintar' : 'actividad-yoga'}">
                                <div class="actividad-media">
                                    <img src="${ctx}/Vista/Imagenes/${listaImagenesActividad[loop.index % fn:length(listaImagenesActividad)]}" alt="${act.tipoActividadNombre}">
                                </div>
                                <div class="actividad-info">
                                    <span class="actividad-tag">Experiencia</span>
                                    <h3>${act.tipoActividadNombre}</h3>
                                    <p>${act.descripcion_actividad}</p>
                                    <c:if test="${not empty act.precioTexto}">
                                        <p class="actividad-precio">${act.precioTexto}</p>
                                    </c:if>
                                    <a href="${actividadReservaUrl}" class="actividad-cta">Reservar</a>
                                </div>
                            </article>
                        </c:forEach>
                        <c:if test="${empty actividades}">
                            <p class="sin-perritos">Pronto tendremos nuevas actividades disponibles.</p>
                        </c:if>
                    </div>
                </div>
            </section>
        </main>

        <%@ include file="Footer.jsp" %>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>