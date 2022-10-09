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
	
	public Recurso[] getRecurso() {
		return (Recurso[]) recursosUsados.toArray();
	}
	
	public String getInstalacion() {
		return instalacion;
	}
	
	@Override
	public String toString() {
		String res = "";
		for (Recurso r : recursosUsados) {
			res = res + r.toString() + " - ";
		}
		res = res + nombre + " - " + intensidad + " - " + instalacion;
		return res;
	}
}
