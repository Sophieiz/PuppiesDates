<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Controlador.PerritoDAO"%>
<%@page import="Modelo.Perrito"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>



<%
    PerritoDAO dao = new PerritoDAO();
    List<Perrito> listaPerritosPanel = dao.listarPerritoDisponible();

    if (listaPerritosPanel == null) {
        listaPerritosPanel = new ArrayList<Perrito>();
    } else if (listaPerritosPanel.size() > 4) {
        listaPerritosPanel = listaPerritosPanel.subList(0, 4);
    }
    
    request.setAttribute("listaPerritosPanel", listaPerritosPanel);
%>

<%
    Controlador.ActividadDAO daoActividad = new Controlador.ActividadDAO();
    request.setAttribute("actividades", daoActividad.Actividad());
%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PUPPIES DATES - Panel Principal</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">    <body>   
        <c:if test="${not empty mensaje}">
            <div class="mensaje-bienvenida">
                <p>${mensaje}</p>
            </div>
        </c:if>

        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Vista/Header.jsp" %>

        <div class="main-container">
            <main>
                <section class="hero-section">
                    <div class="hero-left">
                        <h2 class="hero-title">Bienvenid@ ${sessionScope.nombreUsuario}</h2>
                        <p class="hero-subtitle">Descubre un concepto único diseñado para interactuar, pasear y conectar con hermosos perritos rescatados.</p>
                        <a href="${ctx}/ReservaCliente" class="btn-cta-ingresa">¡Reserva tu Cita!</a>
                    </div>
                    <div class="hero-right">
                        <div class="carrusel-container">
                            <div class="carrusel">
                                <div class="imagen-contenedor" id="carrusel">
                                    <img src="${ctx}/Vista/Imagenes/Perrito5.jpg" alt="Perrito 5">
                                    <img src="${ctx}/Vista/Imagenes/Perrito2.jpg" alt="Perrito 2">
                                    <img src="${ctx}/Vista/Imagenes/perrito3_1.jpg" alt="perrito 3">
                                    <img src="${ctx}/Vista/Imagenes/Perrito4.jpg" alt="Perrito 4">
                                </div>
                                <button type="button" class="btn-carrusel-nav prev" id="left">&#10094;</button>
                                <button type="button" class="btn-carrusel-nav next" id="right">&#10095;</button>
                            </div>
                        </div>
                    </div>
                </section>
            </main>
        </div>

        <div class="divisor-nube divisor-a-adopcion"></div>

        <section class="seccion-adopcion">
            <div class="main-container">
                <h2 class="titulo-apartado">Conoce a nuestros amigos</h2>
                <c:choose>
                    <c:when test="${empty listaPerritosPanel}">
                        <p class="sin-perritos">Por ahora no hay perritos disponibles para adopción. ¡Vuelve pronto!</p>
                    </c:when>
                    <c:otherwise>
                        <div class="grid-adopcion grid-adopcion-catalogo">
                            <c:forEach var="perrito" items="${listaPerritosPanel}" varStatus="i">
                                <c:set var="colorBorde" value="${i.index % 3 == 0 ? 'card-borde-rosa' : (i.index % 3 == 1 ? 'card-borde-azul' : 'card-borde-mostaza')}"/>
                                <c:set var="colorTag" value="${i.index % 3 == 0 ? 'bg-tag-rosa' : (i.index % 3 == 1 ? 'bg-tag-azul' : 'bg-tag-mostaza')}"/>
                                <div class="tarjeta-perrito ${colorBorde}">
                                    <c:choose>
                                        <c:when test="${not empty perrito.foto}">
                                            <img src="${fn:startsWith(perrito.foto, 'http') ? perrito.foto : ctx.concat('/').concat(perrito.foto)}" alt="${perrito.nombre}"
                                                 onerror="this.onerror=null; this.src='${ctx}/Vista/Imagenes/Perrito1.jpg';">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="avatar-perrito"></div>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${not empty perrito.etapa_madurez}">
                                        <div class="tag-edad ${colorTag}">${perrito.etapa_madurez}</div>
                                    </c:if>
                                    <h3>${perrito.nombre}</h3>
                                    <p>${perrito.descripcionRaza}${not empty perrito.descripcionRaza ? " · " : ""}${perrito.descripcionSexo}</p>
                                    <a href="${ctx}/SolicitudAdopcionCliente?idPerrito=${perrito.idPerrito}"
                                       class="estado-adopcion js-adoption-modal-link"
                                       data-id="${perrito.idPerrito}">
                                        Quiero adoptarlo
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="ver-mas-wrap">
                            <a href="${ctx}/CatalogoPerritos" class="btn-menu btn-verde-activo">Ver más</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>

        <div class="divisor-nube divisor-a-videos"></div>

        <section class="seccion-videos">
            <div class="main-container">
                <h2 class="titulo-apartado">Momentos Felices</h2>
                <div class="grid-videos">
                    <div class="tarjeta-video">
                        <div class="contenedor-video-real">
                            <iframe src="https://www.youtube.com/embed/4JiT3WrqhCM" 
                                    title="Yoga con perritos" 
                                    frameborder="0" 
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                                    allowfullscreen>
                            </iframe>
                        </div>
                        <h3>Nuestro paseo del domingo</h3>
                    </div>

                    <div class="tarjeta-video">
                        <div class="contenedor-video-real">
                            <iframe src="https://www.youtube.com/embed/-d-LC4UyXis" 
                                    title="Paseo en bicicleta" 
                                    frameborder="0" 
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                                    allowfullscreen>
                            </iframe>
                        </div>
                        <h3>Aprendiendo a jugar</h3>
                    </div>
                </div>
            </div>
        </section>

        <div class="divisor-nube divisor-a-actividades"></div>

        <section class="seccion-actividades">
            <div class="main-container">
                <h2 class="titulo-apartado">Actividades con perritos</h2>
                <p class="subtitulo-seccion">Reserva una experiencia creativa o tranquila para compartir con nuestros peluditos.</p>
                <c:set var="imagenesActividad" value="Perrito3.jpg,Perrito4.jpg,Perrito5.jpg,Perrito6.jpg,Perrito7.jpg,Gatito1.jpg,Gatito2.jpg,Gatito3.jpg,Gatito4.jpg,Gatito5.jpg,Gatito6.jpg"/>
                <c:set var="listaImagenesActividad" value="${fn:split(imagenesActividad, ',')}"/>
                <div class="actividades-showcase">
                    <c:forEach var="act" items="${actividades}" varStatus="loop" end="2">
                        <article class="actividad-card ${loop.index % 2 == 0 ? 'actividad-pintar' : 'actividad-yoga'}">
                            <div class="actividad-media">
                                <img src="${ctx}/Vista/Imagenes/${listaImagenesActividad[loop.index % fn:length(listaImagenesActividad)]}" alt="${act.tipoActividadNombre}"
                                     onerror="this.onerror=null; this.src='${ctx}/Vista/Imagenes/Perrito1.jpg';">
                            </div>
                            <div class="actividad-info">
                                <span class="actividad-tag">Experiencia</span>
                                <h3>${act.tipoActividadNombre}</h3>
                                <p>${act.descripcion_actividad}</p>
                                <c:if test="${not empty act.precioTexto}">
                                    <p class="actividad-precio">${act.precioTexto}</p>
                                </c:if>
                                <a href="${ctx}/ReservaCliente" class="actividad-cta">Reservar</a>
                            </div>
                        </article>
                    </c:forEach>
                    <c:if test="${empty actividades}">
                        <p class="sin-perritos">Pronto tendremos nuevas actividades disponibles.</p>
                    </c:if>
                </div>
                <c:if test="${not empty actividades and fn:length(actividades) > 3}">
                    <div class="ver-mas-wrap">
                        <a href="${ctx}/Actividades" class="btn-menu btn-verde-activo">Ver más</a>
                    </div>
                </c:if>
                <div class="grid-actividades">
                    <div class="bloque-actividad bloque-azul">
                        <div class="icono-actividad"></div>
                        <h3>Paseos y Socialización</h3>
                        <p>Acompaña a nuestros perritos en caminatas guiadas al aire libre para mejorar su confianza con el entorno.</p>
                    </div>
                    <div class="bloque-actividad bloque-rosa">
                        <div class="icono-actividad"></div>
                        <h3>Tarde de Juegos y Mimos</h3>
                        <p>Disfruta de un espacio cerrado repleto de juguetes interactivos donde podrás cepillarlos y darles snacks.</p>
                    </div>
                </div>
            </div> 
        </section>

        <section class="seccion-quienes-somos">
            <div class="main-container">
                <h2 class="titulo-apartado">Quiénes somos</h2>
                <p class="subtitulo-seccion">
                    Puppies Dates nació con un propósito simple: darle una segunda oportunidad a los perritos rescatados y crear un espacio donde las personas puedan conocerlos, cuidarlos y, si lo desean, adoptarlos. Desde el primer paseo hasta la primera sesión de yoga con nuestros peluditos, cada actividad está pensada para acercar a las personas al bienestar animal.
                </p>
                <div class="grid-actividades">
                    <div class="bloque-actividad bloque-azul">
                        <div class="icono-actividad"></div>
                        <h3>Nuestra Misión</h3>
                        <p>Conectar a personas y perritos rescatados a través de actividades y experiencias que promuevan el bienestar animal y faciliten procesos de adopción responsable.</p>
                    </div>
                    <div class="bloque-actividad bloque-rosa">
                        <div class="icono-actividad"></div>
                        <h3>Nuestra Visión</h3>
                        <p>Ser la comunidad de referencia en adopción y cuidado responsable de perritos, construyendo un entorno donde cada peludito encuentre un hogar amoroso.</p>
                    </div>
                </div>
            </div>
        </section>

        <%@ include file="Vista/Footer.jsp" %>

        <div class="adoption-modal-shell" id="adoptionModal" aria-hidden="true">
            <div class="adoption-modal-backdrop" data-adoption-close></div>
            <section class="adoption-modal-box" role="dialog" aria-modal="true" aria-labelledby="adoptionModalTitle">
                <button type="button" class="adoption-modal-close" data-adoption-close aria-label="Cerrar">&times;</button>
                <div class="adoption-modal-content" id="adoptionModalContent">
                    <p class="adoption-modal-loading">Cargando formulario...</p>
                </div>
            </section>
        </div>

        <script>window.ctxApp = "${ctx}";</script>
        <script src="${ctx}/Vista/JavaScript/funciones.js"></script>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
        <script src="${ctx}/Vista/JavaScript/ubicacionAdopcion.js"></script>
    </body>
</html>
