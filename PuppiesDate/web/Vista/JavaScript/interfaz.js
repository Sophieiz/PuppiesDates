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
                      <div class="adopcion-mascota" aria-hidden="true">
                        <svg viewBox="0 0 200 200" xmlns="http://www.w3.org/2000/svg">
                          <path class="mascota-cola" d="M150 130 Q185 110 175 75" stroke="#F8B553" stroke-width="18" stroke-linecap="round" fill="none"/>
                          <ellipse cx="100" cy="150" rx="58" ry="42" fill="#F8B553"/>
                          <circle cx="100" cy="95" r="42" fill="#F8B553"/>
                          <ellipse cx="65" cy="80" rx="16" ry="26" fill="#6D3A52" transform="rotate(-20 65 80)"/>
                          <ellipse cx="135" cy="80" rx="16" ry="26" fill="#6D3A52" transform="rotate(20 135 80)"/>
                          <ellipse cx="100" cy="110" rx="22" ry="16" fill="#FFF8EE"/>
                          <ellipse cx="100" cy="106" rx="7" ry="5" fill="#6D3A52"/>
                          <circle cx="82" cy="88" r="5" fill="#6D3A52"/>
                          <circle cx="118" cy="88" r="5" fill="#6D3A52"/>
                          <path d="M96 122 Q100 138 104 122 Z" fill="#DA74A3"/>
                          <ellipse cx="75" cy="185" rx="14" ry="9" fill="#FFF8EE"/>
                          <ellipse cx="125" cy="185" rx="14" ry="9" fill="#FFF8EE"/>
                        </svg>
                      </div>
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

// Inicializa el modal de adopción (sin esta llamada, los links
// "Quiero adoptarlo" nunca reciben preventDefault() y el navegador navega
// directo al servlet en vez de abrir el modal).
document.addEventListener('DOMContentLoaded', function () {
    configurarModalAdopcion();
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
})();

// Precio dinámico al seleccionar actividad
function configurarPrecioActividad() {
    const select = document.getElementById('actividad');
    const textoPrecio = document.getElementById('precioActividadTexto');

    if (!select || !textoPrecio) {
        return;
    }

    select.addEventListener('change', function () {
        const opcion = select.options[select.selectedIndex];
        const precio = opcion ? opcion.getAttribute('data-precio') : '';

        if (precio && precio.trim() !== '') {
            textoPrecio.textContent = 'Precio: ' + precio;
            textoPrecio.style.display = 'block';
        } else {
            textoPrecio.style.display = 'none';
        }
    });
}

// Modal de fechas disponibles
function configurarModalFechasDisponibles() {
    const boton = document.getElementById('btnVerFechas');
    const modal = document.getElementById('fechasModal');
    const cerrar = document.getElementById('cerrarFechasModal');
    const contenido = document.getElementById('fechasModalContent');
    const form = document.getElementById('formReserva');

    if (!boton || !modal || !contenido || !form) {
        return;
    }

    const ctx = form.dataset.ctx || '';

    const abrirModal = async () => {
        modal.classList.add('is-open');
        contenido.innerHTML = '<div class="adoption-modal-loading"><span></span><span></span><span></span></div>';

        try {
            const response = await fetch(ctx + '/ConsultarFechasDisponibles');
            const html = await response.text();
            contenido.innerHTML = html;
        } catch (error) {
            console.error('Error al cargar fechas disponibles:', error);
            contenido.innerHTML = '<p class="sin-perritos">No pudimos cargar las fechas. Intenta de nuevo.</p>';
        }
    };

    const cerrarModal = () => {
        modal.classList.remove('is-open');
    };

    boton.addEventListener('click', abrirModal);
    cerrar.addEventListener('click', cerrarModal);
}

document.addEventListener('DOMContentLoaded', function () {
    configurarPrecioActividad();
    configurarModalFechasDisponibles();
});