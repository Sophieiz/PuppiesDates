<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
</head>
<body>
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
                <div class="actividades-showcase">
                    <article class="actividad-card actividad-pintar">
                        <div class="actividad-media">
                            <img src="${ctx}/Vista/Imagenes/Perrito6.jpg" alt="Perrito en actividad de pintura">
                        </div>
                        <div class="actividad-info">
                            <span class="actividad-tag">Creativa</span>
                            <h3>Pintar tote-bag</h3>
                            <p>Disena y pinta una tote-bag mientras compartes con perritos tranquilos y curiosos.</p>
                            <details open>
                                <summary>Que incluye</summary>
                                <p>Bolsa, pinturas, pinceles, guia creativa y espacio de convivencia con perritos.</p>
                            </details>
                            <details>
                                <summary>Ideal para</summary>
                                <p>Personas que quieren una actividad manual, colorida y relajada.</p>
                            </details>
                            <a href="${actividadReservaUrl}" class="actividad-cta">Reservar</a>
                        </div>
                    </article>

                    <article class="actividad-card actividad-yoga">
                        <div class="actividad-media">
                            <img src="${ctx}/Vista/Imagenes/Perrito7.jpg" alt="Perrito para yoga">
                        </div>
                        <div class="actividad-info">
                            <span class="actividad-tag">Bienestar</span>
                            <h3>Yoga con perritos</h3>
                            <p>Una sesion de estiramientos suaves, respiracion y conexion con los peluditos.</p>
                            <details open>
                                <summary>Que incluye</summary>
                                <p>Clase guiada, tapete, hidratacion y tiempo de mimos al finalizar.</p>
                            </details>
                            <details>
                                <summary>Ideal para</summary>
                                <p>Visitantes que buscan una experiencia calmada, tierna y diferente.</p>
                            </details>
                            <a href="${actividadReservaUrl}" class="actividad-cta">Reservar</a>
                        </div>
                    </article>
                </div>
            </div>
        </section>
    </main>

    <%@ include file="Footer.jsp" %>
    <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
</body>
</html>
