<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${empty ctx}">
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
</c:if>
<footer class="footer-aesthetic footer-minimal">
    <div class="footer-minimal-inner">
        <div class="footer-minimal-logo">
            <img src="${ctx}/Vista/Imagenes/image.png" alt="Puppies Dates">
        </div>
        <div class="footer-minimal-social">
            <a href="#" aria-label="Instagram">Instagram</a>
            <a href="#" aria-label="WhatsApp">WhatsApp</a>
            <a href="#" aria-label="TikTok">TikTok</a>
        </div>
        <hr class="footer-minimal-divider">
        <p class="footer-minimal-copy">&copy; 2026 Puppies Dates. Todos los derechos reservados.</p>
    </div>
</footer>
<!-- Modal de confirmación de cierre de sesión (compartido) -->
<div class="logout-modal-shell" id="logoutModal" aria-hidden="true">
    <div class="logout-modal-backdrop" data-logout-close></div>
    <section class="logout-modal-box" role="dialog" aria-modal="true" aria-labelledby="logoutModalTitle">
        <button type="button" class="logout-modal-close" data-logout-close aria-label="Cerrar">&times;</button>
        <div class="logout-modal-icon"></div>
        <h3 id="logoutModalTitle">¿Ya te vas?</h3>
        <p>¿Estás seguro de que deseas cerrar sesión? Te esperamos pronto en Puppies Dates.</p>
        <div class="logout-modal-actions">
            <button type="button" class="logout-modal-cancel" data-logout-close>Cancelar</button>
            <button type="button" class="logout-modal-confirm" data-logout-confirm>Cerrar sesión</button>
        </div>
    </section>
</div>
