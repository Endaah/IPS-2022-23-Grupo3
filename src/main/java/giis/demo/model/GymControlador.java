package giis.demo.model;

import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Db;

public class GymControlador {

	private List<Recurso> recursosDisponibles;
	private List<TipoActividad> tiposActividadDisponibles;
	private List<Instalacion> instalacionesDisponibles;
	private List<Actividad> actividadesDisponibles;
	
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
	
	public void addTipoActividad(List<Recurso> r, String nombre, String intensidad, String instalacion) {
		TipoActividad t = new TipoActividad(r, nombre, intensidad, instalacion);
		this.tiposActividadDisponibles.add(t);
		guardarTipoActividad(t);
	}
	
	public List<Instalacion> getInstalacionesDisponibles() {
		List<Instalacion> tmp = new ArrayList<Instalacion>();
		for (Instalacion i : instalacionesDisponibles) {
			tmp.add(i);
		}
		return tmp;
	}
	
	public void addInstalacion(Instalacion r) {
		this.instalacionesDisponibles.add(r);
	}
	
	public List<Actividad> getActividadesDisponibles() {
		List<Actividad> tmp = new ArrayList<Actividad>();
		for (Actividad ta : actividadesDisponibles) {
			tmp.add(ta);
		}
		return tmp;
	}
	
	public void addActividad(int id, String nombre, java.sql.Date fecha, int hini, int hfin, int plazas) {
		Actividad t = new Actividad(id, nombre, fecha, hini, hfin, plazas);
		this.actividadesDisponibles.add(t);
		guardarActividad(t);
	}
	
	// ============ CARGAR DATOS DESDE LA DB ================
	public void cargarRecursos() {
		recursosDisponibles = Db.cargarRecursos();
	}
	
	public void cargarTiposDeActividad() {
		tiposActividadDisponibles = Db.cargarTiposDeActividad();
	}
	
	public void cargarInstalaciones() {
		instalacionesDisponibles = Db.cargarInstalaciones();
	}
	
	public void cargarActividades() {
		actividadesDisponibles = Db.cargarActividades();
	}
	
	// ============ INTRODUCIR DATOS A LA DB ================
	private void guardarTipoActividad(TipoActividad ta) {
		Db.dbInsertarTA(ta);
	}
	
	private void guardarActividad(Actividad a) {
		Db.dbInsertarAct(a);
	}
}
