package giis.demo.model;

import java.util.List;

public class Instalacion {

	private String nombre;
	private List<Recurso> recursos;
	
	public static final int CANCELADA = 1;
	public static final int VALIDA = 0;
	
	public Instalacion(String nombre, List<Recurso> recurso) {
		this.nombre = nombre;
		this.recursos = recurso;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Recurso[] getRecurso() {
		return recursos.toArray(new Recurso[recursos.size()]);
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
