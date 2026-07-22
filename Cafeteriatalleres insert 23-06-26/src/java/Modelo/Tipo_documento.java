package Modelo;

public class Tipo_documento {

    private int idTipo_documento;
    private String descripcion_doc;
    private boolean solo_numeros;

    public int getidTipo_documento() {
        return idTipo_documento;
    }

    public void setidTipo_documento(int idTipo_documento) {
        this.idTipo_documento = idTipo_documento;
    }

    public String getdescripcion_doc() {
        return descripcion_doc;
    }

    public void setdescripcion_doc(String descripcion_doc) {
        this.descripcion_doc = descripcion_doc;
    }

    public boolean isSolo_numeros() {
        return solo_numeros;
    }

    public void setSolo_numeros(boolean solo_numeros) {
        this.solo_numeros = solo_numeros;
    }
}
