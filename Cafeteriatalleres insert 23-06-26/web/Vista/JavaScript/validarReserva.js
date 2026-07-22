document.addEventListener('DOMContentLoaded', function () {

    const campos = {
        documento: {
            input: document.getElementById('documento'),
            error: document.getElementById('error_documento')
        },
        num_personas: {
            input: document.getElementById('num_personas'),
            error: document.getElementById('error_num_personas')
        },
        fecha: {
            input: document.getElementById('fecha'),
            error: document.getElementById('error_fecha')
        },
        hora: {
            input: document.getElementById('hora'),
            error: document.getElementById('error_hora')
        },
        actividad: {
            input: document.getElementById('actividad'),
            error: document.getElementById('error_actividad')
        }
    };

    function mostrarError(campo, mensaje) {
        campo.error.textContent = mensaje;
        campo.error.classList.add('visible');
        campo.input.classList.add('input-error');
        campo.input.classList.remove('input-ok');
    }

    function limpiarError(campo) {
        campo.error.textContent = '';
        campo.error.classList.remove('visible');
        campo.input.classList.remove('input-error');
        campo.input.classList.add('input-ok');
    }

    function validarDocumento() {
        const valor = campos.documento.input.value.trim();
        if (valor === '') {
            mostrarError(campos.documento, 'El número de documento es obligatorio.');
            return false;
        }
        if (!/^\d+$/.test(valor)) {
            mostrarError(campos.documento, 'Solo se permiten números.');
            return false;
        }
        if (valor.length < 6 || valor.length > 12) {
            mostrarError(campos.documento, 'El documento debe tener entre 6 y 12 dígitos.');
            return false;
        }
        limpiarError(campos.documento);
        return true;
    }

    function validarNumPersonas() {
        const valor = campos.num_personas.input.value.trim();
        if (valor === '') {
            mostrarError(campos.num_personas, 'El número de personas es obligatorio.');
            return false;
        }
        const numero = parseInt(valor);
        if (isNaN(numero) || numero < 1) {
            mostrarError(campos.num_personas, 'Debe ser al menos 1 persona.');
            return false;
        }
        if (numero > 20) {
            mostrarError(campos.num_personas, 'Máximo 20 personas por reserva.');
            return false;
        }
        limpiarError(campos.num_personas);
        return true;
    }

    function validarFecha() {
        const valor = campos.fecha.input.value;
        if (valor === '') {
            mostrarError(campos.fecha, 'La fecha es obligatoria.');
            return false;
        }
        const hoy = new Date();
        hoy.setHours(0, 0, 0, 0);
        const fechaSeleccionada = new Date(valor + 'T00:00:00');
        if (fechaSeleccionada < hoy) {
            mostrarError(campos.fecha, 'No puedes reservar en una fecha pasada.');
            return false;
        }
        limpiarError(campos.fecha);
        return true;
    }

    function validarHora() {
        const valor = campos.hora.input.value;
        if (valor === '') {
            mostrarError(campos.hora, 'La hora es obligatoria.');
            return false;
        }
        const [hh, mm] = valor.split(':').map(Number);
        const totalMinutos = hh * 60 + mm;
        const inicio = 8 * 60;
        const fin = 17 * 60;
        if (totalMinutos < inicio || totalMinutos > fin) {
            mostrarError(campos.hora, 'El horario de atención es de 8:00 AM a 5:00 PM.');
            return false;
        }
        limpiarError(campos.hora);
        return true;
    }

    function validarActividad() {
        const valor = campos.actividad.input.value;
        if (!valor || valor === '' || valor === '0') {
            mostrarError(campos.actividad, 'Selecciona una actividad.');
            return false;
        }
        limpiarError(campos.actividad);
        return true;
    }

    campos.documento.input.addEventListener('blur', validarDocumento);
    campos.num_personas.input.addEventListener('blur', validarNumPersonas);
    campos.fecha.input.addEventListener('change', validarFecha);
    campos.hora.input.addEventListener('change', validarHora);
    campos.actividad.input.addEventListener('change', validarActividad);

    campos.documento.input.addEventListener('input', function () {
        if (campos.documento.input.classList.contains('input-error')) validarDocumento();
    });

    window.validarFormularioReserva = function () {
        const resultados = [
            validarDocumento(),
            validarNumPersonas(),
            validarFecha(),
            validarHora(),
            validarActividad()
        ];
        if (resultados.includes(false)) {
            const primerError = document.querySelector('.input-error');
            if (primerError) {
                primerError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                primerError.focus();
            }
            return false;
        }
        return true;
    };

    // Modal de confirmación antes de enviar la reserva
    const formReserva = document.getElementById('formReserva');
    const modalConfirmar = document.getElementById('modal-confirmar-reserva');
    const btnAbrir = document.getElementById('btnAbrirConfirmarReserva');
    const btnSi = document.getElementById('btnConfirmarReserva');
    const btnNo = document.getElementById('btnCancelarReserva');

    if (btnAbrir && modalConfirmar) {
        btnAbrir.addEventListener('click', function () {
            if (!window.validarFormularioReserva()) {
                return;
            }
            modalConfirmar.classList.add('activo');
            modalConfirmar.setAttribute('aria-hidden', 'false');
        });

        btnNo.addEventListener('click', function () {
            modalConfirmar.classList.remove('activo');
            modalConfirmar.setAttribute('aria-hidden', 'true');
        });

        btnSi.addEventListener('click', function () {
            formReserva.submit();
        });
    }
});
