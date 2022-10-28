package giis.demo.model;

import java.util.HashMap;
import java.util.List;

import giis.demo.util.Db;

public class GymControlador {
	
	private static HashMap<String, Recurso> recursosDisponibles;
	private static HashMap<String, TipoActividad> tiposActividadDisponibles;
	private static HashMap<String, Instalacion> instalacionesDisponibles;
	private static HashMap<Integer, Actividad> actividadesDisponibles;
	
	public static HashMap<String, Recurso> getRecursosDisponibles(){
		return recursosDisponibles;
	}
	
	public static void addRecurso(Recurso r) {
		recursosDisponibles.put(r.getNombre(), r);
	}
	
	public static HashMap<String, TipoActividad> getTiposActividadDisponibles() {
		return tiposActividadDisponibles;
	}
	
	public static void addTipoActividad(List<Recurso> r, String nombre, String intensidad) {
		TipoActividad t = new TipoActividad(r, nombre, intensidad);
		tiposActividadDisponibles.put(nombre, t);
		guardarTipoActividad(t);
	}
	
	public static HashMap<String, Instalacion> getInstalacionesDisponibles() {
		return instalacionesDisponibles;
	}
	
	public static void addInstalacion(Instalacion i) {
		instalacionesDisponibles.put(i.getNombre(), i);
	}
	
	public static HashMap<Integer, Actividad> getActividadesDisponibles() {
		return actividadesDisponibles;
	}
	
	public static void addActividad(int id, String nombre, java.sql.Date fecha, int hini, int hfin, int plazas, String nombreInstalacion) {
		Actividad t = new Actividad(id, nombre, fecha, hini, hfin, plazas, GymControlador.getInstalacionesDisponibles().get(nombreInstalacion));
		actividadesDisponibles.put(id, t);
		guardarActividad(t);
	}
	
	// ============ CARGAR DATOS DESDE LA DB ================
	public static void cargarRecursos() {
		recursosDisponibles = Db.cargarRecursos();
	}
	
	public static void cargarTiposDeActividad() {
		tiposActividadDisponibles = Db.cargarTiposDeActividad();
	}
	
	public static void cargarInstalaciones() {
		instalacionesDisponibles = Db.cargarInstalaciones();
	}
	
	public static void cargarActividades() {
		actividadesDisponibles = Db.cargarActividades();
	}
	
	// ============ INTRODUCIR DATOS A LA DB ================
	private static void guardarTipoActividad(TipoActividad ta) {
		Db.dbInsertarTA(ta);
	}
	
	private static void guardarActividad(Actividad a) {
		Db.dbInsertarAct(a);
	}
}
