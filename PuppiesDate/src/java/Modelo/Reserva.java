package Modelo;

import java.sql.Date;
import java.sql.Time;

public class Reserva {

    private int idReserva;
    private int num_personas;
    private Time hora;
    private Date fecha;
    private int usuarios_idUsuarios;
    private int Disponibilidad_idDisponibilidad;
    private int Estado_reserva_idEstado_reserva;
    private int Actividad_idActividad;
    private int Pagos_idPagos;
    private String nombreActividad;
    private String nombreUsuario;
    private String descripcionEstadoReserva;

    public Reserva() {
    }

    public Reserva(int idReserva ,int num_personas, Time hora, Date fecha, int usuarios_idUsuarios, int Disponibilidad_idDisponibilidad,int Estado_reserva_idEstado_reserva, int Actividad_idActividad) {
        this.num_personas = num_personas;
        this.hora = hora;
        this.fecha = fecha;
        this.usuarios_idUsuarios = usuarios_idUsuarios;
        this.Disponibilidad_idDisponibilidad = Disponibilidad_idDisponibilidad;
        this.Estado_reserva_idEstado_reserva = Estado_reserva_idEstado_reserva;
        this.Actividad_idActividad = Actividad_idActividad;
        this.Pagos_idPagos = Pagos_idPagos;
    }
    
    public int getidReserva() {
        return idReserva;
    }
    
    public void setidReserva(int idReserva) {
        this. idReserva = idReserva;
    }

    public int getNum_personas() {
        return num_personas;
    }

    public void setNum_personas(int num_personas) {
        this.num_personas = num_personas;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getUsuarios_idUsuarios() {
        return usuarios_idUsuarios;
    }

    public void setUsuarios_idUsuarios(int usuarios_idUsuarios) {
        this.usuarios_idUsuarios = usuarios_idUsuarios;
    }

    public int getDisponibilidad_idDisponibilidad() {
        return Disponibilidad_idDisponibilidad;
    }
     
    public void setDisponibilidad_idDisponibilidad(int Disponibilidad_idDisponibilidad) {
        this.Disponibilidad_idDisponibilidad = Disponibilidad_idDisponibilidad;
    }
   
    public int getEstado_reserva_idEstado_reserva() {
        return Estado_reserva_idEstado_reserva;
    }
    

    public void setEstado_reserva_idEstado_reserva(int Estado_reserva_idEstado_reserva) {
        this.Estado_reserva_idEstado_reserva = Estado_reserva_idEstado_reserva;
    }
   
    public int getActividad_idActividad() {
        return Actividad_idActividad;
    }

    public void setActividad_idActividad(int Actividad_idActividad) {
        this.Actividad_idActividad = Actividad_idActividad;
    }
    
    public int getPagos_idPagos() {
        return Pagos_idPagos;
    }
    
    public void setPagos_idPagos(int Pagos_idPagos) {
        this. Pagos_idPagos = Pagos_idPagos;
    }



public String getNombreActividad() {
    return nombreActividad;
}
public void setNombreActividad(String nombreActividad) {
    this.nombreActividad = nombreActividad;
}
public String getNombreUsuario() {
    return nombreUsuario;
}
public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
}

public String getDescripcionEstadoReserva() {
    return descripcionEstadoReserva;
}
public void setDescripcionEstadoReserva(String descripcionEstadoReserva) {
    this.descripcionEstadoReserva = descripcionEstadoReserva;
}
}