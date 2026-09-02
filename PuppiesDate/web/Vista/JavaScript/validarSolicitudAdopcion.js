function validarSolicitudAdopcionCompleta() {
  const campos = [
    ["direccion", "Ingresa tu dirección."],
    ["departamentoId", "Selecciona un departamento."],
    ["ubicacionId", "Selecciona un municipio o localidad."],
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

  // El campo ubicacionId empieza deshabilitado hasta elegir departamento.
  // Si sigue deshabilitado al enviar, es porque no se completó el paso anterior.
  const ubicacion = document.getElementById("ubicacionId");
  if (ubicacion && ubicacion.disabled) {
    const errorUbicacion = document.getElementById("error_ubicacionId");
    if (errorUbicacion) {
      errorUbicacion.textContent = "Selecciona primero un departamento.";
    }
    ubicacion.classList.add("campo-error");
    valido = false;
  }

  if (!valido) {
    const primerError = document.querySelector(".campo-error");
    if (primerError) {
      primerError.scrollIntoView({ behavior: "smooth", block: "center" });
      primerError.focus();
    }
  }

  return valido;
}