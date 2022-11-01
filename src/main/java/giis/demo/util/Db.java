package giis.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.Recurso;
import giis.demo.model.ReservaInstalacion;
import giis.demo.model.Socio;
import giis.demo.model.TipoActividad;

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
	
	// ============= EXTRAER DATOS DE LA BD =============
	/**
	 * Ejecuta una query SQL simple, sin parámetros
	 * @param query Query SQL a ejecutar
	 * @return ResultSet con los elementos buscados
	 */
	private static ResultSet sqlExecute(String query) {
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
	 * Ejecuta una query SQL con parámetros de tipo String
	 * @param query Query SQL a ejecutar
	 * @param params Lista de strings que serán los parámetros de la query
	 * @return ResultSet con los elementos buscados
	 */
	private static ResultSet sqlExecute(String query, List<Object> params) {
		connect();
		ResultSet rs = null;
		try {
			PreparedStatement st = con.prepareStatement(query);
			for (int i = 1; i <= params.size(); i++) {
				if (params.get(i - 1) instanceof String)
					st.setString(i, (String) params.get(i - 1));
				else if (params.get(i - 1) instanceof Integer)
					st.setInt(i, (int) params.get(i - 1));
			}
			rs = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} return rs;
	}
	
	public static List<Socio> getSocios() {
		String query = "SELECT * FROM SOCIO";
		ResultSet rs = sqlExecute(query);
		List<Socio> socios = new ArrayList<Socio>();
		try {
			while(rs.next()) {
				socios.add(new Socio(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return socios;
	}
	
	/**
	 * Devuelve las actividades a las que está apuntado el socio dado
	 * @param idSocio Socio a consultar
	 * @return Lista de las actividades a las que está apuntado el socio
	 */
	public static List<Actividad> getActividadesDe(int idSocio) {
		String query = "SELECT A_ID FROM SEAPUNTA "
				+ "WHERE S_ID = ?";
		
		ResultSet rs = sqlExecute(query, Arrays.asList(idSocio));
		List<Actividad> actividades = new ArrayList<Actividad>();
		try {
			while(rs.next()) {
				for (Actividad a : GymControlador.getActividadesDisponibles()) {
					if (a.getId() == rs.getInt(1)) {
						actividades.add(a);
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return actividades;
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
					st.setDate(i + 1, new java.sql.Date(((java.util.Date) params.get(i)).getTime()));
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
		String query = "INSERT INTO \"TIPOACTIVIDAD\" VALUES(?, ?)";
		params = Arrays.asList(ta.getNombre(), ta.getIntensidad().toString().toLowerCase());
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
	
	/**
	 * Inserta una reserva de instalación a la base de datos, en la tabla Reserva
	 * @param r Reserva a insertar en la bd
	 */
	public static void dbInsertarReserva(ReservaInstalacion r) {
		String query = "INSERT INTO \"RESERVA\" VALUES (?, ?, ?, ?, ?)";
		
		List<Object> params = Arrays.asList(r.getIdSocio(), r.getInstalacion(), Date.valueOf(r.getFecha()), r.getHora(), 0);
		sqlInsertParam(query, params);
	}
	
	// ============ CARGA DE TABLAS A MEMORIA ==============
	
	/**
	 * Carga los recursos desde la base de datos a listas en memoria
	 * @return Lista con todas las instalaciones de la bd
	 */
	public static HashMap<String, Recurso> cargarRecursos() {
		String query = "SELECT * FROM RECURSO";
		
		HashMap<String, Recurso> recursos = new HashMap<String, Recurso>();
		ResultSet rs = sqlExecute(query);
		try {
			while (rs.next()) {
				recursos.put(rs.getString(1), new Recurso(rs.getString(1), rs.getInt(2)));
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
	public static HashMap<String, Instalacion> cargarInstalaciones() {
		String queryI = "SELECT * FROM INSTALACION";
		String queryRC = "SELECT RC_NOMBRE "
				+ "FROM TIENE "
				+ "WHERE I_NOMBRE = ?";
		String queryRes = "SELECT * FROM RESERVA "
				+ "WHERE I_NOMBRE = ?";
		String queryAct = "SELECT A_DIA, A_INI, A_FIN FROM ACTIVIDAD "
				+ "WHERE I_NOMBRE = ?";
		
		HashMap<String, Instalacion> instalaciones = new HashMap<String, Instalacion>();
		ResultSet rsI = sqlExecute(queryI);
		ResultSet rsRC = null;
		ResultSet rsRes = null;
		ResultSet rsAct = null;
		try {
			while (rsI.next()) {
				rsRC = sqlExecute(queryRC, Arrays.asList(rsI.getString(1)));
				List<Recurso> tmp = new ArrayList<Recurso>();
				while (rsRC.next()) {
					Recurso r = GymControlador.getRecursosDisponibles().get(rsRC.getString(1));
					if (r != null)
						tmp.add(r);
				}
				rsRes = sqlExecute(queryRes, Arrays.asList(rsI.getString(1)));
				List<ReservaInstalacion> reservas = new ArrayList<ReservaInstalacion>();
				while (rsRes.next()) {
					ReservaInstalacion rI = new ReservaInstalacion(rsRes.getInt(1), rsRes.getDate(3).toLocalDate(), rsRes.getInt(4), rsI.getString(1));
					reservas.add(rI);
				}
				rsAct = sqlExecute(queryAct, Arrays.asList(rsI.getString(1)));
				while(rsAct.next()) {
					for (int i = 0; i < rsAct.getInt(3) - rsAct.getInt(2); i++) {
						ReservaInstalacion rI = new ReservaInstalacion(0, rsAct.getDate(1).toLocalDate(), rsAct.getInt(2) + i, rsI.getString(1));
						reservas.add(rI);
					}
				}
				instalaciones.put(rsI.getString(1), new Instalacion(rsI.getString(1), tmp, reservas));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rsI.close();} catch (SQLException e) {e.printStackTrace();}
			try {rsRC.close();} catch (SQLException e) {e.printStackTrace();}
			try {rsRes.close();} catch (SQLException e) {e.printStackTrace();}
			try {rsAct.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return instalaciones;
	}
	
	/**
	 * Carga los tipos de actividad desde la base de datos a listas en memoria
	 * @return Lista con todos los tipos de actividad de la bd
	 */
	public static HashMap<String, TipoActividad> cargarTiposDeActividad() {
		String queryTA = "SELECT * FROM TIPOACTIVIDAD";
		String queryRC = "SELECT RC_NOMBRE "
				+ "FROM UTILIZA "
				+ "WHERE TA_NOMBRE = ?";
		
		HashMap<String, TipoActividad> tiposActividad = new HashMap<String, TipoActividad>();
		ResultSet rsTA = sqlExecute(queryTA);
		ResultSet rsRC = null;
		try {
			while (rsTA.next()) {
				rsRC = sqlExecute(queryRC, Arrays.asList(rsTA.getString(1)));
				List<Recurso> rcUsados = new ArrayList<Recurso>();
				while (rsRC.next())
				{
					rcUsados.add(GymControlador.getRecursosDisponibles().get(rsRC.getString(1)));
				}
				tiposActividad.put(rsTA.getString(1), new TipoActividad(new ArrayList<>(rcUsados), rsTA.getString(1), rsTA.getString(2)));
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
		ResultSet rs = sqlExecute(query);
		try {
			while (rs.next()) {
				actividades.add(new Actividad(rs.getInt(1), rs.getString(2),rs.getDate(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),GymControlador.getInstalacionesDisponibles().get(rs.getString(7))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return actividades;
	}
	
	// =========== CIERRE =============
	public static void shutdown() {
		/* TODO: descomentar cuando esté listo 
		try {
			if (con != null) {
				Statement st = con.createStatement();
				st.execute("SHUTDOWN");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
}
