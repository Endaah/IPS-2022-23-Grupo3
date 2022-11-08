package giis.demo.model;

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
		this.setIntensidad(intensidad);
	}
	
	private void setIntensidad(String intensidad) {
		switch(intensidad) {
		case "alta":
			this.intensidad = Intensidad.ALTA;
			break;
		case "media":
			this.intensidad = Intensidad.MEDIA;
			break;
		case "baja":
			this.intensidad = Intensidad.BAJA;
			break;
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getIntensidad() {
		return intensidad.toString().toLowerCase();
	}
	
	public List<Recurso> getRecurso() {
		List<Recurso> tmp = new ArrayList<Recurso>();
		for (Recurso r : recursosUsados) {
			tmp.add(r);
		}
		return tmp;
	}
	
	public boolean usaRecurso() {
		return !recursosUsados.isEmpty();
	}
	
	@Override
	public String toString() {
		String res = nombre + " - " + intensidad;
		return res;
	}
}
