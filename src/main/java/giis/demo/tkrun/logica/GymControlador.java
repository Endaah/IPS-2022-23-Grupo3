package giis.demo.tkrun.logica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
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
		testDb(); //TODO: QUITAR TESTS
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
		printAct(); // TODO: QUITAR
	}
	
	// TODO: BORRAR TESTS
	private void testDb() {
		System.out.println("---- CARGA DESDE BD ----");
		String testQuery = "SELECT * FROM TIPOACTIVIDAD";
		String testQueryU = "SELECT * FROM UTILIZA";
		ResultSet rsTA = Db.sqlExecuteSimple(testQuery);
		ResultSet rsU = Db.sqlExecuteSimple(testQueryU);
		try {
			System.out.println("=== TABLA UTILIZA ===");
			while (rsU.next()) {
				System.out.println(rsU.getString(1) + " - " + rsU.getString(2));
			}
			System.out.println("=== TABLA TIPOACTIVIDAD ===");
			while (rsTA.next()) {
				System.out.println(rsTA.getString(1) + " - " + rsTA.getString(2) + " - " + rsTA.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO: BORRAR
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
	
	public void cargarInstalaciones() {
		instalacionesDisponibles = Db.cargarInstalaciones();
	}
	
	public void cargarActividades() {
		actividadesDisponibles = Db.cargarActividades();
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
}
