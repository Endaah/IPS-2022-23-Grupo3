package giis.demo.tkrun.logica;

import java.util.ArrayList;
import java.util.List;

public class GymControlador {

	private List<Recurso> recursosDisponibles = new ArrayList<Recurso>();
	private List<TipoActividad> tiposActividadDisponibles = new ArrayList<TipoActividad>();
	
	public Recurso[] getRecursosDisponibles(){
		return (Recurso[]) this.recursosDisponibles.toArray();
	}
	
	public void addRecurso(Recurso r) {
		this.recursosDisponibles.add(r);
	}
	
	public TipoActividad[] getTiposActividadDisponibles() {
		return (TipoActividad[]) this.tiposActividadDisponibles.toArray();
	}
	
	public void addTipoActividad(List<Recurso> r, String nombre, String intensidad, String instalacion) {
		TipoActividad t = new TipoActividad(r, nombre, intensidad, instalacion);
		System.out.println(t.toString());
		this.tiposActividadDisponibles.add(t);
	}
	
	public void printAct() {
		for (TipoActividad t : tiposActividadDisponibles) {
			System.out.println(t.toString());
		}
	}
}
