package giis.demo.model;

import java.util.ArrayList;
import java.util.List;

public class GrupoReservas {

	private int idReserva;
	private int idSocio;
	private int precioReservas;
	private List<ReservaInstalacion> reservas;
	
	public GrupoReservas(int idReserva, int idSocio, int precioReservas) {
		this.idSocio = idSocio;
		this.idReserva = idReserva;
		this.precioReservas = precioReservas;
		this.reservas = new ArrayList<ReservaInstalacion>();
	}
	
	public void addReserva(ReservaInstalacion reserva) {
		reservas.add(reserva);
	}
	
	public ReservaInstalacion[] getReservas() {
		return reservas.toArray(new ReservaInstalacion[reservas.size()]);
	}
	
	public int getIdReserva() {
		return idReserva;
	}
	
	public int getIdSocio() {
		return idSocio;
	}
	
	public int getPrecio() {
		int precio = 0;
		for (ReservaInstalacion rI : reservas) {
			precio += precioReservas;
		} return precio;
	}
	
	@Override
	public String toString() {
		return reservas.get(0).toString() + " - " + reservas.size() + " horas";
	}
}
