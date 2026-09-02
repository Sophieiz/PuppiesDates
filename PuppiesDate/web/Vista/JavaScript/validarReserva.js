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
    
        // --- Helpers de fecha/hora ---
    function hoyString() {
        const hoy = new Date();
        const yyyy = hoy.getFullYear();
        const mm = String(hoy.getMonth() + 1).padStart(2, '0');
        const dd = String(hoy.getDate()).padStart(2, '0');
        return `${yyyy}-${mm}-${dd}`;
    }

    function horaActualString() {
        const ahora = new Date();
        const hh = String(ahora.getHours()).padStart(2, '0');
        const mm = String(ahora.getMinutes()).padStart(2, '0');
        return `${hh}:${mm}`;
    }

    // Bloquea fechas anteriores directamente en el calendario del navegador
    if (campos.fecha.input) {
        campos.fecha.input.setAttribute('min', hoyString());
    }

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
        if (!/^\d+$/.test(valor)) {
            mostrarError(campos.num_personas, 'Ingresa un número entero válido (sin decimales ni símbolos).');
            return false;
        }
        const numero = parseInt(valor, 10);
        if (numero < 1) {
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
        if (valor < hoyString()) {
            mostrarError(campos.fecha, 'No puedes reservar en una fecha pasada.');
            return false;
        }
        limpiarError(campos.fecha);
        if (campos.hora.input.value !== '') {
            validarHora();
        }
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
        if (campos.fecha.input.value === hoyString() && valor < horaActualString()) {
            mostrarError(campos.hora, 'Esa hora ya pasó. Elige una hora posterior a la actual.');
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