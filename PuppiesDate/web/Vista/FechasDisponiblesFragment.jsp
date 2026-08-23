<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<h3 class="admin-modal-titulo">Fechas disponibles</h3>
<p class="admin-modal-texto">Estas son las próximas fechas con cupo. Horario de atención: 8:00 AM a 5:00 PM.</p>

<c:choose>
    <c:when test="${empty fechasDisponibles}">
        <p class="admin-empty">Por el momento no hay fechas disponibles.</p>
    </c:when>
    <c:otherwise>
        <div class="admin-crud-table-wrap">
            <table class="admin-crud-table">
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Horario</th>
                        <th>Cupos</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${fechasDisponibles}">
                        <tr>
                            <td><fmt:formatDate value="${d.fecha}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatDate value="${d.horaIni}" pattern="HH:mm"/> - <fmt:formatDate value="${d.horaFin}" pattern="HH:mm"/></td>
                            <td>${d.cupo_disponible}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>