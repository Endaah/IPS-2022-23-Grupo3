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
	
	public List<Actividad> getListActivitiesFor(Date date) {
		List<Actividad> activities = new ArrayList<>(); 
		
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
				
				activities.add(new Actividad(id, nombre, dia, ini, fin, plazas));
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error obteniendo las actividades");
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
	
	public boolean checkIsInt(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean existsIdSocio(int id) {
		try {
			Connection c = getConnection();
			
			String query = "SELECT s_id FROM socio WHERE s_id = ?";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setInt(1, id);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    while(rs.next()) {
		    	return true;
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error obteniendo los socios");
		}
		return false;
	}
	
	public void reservarActividad(int actId, int userId) {
		try {
			Connection c = getConnection();
			
			String query = "INSERT INTO RESERVA VALUES(?,?)";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setInt(1, userId);
		    pst.setInt(2, actId);
		    
		    int res = pst.executeUpdate();
		    
		    if (res == 1) {
				System.out.println("Datos insertados correctamente");
			}
			else {
				System.out.println("ERROR insertando los datos");
			}
		    
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error obteniendo las actividades");
		}
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



	public void eliminarReserva(int userId, int actId) {
		try {
			Connection c = getConnection();
			
			String query = "DELETE FROM RESERVA WHERE S_ID = ? AND A_ID = ?";
			
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setInt(1, userId);
		    pst.setInt(2, actId);
		    
		    int res = pst.executeUpdate();
		    
		    if (res == 1) {
				System.out.println("Datos insertados correctamente");
			}
			else {
				System.out.println("ERROR insertando los datos");
			}
		    
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error obteniendo las actividades");
		}
		
	}

}