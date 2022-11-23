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
	 * Devuelve los recursos que tiene esta instalacion
	 * @param nombreInstalacion
	 * @return
	 */
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
				nombre = rs.getString("RC_NOMBRE");
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

	/**
	 * Devuelve todos los recursos que existen
	 * @return
	 */
	public List<Recurso> getTodosLosRecursos() {
		List<Recurso> recursos = new ArrayList<>();
		
		try {
			Connection c = getConnection();
			
			String query = "SELECT * "
					+ "FROM Recurso ";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    String nombre;
		    while(rs.next()) {
				nombre = rs.getString("RC_NOMBRE");
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

	/**
	 * Elimina una relacion entre un recurso y una instalacion
	 * @param nombreRecurso
	 * @param nombreInstalacion
	 */
	public void eliminarRecursoDeInstalacion(String nombreRecurso, String nombreInstalacion) {
		try {
			Connection c = getConnection();
			
			String query = "DELETE FROM Tiene WHERE RC_NOMBRE = ? and I_NOMBRE = ?";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setString(1, nombreRecurso);
		    pst.setString(2, nombreInstalacion);
		    
		    int res = pst.executeUpdate();
		    
		    if (res == 1) {
				System.out.println("Recursos borrados correctamente");
			}
			else {
				System.out.println("ERROR borrando el recurso");
			}
		    
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error obteniendo los recursos");
		}
	}
	
	/**
	 * Añade una relacion entre un recurso y una instalacion
	 * @param nombreRecurso
	 * @param nombreInstalacion
	 */
	public void AñadirRecursoAInstalacion(String nombreRecurso, String nombreInstalacion, int cantidad) {
		try {
			Connection c = getConnection();
			
			String query = "INSERT INTO TIENE VALUES(?, ?, ?)";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setString(1, nombreRecurso);
		    pst.setString(2, nombreInstalacion);
		    pst.setInt(3, cantidad);
		    
		    int res = pst.executeUpdate();
		    
		    if (res == 1) {
				System.out.println("Recurso añadido correctamente");
			}
			else {
				System.out.println("ERROR añadiendo el recurso");
			}
		    
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error obteniendo los recursos");
		}
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
