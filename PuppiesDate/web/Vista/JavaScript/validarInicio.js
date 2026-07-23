/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', function () {

    const campos = {
        correo: {
            input: document.getElementById('correo'),
            error: document.getElementById('error_correo')
        },
        pass: {
            input: document.getElementById('pass'),
            error: document.getElementById('error_pass')
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

    function validarCorreo() {
        const valor = campos.correo.input.value.trim();
        if (valor === '') {
            mostrarError(campos.correo, 'El correo electrónico es obligatorio.');
            return false;
        }
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor)) {
            mostrarError(campos.correo, 'Ingresa un correo electrónico válido.');
            return false;
        }
        limpiarError(campos.correo);
        return true;
    }

    function validarPass() {
        const valor = campos.pass.input.value;
        if (valor === '') {
            mostrarError(campos.pass, 'La contraseña es obligatoria.');
            return false;
        }
        if (valor.length < 6) {
            mostrarError(campos.pass, 'La contraseña debe tener al menos 6 caracteres.');
            return false;
        }
        limpiarError(campos.pass);
        return true;
    }

    campos.correo.input.addEventListener('blur', validarCorreo);
    campos.pass.input.addEventListener('blur', validarPass);

    campos.correo.input.addEventListener('input', function () {
        if (campos.correo.input.classList.contains('input-error')) validarCorreo();
    });
    campos.pass.input.addEventListener('input', function () {
        if (campos.pass.input.classList.contains('input-error')) validarPass();
    });

    window.validarLogin = function () {
        const resultados = [
            validarCorreo(),
            validarPass()
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
});



