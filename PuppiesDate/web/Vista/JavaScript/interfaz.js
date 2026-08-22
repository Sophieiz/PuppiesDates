function configurarModalAdopcion() {
    const modal = document.getElementById("adoptionModal");
    const content = document.getElementById("adoptionModalContent");

    if (!modal || !content) {
        return;
    }

    const cerrarModal = () => {
        modal.classList.remove("is-open");
        modal.setAttribute("aria-hidden", "true");
        document.body.classList.remove("modal-open");
        content.innerHTML = '<div class="adoption-modal-loading"><span></span><span></span><span></span></div>';
    };

    document.querySelectorAll("[data-adoption-close]").forEach((button) => {
        button.addEventListener("click", cerrarModal);
    });

    document.addEventListener("keydown", (event) => {
        if (event.key === "Escape" && modal.classList.contains("is-open")) {
            cerrarModal();
        }
    });

    document.querySelectorAll(".js-adoption-modal-link").forEach((link) => {
        link.addEventListener("click", async (event) => {
            event.preventDefault();
            modal.classList.add("is-open");
            modal.setAttribute("aria-hidden", "false");
            document.body.classList.add("modal-open");

            try {
                const response = await fetch(link.href, {
                    headers: {
                        "X-Requested-With": "XMLHttpRequest"
                    }
                });

                if (response.status === 401) {
                    content.innerHTML = `
                    <div class="adopcion-auth-required">
                      <h3>¡Espera un momento!</h3>
                      <p>Para adoptar debes iniciar sesión o registrarte primero.</p>
                      <div class="adopcion-auth-botones">
                        <a href="${window.ctxApp || ""}/Iniciar" class="btn-menu btn-verde-activo">Iniciar sesión</a>
                        <a href="${window.ctxApp || ""}/Registrarse" class="btn-menu btn-rosa-sesion">Registrarme</a>
                      </div>
                    </div>`;
                    return;
                }

                // Recibimos el HTML directamente sin usar DOMParser
                const html = await response.text();

                // Inyectamos el contenido devuelto por el Servlet (sea el aviso o el formulario)
                content.innerHTML = html;

                // Si se cargó el formulario completo, inicializamos eventos adicionales de ubicación
                if (content.querySelector(".adopcion-wrap") && typeof inicializarUbicacionAdopcion === "function") {
                    inicializarUbicacionAdopcion();
                }
            } catch (error) {
                console.error("Error al cargar el modal:", error);
                content.innerHTML = '<p class="sin-perritos">No pudimos cargar el formulario. Intenta de nuevo.</p>';
            }
        });
    });
}


function validarSolicitudAdopcion() {
    const campos = [
        ["direccion", "Ingresa tu dirección."],
        ["localidad", "Ingresa tu localidad."],
        ["barrio", "Ingresa tu barrio."],
        ["profesion", "Ingresa tu profesión."],
        ["vive_en", "Selecciona dónde vives."],
        ["tipo_vivienda", "Selecciona el tipo de vivienda."],
        ["nucleo_familiar", "Cuéntanos sobre tu núcleo familiar."]
    ];

    let valido = true;

    campos.forEach(([id, mensaje]) => {
        const campo = document.getElementById(id);
        const error = document.getElementById(`error_${id}`);

        if (!campo) {
            return;
        }

        const vacio = !campo.value || !campo.value.trim();

        if (error) {
            error.textContent = vacio ? mensaje : "";
        }

        campo.classList.toggle("campo-error", vacio);

        if (vacio) {
            valido = false;
    }
    });

    return valido;
}


// Menú hamburguesa
document.addEventListener('DOMContentLoaded', function () {
    const menuToggle = document.getElementById('menuToggle');
    const navMenu = document.getElementById('navMenu');

    if (menuToggle && navMenu) {
        menuToggle.addEventListener('click', function () {
            const isOpen = navMenu.classList.toggle('is-open');
            menuToggle.classList.toggle('is-open', isOpen);
            menuToggle.setAttribute('aria-expanded', isOpen);
        });

        
        navMenu.querySelectorAll('a').forEach(function (link) {
            link.addEventListener('click', function () {
                navMenu.classList.remove('is-open');
                menuToggle.classList.remove('is-open');
                menuToggle.setAttribute('aria-expanded', 'false');
            });
        });
    }
});

(function () {
    var filtroRaza = document.getElementById('filtroRaza');
    var tarjetas = document.querySelectorAll('#gridPerritos .tarjeta-perrito');
    var mensajeVacio = document.getElementById('sinResultadosFiltro');

    if (!filtroRaza) {
        return;
    }

    filtroRaza.addEventListener('change', function () {
        var razaSeleccionada = filtroRaza.value;
        var visibles = 0;

        tarjetas.forEach(function (tarjeta) {
            var coincide = razaSeleccionada === 'todas' || tarjeta.dataset.raza === razaSeleccionada;
            tarjeta.style.display = coincide ? '' : 'none';
            if (coincide) {
                visibles++;
            }
        });

        if (mensajeVacio) {
            mensajeVacio.style.display = visibles === 0 ? 'block' : 'none';
        }
    });
})
();