
document.addEventListener('DOMContentLoaded', function () {

    const REGEX = {
        nombre: /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü\s]{2,}$/,
        documento: /^[A-Za-z0-9]{6,15}$/,
        telefono: /^[0-9]{10}$/,
        correo: /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    };

    const MENSAJES = {
        nombre: 'Solo letras, mínimo 2 caracteres',
        documento: 'Documento inválido (6 a 15 caracteres alfanuméricos)',
        telefono: 'Debe tener 10 dígitos numéricos',
        correo: 'Correo electrónico no válido',
        fecha_nac: 'Debe ser mayor de 14 años y la fecha no puede ser futura',
        fecha_cad: 'La fecha de expedición no puede ser futura ni anterior al nacimiento'
    };


    function calcularEdad(fechaNacStr) {
        const hoy = new Date();
        const nacimiento = new Date(fechaNacStr);
        let edad = hoy.getFullYear() - nacimiento.getFullYear();
        const mesDiff = hoy.getMonth() - nacimiento.getMonth();
        if (mesDiff < 0 || (mesDiff === 0 && hoy.getDate() < nacimiento.getDate())) {
            edad--;
        }
        return edad;
    }


    function validarCampo(input) {
        const tipo = input.dataset.tipo;
        const valor = input.value.trim();
        let esValido = true;

        if (!tipo) return true;

        if (valor === '') {
            esValido = false;
        } else {
            switch (tipo) {
                case 'nombre':
                    esValido = REGEX.nombre.test(valor);
                    break;

                case 'documento':
                    esValido = REGEX.documento.test(valor);
                    break;

                case 'telefono':
                    esValido = REGEX.telefono.test(valor);
                    break;

                case 'correo':
                    esValido = REGEX.correo.test(valor);
                    break;

                case 'fecha_nac': {
                    const fecha = new Date(valor);
                    const hoy = new Date();
                    if (fecha > hoy) {
                        esValido = false;
                    } else {
                        esValido = calcularEdad(valor) >= 14;
                    }
                    break;
                }

                case 'fecha_cad': {
                    const fila = input.closest('tr');
                    const inputNac = fila ? fila.querySelector('[data-tipo="fecha_nac"]') : null;
                    const fechaCad = new Date(valor);
                    const hoy = new Date();

                    if (fechaCad > hoy) {
                        esValido = false;
                    } else if (inputNac && inputNac.value) {
                        const fechaNac = new Date(inputNac.value);
                        esValido = fechaCad >= fechaNac;
                    }
                    break;
                }

                default:
                    esValido = true;
            }
        }

        mostrarEstado(input, esValido, tipo);
        return esValido;
    }

    function mostrarEstado(input, esValido, tipo) {
        const fila = input.closest('tr');
        const spanError = fila ? fila.querySelector('[data-error-de="' + input.name + '"]') : null;

        input.classList.remove('input-error', 'input-ok');

        if (esValido) {
            input.classList.add('input-ok');
            if (spanError) {
                spanError.textContent = '';
                spanError.classList.remove('visible');
            }
        } else {
            input.classList.add('input-error');
            if (spanError) {
                spanError.textContent = MENSAJES[tipo] || 'Campo inválido';
                spanError.classList.add('visible');
            }
        }
    }


    const camposValidables = document.querySelectorAll('[data-tipo]');

    camposValidables.forEach(function (input) {
        input.addEventListener('input', function () {
            validarCampo(input);
            
            if (input.dataset.tipo === 'fecha_nac') {
                const fila = input.closest('tr');
                const inputCad = fila ? fila.querySelector('[data-tipo="fecha_cad"]') : null;
                if (inputCad && inputCad.value) {
                    validarCampo(inputCad);
                }
            }
        });

        input.addEventListener('blur', function () {
            validarCampo(input);
        });
    });


    const formularios = document.querySelectorAll('.form-usuario-admin');

    formularios.forEach(function (form) {
        form.addEventListener('submit', function (e) {
            
            const inputsDelForm = document.querySelectorAll('[form="' + form.id + '"][data-tipo]');
            let formularioValido = true;

            inputsDelForm.forEach(function (input) {
                const esValido = validarCampo(input);
                if (!esValido) formularioValido = false;
            });

            if (!formularioValido) {
                e.preventDefault();
            }
        });
    });

});


