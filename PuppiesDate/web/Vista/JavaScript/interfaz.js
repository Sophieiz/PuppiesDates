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

                const html = await response.text();
                const documentFragment = new DOMParser().parseFromString(html, "text/html");
                const adoptionWrap = documentFragment.querySelector(".adopcion-wrap");

                // CAMBIO AQUÍ: Si encuentra .adopcion-wrap lo inserta, si no, inyecta directamente el HTML que vino del Servlet (el aviso)
                content.innerHTML = adoptionWrap ? adoptionWrap.outerHTML : html;

                if (adoptionWrap && typeof inicializarUbicacionAdopcion === "function") {
                    inicializarUbicacionAdopcion();
                }
            } catch (error) {
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