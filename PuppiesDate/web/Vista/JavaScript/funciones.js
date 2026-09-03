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

    var modalEntrevista = document.getElementById('modal-programar-entrevista');

    document.querySelectorAll('[data-abrir-entrevista]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('idSolicitudEntrevistaModal').value = btn.getAttribute('data-id');
            document.getElementById('nombrePerritoEntrevistaModal').textContent = btn.getAttribute('data-perrito');
            modalEntrevista.classList.add('activo');
            modalEntrevista.setAttribute('aria-hidden', 'false');
        });
    });

    document.querySelectorAll('[data-cerrar="modal-programar-entrevista"]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            modalEntrevista.classList.remove('activo');
            modalEntrevista.setAttribute('aria-hidden', 'true');
        });
    });
});

function validarEntrevista() {
    const inputFecha = document.getElementById("fecha");
    const inputHora = document.getElementById("hora");
    const errorFecha = document.getElementById("error_fecha");
    const errorHora = document.getElementById("error_hora");

    let valido = true;

    // --- Validar fecha ---
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    const fechaSeleccionada = new Date(inputFecha.value + "T00:00:00");

    if (!inputFecha.value || fechaSeleccionada < hoy) {
        errorFecha.textContent = "Selecciona una fecha igual o posterior a hoy.";
        inputFecha.classList.add("field-invalid");
        valido = false;
    } else {
        errorFecha.textContent = "";
        inputFecha.classList.remove("field-invalid");
    }

    // --- Validar hora (8:00am a 5:00pm) ---
    if (!inputHora.value || inputHora.value < "08:00" || inputHora.value > "17:00") {
        errorHora.textContent = "Selecciona una hora entre 8:00 a.m. y 5:00 p.m.";
        inputHora.classList.add("field-invalid");
        valido = false;
    } else {
        errorHora.textContent = "";
        inputHora.classList.remove("field-invalid");
    }

    return valido;
}

// Evita que se pueda escoger un día anterior a hoy en el selector de fecha
document.addEventListener("DOMContentLoaded", function () {
    const inputFecha = document.getElementById("fecha");
    if (inputFecha) {
        const hoyStr = new Date().toISOString().split("T")[0];
        inputFecha.min = hoyStr;
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const modalEstadoReserva = document.getElementById('modal-cambiar-estado-reserva');
    if (!modalEstadoReserva) {
        return;
    }

    document.querySelectorAll('[data-abrir-cambio-estado-reserva]').forEach(function (boton) {
        boton.addEventListener('click', function () {
            document.getElementById('idReservaEstadoModal').value = boton.getAttribute('data-id');
            document.getElementById('Estado_reserva_idEstado_reserva_modal').value = boton.getAttribute('data-estado-actual');
            modalEstadoReserva.classList.add('activo');
            modalEstadoReserva.setAttribute('aria-hidden', 'false');
        });
    });

    modalEstadoReserva.querySelectorAll('[data-cerrar-estado-reserva]').forEach(function (btn) {
        btn.addEventListener('click', function () {
            modalEstadoReserva.classList.remove('activo');
            modalEstadoReserva.setAttribute('aria-hidden', 'true');
        });
    });
});