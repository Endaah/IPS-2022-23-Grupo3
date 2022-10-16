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
	// TODO: CAMBIAR DE VUELTA A PRIVATE
	public static ResultSet sqlExecuteSimple(String query) {
		connect();
		ResultSet rs = null;
		try {
			PreparedStatement st = con.prepareStatement(query);
			rs = st.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} return rs;
	}

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
	public static void sqlInsertParam(String query, List<Object> params) {
		connect();
		try {
			PreparedStatement st = con.prepareStatement(query);
			for (int i = 0; i < params.size(); i++) {
				if (params.get(i) instanceof String)
					st.setString(i + 1, (String) params.get(i));
				else if (params.get(i) instanceof Integer)
					st.setInt(i + 1, i);
			}
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ============ CARGA DE TABLAS A MEMORIA ==============
	
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
		}
		return recursos;
	}
	
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
		}
		return instalaciones;
	}
	
	public static List<TipoActividad> cargarTiposDeActividad() {
		String queryTA = "SELECT * FROM TIPOACTIVIDAD";
		String queryRC = "SELECT RECURSO.RC_NOMBRE, RECURSO.RC_CANTIDAD "
				+ "FROM RECURSO "
				+ "JOIN UTILIZA ON RECURSO.RC_NOMBRE = UTILIZA.RC_NOMBRE "
				+ "JOIN TIPOACTIVIDAD ON UTILIZA.TA_NOMBRE = TIPOACTIVIDAD.TA_NOMBRE "
				+ "WHERE TA_NOMBRE = ?";
		
		List<TipoActividad> tiposActividad = new ArrayList<TipoActividad>();
		ResultSet rsTA = sqlExecuteSimple(queryTA);
		try {
			while (rsTA.next()) {
				ResultSet rsRC = sqlExecuteParam(queryRC, Arrays.asList(rsTA.getString(1)));
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
		}
		return tiposActividad;
	}
	
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
		}
		return actividades;
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
