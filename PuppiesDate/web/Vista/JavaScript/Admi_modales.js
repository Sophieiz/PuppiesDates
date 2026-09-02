document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.mensaje-bienvenida').forEach(function (message) {
        const text = normalizeValue(message.textContent);
        if (text.includes('error') || text.includes('no se puede') || text.includes('fall')) {
            message.classList.add('mensaje-error');
        }
    });

    const editModal = document.getElementById('modal-editar') || document.getElementById('modal-modificar');
    const confirmModal = document.getElementById('modal-confirmar-eliminar') || document.getElementById('modal-eliminar');
    const editForm = editModal ? editModal.querySelector('form') : null;
    const deleteForm = confirmModal ? confirmModal.querySelector('form') : null;
    const title = editModal ? editModal.querySelector('[data-modal-title]') : null;
    let currentRow = null;

    function openModal(modal) {
        if (!modal)
            return;
        modal.classList.add('activo', 'is-open');
        modal.setAttribute('aria-hidden', 'false');
        document.body.classList.add('modal-abierto');
    }

    function closeModal(modal) {
        if (!modal)
            return;
        modal.classList.remove('activo', 'is-open');
        modal.setAttribute('aria-hidden', 'true');
        if (!document.querySelector('.modal-overlay.activo, .admin-crud-modal.is-open')) {
            document.body.classList.remove('modal-abierto');
        }
    }

    function normalizeValue(value) {
        return String(value || '').trim().replace(/\s+/g, ' ').toLowerCase();
    }

    function alertBox(form) {
        return form ? form.querySelector('[data-form-alert]') : null;
    }

    function showAlert(form, message) {
        const box = alertBox(form);
        if (!box)
            return;
        box.textContent = message;
        box.classList.add('is-open', 'activa');
    }

    function clearAlert(form) {
        const box = alertBox(form);
        if (!box)
            return;
        box.textContent = '';
        box.classList.remove('is-open', 'activa');
    }

    function getFieldContainer(input) {
        return input.closest('.admin-crud-field') || input.closest('.campo-reserva') || input.parentElement;
    }

    function getErrorElement(input) {
        const container = getFieldContainer(input);
        if (!container)
            return null;
        let error = container.querySelector('.admin-crud-error, .error-mensaje');
        if (!error) {
            error = document.createElement('span');
            error.className = 'admin-crud-error error-mensaje';
            container.appendChild(error);
        }
        return error;
    }

    function clearFormState(form) {
        if (!form)
            return;
        clearAlert(form);
        form.querySelectorAll('.input-error, .input-ok, .field-invalid, .field-valid').forEach(function (field) {
            field.classList.remove('input-error', 'input-ok', 'field-invalid', 'field-valid');
        });
        form.querySelectorAll('.admin-crud-error, .error-mensaje').forEach(function (message) {
            message.textContent = '';
            message.classList.remove('visible');
        });
    }

    function setFieldState(input, valid, message, showMessage) {
        const error = getErrorElement(input);
        input.classList.remove('input-error', 'input-ok', 'field-invalid', 'field-valid');
        input.classList.add(valid ? 'input-ok' : 'input-error');
        input.classList.add(valid ? 'field-valid' : 'field-invalid');
        if (error) {
            error.textContent = valid || !showMessage ? '' : message;
            error.classList.toggle('visible', !valid && showMessage);
        }
    }

    function requiredMessage(input) {
        if (input.dataset.requiredMessage)
            return input.dataset.requiredMessage;
        const label = input.dataset.label || input.getAttribute('aria-label') || input.name || 'campo';
        const article = input.dataset.article || 'El';
        return article + ' ' + label + ' es obligatorio.';
    }

    function validateField(input, showMessage) {
        if (input.disabled || input.type === 'hidden' || input.type === 'file' || input.name === 'accion')
            return true;

        const value = String(input.value || '').trim();
        const required = input.required || input.hasAttribute('required');
        let valid = true;
        let message = requiredMessage(input);

        if (required && value === '') {
            valid = false;
        } else if (value !== '') {
            if (input.tagName === 'SELECT') {
                valid = value !== '';
            } else if (input.type === 'email') {
                valid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
                message = 'El correo no tiene un formato válido.';
            } else if (input.type === 'number') {
                const numberValue = Number(value);
                const min = input.min !== '' ? Number(input.min) : null;
                const max = input.max !== '' ? Number(input.max) : null;
                valid = Number.isFinite(numberValue);
                if (valid && min !== null)
                    valid = numberValue >= min;
                if (valid && max !== null)
                    valid = numberValue <= max;
                message = input.dataset.rangeMessage || ('El ' + (input.dataset.label || input.name) + ' tiene un valor incorrecto.');
            }
        } else if (input.type === 'date') {
            valid = !Number.isNaN(new Date(value + 'T00:00:00').getTime());
            message = 'La ' + (input.dataset.label || 'fecha') + ' no es válida.';
            if (valid && input.min) {
                valid = value >= input.min;
                message = 'La ' + (input.dataset.label || 'fecha') + ' no puede ser anterior a hoy.';
            }
            if (valid && input.max) {
                valid = value <= input.max;
                message = 'La ' + (input.dataset.label || 'fecha') + ' no puede ser posterior a hoy.';
            }
        } else if (input.type === 'time') {
            valid = /^([01]\d|2[0-3]):[0-5]\d(:[0-5]\d)?$/.test(value);
            message = 'La ' + (input.dataset.label || 'hora') + ' no es válida.';
            if (valid && input.min) {
                valid = value >= input.min;
                message = 'La ' + (input.dataset.label || 'hora') + ' debe ser igual o posterior a ' + input.min + '.';
            }
            if (valid && input.max) {
                valid = value <= input.max;
                message = 'La ' + (input.dataset.label || 'hora') + ' debe ser igual o anterior a ' + input.max + '.';
            }
        }

        if (valid && input.dataset.pattern) {
            valid = new RegExp(input.dataset.pattern).test(value);
            message = input.dataset.patternMessage || ('El ' + (input.dataset.label || input.name) + ' tiene un formato incorrecto.');
        }

        if (valid && input.dataset.pattern) {
            valid = new RegExp(input.dataset.pattern).test(value);
            message = input.dataset.patternMessage || ('El ' + (input.dataset.label || input.name) + ' tiene un formato incorrecto.');
        }

        if (valid && (input.dataset.min !== undefined || input.dataset.max !== undefined)) {
            const numericValue = Number(value);
            const min = input.dataset.min !== undefined ? Number(input.dataset.min) : null;
            const max = input.dataset.max !== undefined ? Number(input.dataset.max) : null;
            valid = Number.isFinite(numericValue);
            if (valid && min !== null)
                valid = numericValue >= min;
            if (valid && max !== null)
                valid = numericValue <= max;
            if (!valid) {
                message = input.dataset.rangeMessage || ('El ' + (input.dataset.label || input.name) + ' debe estar entre ' + min + ' y ' + max + '.');
            }
        }



        setFieldState(input, valid, message, showMessage);
        return valid;
    }

    function duplicateKeyFromForm(form) {
        const names = (form.dataset.duplicateKeys || '').split(',').map(function (name) {
            return name.trim();
        }).filter(Boolean);
        return names.map(function (name) {
            const field = form.querySelector('[name="' + name + '"]');
            return normalizeValue(field ? field.value : '');
        }).join('|');
    }

    function isDuplicate(form) {
        const key = duplicateKeyFromForm(form);
        if (!key)
            return false;
        const idName = form.dataset.idName || '';
        const idField = idName ? form.querySelector('[name="' + idName + '"]') : null;
        const currentId = normalizeValue(idField ? idField.value : '');

        return Array.from(document.querySelectorAll('[data-duplicate-key]')).some(function (row) {
            const rowId = normalizeValue(idName ? row.getAttribute('data-field-' + idName) : '');
            return normalizeValue(row.getAttribute('data-duplicate-key')) === key && rowId !== currentId;
        });
    }
    function validateAfterFields(form) {
        let valid = true;
        form.querySelectorAll('[data-after]').forEach(function (input) {
            const otherName = input.dataset.after;
            const other = form.querySelector('[name="' + otherName + '"]');
            if (!other || !input.value || !other.value)
                return;
            if (input.value <= other.value) {
                const label = input.dataset.label || input.name;
                setFieldState(input, false, 'La ' + label + ' debe ser posterior al campo anterior.', true);
                valid = false;
            }
        });
        return valid;
    }
    function validateForm(form) {
        let valid = true;
        clearAlert(form);

           form.querySelectorAll('input, select, textarea').forEach(function (field) {
            if (!validateField(field, true))
                valid = false;
        });

        if (!validateAfterFields(form)) {
            valid = false;
        }

        if (!valid) {
            showAlert(form, 'Completa todos los campos obligatorios antes de guardar.');
            return false;
        }

        if (isDuplicate(form)) {
            showAlert(form, 'Ya existe un registro idéntico en la lista. No se puede guardar duplicado.');
            return false;
        }

        return true;
    }

    function fillFormFromRow(form, row) {
        form.querySelectorAll('[name]').forEach(function (field) {
            if (field.name === 'accion')
                return;
            const value = row.getAttribute('data-field-' + field.name);
            if (value !== null)
                field.value = value;
        });

        const modalId = form.querySelector('#modalId');
        const rowId = row.getAttribute('data-field-id') || row.getAttribute('data-id');
        if (modalId && rowId !== null)
            modalId.value = rowId;

        const fotoNombre = row.getAttribute('data-field-foto');
        const inputHiddenFoto = document.getElementById('foto');
        const imgPreview = document.getElementById('preview-foto');
        const inputFileInput = document.getElementById('fotoArchivo');

        if (inputFileInput)
            inputFileInput.value = '';

        if (fotoNombre && fotoNombre.trim() !== '') {
            if (inputHiddenFoto)
                inputHiddenFoto.value = fotoNombre;
            if (imgPreview) {
                imgPreview.src = 'Vista/Fotos/' + fotoNombre;
                imgPreview.style.display = 'block';
            }
        } else {
            if (inputHiddenFoto)
                inputHiddenFoto.value = '';
            if (imgPreview) {
                imgPreview.src = '';
                imgPreview.style.display = 'none';
            }
        }
    }

    function fillDeleteForm(row) {
        if (!deleteForm || !row)
            return;
        deleteForm.querySelectorAll('[name]').forEach(function (field) {
            if (field.name === 'accion')
                return;
            const value = row.getAttribute('data-field-' + field.name);
            if (value !== null)
                field.value = value;
        });
    }

    function setAction(form, action) {
        let actionField = form.querySelector('#modalAccion') || form.querySelector('[name="accion"]');
        if (!actionField) {
            actionField = document.createElement('input');
            actionField.type = 'hidden';
            actionField.name = 'accion';
            actionField.id = 'modalAccion';
            form.appendChild(actionField);
        }
        actionField.value = action;
    }

    function submitDeleteFromMainForm() {
        if (!editForm || !currentRow)
            return;
        fillFormFromRow(editForm, currentRow);
        setAction(editForm, editForm.dataset.deleteAction || 'eliminar');
        HTMLFormElement.prototype.submit.call(editForm);
    }

    document.querySelectorAll('[data-cerrar]').forEach(function (button) {
        button.addEventListener('click', function () {
            closeModal(document.getElementById(button.dataset.cerrar));
        });
    });

    document.querySelectorAll('.modal-overlay, .admin-crud-modal').forEach(function (overlay) {
        overlay.addEventListener('click', function (event) {
            if (event.target === overlay)
                closeModal(overlay);
        });
    });

    document.addEventListener('keydown', function (event) {
        if (event.key === 'Escape') {
            document.querySelectorAll('.modal-overlay.activo, .admin-crud-modal.is-open').forEach(closeModal);
        }
    });

    document.querySelectorAll('[data-open-create]').forEach(function (button) {
        button.addEventListener('click', function () {
            if (!editModal || !editForm)
                return;
            currentRow = null;
            editForm.reset();
            clearFormState(editForm);

            const selectRaza = document.getElementById('raza');
            if (selectRaza) {
                selectRaza.disabled = true;
                selectRaza.innerHTML = '<option value="" selected disabled>Selecciona primero la especie</option>';
            }

            const imgPreview = document.getElementById('preview-foto');
            const inputHiddenFoto = document.getElementById('foto');
            if (imgPreview) {
                imgPreview.src = '';
                imgPreview.style.display = 'none';
            }
            if (inputHiddenFoto)
                inputHiddenFoto.value = '';

            editModal.classList.remove('admin-crud-edit-mode');
            setAction(editForm, editForm.dataset.createAction || 'insertar');
            const modalId = editForm.querySelector('#modalId');
            if (modalId)
                modalId.value = '';
            if (title)
                title.textContent = editForm.dataset.createTitle || 'Nuevo registro';
            openModal(editModal);
            const firstField = editForm.querySelector('input:not([type="hidden"]), select, textarea');
            if (firstField)
                firstField.focus();
        });
    });

    document.querySelectorAll('[data-admin-row]').forEach(function (row) {
        row.addEventListener('click', function (event) {
            if (event.target.closest('a, button, input, select, textarea'))
                return;
            if (!editModal || !editForm)
                return;
            currentRow = row;
            editForm.reset();
            clearFormState(editForm);
            fillFormFromRow(editForm, row);
            const idEspecieFila = row.getAttribute('data-field-especie');
            const idRazaFila = row.getAttribute('data-field-raza');
            if (typeof cargarRazasParaEdicion === 'function' && idEspecieFila) {
                cargarRazasParaEdicion(idEspecieFila, idRazaFila);
            }

            editModal.classList.add('admin-crud-edit-mode');
            setAction(editForm, editForm.dataset.editAction || 'actualizar');
            if (title)
                title.textContent = editForm.dataset.editTitle || 'Modificar registro';
            openModal(editModal);
            const firstField = editForm.querySelector('input:not([type="hidden"]), select, textarea');
            if (firstField)
                firstField.focus();
        });

        row.addEventListener('keydown', function (event) {
            if (event.key === 'Enter' || event.key === ' ') {
                event.preventDefault();
                row.click();
            }
        });
    });

    document.querySelectorAll('.admin-managed-form').forEach(function (form) {
        form.setAttribute('novalidate', 'novalidate');

        form.querySelectorAll('input, select, textarea').forEach(function (field) {
            if (field.type === 'hidden' || field.name === 'accion')
                return;
            ['input', 'change', 'blur'].forEach(function (eventName) {
                field.addEventListener(eventName, function () {
                    validateField(field, true);
                    clearAlert(form);
                });
            });
        });

        form.addEventListener('submit', function (event) {
            if (!validateForm(form)) {
                event.preventDefault();
                return;
            }

            const accionField = form.querySelector('[name="accion"]');
            const esCreacion = accionField && accionField.value === (form.dataset.createAction || 'insertar');
            const fotoInput = document.getElementById('fotoArchivo');
            const fotoOculta = document.getElementById('foto');
            const errorFoto = document.getElementById('error-foto');

            // SE CORRIGE AQUÍ: Solo validar la foto si el input existe en este formulario
            if (fotoInput) {
                const tieneArchivoNuevo = fotoInput.files.length > 0;
                const tieneFotoExistente = fotoOculta && fotoOculta.value.trim() !== '';

                if (esCreacion && !tieneArchivoNuevo) {
                    if (errorFoto)
                        errorFoto.textContent = 'Debes subir una foto del perrito.';
                    fotoInput.classList.add('input-error');
                    event.preventDefault();
                    return;
                }

                if (!esCreacion && !tieneArchivoNuevo && !tieneFotoExistente) {
                    if (errorFoto)
                        errorFoto.textContent = 'Debes subir una foto del perrito.';
                    fotoInput.classList.add('input-error');
                    event.preventDefault();
                    return;
                }
            }
        });
    });

    document.querySelectorAll('[data-open-delete]').forEach(function (button) {
        button.addEventListener('click', function () {
            if (!currentRow || !confirmModal)
                return;
            fillDeleteForm(currentRow);
            openModal(confirmModal);
        });
    });

    if (confirmModal) {
        confirmModal.querySelectorAll('button[type="submit"], [data-confirm-delete]').forEach(function (button) {
            button.type = 'button';
            button.setAttribute('data-confirm-delete', 'true');
            button.addEventListener('click', submitDeleteFromMainForm);
        });
    }

    const fotoInput = document.getElementById('fotoArchivo');
    if (fotoInput) {
        fotoInput.addEventListener('change', function (e) {
            const file = e.target.files[0];
            const imgPreview = document.getElementById('preview-foto');
            if (file && imgPreview) {
                imgPreview.src = URL.createObjectURL(file);
                imgPreview.style.display = 'block';
            }
        });
    }
});

// Agregar este bloque a Admi_modales.js (o a un <script> al final de PerritoAdmi.jsp).
// Calcula automáticamente la etapa de madurez del perrito según su fecha de
// nacimiento, cada vez que el admin cambia esa fecha en el formulario.

document.addEventListener('DOMContentLoaded', function () {
    const inputFechaNacimiento = document.getElementById('fecha_nacimiento');
    const selectEtapaMadurez = document.getElementById('etapa_madurez');

    if (!inputFechaNacimiento || !selectEtapaMadurez) {
        return;
    }

    // Bloquea fechas futuras directamente en el calendario del navegador
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm = String(hoy.getMonth() + 1).padStart(2, '0');
    const dd = String(hoy.getDate()).padStart(2, '0');
    inputFechaNacimiento.setAttribute('max', `${yyyy}-${mm}-${dd}`);

    // Calcula la edad en meses completos a partir de la fecha de nacimiento
    function calcularEdadEnMeses(fechaNacStr) {
        const nacimiento = new Date(fechaNacStr + 'T00:00:00');
        const ahora = new Date();

        let meses = (ahora.getFullYear() - nacimiento.getFullYear()) * 12
                + (ahora.getMonth() - nacimiento.getMonth());

        // Si todavía no ha llegado el "día" del mes, resta un mes
        if (ahora.getDate() < nacimiento.getDate()) {
            meses--;
        }

        return Math.max(meses, 0);
    }

    // Traduce meses de edad a la etapa correspondiente
    // Cachorro: 0-1 año | Joven: 1-3 años | Adulto: 3-8 años | Senior: 8+ años
    function calcularEtapaMadurez(meses) {
        if (meses < 12)
            return 'Cachorro';
        if (meses < 36)
            return 'Joven';
        if (meses < 96)
            return 'Adulto';
        return 'Senior';
    }

    function actualizarEtapaMadurez() {
        const valor = inputFechaNacimiento.value;
        if (!valor) {
            return;
        }

        const meses = calcularEdadEnMeses(valor);
        const etapaCalculada = calcularEtapaMadurez(meses);

        // Selecciona automáticamente la opción que coincide con el texto calculado
        for (const opcion of selectEtapaMadurez.options) {
            if (opcion.value === etapaCalculada) {
                selectEtapaMadurez.value = etapaCalculada;
                break;
            }
        }
    }

    inputFechaNacimiento.addEventListener('change', actualizarEtapaMadurez);

    // Si el modal se abre en modo "editar" con una fecha ya cargada,
    // recalcula la etapa apenas se llene el campo (ver nota abajo).
    const observer = new MutationObserver(actualizarEtapaMadurez);
    observer.observe(inputFechaNacimiento, {attributes: true, attributeFilter: ['value']});
});

document.addEventListener('DOMContentLoaded', function () {
    const inputFecha = document.getElementById('fechaDisp');
    if (!inputFecha) return;
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm = String(hoy.getMonth() + 1).padStart(2, '0');
    const dd = String(hoy.getDate()).padStart(2, '0');
    inputFecha.setAttribute('min', `${yyyy}-${mm}-${dd}`);
});