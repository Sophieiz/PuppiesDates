/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const selectDepartamento = document.getElementById("departamentoId");
    const selectUbicacion = document.getElementById("ubicacionId");
    const inputTipoDivision = document.getElementById("tipoDivision");
    const labelUbicacion = document.querySelector('label[for="ubicacionId"]');

    if (!selectDepartamento || !selectUbicacion) return;

    selectDepartamento.addEventListener("change", function () {
        const opcionSeleccionada = selectDepartamento.options[selectDepartamento.selectedIndex];
        const idDepartamento = selectDepartamento.value;
        const tipoDivision = opcionSeleccionada.getAttribute("data-tipo");

        // Reinicia el select mientras carga
        selectUbicacion.disabled = true;
        selectUbicacion.innerHTML = '<option value="" selected disabled>Cargando...</option>';
        inputTipoDivision.value = tipoDivision || "";

        if (labelUbicacion) {
            labelUbicacion.textContent = tipoDivision === "MUNICIPIO" ? "Municipio" : "Localidad";
        }

        if (!idDepartamento || !tipoDivision) {
            selectUbicacion.innerHTML = '<option value="" selected disabled>Selecciona primero el departamento</option>';
            return;
        }

        const url = contextPath + "/ObtenerUbicaciones?departamentoId=" + encodeURIComponent(idDepartamento)
                  + "&tipoDivision=" + encodeURIComponent(tipoDivision);

        fetch(url)
            .then(function (res) { return res.json(); })
            .then(function (data) {
                selectUbicacion.innerHTML = '<option value="" selected disabled>Seleccione...</option>';
                data.forEach(function (item) {
                    const option = document.createElement("option");
                    option.value = item.id;
                    option.textContent = item.nombre;
                    selectUbicacion.appendChild(option);
                });
                selectUbicacion.disabled = false;
            })
            .catch(function (err) {
                console.error("Error al cargar ubicaciones:", err);
                selectUbicacion.innerHTML = '<option value="" selected disabled>Error al cargar</option>';
            });
    });
});