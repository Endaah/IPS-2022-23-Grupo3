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
	private String instalacion;
	
	public TipoActividad(List<Recurso> recursos, String nombre, String intensidad, String instalacion) {
		this.recursosUsados = recursos;
		this.nombre = nombre;
		this.setIntensidad(intensidad);
		this.instalacion = instalacion;
	}
	
	private void setIntensidad(String intensidad) {
		switch(intensidad) {
		case "Alta":
			this.intensidad = Intensidad.ALTA;
			break;
		case "Media":
			this.intensidad = Intensidad.MEDIA;
			break;
		case "Baja":
			this.intensidad = Intensidad.BAJA;
			break;
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getIntensidad() {
		return intensidad.toString();
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
	
	public String getInstalacion() {
		return instalacion;
	}
	
	@Override
	public String toString() {
		String res = nombre + " - " + intensidad + " - " + instalacion;
		return res;
	}
}
