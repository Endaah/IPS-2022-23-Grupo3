package giis.demo.model;

public class Apunta {
	
	private Actividad actividad;
	private int id;
	
	public Apunta(int id, Actividad a) {
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
