package giis.demo.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import giis.demo.util.Db;

public class Instalacion {

	public static final int CANCELADA = 1;
	public static final int VALIDA = 0;
	
	private String nombre;
	private int precioPorHora;
	private List<Recurso> recursos;
	private List<GrupoReservas> reservas;
	
	private HashMap<String, Integer> cantidades;
	
	public Instalacion(String nombre, int precio, List<Recurso> recurso, List<GrupoReservas> reservas) {
		this.nombre = nombre;
		this.precioPorHora = precio;
		this.recursos = recurso;
		this.reservas = reservas;
		
		this.cantidades = new HashMap<>();
		for (Recurso r : recursos) {
			cantidades.put(r.getNombre(), Db.getNumeroRecursos(r.getNombre(), nombre));
		}
		
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Recurso[] getRecurso() {
		return recursos.toArray(new Recurso[recursos.size()]);
	}
	
	public GrupoReservas[] getReservas() {
		return reservas.toArray(new GrupoReservas[reservas.size()]);
	}
	
	public HashMap<String, Integer> getCantidades() {
		return new HashMap<>(cantidades);
	}
	
	public String validarReserva(int idSocio, LocalDate fecha, int hora, boolean larga) {
		String motivo = "";
		if (fecha.getDayOfYear() - LocalDate.now().getDayOfYear() >= 8) {		// Si la reserva se ha hecho más de 7 días antes
			motivo += "Las resrvas se deben hacer como mucho 7 días antes\n";
		}
		
		for (GrupoReservas gr : reservas) {										// Por cada reserva
			for (ReservaInstalacion ri : gr.getReservas()) {
				if (ri.getAnulada() == 0) {
					if (ri.getFecha().equals(fecha)
							&& (ri.getHora() == hora
								|| (larga && ri.getHora() == hora + 1))) { 		// Si existe una reserva el mismo dia a la misma hora que lo que se ha intentado reservar
						motivo += "La instalación ya estaba reservada\n";
					}
				}
			}
		}
		
		for (GrupoReservas gr : Db.getReservasSocio(idSocio)) {
			for (ReservaInstalacion ri : gr.getReservas())
				if (ri.getFecha().equals(fecha)
						&& (ri.getHora() == hora
						|| (larga && (ri.getHora() == hora || ri.getHora() == hora + 1)))) {			// Si el socio ya tiene una reserva de instalación
					motivo += "Este socio ya tiene una reserva a esta hora\n";
				}
		}
		
		for (Actividad a : Db.getActividadesDe(idSocio)) {
			
			boolean overlaps = false;
			for (int i = 0; i < a.getFin() - a.getIni(); i++) {
				if (a.getDia().toLocalDate().equals(fecha)
						&& (a.getIni() + i == hora
						|| (larga && (a.getIni() + i == hora || a.getIni() + i == hora + 1)))) {	// Si el socio está apuntado a una actividad a esa hora
					overlaps = true;
				}
			}
			
			if (overlaps) {
				motivo += "Este socio ya está apuntado a una actividad a esta hora\n";
			}
		}
		
		return motivo;
	}
	
	public String reservar(int idSocio, LocalDate fecha, int hora, boolean larga) {
		String motivo = validarReserva(idSocio, fecha, hora, larga);
		if (!motivo.isBlank()) return motivo;
		
		int idReserva = 0;
		for (GrupoReservas gr : reservas)
			if (gr.getIdSocio() == idSocio)
				idReserva++;
		GrupoReservas gr = new GrupoReservas(idReserva, idSocio, precioPorHora);
		ReservaInstalacion reserva = new ReservaInstalacion(idSocio, fecha, hora, nombre, 0, idReserva);
		gr.addReserva(reserva);
		if (larga) {
			gr.addReserva(new ReservaInstalacion(reserva));
		}
		reservas.add(gr);
		Db.dbInsertarReserva(gr);
		return "";
	}
	
	public String reservar(Actividad act) {
		int idReserva = 0;
		for (GrupoReservas gr : reservas)
			if (gr.getIdSocio() == 0)
				idReserva++;
		GrupoReservas gr = new GrupoReservas(idReserva, 0, precioPorHora);
		for (int i = act.getIni(); i < act.getFin(); i++) {
			ReservaInstalacion reserva = new ReservaInstalacion(0, act.getDia().toLocalDate(), i, nombre, 0, idReserva);
			gr.addReserva(reserva);
		}
		reservas.add(gr);
		return "";
	}
	
	public void anular(GrupoReservas gr) {
		if (gr.getReservas()[0].getFecha().getDayOfYear() - LocalDate.now().getDayOfYear() <= 0) {
			System.err.println("No se ha podido anular la reserva, "
					+ "no se puede anular una reserva para el mismo día");
			return;
		}
		
		reservas.remove(gr);
		for (ReservaInstalacion rI : gr.getReservas()) {
			if (rI != null)
				Db.dbAnularReserva(Date.valueOf(rI.getFecha()), rI.getHora(), nombre);
		}
		
	}
	
	public void anular(Actividad act) {
		if ((act.getDia().toLocalDate().getDayOfMonth() < LocalDate.now().getDayOfMonth()
				&& act.getDia().toLocalDate().getMonthValue() == LocalDate.now().getMonthValue())
				|| act.getDia().toLocalDate().getMonthValue() < LocalDate.now().getMonthValue()) {
			System.err.println("No se ha podido anular la actividad, "
					+ "no se puede anular una reserva pasada");
			return;
		}
		
		for (GrupoReservas gr : reservas) {
			if (gr.getIdSocio() == 0)
				for (ReservaInstalacion rI : gr.getReservas()) {
					if (act.getDia().toLocalDate().equals(rI.getFecha())
							&& (act.getIni() <= rI.getHora() && act.getFin() >= rI.getHora()))
						rI.anular();
				}
		}
	}
	
	public boolean comprobarReserva(LocalDate fecha, int hora) {
		if (fecha.getDayOfYear() - LocalDate.now().getDayOfYear() >= 8) {		// Si la reserva se ha hecho más de 7 días antes
			System.err.println("No se ha realizado la reserva, "				// Avisar de que no se ha realizado
					+ "las resrvas se deben hacer como mucho 7 días antes");
			return false;
		}
		
		for (GrupoReservas gr : reservas)
			for (ReservaInstalacion ri : gr.getReservas()) {						// Por cada reserva
				if (ri.getFecha().equals(fecha)
						&& (ri.getHora() == hora
							|| ( ri.getHora() == hora + 1))) { 		// Si existe una reserva el mismo dia a la misma hora que lo que se ha intentado reservar
					System.err.println("No se ha realizado la reserva, "	// Avisar que no se ha realizado
							+ "la instalación ya estaba reservada");
					return false;
				}
			}
		
		return true;
	}
	
	public boolean reservarEmpresa(int idSocio, LocalDate fecha, int hora,boolean larga) {
		for (GrupoReservas gr : reservas) {
			for (ReservaInstalacion ri : gr.getReservas()) {						// Por cada reserva
				if (ri.getFecha().equals(fecha)
						&& (ri.getHora() == hora
							|| (larga ))) { 		// Si existe una reserva el mismo dia a la misma hora que lo que se ha intentado reservar
					System.err.println("No se ha realizado la reserva, "	// Avisar que no se ha realizado
							+ "la instalación ya estaba reservada");
					return false;
				}
			}
		}
		
		int idReserva = reservas.get(reservas.size() - 1).getIdReserva() + 1;
		GrupoReservas gr = new GrupoReservas(idReserva, idSocio, precioPorHora);
		ReservaInstalacion reserva = new ReservaInstalacion(idSocio, fecha, hora, nombre, 0, idReserva);
		if (larga) {
			for(int i = 8 ; i < 23; i++) {
					gr.addReserva(reserva);
					reserva = new ReservaInstalacion(reserva);
			}
		} else
			gr.addReserva(reserva);
		reservas.add(gr);
		Db.dbInsertarReserva(gr);
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
