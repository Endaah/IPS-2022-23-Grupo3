package giis.demo.model;

import java.time.LocalDate;

public class ReservaInstalacion {

	private int idSocio;
	private LocalDate fecha;
	private int hora;
	
	public ReservaInstalacion(int idSocio, LocalDate fecha, int hora) {
		this.idSocio = idSocio;
		this.fecha = fecha;
		this.hora = hora;
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
	
}
