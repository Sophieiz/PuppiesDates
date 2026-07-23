<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Controlador.PerritoDAO"%>
<%@page import="Modelo.Perrito"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    List<Perrito> listaPerritosInicio = new PerritoDAO().listarPerritoDisponible();
    if (listaPerritosInicio.size() > 4) {
        listaPerritosInicio = listaPerritosInicio.subList(0, 4);
    }
    request.setAttribute("listaPerritosInicio", listaPerritosInicio);
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>PUPPIES DATES</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body>   
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Vista/Header.jsp" %>

        <div class="main-container">
            <main>
                <section class="hero-section">
                    <div class="hero-left">
                        <h2 class="hero-title">Bienvenidos</h2>
                        <p class="hero-subtitle">Descubre un concepto único diseñado para interactuar, pasear y conectar con hermosos perritos rescatados.</p>
                        <a href="${ctx}/Iniciar" class="btn-cta-ingresa">Ingresa para Reservar!</a>
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
                    <c:when test="${empty listaPerritosInicio}">
                        <p class="sin-perritos">Por ahora no hay perritos disponibles para adopción. ¡Vuelve pronto!</p>
                    </c:when>
                    <c:otherwise>
                        <div class="grid-adopcion grid-adopcion-catalogo">
                            <c:forEach var="perrito" items="${listaPerritosInicio}" varStatus="i">
                                <c:set var="colorBorde" value="${i.index % 3 == 0 ? 'card-borde-rosa' : (i.index % 3 == 1 ? 'card-borde-azul' : 'card-borde-mostaza')}"/>
                                <c:set var="colorTag" value="${i.index % 3 == 0 ? 'bg-tag-rosa' : (i.index % 3 == 1 ? 'bg-tag-azul' : 'bg-tag-mostaza')}"/>
                                <div class="tarjeta-perrito ${colorBorde}">
                                    <c:choose>
                                        <c:when test="${not empty perrito.foto}">
                                            <img src="${ctx}/${perrito.foto}" alt="${perrito.nombre}"
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
                                    <p>${perrito.raza}${not empty perrito.raza ? " · " : ""}${perrito.sexo}</p>
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
                <div class="actividades-showcase">
                    <article class="actividad-card actividad-pintar">
                        <div class="actividad-media">
                            <img src="${ctx}/Vista/Imagenes/Perrito6.jpg" alt="Perrito en actividad de pintura">
                        </div>
                        <div class="actividad-info">
                            <span class="actividad-tag">Creativa</span>
                            <h3>Pintar tote-bag</h3>
                            <p>Pinta una tote-bag personalizada mientras convives con perritos rescatados en un ambiente relajado.</p>
                            <details>
                                <summary>Que incluye</summary>
                                <p>Bolsa, pinturas, pinceles, acompanamiento y espacio de interaccion con perritos.</p>
                            </details>
                            <a href="${ctx}/Iniciar" class="actividad-cta">Reservar</a>
                        </div>
                    </article>
                    <article class="actividad-card actividad-yoga">
                        <div class="actividad-media">
                            <img src="${ctx}/Vista/Imagenes/Perrito7.jpg" alt="Perrito para yoga">
                        </div>
                        <div class="actividad-info">
                            <span class="actividad-tag">Bienestar</span>
                            <h3>Yoga con perritos</h3>
                            <p>Una sesion suave de yoga para estirar, respirar y compartir momentos tranquilos con los peluditos.</p>
                            <details>
                                <summary>Que incluye</summary>
                                <p>Clase guiada, tapete, hidratacion y tiempo de mimos al finalizar la sesion.</p>
                            </details>
                            <a href="${ctx}/Iniciar" class="actividad-cta">Reservar</a>
                        </div>
                    </article>
                </div>
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

        <script src="${ctx}/Vista/JavaScript/funciones.js"></script>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
