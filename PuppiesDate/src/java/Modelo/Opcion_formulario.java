package Modelo;

public class Opcion_formulario {

    private int idOpcion_formulario;
    private String categoria;
    private String descripcion;

    public Opcion_formulario() {
    }

    public int getIdOpcion_formulario() {
        return idOpcion_formulario;
    }

    public void setIdOpcion_formulario(int idOpcion_formulario) {
        this.idOpcion_formulario = idOpcion_formulario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
