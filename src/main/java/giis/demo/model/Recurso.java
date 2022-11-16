package giis.demo.model;

public class Recurso {

	private String nombre;
	
	public Recurso(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return "Recurso: " + nombre;
	}
}

