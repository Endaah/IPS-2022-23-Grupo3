package giis.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import giis.demo.tkrun.Main;
import giis.demo.tkrun.logica.Actividad;
import giis.demo.tkrun.logica.Instalacion;
import giis.demo.tkrun.logica.Recurso;
import giis.demo.tkrun.logica.TipoActividad;

public class Db {

	private final static String URL = "jdbc:hsqldb:hsql://localhost:9002/labdb";
	private final static String USER = "SA";
	private final static String PWD = "";
	
	private static Connection con;
	
	public static void getDriver() {
		try {
			DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void connect() {
		if (con == null) try { con = DriverManager.getConnection(URL, USER, PWD); } catch (SQLException e) { e.printStackTrace(); };
	}
	
	/**
	 * Ejecuta una query SQL simple, sin parámetros
	 * @param query Query SQL a ejecutar
	 * @return ResultSet con los elementos buscados
	 */
	private static ResultSet sqlExecuteSimple(String query) {
		connect();
		ResultSet rs = null;
		try {
			PreparedStatement st = con.prepareStatement(query);
			rs = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} return rs;
	}

	/**
	 * Ejecuta una query SQL con parámetros
	 * @param query Query SQL a ejecutar
	 * @param params Lista de strings que serán los parámetros de la query
	 * @return ResultSet con los elementos buscados
	 */
	private static ResultSet sqlExecuteParam(String query, List<String> params) {
		connect();
		ResultSet rs = null;
		try {
			PreparedStatement st = con.prepareStatement(query);
			for (int i = 1; i <= params.size(); i++) {
				st.setString(i, params.get(i - 1));
			}
			rs = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} return rs;
	}
	
	// ============ INSERCIÓN DE DATOS ==============
	/*
	 * Método general para insertar en la base de datos.
	 * Se le pasa una query en SQL y los parámetros a rellenar, si no necesita parámetros pasarle null
	 */
	private static void sqlInsertParam(String query, List<Object> params) {
		connect();
		try {
			PreparedStatement st = con.prepareStatement(query);
			for (int i = 0; i < params.size(); i++) {
				if (params.get(i) instanceof String)
					st.setString(i + 1, (String) params.get(i));
				else if (params.get(i) instanceof Integer)
					st.setInt(i + 1, (Integer) params.get(i));
				else if (params.get(i) instanceof Date)
					st.setDate(i + 1, (java.sql.Date) params.get(i));
			}
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inserta un tipo de actividad a la base de datos, en la tabla TipoActividad
	 * @param ta Tipo de Actividad a insertar en la bd
	 */
	public static void dbInsertarTA(TipoActividad ta) {
		List<Recurso> rcUsados = ta.getRecurso();
		List<Object> params;
		String query = "INSERT INTO \"TIPOACTIVIDAD\" VALUES(?, ?, ?)";
		params = Arrays.asList(ta.getNombre(), ta.getIntensidad().toString().toLowerCase(), ta.getInstalacion());
		Db.sqlInsertParam(query, params);
		query = "INSERT INTO \"UTILIZA\" VALUES(?, ?)";
		for (Recurso r : rcUsados) {
			params = Arrays.asList(r.getNombre(), ta.getNombre());
			Db.sqlInsertParam(query, params);
		}
	}
	
	/**
	 * Inserta una actividad a la base de datos, en la tabla Actividad
	 * @param act Actividad a insertar a la bd
	 */
	public static void dbInsertarAct(Actividad act) {
		String query = "INSERT INTO actividad (a_id, TA_NOMBRE, A_DIA, A_INI, A_FIN, A_PLAZAS) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		List<Object> params = Arrays.asList(act.getId(), act.getNombre(), act.getDia(), act.getIni(), act.getFin(), act.getPlazas());
		sqlInsertParam(query, params);
		
	}
	
	// ============ CARGA DE TABLAS A MEMORIA ==============
	
	/**
	 * Carga los recursos desde la base de datos a listas en memoria
	 * @return Lista con todas las instalaciones de la bd
	 */
	public static List<Recurso> cargarRecursos() {
		String query = "SELECT * FROM RECURSO";
		
		List<Recurso> recursos = new ArrayList<Recurso>();
		ResultSet rs = sqlExecuteSimple(query);
		try {
			while (rs.next()) {
				recursos.add(new Recurso(rs.getString(1), rs.getInt(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return recursos;
	}
	
	/**
	 * Carga las instalaciones desde la base de datos a listas en memoria
	 * @return Lista con todas las instalaciones de la bd
	 */
	public static List<Instalacion> cargarInstalaciones() {
		String query = "SELECT * FROM INSTALACION";
		
		List<Instalacion> instalaciones = new ArrayList<Instalacion>();
		ResultSet rs = sqlExecuteSimple(query);
		try {
			while (rs.next()) {
				Recurso tmp = null;
				if (rs.getObject(2) != null) {
					for (Recurso r : Main.getInstanceControlador().getRecursosDisponibles()) {
						if (r.getNombre().equals(rs.getString(2))) {
							tmp = r;
							break;
						}
					}
				}
				instalaciones.add(new Instalacion(rs.getString(1), tmp ));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return instalaciones;
	}
	
	/**
	 * Carga los tipos de actividad desde la base de datos a listas en memoria
	 * @return Lista con todos los tipos de actividad de la bd
	 */
	public static List<TipoActividad> cargarTiposDeActividad() {
		String queryTA = "SELECT * FROM TIPOACTIVIDAD";
		String queryRC = "SELECT RECURSO.RC_NOMBRE, RECURSO.RC_CANTIDAD "
				+ "FROM RECURSO "
				+ "JOIN UTILIZA ON RECURSO.RC_NOMBRE = UTILIZA.RC_NOMBRE "
				+ "JOIN TIPOACTIVIDAD ON UTILIZA.TA_NOMBRE = TIPOACTIVIDAD.TA_NOMBRE "
				+ "WHERE TA_NOMBRE = ?";
		
		List<TipoActividad> tiposActividad = new ArrayList<TipoActividad>();
		ResultSet rsTA = sqlExecuteSimple(queryTA);
		ResultSet rsRC = null;
		try {
			while (rsTA.next()) {
				rsRC = sqlExecuteParam(queryRC, Arrays.asList(rsTA.getString(1)));
				ArrayList<Recurso> rcUsados = new ArrayList<Recurso>();
				while (rsRC.next())
				{
					Recurso tmp = new Recurso(rsRC.getString(1), rsRC.getInt(2));
					rcUsados.add(tmp);
				}
				tiposActividad.add(new TipoActividad(new ArrayList<>(rcUsados), rsTA.getString(1), rsTA.getString(2), rsTA.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rsTA.close();} catch (SQLException e) {e.printStackTrace();}
			try {rsRC.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return tiposActividad;
	}
	
	/**
	 * Carga las actividades desde la base de datos a listas en memoria
	 * @return Lista con todas las actividades de la bd
	 */
	public static List<Actividad> cargarActividades() {
		String query = "SELECT * FROM ACTIVIDAD";
		
		List<Actividad> actividades = new ArrayList<Actividad>();
		ResultSet rs = sqlExecuteSimple(query);
		try {
			while (rs.next()) {
				actividades.add(new Actividad(rs.getInt(1), rs.getString(2),rs.getDate(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return actividades;
	}
	
	// =========== RESET ============ TODO: QUITAR
	public static void dbClear() {
		try {
			connect();
			
			Statement st = con.createStatement();
			st.execute("DELETE FROM TIPOACTIVIDAD WHERE TA_NOMBRE = ''");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// =========== CIERRE =============
	
	public static void shutdown() {
		try {
			if (con != null) {
				Statement st = con.createStatement();
				//st.execute("SHUTDOWN"); TODO: descomentar
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
