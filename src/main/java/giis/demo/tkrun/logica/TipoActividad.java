package giis.demo.tkrun.logica;

import java.util.ArrayList;
import java.util.List;

enum Intensidad {
	ALTA,
	MEDIA,
	BAJA
}

public class TipoActividad {

	private List<Recurso> recursosUsados = new ArrayList<Recurso>();
	private String nombre;
	private Intensidad intensidad;
	
	public TipoActividad(List<Recurso> recursos, String nombre, String intensidad) {
		this.recursosUsados = recursos;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getIntensidad() {
		return intensidad.toString();
	}
	
	public Recurso[] getRecurso() {
		return (Recurso[]) recursosUsados.toArray();
	}
}
