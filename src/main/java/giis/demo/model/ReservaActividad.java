package giis.demo.model;

public class ReservaActividad {
	
	private Actividad actividad;
	private int id;
	public ReservaActividad(int id, Actividad a) {
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
