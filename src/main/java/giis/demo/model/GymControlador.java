package giis.demo.model;

import java.util.ArrayList;
import java.util.List;

import giis.demo.util.Db;

public class GymControlador {
	
	private static List<Recurso> recursosDisponibles;
	private static List<TipoActividad> tiposActividadDisponibles;
	private static List<Instalacion> instalacionesDisponibles;
	private static List<Actividad> actividadesDisponibles;
	
	public static List<Recurso> getRecursosDisponibles(){
		List<Recurso> tmp = new ArrayList<Recurso>();
		for (Recurso r : recursosDisponibles) {
			tmp.add(r);
		}
		return tmp;
	}
	
	public static void addRecurso(Recurso r) {
		recursosDisponibles.add(r);
	}
	
	public static List<TipoActividad> getTiposActividadDisponibles() {
		List<TipoActividad> tmp = new ArrayList<TipoActividad>();
		for (TipoActividad ta : tiposActividadDisponibles) {
			tmp.add(ta);
		}
		return tmp;
	}
	
	public static void addTipoActividad(List<Recurso> r, String nombre, String intensidad, String instalacion) {
		TipoActividad t = new TipoActividad(r, nombre, intensidad, instalacion);
		tiposActividadDisponibles.add(t);
		guardarTipoActividad(t);
	}
	
	public static List<Instalacion> getInstalacionesDisponibles() {
		List<Instalacion> tmp = new ArrayList<Instalacion>();
		for (Instalacion i : instalacionesDisponibles) {
			tmp.add(i);
		}
		return tmp;
	}
	
	public static void addInstalacion(Instalacion r) {
		instalacionesDisponibles.add(r);
	}
	
	public static List<Actividad> getActividadesDisponibles() {
		List<Actividad> tmp = new ArrayList<Actividad>();
		for (Actividad ta : actividadesDisponibles) {
			tmp.add(ta);
		}
		return tmp;
	}
	
	public static void addActividad(int id, String nombre, java.sql.Date fecha, int hini, int hfin, int plazas) {
		Actividad t = new Actividad(id, nombre, fecha, hini, hfin, plazas);
		actividadesDisponibles.add(t);
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
