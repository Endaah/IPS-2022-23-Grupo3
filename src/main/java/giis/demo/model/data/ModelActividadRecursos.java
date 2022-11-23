package giis.demo.model.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.model.Recurso;

public class ModelActividadRecursos {
	
	public static final String url = "jdbc:hsqldb:hsql://localhost:9002/labdb";
	public static final String user = "SA";
	public static final String password = "";
	
	/**
	 * Comprueba que los datos introducidos por el usuario son digitos
	 * @param s
	 * @return
	 */
	public boolean checkIsInt(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Metodo que pregunta al administrador el numero de recursos que tiene al añadir un recurso a una instalacion
	 * @return
	 */
	public int askForNumeroDeRecursos(){
		String input;
		do {
			input = JOptionPane.showInputDialog("Introduzca el numero de recursos del que dispondría (Número)");
		} while (input == null || input.isEmpty() || !checkIsInt(input) || input.trim().equals("0"));
		
		int result = Integer.parseInt(input);
		
		return result;
	}

	public List<Recurso> getRecursosTiene(String nombreInstalacion) {
		List<Recurso> recursos = new ArrayList<>();
		
		try {
			Connection c = getConnection();
			
			String query = "SELECT * "
					+ "FROM TIENE WHERE I_NOMBRE = ? ";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setString(1, nombreInstalacion);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    String nombre;
		    while(rs.next()) {
				nombre = rs.getString("I_NOMBRE");
				recursos.add(new Recurso(nombre));
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error obteniendo los recursos");
		}
		
		return recursos;
	}

	public List<Recurso> getRecursosNoTiene(String nombreInstalacion) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/**
	 * Proporciona una instancia de connection
	 * @return
	 */
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
