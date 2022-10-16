package giis.demo.tkrun.logica;

public class Instalacion {

	private String nombre;
	private Recurso recurso;
	
	public Instalacion(String nombre, Recurso recurso) {
		this.nombre = nombre;
		this.recurso = recurso;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Recurso getRecurso() {
		return recurso;
	}
	
	@Override
	public String toString() {
		String res = nombre + " - ";
		if (recurso == null) {
			res += "Sin recurso asignado";
		} else {
			res += recurso.toString();
		} return res;
	}
	
}
