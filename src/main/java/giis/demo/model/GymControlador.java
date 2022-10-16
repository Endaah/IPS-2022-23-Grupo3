package giis.demo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import giis.demo.util.Db;

public class GymControlador {

	private List<Recurso> recursosDisponibles;
	private List<TipoActividad> tiposActividadDisponibles;
	private List<Actividad> ActividadesDisponibles = new ArrayList<Actividad>();
	
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
		printAct2();
	}
	
	public void printAct() {
		for (TipoActividad t : tiposActividadDisponibles) {
			System.out.println(t.toString());
		}
	}
	
	public void printAct2() {
		for (Actividad t : ActividadesDisponibles) {
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
	
	// ============ INTRODUCIR DATOS A LA DB ================
		private void guardarTipoActividad(TipoActividad ta) {
			List<Recurso> rcUsados = ta.getRecurso();
			String query = "INSERT INTO \"TIPOACTIVIDAD\" VALUES(?, ?, ?)" ;
			Db.sqlInsertParam(query, new ArrayList<Object>(Arrays.asList(ta.getNombre(), ta.getIntensidad().toString().toLowerCase(), ta.getInstalacion())));
			query = "INSERT INTO \"UTILIZA\" VALUES(?, ?)";
			for (Recurso r : rcUsados) {
				Db.sqlInsertParam(query, new ArrayList<Object>(Arrays.asList(r.getNombre(), ta.getNombre())));
			}
		}
		
		private void guardarActividad(Actividad ta) {
			String query = "INSERT INTO \"ACTIVIDAD\" VALUES (?, ?, ?)" ;
			Db.sqlInsertParam(query , new ArrayList<Object>(Arrays.asList(ta.getId(),ta.getNombre(),ta.getDia(),ta.getIni(),ta.getFin(),ta.getPlazas())));
			System.out.println(Db.cargarActividades());
		}
}
