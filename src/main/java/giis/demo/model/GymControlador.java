package giis.demo.model;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import giis.demo.util.Db;

public class GymControlador {
	
	private static HashMap<String, Recurso> recursosDisponibles;
	private static HashMap<String, TipoActividad> tiposActividadDisponibles;
	private static HashMap<String, Instalacion> instalacionesDisponibles;
	private static List<Actividad> actividadesDisponibles;
	
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
	
	public static List<Actividad> getActividadesExistentes() {
		return actividadesDisponibles;
	}
	
	public static List<Actividad> getActividadesDisponibles() {
		List<Actividad> tmp = new ArrayList<Actividad>();
		for (Actividad act : actividadesDisponibles)
			if (act.getCancelada() == 0)
				tmp.add(act);
		return tmp;
	}
	
	public static void addActividad(int id, String nombre, java.sql.Date fecha, int hini, int hfin, int plazas, String nombreInstalacion) {
		Actividad t = new Actividad(id, nombre, fecha, hini, hfin, plazas, GymControlador.getInstalacionesDisponibles().get(nombreInstalacion), 0);
		actividadesDisponibles.add(t);
		instalacionesDisponibles.get(nombreInstalacion).reservar(t);
		guardarActividad(t);
	}
	
	public static void anularActividad(Actividad act) {
		for(Actividad a : actividadesDisponibles) {
			if (a.equals(act)) {
				a.anular();
				break;
			}
		}
		instalacionesDisponibles.get(act.getInstalacion().getNombre()).anular(act);
		Db.dbAnularActividad(act);
	}
	
	public static void addGrupoReserva(Instalacion i, Actividad act) {
		for (int hora = act.getIni(); hora < act.getFin(); hora++) {
			i.reservar(0, act.getDia().toLocalDate(), hora, false);
		}
	}
	
	public static List<Socio> buscarSocios(List<Socio> lista, String str) {
		if (str.isBlank()) return lista;
		
		List<Socio> res = new ArrayList<Socio>();
		for (Socio socio : lista) {
			boolean isId = true;
			for (char c : str.toCharArray())
				if (!Character.isDigit(c)) {
					isId = false;
					break;
				}
			
			if (isId && socio.getId() == Integer.parseInt(str))
				res.add(socio);
			else if (socio.getNombre().contains(str))
				res.add(socio);
		} return res;
	}
	/**
	 * Devuelve si la instalación está disponible en un momento determinado
	 * @param i Instalación a comprobar
	 * @param dia Día a comprobar
	 * @param inicio Hora inicial a comprobar
	 * @param fin Hora final a comprobar
	 * @return True si está disponible, False si no
	 */
	public static boolean comprobarDisponibilidad(Instalacion i, LocalDate dia, int inicio, int fin) {
		for (GrupoReservas gr : i.getReservas()) {
			for (ReservaInstalacion rI : gr.getReservas()) {
				if (rI.getFecha().equals(dia)
						&& (inicio <= rI.getHora() && rI.getHora() < fin))
					return false;
			}
		} return true;
	}
	
	public static String getSociosDe(Actividad act) {
		String socios = "";
		for (Socio s : Db.getSociosDe(act)) {
			socios += s.getNombre() + "\n" + s.getEmail() + "\n\n";
		}
		return socios;
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
