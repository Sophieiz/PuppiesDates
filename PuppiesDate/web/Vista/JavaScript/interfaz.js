document.addEventListener("DOMContentLoaded", () => {
  configurarCierreSesion();
  configurarModalAdopcion();
  configurarCargaFotos();
  configurarMenuUsuario();
});

function configurarMenuUsuario() {
  const toggle = document.getElementById("userDropdownToggle");
  const menu = document.getElementById("userDropdownMenu");

  if (!toggle || !menu) {
    return;
  }

  const cerrarMenu = () => {
    menu.classList.remove("is-open");
    toggle.setAttribute("aria-expanded", "false");
  };

  toggle.addEventListener("click", (event) => {
    event.stopPropagation();
    const abierto = menu.classList.toggle("is-open");
    toggle.setAttribute("aria-expanded", abierto ? "true" : "false");
  });

  document.addEventListener("click", (event) => {
    if (!menu.contains(event.target) && event.target !== toggle) {
      cerrarMenu();
    }
  });

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
      cerrarMenu();
    }
  });
}

function configurarCierreSesion() {
  const modal = document.getElementById("logoutModal");
  let enlaceDestino = null;

  document.querySelectorAll(".js-logout-link, a[href$='CerrarSesion']").forEach((link) => {
    link.addEventListener("click", (event) => {
      if (!modal) {
        const confirmar = confirm("¿Estás seguro de que deseas cerrar sesión? Te esperamos pronto en Puppies Dates.");
        if (!confirmar) {
          event.preventDefault();
        }
        return;
      }

      event.preventDefault();
      enlaceDestino = link.href;
      modal.classList.add("is-open");
      modal.setAttribute("aria-hidden", "false");
      document.body.classList.add("modal-open");
    });
  });

  if (!modal) {
    return;
  }

  const cerrarLogoutModal = () => {
    modal.classList.remove("is-open");
    modal.setAttribute("aria-hidden", "true");
    document.body.classList.remove("modal-open");
  };

  modal.querySelectorAll("[data-logout-close]").forEach((el) => {
    el.addEventListener("click", cerrarLogoutModal);
  });

  const confirmarBtn = modal.querySelector("[data-logout-confirm]");
  if (confirmarBtn) {
    confirmarBtn.addEventListener("click", () => {
      if (enlaceDestino) {
        window.location.href = enlaceDestino;
      }
    });
  }

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape" && modal.classList.contains("is-open")) {
      cerrarLogoutModal();
    }
  });
}

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
    content.innerHTML = '<p class="adoption-modal-loading">Cargando formulario...</p>';
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
        const html = await response.text();
        const documentFragment = new DOMParser().parseFromString(html, "text/html");
        const adoptionWrap = documentFragment.querySelector(".adopcion-wrap");

        content.innerHTML = adoptionWrap
          ? adoptionWrap.outerHTML
          : '<p class="sin-perritos">No pudimos cargar el formulario. Intenta de nuevo.</p>';
      } catch (error) {
        content.innerHTML = '<p class="sin-perritos">No pudimos cargar el formulario. Intenta de nuevo.</p>';
      }
    });
  });
}

function configurarCargaFotos() {
  document.querySelectorAll("[data-photo-picker]").forEach((input) => {
    input.addEventListener("change", () => {
      const file = input.files && input.files[0];
      const target = document.getElementById(input.dataset.photoTarget);
      const preview = document.getElementById(input.dataset.photoPreview);

      if (!file || !target) {
        return;
      }

      const reader = new FileReader();
      reader.addEventListener("load", () => {
        target.value = reader.result;

        if (preview) {
          preview.src = reader.result;
          preview.classList.add("is-visible");
        }
      });
      reader.readAsDataURL(file);
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
  
  document.addEventListener('DOMContentLoaded', function () {
    // Redirección al Iniciar Sesión desde el Modal de Éxito
    const btnIrIniciar = document.getElementById('btnIrIniciarSesion');
    
    if (btnIrIniciar) {
        btnIrIniciar.addEventListener('click', function () {
            const redirectUrl = this.getAttribute('data-url');
            if (redirectUrl) {
                window.location.href = redirectUrl;
            }
        });
    }
});
}
