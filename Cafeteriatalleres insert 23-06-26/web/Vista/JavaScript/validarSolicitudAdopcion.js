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
