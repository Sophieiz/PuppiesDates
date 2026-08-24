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
            <a href="https://www.instagram.com/puppiesdates/" aria-label="Instagram" target="_blank" rel="noopener">Instagram</a>
            <a href="https://wa.me/573159686333" aria-label="WhatsApp" target="_blank" rel="noopener">WhatsApp</a>
            <a href="https://www.tiktok.com/@puppiesdates?lang=es" aria-label="TikTok" target="_blank" rel="noopener">TikTok</a>
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
<!-- Modal de éxito al enviar una solicitud de adopción (compartido) -->
<div class="modal-exito-shell" id="modalExitoAdopcion" aria-hidden="true">
    <div class="modal-exito-backdrop" data-cerrar-exito></div>
    <section class="modal-exito-box" role="dialog" aria-modal="true" aria-labelledby="modalExitoAdopcionTitle">
        <h3 class="modal-exito-titulo" id="modalExitoAdopcionTitle">¡Solicitud enviada!</h3>
        <p class="modal-exito-texto">La fundación revisará tu información y te contactará pronto.</p>
        <button type="button" class="modal-exito-btn" data-cerrar-exito>Entendido</button>
    </section>
</div>
