package giis.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import giis.demo.util.Db;

public class GymControlador {

	private List<Recurso> recursosDisponibles;
	private List<TipoActividad> tiposActividadDisponibles;
	private List<Actividad> ActividadesDisponibles;
	
	public List<Recurso> getRecursosDisponibles(){
		List<Recurso> tmp = new ArrayList<Recurso>();
		for (Recurso r : recursosDisponibles) {
			tmp.add(r);
		}
		return tmp;
	}
	
	public void addRecurso(Recurso r) {
		this.recursosDisponibles.add(r);
	}
	
	public List<TipoActividad> getTiposActividadDisponibles() {
		List<TipoActividad> tmp = new ArrayList<TipoActividad>();
		for (TipoActividad ta : tiposActividadDisponibles) {
			tmp.add(ta);
		}
		return tmp;
	}
	
	public List<Actividad> getActividadesDisponibles() {
		List<Actividad> tmp = new ArrayList<Actividad>();
		for (Actividad ta : ActividadesDisponibles) {
			tmp.add(ta);
		}
		return tmp;
	}
	
	public void addTipoActividad(List<Recurso> r, String nombre, String intensidad, String instalacion) {
		TipoActividad t = new TipoActividad(r, nombre, intensidad, instalacion);
		this.tiposActividadDisponibles.add(t);
		printAct();
	}
	
	public void addActividad(int id, String nombre, java.sql.Date fecha, int hini, int hfin, int plazas) {
		Actividad t = new Actividad(id, nombre, fecha, hini, hfin, plazas);
		this.ActividadesDisponibles.add(t);
		printAct();
	}
	
	public void printAct() {
		for (TipoActividad t : tiposActividadDisponibles) {
			System.out.println(t.toString());
		}
	}
	
	// ============ CARGAR DATOS DESDE LA DB ================
	public void cargarRecursos() {
		recursosDisponibles = Db.cargarRecursos();
	}
	
	public void cargarTiposDeActividad() {
		tiposActividadDisponibles = Db.cargarTiposDeActividad();
	}
	
	public void cargarActividades() {
		ActividadesDisponibles = Db.cargarActividades();
	}
}
