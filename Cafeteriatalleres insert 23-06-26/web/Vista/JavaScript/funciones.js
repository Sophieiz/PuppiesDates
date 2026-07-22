document.addEventListener("DOMContentLoaded", () => {
    const carruseles = document.querySelectorAll(".carrusel");

    carruseles.forEach((carrusel) => {
        const contenedor = carrusel.querySelector(".imagen-contenedor");
        const btnIzq = carrusel.querySelector(".prev");
        const btnDer = carrusel.querySelector(".next");

        if (!contenedor || contenedor.children.length === 0) {
            return;
        }

        let index = 0;
        const total = contenedor.children.length;
        let autoplay = null;

        function moverCarrusel() {
            contenedor.style.transform = `translateX(-${index * 100}%)`;
        }

        function avanzarCarrusel() {
            index = (index + 1) % total;
            moverCarrusel();
        }

        function retrocederCarrusel() {
            index = (index - 1 + total) % total;
            moverCarrusel();
        }

        function reiniciarAutoplay() {
            window.clearInterval(autoplay);
            autoplay = window.setInterval(avanzarCarrusel, 3500);
        }

        if (btnDer) {
            btnDer.addEventListener("click", () => {
                avanzarCarrusel();
                reiniciarAutoplay();
            });
        }

        if (btnIzq) {
            btnIzq.addEventListener("click", () => {
                retrocederCarrusel();
                reiniciarAutoplay();
            });
        }

        moverCarrusel();
        reiniciarAutoplay();
    });

    document.addEventListener('DOMContentLoaded', function () {
        var modal = document.getElementById('modal-cambiar-estado');

        document.querySelectorAll('[data-abrir-cambio]').forEach(function (fila) {
            fila.addEventListener('click', function () {
                document.getElementById('idSolicitudModal').value = fila.getAttribute('data-id');
                document.getElementById('nombrePerritoModal').textContent = fila.getAttribute('data-perrito');
                document.getElementById('idEstado_solicitud').value = fila.getAttribute('data-estado-actual');
                modal.classList.add('activo');
                modal.setAttribute('aria-hidden', 'false');
            });
        });

        document.querySelectorAll('[data-cerrar="modal-cambiar-estado"]').forEach(function (btn) {
            btn.addEventListener('click', function () {
                modal.classList.remove('activo');
                modal.setAttribute('aria-hidden', 'true');
            });
        });
    });
});
