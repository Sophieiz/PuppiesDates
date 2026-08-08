document.addEventListener('DOMContentLoaded', function () {

    const campos = {
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

    campos.num_personas.input.addEventListener('blur', validarNumPersonas);
    campos.fecha.input.addEventListener('change', validarFecha);
    campos.hora.input.addEventListener('change', validarHora);
    campos.actividad.input.addEventListener('change', validarActividad);

    function validarFormularioReserva() {
        const resultados = [
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
    }

    // El form hace submit normal a VerificarDisponibilidad.
    // Interceptamos el submit para validar antes de enviar.
    const formReserva = document.getElementById('formReserva');
    if (formReserva) {
        formReserva.addEventListener('submit', function (event) {
            if (!validarFormularioReserva()) {
                event.preventDefault();
            }
        });
    }
});