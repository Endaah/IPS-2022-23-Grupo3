package giis.demo.model;

import java.time.LocalDate;

public class ReservaInstalacion {

	private int idSocio;
	private LocalDate fecha;
	private int hora;
	private String instalacion;
	
	public ReservaInstalacion(int idSocio, LocalDate fecha, int hora, String instalacion) {
		this.idSocio = idSocio;
		this.fecha = fecha;
		this.hora = hora;
		this.instalacion = instalacion;
	}
	
	public int getIdSocio() {
		return idSocio;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public int getHora() {
		return hora;
	}
	
	public String getInstalacion() {
		return instalacion;
	}
	
	@Override
	public String toString() {
		return idSocio + " - " + instalacion + " - " + fecha.toString();
	}
}
