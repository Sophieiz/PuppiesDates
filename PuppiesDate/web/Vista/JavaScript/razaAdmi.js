/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function inicializarRazaPorEspecie() {
    const selectEspecie = document.getElementById("especie");
    const selectRaza = document.getElementById("raza");
    if (!selectEspecie || !selectRaza) return;

    selectEspecie.addEventListener("change", function () {
        const idEspecie = selectEspecie.value;

        selectRaza.disabled = true;
        selectRaza.innerHTML = '<option value="" selected disabled>Cargando...</option>';

        if (!idEspecie) {
            selectRaza.innerHTML = '<option value="" selected disabled>Selecciona primero la especie</option>';
            return;
        }

        const url = (window.contextPath || window.ctxApp || "") + "/ObtenerRazas?idEspecie=" + encodeURIComponent(idEspecie);

        fetch(url)
            .then(function (res) { return res.json(); })
            .then(function (data) {
                selectRaza.innerHTML = '<option value="" selected disabled>Seleccione...</option>';
                data.forEach(function (item) {
                    const option = document.createElement("option");
                    option.value = item.id;
                    option.textContent = item.nombre;
                    selectRaza.appendChild(option);
                });
                selectRaza.disabled = false;
            })
            .catch(function (err) {
                console.error("Error al cargar razas:", err);
                selectRaza.innerHTML = '<option value="" selected disabled>Error al cargar</option>';
            });
    });
}
document.addEventListener("DOMContentLoaded", inicializarRazaPorEspecie);

function cargarRazasParaEdicion(idEspecie, idRazaSeleccionada) {
    const selectRaza = document.getElementById("raza");
    if (!selectRaza || !idEspecie) return;

    selectRaza.disabled = true;
    selectRaza.innerHTML = '<option value="" selected disabled>Cargando...</option>';

    const url = (window.contextPath || window.ctxApp || "") + "/ObtenerRazas?idEspecie=" + encodeURIComponent(idEspecie);

    fetch(url)
        .then(function (res) { return res.json(); })
        .then(function (data) {
            selectRaza.innerHTML = '<option value="" selected disabled>Seleccione...</option>';
            data.forEach(function (item) {
                const option = document.createElement("option");
                option.value = item.id;
                option.textContent = item.nombre;
                if (String(item.id) === String(idRazaSeleccionada)) {
                    option.selected = true;
                }
                selectRaza.appendChild(option);
            });
            selectRaza.disabled = false;
        })
        .catch(function (err) {
            console.error("Error al cargar razas para edición:", err);
            selectRaza.innerHTML = '<option value="" selected disabled>Error al cargar</option>';
        });
}