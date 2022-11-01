package giis.demo.model;

public class Reserva {
	
	private Actividad actividad;
	private int id;
	public Reserva(int id, Actividad a) {
		this.id = id;
		actividad = a;
	}
	
	public int getId() {
		return id;
	}
	
	public Actividad getActividad() {
		return actividad;
	}
}
