document.addEventListener('DOMContentLoaded', function () {

    const campos = {
        nombre: {
            input: document.getElementById('nombre'),
            error: document.getElementById('error_nombre')
        },
        apellido: {
            input: document.getElementById('apellido'),
            error: document.getElementById('error_apellido')
        },
        tipodoc: {
            input: document.getElementById('tipodoc'),
            error: document.getElementById('error_tipodoc')
        },
        documento: {
            input: document.getElementById('documento'),
            error: document.getElementById('error_documento')
        },
        telefono: {
            input: document.getElementById('telefono'),
            error: document.getElementById('error_telefono')
        },
        correo: {
            input: document.getElementById('correo'),
            error: document.getElementById('error_correo')
        },
        clave: {
            input: document.getElementById('clave'),
            error: document.getElementById('error_clave')
        },
        fecha_nac: {
            input: document.getElementById('fecha_nac'),
            error: document.getElementById('error_fecha_nac')
        },
        checkbox: {
            input: document.getElementById('checkbox'),
            error: document.getElementById('error_checkbox')
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

    function validarNombre() {
        const valor = campos.nombre.input.value.trim();
        if (valor === '') {
            mostrarError(campos.nombre, 'El nombre es obligatorio.');
            return false;
        }
        if (valor.length < 2) {
            mostrarError(campos.nombre, 'El nombre debe tener al menos 2 caracteres.');
            return false;
        }
        if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(valor)) {
            mostrarError(campos.nombre, 'El nombre solo puede contener letras.');
            return false;
        }
        limpiarError(campos.nombre);
        return true;
    }

    function validarApellido() {
        const valor = campos.apellido.input.value.trim();
        if (valor === '') {
            mostrarError(campos.apellido, 'El apellido es obligatorio.');
            return false;
        }
        if (valor.length < 2) {
            mostrarError(campos.apellido, 'El apellido debe tener al menos 2 caracteres.');
            return false;
        }
        if (!/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(valor)) {
            mostrarError(campos.apellido, 'El apellido solo puede contener letras.');
            return false;
        }
        limpiarError(campos.apellido);
        return true;
    }

    function validarTipoDoc() {
        const valor = campos.tipodoc.input.value;
        if (!valor || valor === '' || valor === '0') {
            mostrarError(campos.tipodoc, 'Selecciona un tipo de documento.');
            return false;
        }
        limpiarError(campos.tipodoc);
        return true;
    }

    function validarDocumento() {
        const valor = campos.documento.input.value.trim();
        const selectTipo = campos.tipodoc.input;
        const opcionSeleccionada = selectTipo.options[selectTipo.selectedIndex];
        const soloNumeros = opcionSeleccionada ? opcionSeleccionada.getAttribute('data-solo-numeros') : 'true';

        if (valor === '') {
            mostrarError(campos.documento, 'El número de documento es obligatorio.');
            return false;
        }

        if (soloNumeros === 'true') {
            if (!/^\d+$/.test(valor)) {
                mostrarError(campos.documento, 'Para este tipo de documento solo se permiten números.');
                return false;
            }
            if (valor.length < 6 || valor.length > 11) {
                mostrarError(campos.documento, 'Debe tener entre 6 y 11 dígitos.');
                return false;
            }
        } else {
            if (valor.length < 6 || valor.length > 15) {
                mostrarError(campos.documento, 'El documento debe tener entre 6 y 15 caracteres.');
                return false;
            }
        }

        limpiarError(campos.documento);
        return true;
    }

    function validarTelefono() {
        const valor = campos.telefono.input.value.trim();
        if (valor === '') {
            mostrarError(campos.telefono, 'El teléfono es obligatorio.');
            return false;
        }
        if (!/^\d+$/.test(valor)) {
            mostrarError(campos.telefono, 'Solo se permiten números.');
            return false;
        }
        if (valor.length < 7 || valor.length > 10) {
            mostrarError(campos.telefono, 'El teléfono debe tener entre 7 y 10 dígitos.');
            return false;
        }
        limpiarError(campos.telefono);
        return true;
    }

    function validarCorreo() {
        const valor = campos.correo.input.value.trim();
        if (valor === '') {
            mostrarError(campos.correo, 'El correo es obligatorio.');
            return false;
        }
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor)) {
            mostrarError(campos.correo, 'Ingresa un correo válido. Ej: nombre@correo.com');
            return false;
        }
        limpiarError(campos.correo);
        return true;
    }

    function validarClave() {
        const valor = campos.clave.input.value;
        if (valor === '') {
            mostrarError(campos.clave, 'La contraseña es obligatoria.');
            return false;
        }
        if (valor.length < 6) {
            mostrarError(campos.clave, 'La contraseña debe tener al menos 6 caracteres.');
            return false;
        }
        limpiarError(campos.clave);
        return true;
    }

    function validarFechaNac() {
        const valor = campos.fecha_nac.input.value;
        if (valor === '') {
            mostrarError(campos.fecha_nac, 'La fecha de nacimiento es obligatoria.');
            return false;
        }
        const hoy = new Date();
        const fecha = new Date(valor + 'T00:00:00');
        let edad = hoy.getFullYear() - fecha.getFullYear();
        const m = hoy.getMonth() - fecha.getMonth();
        if (m < 0 || (m === 0 && hoy.getDate() < fecha.getDate()))
            edad--;
        if (edad < 18) {
            mostrarError(campos.fecha_nac, 'Debes ser mayor de 18 años para registrarte.');
            return false;
        }
        if (edad > 100) {
            mostrarError(campos.fecha_nac, 'Ingresa una fecha de nacimiento válida.');
            return false;
        }
        limpiarError(campos.fecha_nac);
        return true;
    }

    function validarCheckbox() {
        if (!campos.checkbox.input.checked) {
            campos.checkbox.error.textContent = 'Debes aceptar el tratamiento de datos.';
            campos.checkbox.error.classList.add('visible');
            return false;
        }
        campos.checkbox.error.textContent = '';
        campos.checkbox.error.classList.remove('visible');
        return true;
    }

    campos.nombre.input.addEventListener('blur', validarNombre);
    campos.apellido.input.addEventListener('blur', validarApellido);
    campos.tipodoc.input.addEventListener('change', validarTipoDoc);
    campos.documento.input.addEventListener('blur', validarDocumento);
    campos.telefono.input.addEventListener('blur', validarTelefono);
    campos.correo.input.addEventListener('blur', validarCorreo);
    campos.clave.input.addEventListener('blur', validarClave);
    campos.fecha_nac.input.addEventListener('change', validarFechaNac);
    campos.checkbox.input.addEventListener('change', validarCheckbox);

    ['nombre', 'apellido', 'documento', 'telefono', 'correo', 'clave'].forEach(key => {
        campos[key].input.addEventListener('input', function () {
            if (campos[key].input.classList.contains('input-error')) {
                window['validar' + key.charAt(0).toUpperCase() + key.slice(1)]();
            }
        });
    });

    window.validarRegistro = function () {
        const resultados = [
            validarNombre(),
            validarApellido(),
            validarTipoDoc(),
            validarDocumento(),
            validarTelefono(),
            validarCorreo(),
            validarClave(),
            validarFechaNac(),
            validarCheckbox()
        ];
        if (resultados.includes(false)) {
            const primerError = document.querySelector('.input-error');
            if (primerError) {
                primerError.scrollIntoView({behavior: 'smooth', block: 'center'});
                primerError.focus();
            }
            return false;
        }
        return true;
    };
});
