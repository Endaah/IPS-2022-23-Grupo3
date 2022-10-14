package giis.demo.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ModelSocio {
	
	public static final String url = "jdbc:hsqldb:hsql://localhost:9002/labdb";
	public static final String user = "SA";
	public static final String password = "";
	
	public List<Activity> getListActivitiesFor(Date date) {
		List<Activity> activities = new ArrayList<>(); 
		
		try {
			Connection c = getConnection();
			
			String query = "SELECT a_id, TA_NOMBRE, A_DIA, A_INI, A_FIN, A_PLAZAS "
					+ "FROM actividad WHERE A_DIA = ? ORDER BY A_INI";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setDate(1, date);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    int id;
		    String nombre;
		    Date dia;
		    int ini;
		    int fin;
		    int plazas;
		    while(rs.next()) {
		    	id = rs.getInt(1);
				nombre = rs.getString(2);
				dia = rs.getDate(3);
				ini = rs.getInt(4);
				fin = rs.getInt(5);
				plazas = rs.getInt(6);
				
				activities.add(new Activity(id, nombre, dia, ini, fin, plazas));
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return activities;
	}
	
	public boolean comprobarFecha(int day, int month, int year) {
		if(day <= 28) {
			return true;
		}
		if(day <= 30 && month != 2) {
			return true;
		}
		if (month != 4 && month != 6 && month != 9 && month != 11 && month != 2) {
			return true;
		}
		return false;
	}

	public Connection getConnection() {
		Connection c = null;
		try {
			DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("No se pudo crear la conexion");
			e.printStackTrace();
		}
		return c;
	}

}
