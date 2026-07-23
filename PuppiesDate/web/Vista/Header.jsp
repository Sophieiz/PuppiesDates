<%-- 
    Header.jsp - Encabezado reutilizable (3 niveles) Spring Aesthetic
    Requiere (opcional) el atributo de request "activePage" para resaltar el link activo.
    Uso:
        <c:set var="activePage" value="inicio" scope="request"/>
        <%@ include file="Header.jsp" %>          (si el archivo está dentro de /Vista/)
        <%@ include file="Vista/Header.jsp" %>    (si el archivo está en la raíz de /web/)
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:if test="${empty ctx}">
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
</c:if>
<c:set var="urlInicio" value="${not empty sessionScope.nombreUsuario ? (sessionScope.perfil == 1 ? ctx.concat('/PanelAdmin.jsp') : ctx.concat('/PanelUsuario.jsp')) : ctx.concat('/index.jsp')}"/>

<!-- Franja promocional superior -->
<div class="promo-bar">¡Encuentra a tu nuevo mejor amigo hoy en Puppies Dates!</div>

<div class="header-cloud-wrapper">
    <header class="barrainicio barrainicio-centrado main-container">

        <!-- Logo central -->
        <div class="brand-container-centro">
            <a href="${urlInicio}" class="logocorto logocorto-grande" aria-label="Puppies Dates - Inicio">
                <img src="${ctx}/Vista/Imagenes/image.png" alt="Logo Puppies Dates">
            </a>
        </div>

        <!-- Navegación inferior -->
        <nav class="navegacion navegacion-centrada">
            <ul>
                <li><a href="${urlInicio}" class="btn-menu ${activePage == 'inicio' ? 'btn-verde-activo' : ''}">Inicio</a></li>
                <li><a href="${ctx}/Vista/Actividad.jsp" class="btn-menu ${activePage == 'actividades' ? 'btn-verde-activo' : ''}">Actividades</a></li>
                <li><a href="${not empty sessionScope.nombreUsuario ? ctx.concat('/ReservaCliente') : ctx.concat('/Vista/Reserva.jsp')}" class="btn-menu ${activePage == 'reservas' ? 'btn-verde-activo' : ''}">Reservas</a></li>
                <li><a href="${ctx}/CatalogoPerritos" class="btn-menu ${activePage == 'adopta' ? 'btn-verde-activo' : ''}">Adopta</a></li>
            </ul>

            <c:choose>
                <c:when test="${not empty sessionScope.nombreUsuario}">
                    <!-- Menú desplegable de usuario (circulito con patita) -->
                    <div class="user-dropdown">
                        <button type="button" class="user-dropdown-toggle" id="userDropdownToggle" aria-haspopup="true" aria-expanded="false" aria-controls="userDropdownMenu">
                            <span aria-hidden="true">${fn:toUpperCase(fn:substring(sessionScope.nombreUsuario, 0, 1))}</span>
                        </button>
                        <div class="user-dropdown-menu" id="userDropdownMenu" role="menu">
                            <p class="user-dropdown-saludo">Bienvenid@ ${sessionScope.nombreUsuario}</p>
                            <a href="${ctx}/MisSolicitudes" role="menuitem">Mis Solicitudes</a>
                            <a href="${ctx}/MisReservas" role="menuitem">Mis Reservas</a>
                            <hr class="user-dropdown-divider">
                            <a href="${ctx}/CerrarSesion" class="js-logout-link user-dropdown-logout" role="menuitem">Cerrar Sesión</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/Iniciar" class="btn-menu btn-rosa-sesion">Iniciar Sesión</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>
    <div class="cloud-wave"></div>
</div>
