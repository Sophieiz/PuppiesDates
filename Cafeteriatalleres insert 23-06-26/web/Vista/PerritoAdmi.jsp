<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Perritos</title>
        <link rel="stylesheet" href="${ctx}/Vista/Css/style.css">
    </head>
    <body class="admin-form-body">
        <a href="${ctx}/PanelAdmin.jsp" class="btn-volver-panel">&#8592; Volver al panel</a>

        <c:if test="${not empty mensaje}">
            <div class="mensaje-bienvenida">
                <p>${mensaje}</p>
            </div>
        </c:if>

        <div class="admin-form-wrap">
            <div class="admin-table-card">
                <div class="admin-crud-toolbar">
                    <h3>Perritos</h3>
                    <button type="button" class="admin-crud-btn-primary" data-open-create>Nuevo perrito</button>
                </div>

                <p class="admin-crud-help">Haz clic sobre una fila para modificar o eliminar el registro.</p>

                <div class="admin-crud-table-wrap">
                    <table class="admin-crud-table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nombre</th>
                                <th>Raza</th>
                                <th>Sexo</th>
                                <th>Microchip</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="perrito" items="${listaPerritos}">
                                <tr class="admin-crud-row" 
                                    data-admin-row 
                                    tabindex="0"
                                    data-field-id="${perrito.idPerrito}"
                                    data-field-idPerrito="${perrito.idPerrito}"
                                    data-field-nombre="${perrito.nombre}"
                                    data-field-especie="${perrito.especie}"
                                    data-field-raza="${perrito.raza}"
                                    data-field-fecha_nacimiento="${perrito.fecha_nacimiento}"
                                    data-field-sexo="${perrito.sexo}"
                                    data-field-microchip="${perrito.microchip}"
                                    data-field-etapa_madurez="${perrito.etapa_madurez}"
                                    data-field-especialidad="${perrito.especialidad}"
                                    data-field-condiciones_especiales="${perrito.condiciones_especiales}"
                                    data-field-titulo_historia="${perrito.titulo_historia}"
                                    data-field-historia="${perrito.historia}"
                                    data-field-foto="${perrito.foto}"
                                    data-field-ciudad="${perrito.ciudad}"
                                    data-field-Estado_perrito_idEstado_perrito="${perrito.estado_perrito_idEstado_perrito}"
                                    data-duplicate-key="${perrito.microchip}">

                                    <td>${perrito.idPerrito}</td>
                                    <td>${perrito.nombre}</td>
                                    <td>${perrito.raza}</td>
                                    <td>${perrito.sexo}</td>
                                    <td>${perrito.microchip}</td>
                                    <td>${perrito.descripcionEstado_perrito}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


        <div class="admin-crud-modal modal-overlay" id="modal-editar" aria-hidden="true">
            <div class="admin-crud-modal-box">
                <button type="button" class="admin-modal-cerrar" data-cerrar="modal-editar" aria-label="Cerrar">&times;</button>
                <h2 class="admin-crud-title" data-modal-title>Nuevo perrito</h2>

                <form action="${ctx}/PerritoAdmi" method="POST" enctype="multipart/form-data" class="admin-managed-form"
                      data-id-name="idPerrito" 
                      data-duplicate-keys="microchip"
                      data-create-action="insertar" 
                      data-edit-action="actualizar"
                      data-create-title="Nuevo perrito" 
                      data-edit-title="Modificar perrito">

                    <input type="hidden" id="modalAccion" name="accion" value="insertar">
                    <input type="hidden" id="modalId" name="idPerrito" value="">

                    <div class="admin-crud-alert" data-form-alert></div>

                    <div class="admin-crud-field">
                        <label for="nombre">Nombre:</label>
                        <input type="text" name="nombre" id="nombre" data-label="nombre" data-required-message="El nombre es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>



                    <div class="admin-crud-field">
                        <label for="especie">Especie:</label>
                        <input type="text" name="especie" id="especie" value="Canino" data-label="especie" data-required-message="La especie es obligatoria." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="raza">Raza:</label>
                        <input type="text" name="raza" id="raza">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="fecha_nacimiento">Fecha de nacimiento:</label>
                        <input type="date" name="fecha_nacimiento" id="fecha_nacimiento">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="sexo">Sexo:</label>
                        <select name="sexo" id="sexo" data-label="sexo" data-required-message="El sexo es obligatorio." required>
                            <option value="">Seleccione...</option>
                            <c:forEach var="sexoOpcion" items="${listaSexos}">
                                <option value="${sexoOpcion.descripcion}">${sexoOpcion.descripcion}</option>
                            </c:forEach>
                        </select>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="microchip">Microchip:</label>
                        <input type="text" name="microchip" id="microchip" data-label="microchip" data-required-message="El microchip es obligatorio." required>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="etapa_madurez">Etapa de madurez:</label>
                        <input type="text" name="etapa_madurez" id="etapa_madurez" placeholder="Ej: Cachorro, Adulto, Geronte">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="especialidad">Especialidad:</label>
                        <input type="text" name="especialidad" id="especialidad" placeholder="Ej: NA">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="condiciones_especiales">Condiciones especiales:</label>
                        <input type="text" name="condiciones_especiales" id="condiciones_especiales" placeholder="Ej: No aplica">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="ciudad">Ciudad:</label>
                        <input type="text" name="ciudad" id="ciudad">
                        <span class="admin-crud-error"></span>
                    </div>



                    <div class="admin-crud-field">
                        <label for="titulo_historia">Título de la historia:</label>
                        <input type="text" name="titulo_historia" id="titulo_historia" placeholder="Ej: El milagro de volver a confiar">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="historia">Historia completa:</label>
                        <textarea name="historia" id="historia" rows="5" data-label="historia" data-required-message="La historia es obligatoria." required></textarea>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="Estado_perrito_idEstado_perrito">Estado:</label>
                        <select name="Estado_perrito_idEstado_perrito" id="Estado_perrito_idEstado_perrito" data-label="estado" data-required-message="El estado es obligatorio." required>
                            <c:forEach var="estado" items="${listaEstados}">
                                <option value="${estado.idEstado_perrito}">${estado.descripcion_estado}</option>
                            </c:forEach>
                        </select>
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-field">
                        <label for="fotoArchivo">Foto del perrito:</label>

                        <input type="file" name="fotoArchivo" id="fotoArchivo" class="admin-photo-input" accept="image/*" data-photo-picker data-photo-target="foto" data-photo-preview="preview-foto">
                        <input type="hidden" name="foto" id="foto">
                        <img class="admin-photo-preview" id="preview-foto" alt="Vista previa de la foto" style="max-width: 150px; margin-top: 10px; display: block;">
                        <span class="admin-crud-error"></span>
                    </div>

                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-danger" data-open-delete>Eliminar</button>
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-editar">Cancelar</button>
                        <button type="submit" class="admin-crud-btn-primary">Guardar</button>
                    </div>
                </form>
            </div>
        </div>


        <div class="admin-crud-modal modal-overlay" id="modal-confirmar-eliminar" aria-hidden="true">
            <div class="admin-crud-modal-box admin-crud-confirm">
                <h2 class="admin-crud-title">¿Está seguro de eliminar este registro?</h2>
                <form action="${ctx}/PerritoAdmi" method="POST">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="idPerrito">
                    <div class="admin-crud-actions">
                        <button type="button" class="admin-crud-btn-secondary" data-cerrar="modal-confirmar-eliminar">No</button>
                        <button type="submit" class="admin-crud-btn-danger">Sí</button>
                    </div>
                </form>
            </div>
        </div>

        <script src="${ctx}/Vista/JavaScript/Admi_modales.js"></script>
        <script src="${ctx}/Vista/JavaScript/interfaz.js"></script>
    </body>
</html>