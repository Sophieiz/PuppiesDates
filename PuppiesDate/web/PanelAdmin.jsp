<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Controlador.ReservaDAO"%>
<%@page import="Controlador.Solicitud_adopcionDAO"%>
<%@page import="Modelo.Reserva"%>
<%@page import="Modelo.Solicitud_adopcion"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    LocalDate hoyAdmin = LocalDate.now(ZoneId.of("America/Bogota"));
    List<Reserva> todasReservasAdmin = new ReservaDAO().listarReserva();
    List<Reserva> reservasHoyAdmin = new ArrayList<>();
    List<Reserva> reservasProgramadasAdmin = new ArrayList<>();

    for (Reserva reservaAdmin : todasReservasAdmin) {
        if (reservaAdmin.getFecha() != null) {
            LocalDate fechaReservaAdmin = reservaAdmin.getFecha().toLocalDate();
            if (fechaReservaAdmin.isEqual(hoyAdmin)) {
                reservasHoyAdmin.add(reservaAdmin);
                reservasProgramadasAdmin.add(reservaAdmin);
            } else if (fechaReservaAdmin.isAfter(hoyAdmin)) {
                reservasProgramadasAdmin.add(reservaAdmin);
            }
        }
    }

    reservasProgramadasAdmin.sort(
        Comparator.comparing(Reserva::getFecha).thenComparing(Reserva::getHora, Comparator.nullsLast(Comparator.naturalOrder()))
    );

    int limiteReservasAdmin = Math.min(reservasProgramadasAdmin.size(), 6);
    List<Reserva> reservasPanelAdmin = new ArrayList<>(reservasProgramadasAdmin.subList(0, limiteReservasAdmin));
    List<Solicitud_adopcion> todasSolicitudesAdmin = new Solicitud_adopcionDAO().listarSolicitud_adopcion();
    int limiteSolicitudesAdmin = Math.min(todasSolicitudesAdmin.size(), 6);
    List<Solicitud_adopcion> solicitudesPanelAdmin = new ArrayList<>(todasSolicitudesAdmin.subList(0, limiteSolicitudesAdmin));
    int progresoReservasAdmin = Math.min(360, reservasHoyAdmin.size() * 30);

    request.setAttribute("fechaHoyAdmin", hoyAdmin);
    request.setAttribute("diaHoyAdmin", hoyAdmin.getDayOfMonth());
    request.setAttribute("mesHoyAdmin", hoyAdmin.getMonth().toString());
    request.setAttribute("anioHoyAdmin", hoyAdmin.getYear());
    request.setAttribute("totalReservasHoyAdmin", reservasHoyAdmin.size());
    request.setAttribute("totalReservasProgramadasAdmin", reservasProgramadasAdmin.size());
    request.setAttribute("reservasPanelAdmin", reservasPanelAdmin);
    request.setAttribute("solicitudesPanelAdmin", solicitudesPanelAdmin);
    request.setAttribute("progresoReservasAdmin", progresoReservasAdmin);
%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="adminNombre" value="${empty sessionScope.nombreUsuario ? 'Iber' : sessionScope.nombreUsuario}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Panel Admin - Puppies Dates</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Fredoka:wght@400;600;700&family=Quicksand:wght@500;700&display=swap" rel="stylesheet">
    </head>
    <body class="admin-body">
        <aside class="barrainicioadmin">
            <div class="logotitulosoftadmin">
                <div class="logocortoadmin">
                    <img src="${ctx}/Vista/Imagenes/image.png" alt="Puppies Dates">
                </div>
                <h1>Puppies Dates</h1>
            </div>

            <nav class="navegacionadmin">
                <a class="admin-nav-home" href="${ctx}/PanelAdmin.jsp">Inicio</a>
                <details class="admin-nav-group" open>
                    <summary>Gestión de Reservas</summary>
                    <a href="${ctx}/Actividad">Actividades</a>
                    <a href="${ctx}/Tipoactividad">Tipo de actividad</a>
                    <a href="${ctx}/Listaprecios">Lista de precios</a>
                    <a href="${ctx}/Disponibilidaad">Disponibilidad</a>
                    <a href="${ctx}/Horarios">Horario</a>
                    <a href="${ctx}/ReservaAdmi">Reservas</a>
                    <a href="${ctx}/PagosAdmi">Pagos</a>
                    <a href="${ctx}/EstadoReservaAdmi">Estado reserva</a>
                </details>
                <details class="admin-nav-group">
                    <summary>Gestión de Adopciones</summary>
                    <a href="${ctx}/PerritoAdmi">Perritos</a>
                    <a href="${ctx}/SolicitudAdopcionAdmi">Solicitudes de adopción</a>
                </details>
                <details class="admin-nav-group">
                    <summary>Usuarios y permisos</summary>
                    <a href="${ctx}/Tipodocumento">Tipo de documento</a>
                    <a href="${ctx}/UsuarioAdmi">Usuarios</a>
                    <a href="${ctx}/RolesAdmi">Roles</a>
                </details>
                
                <a class="admin-nav-papeleria" href="${ctx}/PapeleraAdmi">Papelería</a>
                <a class="admin-nav-logout js-logout-link" href="${ctx}/CerrarSesion">Cerrar Sesión</a>
            </nav>
        </aside>

        <main class="panel-admin-main">
            <section class="panel-admin-topbar">
                <div>
                    <p class="panel-admin-saludo"> ${adminNombre}</p>
                    <h2>Panel de administracion</h2>
                </div>
            </section>

            <section class="panel-admin-hero">
                <div class="panel-admin-welcome">
                    <p class="panel-admin-kicker">Puppies Dates</p>
                    <h3>Resumen del dia</h3>
                    <p>Controla reservas, cupos, actividades y usuarios desde un panel limpio y rapido.</p>
                    <p class="panel-admin-motivacional">Gracias por liderar el cambio y encontrar un hogar para cada huellita hoy </p>
                </div>
                <img src="${ctx}/Vista/Imagenes/image.png" alt="Logo Puppies Dates">
            </section>

            <section class="admin-section admin-adoption-requests-section">
                <div class="admin-section-title">
                    <div>
                        <span>Adopciones</span>
                        <h3>Solicitudes de adopción</h3>
                    </div>
                    <a href="${ctx}/SolicitudAdopcionAdmi">Ver todas</a>
                </div>

                <div class="admin-table-wrap">
                    <table class="admin-reservas-table admin-solicitudes-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Perrito</th>
                                <th>Solicitante</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="solicitud" items="${solicitudesPanelAdmin}">
                                <tr>
                                    <td>#${solicitud.idSolicitud_adopcion}</td>
                                    <td>${solicitud.nombrePerrito}</td>
                                    <td>${solicitud.nombreUsuario} ${solicitud.apellidoUsuario}</td>
                                    <td><fmt:formatDate value="${solicitud.fecha_solicitud}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td><span class="admin-status">${solicitud.descripcionEstado_solicitud}</span></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty solicitudesPanelAdmin}">
                                <tr>
                                    <td colspan="5" class="admin-empty-state">
                                        <div class="admin-empty-illustration"></div>
                                        <p>Todavía no hay solicitudes de adopción.</p>
                                        <span>Cuando alguien solicite adoptar un perrito, aparecerá aquí.</span>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </section>

            <section class="panel-admin-summary">
                <article class="admin-calendar-card">
                    <span>Hoy</span>
                    <strong>${diaHoyAdmin}</strong>
                    <p>${mesHoyAdmin} ${anioHoyAdmin}</p>
                </article>

                <article class="admin-ring-card">
                    <div class="admin-ring" style="--progress:${progresoReservasAdmin}deg;">
                        <span>${totalReservasHoyAdmin}</span>
                    </div>
                    <div>
                        <h3>Reservas de hoy</h3>
                        <p>${totalReservasProgramadasAdmin} reservas de hoy y proximas en agenda.</p>
                    </div>
                </article>

            </section>

            <section class="panel-admin-content">
                <div class="panel-admin-left">
                    <section class="admin-section">
                        <div class="admin-section-title">
                            <div>
                                <span>Agenda</span>
                                <h3>Reservas registradas</h3>
                            </div>
                            <small>Hoy y proximas</small>
                        </div>

                        <div class="admin-table-wrap">
                            <table class="admin-reservas-table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Fecha</th>
                                        <th>Hora</th>
                                        <th>Personas</th>
                                        <th>Usuario</th>
                                        <th>Actividad</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="reserva" items="${reservasPanelAdmin}">
                                        <tr>
                                            <td>#${reserva.idReserva}</td>
                                            <td>${reserva.fecha}</td>
                                            <td>${reserva.hora}</td>
                                            <td>${reserva.num_personas}</td>
                                            <td>${reserva.usuarios_idUsuarios}</td>
                                            <td>${reserva.nombreActividad}</td>
                                            <td><span class="admin-status">Programada</span></td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty reservasPanelAdmin}">
                                        <tr>
                                            <td colspan="7" class="admin-empty-state">
                                                <div class="admin-empty-illustration"></div>
                                                <p>No hay reservas para hoy ni próximas fechas.</p>
                                                <span>Las nuevas reservas de los usuarios se listarán aquí.</span>
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>

                <aside class="panel-admin-side">
                    <section class="admin-profile-card">
                        <img src="${ctx}/Vista/Imagenes/Perrito1.jpg" alt="Perrito Puppies Dates">
                        <h3>${adminNombre}</h3>
                        <p>Administrador</p>
                        <a href="${ctx}/UsuarioAdmi">Gestionar usuarios</a>
                    </section>
                </aside>
            </section>
        </main>

        <footer class="footer-aesthetic footer-minimal footer-minimal-admin">
            <div class="footer-minimal-inner">
                <div class="footer-minimal-logo">
                    <img src="${ctx}/Vista/Imagenes/image.png" alt="Logo Puppies Dates">
                </div>
                <p class="footer-minimal-nombre">Puppies Dates</p>
                <p class="footer-minimal-copy">&copy; 2026 Puppies Dates. Todos los derechos reservados.</p>
            </div>
        </footer>
        <div class="logout-modal-shell" id="logoutModal" aria-hidden="true">
            <div class="logout-modal-backdrop" data-logout-close></div>
            <section class="logout-modal-box" role="dialog" aria-modal="true" aria-labelledby="logoutModalTitle">
                <div class="logout-modal-icon"></div>
                <h3 id="logoutModalTitle">¿Ya te vas?</h3>
                <p>¿Estás seguro de que deseas cerrar sesión? Te esperamos pronto en Puppies Dates.</p>
                <div class="logout-modal-actions">
                    <button type="button" class="logout-modal-cancel" data-logout-close>Cancelar</button>
                    <button type="button" class="logout-modal-confirm" data-logout-confirm>Cerrar sesión</button>
                </div>
            </section>
        </div>

        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>
