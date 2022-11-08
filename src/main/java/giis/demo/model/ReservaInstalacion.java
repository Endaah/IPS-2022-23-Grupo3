package giis.demo.model;

import java.time.LocalDate;

public class ReservaInstalacion {

	public static final int ID_ACTIVIDAD = 0;
	
	public static final int CANCELADA = 1;
	public static final int VALIDA = 0;
	
	private int idSocio;
	private LocalDate fecha;
	private int hora;
	private String instalacion;
	private int anulada;
	private int idReserva;
	private int precio = 2;
	
	public ReservaInstalacion(int idSocio, LocalDate fecha, int hora, String instalacion, int anulada, int id) {
		this.idSocio = idSocio;
		this.fecha = fecha;
		this.hora = hora;
		this.instalacion = instalacion;
		this.anulada = anulada;
	}
	
	public ReservaInstalacion(ReservaInstalacion reserva) {
		this.idSocio = reserva.getIdSocio();
		this.fecha = reserva.getFecha();
		this.hora = reserva.getHora() + 1;
		this.instalacion = reserva.getInstalacion();
		this.anulada = reserva.getAnulada();
		this.idReserva = reserva.getIdReserva();
	}
	
	public int getIdSocio() {
		return idSocio;
	}
	
	public int getPrecio() {
		return precio;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public int getHora() {
		return hora;
	}
	
	public int getAnulada() {
		return anulada;
	}
	
	public String getInstalacion() {
		return instalacion;
	}
	
	public int getIdReserva() {
		return idReserva;
	}
	
	@Override
	public String toString() {
		return idSocio + " - " + instalacion + " - " + fecha.toString() + " - a las: " + hora;
	}
}
