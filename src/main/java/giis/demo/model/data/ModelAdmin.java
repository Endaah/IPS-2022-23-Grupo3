package giis.demo.model.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Scanner;

import org.hsqldb.types.Types;


public class ModelAdmin {
	public static final String url = "jdbc:hsqldb:hsql://localhost:9002/labdb";
	public static final String user = "SA";
	public static final String password = "";
	
	
	public void insertActivity(int a_id, String ta_nombre, Date a_dia, int a_ini, int a_fin, int plazas) {
		try {
			Connection c = getConnection();
			
			String query = "INSERT INTO actividades (a_id, TA_NOMBRE, A_DIA, A_INI, A_FIN, A_PLAZAS) "
					+ "VALUES (a_id,ta_nombre,a_dia,a_ini,a_fin,plazas)";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    	
		    
		    pst.executeQuery();
		    
		    
		    
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
	

