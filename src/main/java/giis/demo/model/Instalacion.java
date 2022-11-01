package giis.demo.model;

import java.time.LocalDate;
import java.util.List;

import giis.demo.util.Db;

public class Instalacion {

	public static final int CANCELADA = 1;
	public static final int VALIDA = 0;
	
	private String nombre;
	private List<Recurso> recursos;
	private List<ReservaInstalacion> reservas;
	
	public Instalacion(String nombre, List<Recurso> recurso, List<ReservaInstalacion> reservas) {
		this.nombre = nombre;
		this.recursos = recurso;
		this.reservas = reservas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Recurso[] getRecurso() {
		return recursos.toArray(new Recurso[recursos.size()]);
	}
	
	public Integer[] getReservas() {
		return reservas.toArray(new Integer[reservas.size()]);
	}
	
	public boolean reservar(int idSocio, LocalDate fecha, int hora, boolean larga) {
		if (fecha.getDayOfYear() - LocalDate.now().getDayOfYear() >= 8) {		// Si la reserva se ha hecho más de 7 días antes
			System.err.println("No se ha realizado la reserva, "				// Avisar de que no se ha realizado
					+ "las resrvas se deben hacer como mucho 7 días antes");
			return false;
		}
		
		for (ReservaInstalacion ri : reservas) {						// Por cada reserva
			if (ri.getFecha().equals(fecha)
					&& (ri.getHora() == hora
						|| (larga && ri.getHora() == hora + 1))) { 		// Si existe una reserva el mismo dia a la misma hora que lo que se ha intentado reservar
				System.err.println("No se ha realizado la reserva, "	// Avisar que no se ha realizado
						+ "la instalación ya estaba reservada");
				return false;
			}
			
			if (ri.getIdSocio() == idSocio && (ri.getHora() == hora
					|| (larga && ri.getHora() == hora + 1))) {			// Si el socio ya tiene una reserva de instalación
				System.err.println("No se ha realizado la reserva, "	// Avisar que no se ha realizado
						+ "este socio ya tiene una reserva a esta hora");
				return false;
			}
		}
		
		for (Actividad a : Db.getActividadesDe(idSocio)) {
			if ((a.getIni() < hora && a.getFin() > hora)						// Si el socio está apuntado a una actividad a esa hora
					|| (larga && a.getIni() < hora && a.getFin() > hora + 1));
			System.err.println("No se ha realizado la reserva, "				// Avisar que no se ha realizado
					+ "este socio ya está apuntado a una actividad a esta hora");
			return false;
		}
		
		ReservaInstalacion reserva = new ReservaInstalacion(idSocio, fecha, hora, nombre);
		reservas.add(reserva);
		Db.dbInsertarReserva(reserva);
		if (larga) {
			ReservaInstalacion tmp = new ReservaInstalacion(reserva);
			reservas.add(tmp);
			Db.dbInsertarReserva(tmp);
		}
		return true;
	}
	
	@Override
	public String toString() {
		String res = nombre + " - ";
		if (recursos == null) {
			res += "Sin recurso asignado";
		} else {
			res += recursos.toString();
		} return res;
	}
	
}
