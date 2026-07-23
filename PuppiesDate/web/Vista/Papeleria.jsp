<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Papelería</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    </head>
    <body class="admin-form-body">
        <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>

        <div class="admin-form-wrap">
            <div class="admin-table-card">
                <div class="admin-crud-toolbar">
                    <h3>Papelería</h3>
                </div>
                <p class="admin-crud-help">
                    Restablecer datos
                </p>
            </div>

            <!-- Estado reservaaaa -->
            <details class="admin-inactivos-wrap" open>
                <summary>Estados de reserva eliminados (${fn:length(estadosReservaInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                <th>Descripción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="estado" items="${estadosReservaInactivos}">
                                <tr>
                                    <td>${estado.idEstado_reserva}</td>
                                    <td>${estado.descripcion_esta}</td>
                                    <td>
                                        <form action="${ctx}/EstadoReservaAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idEstado_reserva" value="${estado.idEstado_reserva}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty estadosReservaInactivos}">
                                <tr><td colspan="3" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Horariooooos -->
            <details class="admin-inactivos-wrap">
                <summary>Horarios eliminados (${fn:length(horariosInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Hora inicio</th>
                                <th>Hora fin</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="horario" items="${horariosInactivos}">
                                <tr>
                                    <td>${horario.idHorarios}</td>
                                    <td>${horario.hora_ini}</td>
                                    <td>${horario.hora_fin}</td>
                                    <td>
                                        <form action="${ctx}/Horarios" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idHorarios" value="${horario.idHorarios}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty horariosInactivos}">
                                <tr><td colspan="4" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Roleeees -->
            <details class="admin-inactivos-wrap">
                <summary>Roles eliminados (${fn:length(rolesInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                <th>Descripción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="rol" items="${rolesInactivos}">
                                <tr>
                                    <td>${rol.idRoles}</td>
                                    <td>${rol.descripcion_rol}</td>
                                    <td>
                                        <form action="${ctx}/RolesAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idRoles" value="${rol.idRoles}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty rolesInactivos}">
                                <tr><td colspan="3" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <!-- Actividadeees -->
            </details>

            <details class="admin-inactivos-wrap">
                <summary>Actividades eliminadas (${fn:length(listaActividadesInactivas)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                               
                                <th>Descripción</th>
                                <th>Tipo</th>
                                <th>Precio</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="actividad" items="${listaActividadesInactivas}">
                                <tr>
                                    <td>${actividad.idActividad}</td>
                                    <td>${actividad.descripcion_actividad}</td>
                                    <td>
                                        <c:forEach var="tipo" items="${listaTiposActividad}">
                                            <c:if test="${actividad.tipo_Actividad_idTipo_Actividad == tipo.idTipo_Actividad}">
                                                ${tipo.nombre_activi}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="precio" items="${listaPrecios}">
                                            <c:if test="${actividad.lista_Precios_idLista_Precios == precio.idLista_Precio}">
                                                ${precio.descrip_precio}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <form action="${ctx}/Actividad" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idActividad" value="${actividad.idActividad}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaActividadesInactivas}">
                                <tr>
                                    <td colspan="5" class="admin-empty">No hay actividades inactivas.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>


            <!-- Disponibilidaaad -->
            <details class="admin-inactivos-wrap">
                <summary>Disponibilidad eliminados (${fn:length(listaDisponibilidadesInactivas)})</summary>

                <div class="tabla-responsive-admin">
                    <table class="tabla-lista-admin">
                        <thead>
                            <tr>
                                
                                <th>Fecha</th>
                                <th>Cupo total</th>
                                <th>Cupo disponible</th>
                                <th>Horario</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="disp" items="${listaDisponibilidadesInactivas}">
                                <tr>
                                    <td>${disp.idDisponibilidad}</td>
                                    <td>${disp.fecha}</td>
                                    <td>${disp.cupo_total}</td>
                                    <td>${disp.cupo_disponible}</td>
                                    <td>
                                        <c:forEach var="horario" items="${listaHorarios}">
                                            <c:if test="${horario.idHorarios == disp.horarios_idHorarios}">
                                                ${horario.hora_ini} - ${horario.hora_fin}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <form action="${ctx}/Disponibilidaad" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idDisponibilidad" value="${disp.idDisponibilidad}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">
                                                Reactivar
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty listaDisponibilidadesInactivas}">
                                <tr>
                                    <td colspan="6" class="admin-empty">No hay disponibilidad inactiva.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Lista precios -->
            <details class="admin-inactivos-wrap">
                <summary>Precios eliminados (${fn:length(listaPreciosInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Descripción</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="precio" items="${listaPreciosInactivos}">
                                <tr>
                                    <td>${precio.idLista_Precio}</td>
                                    <td>${precio.descrip_precio}</td>
                                    <td>
                                        <form action="${ctx}/Listaprecios" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idLista_Precios" value="${precio.idLista_Precio}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaPreciosInactivos}">
                                <tr><td colspan="3" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Tipo de actividad -->
            <details class="admin-inactivos-wrap">
                <summary>Tipos de actividad eliminados (${fn:length(listaTiposActividadInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Nombre</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="tipo" items="${listaTiposActividadInactivos}">
                                <tr>
                                    <td>${tipo.idTipo_Actividad}</td>
                                    <td>${tipo.nombre_activi}</td>
                                    <td>
                                        <form action="${ctx}/Tipoactividad" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idTipo_Actividad" value="${tipo.idTipo_Actividad}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaTiposActividadInactivos}">
                                <tr><td colspan="3" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Tipo de documento -->
            <details class="admin-inactivos-wrap">
                <summary>Tipos de documento eliminados (${fn:length(listaTiposDocumentoInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Descripción</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="doc" items="${listaTiposDocumentoInactivos}">
                                <tr>
                                    <td>${doc.idTipo_documento}</td>
                                    <td>${doc.descripcion_doc}</td>
                                    <td>
                                        <form action="${ctx}/Tipodocumento" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idTipo_documento" value="${doc.idTipo_documento}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaTiposDocumentoInactivos}">
                                <tr><td colspan="3" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Pagos -->
            <details class="admin-inactivos-wrap">
                <summary>Pagos eliminados (${fn:length(listaPagosInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                               
                                <th>Estado del pago</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pago" items="${listaPagosInactivos}">
                                <tr>
                                    <td>${pago.idPagos}</td>
                                    <td>${pago.estado_pago}</td>
                                    <td>
                                        <form action="${ctx}/PagosAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idPagos" value="${pago.idPagos}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaPagosInactivos}">
                                <tr><td colspan="3" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Perritos -->
            <details class="admin-inactivos-wrap">
                <summary>Perritos eliminados (${fn:length(listaPerritosInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Nombre</th>
                                <th>Raza</th>
                                <th>Ciudad</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${listaPerritosInactivos}">
                                <tr>
                                    <td>${p.idPerrito}</td>
                                    <td>${p.nombre}</td>
                                    <td>${p.raza}</td>
                                    <td>${p.ciudad}</td>
                                    <td>
                                        <form action="${ctx}/PerritoAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idPerrito" value="${p.idPerrito}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaPerritosInactivos}">
                                <tr><td colspan="5" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Reservas -->
            <details class="admin-inactivos-wrap">
                <summary>Reservas eliminadas (${fn:length(listaReservasInactivas)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Actividad</th>
                                <th>Fecha</th>
                                <th>Hora</th>
                                <th>Personas</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${listaReservasInactivas}">
                                <tr>
                                    <td>${r.idReserva}</td>
                                    <td>${r.nombreActividad}</td>
                                    <td>${r.fecha}</td>
                                    <td>${r.hora}</td>
                                    <td>${r.num_personas}</td>
                                    <td>
                                        <form action="${ctx}/ReservaAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idReserva" value="${r.idReserva}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaReservasInactivas}">
                                <tr><td colspan="6" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Solicitudes de adopción -->
            <details class="admin-inactivos-wrap">
                <summary>Solicitudes de adopción eliminadas (${fn:length(listaSolicitudesInactivas)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Perrito</th>
                                <th>Solicitante</th>
                                <th>Estado</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${listaSolicitudesInactivas}">
                                <tr>
                                    <td>${s.idSolicitud_adopcion}</td>
                                    <td>${s.nombrePerrito}</td>
                                    <td>${s.nombreUsuario} ${s.apellidoUsuario}</td>
                                    <td>${s.descripcionEstado_solicitud}</td>
                                    <td>
                                        <form action="${ctx}/SolicitudAdopcionAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idSolicitud_adopcion" value="${s.idSolicitud_adopcion}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaSolicitudesInactivas}">
                                <tr><td colspan="5" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

            <!-- Usuarios -->
            <details class="admin-inactivos-wrap">
                <summary>Usuarios eliminados (${fn:length(listaUsuariosInactivos)})</summary>
                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                
                                <th>Nombre</th>
                                <th>Documento</th>
                                <th>Correo</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="u" items="${listaUsuariosInactivos}">
                                <tr>
                                    <td>${u.idUsuarios}</td>
                                    <td>${u.nombre} ${u.apellido}</td>
                                    <td>${u.documento}</td>
                                    <td>${u.correo}</td>
                                    <td>
                                        <form action="${ctx}/UsuarioAdmi" method="POST">
                                            <input type="hidden" name="accion" value="reactivar">
                                            <input type="hidden" name="idUsuarios" value="${u.idUsuarios}">
                                            <button type="submit" class="admin-crud-btn-primary admin-crud-btn-sm">Reactivar</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty listaUsuariosInactivos}">
                                <tr><td colspan="5" class="admin-empty">Nada eliminado aquí.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </details>

        </div>
    </body>
</html>
