package giis.demo.model.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.igu.VentanaSocio;
import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;


public class ModelSocio {
	
	public static final String url = "jdbc:hsqldb:hsql://localhost:9002/labdb";
	public static final String user = "SA";
	public static final String password = "";
	
	public int askForIdSocio(){
		String input;
		do {
			input = JOptionPane.showInputDialog("Introduzca su ID de socio (Número)");
		} while (input == null || input.isEmpty() || !checkIsInt(input));
		
		int result = Integer.parseInt(input);
		
		if (!existsIdSocio(result)) {
			VentanaSocio.showMessage("No exixte ningun socio con id " + result,
					"Aviso - Socio no válido", JOptionPane.WARNING_MESSAGE);
			return askForIdSocio();
		}
		
		return result;
	}
	
	public List<Actividad> getListActivitiesFor(Date date) {
		List<Actividad> activities = new ArrayList<>(); 
		
		try {
			Connection c = getConnection();
			
			String query = "SELECT a_id, TA_NOMBRE, A_DIA, A_INI, A_FIN, A_PLAZAS, I_NOMBRE "
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
		    String instalacion;
		    while(rs.next()) {
		    	id = rs.getInt(1);
				nombre = rs.getString(2);
				dia = rs.getDate(3);
				ini = rs.getInt(4);
				fin = rs.getInt(5);
				plazas = rs.getInt(6);
				instalacion = rs.getString(7);
				activities.add(new Actividad(id, nombre, dia, ini, fin, plazas, GymControlador.getInstalacionesDisponibles().get(instalacion)));
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
	
	public boolean comprobarFechaCorrecta(int day, int month, int year) {
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
			
			String query = "INSERT INTO SEAPUNTA VALUES(?,?)";
			
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
			System.err.println("Error apuntandose a actividad");
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

  // TODO: Cambiar el nombre del metodo para evitar lios, por el cambio de nombre de la tabla
	public void eliminarReserva(int userId, int actId) {
		try {
			Connection c = getConnection();
			
			String query = "DELETE FROM SEAPUNTA WHERE S_ID = ? AND A_ID = ?";
			
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setInt(1, userId);
		    pst.setInt(2, actId);
		    
		    int res = pst.executeUpdate();
		    
		    if (res == 1) {
				System.out.println("Datos borrados correctamente");
			}
			else {
				System.out.println("ERROR borrando los datos");
			}
		    
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error obteniendo las actividades");
		}
		
	}
	
	public boolean hayPlazas(int actId) {
		try {
			Connection c = getConnection();
			
			String query = "SELECT * FROM ACTIVIDAD WHERE a_id = ? AND a_plazas > 0";
			
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setInt(1, actId);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    while(rs.next()) {
		    	return true;
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error restando plaza actividades");
		}
		return false;
	}

	//Resta una plaza a esa actividad
	public void restarPlaza(int actId) {
		try {
			Connection c = getConnection();
			
			String query = "UPDATE ACTIVIDAD SET a_plazas = a_plazas - 1 WHERE A_ID = ?";
			
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setInt(1, actId);
		    
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
			System.err.println("Error restando plaza actividades");
		}
	}

	public boolean checkPuedoApuntarme(Date dia, int ini) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date(System.currentTimeMillis()));
		
		Calendar act = Calendar.getInstance();
		act.setTime(dia);
		
		//Mirar si coincide fecha
		if (act.getTime().getYear() == now.getTime().getYear() && 
				act.getTime().getMonth() == now.getTime().getMonth() &&
				act.getTime().getDate() == now.getTime().getDate()) {
			//Return true o false segun la hora
			if (now.getTime().getHours() < ini - 1) {
				return true;
			}
			else {
				return false;
			}
		}
		
		act.add(act.DAY_OF_YEAR, -1);
		//Y luego el dia anterior vale tbn
		
		if (act.getTime().getYear() == now.getTime().getYear() && 
				act.getTime().getMonth() == now.getTime().getMonth() &&
				act.getTime().getDate() == now.getTime().getDate()) {
			return true;
		}
		
		return false;
	}

	public boolean checkSocioPuedeApuntarse(Date dia, int ini, int fin, int userId) {
		try {
			Connection c = getConnection();
			
			String query = "SELECT s_id FROM SEAPUNTA a, Actividad ac "
					+ "WHERE a.a_id = ac.a_id AND a.s_id = ?"
					+ "AND ac.a_dia = ?"
					+ "AND ac.a_ini <= ?"
					+ "AND ac.a_fin > ?";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setInt(1, userId);
		    pst.setDate(2, dia);
		    pst.setInt(3, ini);
		    pst.setInt(4, ini);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    while(rs.next()) {
		    	return false;
			}
		    
		    rs.close();
			pst.close();
			
			query = "SELECT s_id FROM SEAPUNTA a, Actividad ac "
					+ "WHERE a.a_id = ac.a_id AND a.s_id = ?"
					+ "AND ac.a_dia = ?"
					+ "AND ac.a_ini < ?"
					+ "AND ac.a_fin >= ?";
			
			PreparedStatement pst2 = null;
		    pst2 = c.prepareStatement(query);
		    		    
		    pst2.setInt(1, userId);
		    pst2.setDate(2, dia);
		    pst2.setInt(3, fin);
		    pst2.setInt(4, fin);
		    
		    ResultSet rs2 = pst2.executeQuery();
		    
		    while(rs2.next()) {
		    	return false;
			}
		    
		    rs2.close();
			pst2.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error comprobando disponibilidad del socio - actividades");
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean checkSocioTieneOtrasReservas(Date dia, int ini, int userId){
		try {
			Connection c = getConnection();
			
			String query = "SELECT s_id FROM RESERVA a WHERE s_id = ? "
					+ "AND r_dia = ? AND r_hora = ? AND r_cancelada = ?";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setInt(1, userId);
		    pst.setDate(2, dia);
		    pst.setInt(3, ini);
		    pst.setInt(4, Instalacion.VALIDA);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    while(rs.next()) {
		    	return true;
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error comprobando disponibilidad del socio - reservas");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkInstalacionLibre(Instalacion inst, Date dia, int hora) {
		try {
			Connection c = getConnection();
			
			String query = "SELECT * FROM RESERVA WHERE I_nombre = ? AND r_dia = ? "
					+ "AND r_hora = ? AND r_cancelada = ?";
			
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    
		    pst.setString(1, inst.getNombre());
		    pst.setDate(2, dia);
		    pst.setInt(3, hora);
		    pst.setInt(4, Instalacion.VALIDA);
		    
		    ResultSet rs = pst.executeQuery();
		    
		    while(rs.next()) {
		    	return true;
			}
		    
		    rs.close();
			pst.close();
			c.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Error comprobando disponibilidad de instalacion");
		}
		return false;
	}
	
	public boolean checkPuedoReservar(Date dia, int ini) {
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date(System.currentTimeMillis()));
		
		Calendar act = Calendar.getInstance();
		act.setTime(dia);
		
		//Mirar si coincide fecha
		if (act.getTime().getYear() == now.getTime().getYear() && 
				act.getTime().getMonth() == now.getTime().getMonth() &&
				act.getTime().getDate() == now.getTime().getDate()) {
			//Return true o false segun la hora
			if (now.getTime().getHours() < ini - 1) {
				return true;
			}
			else {
				return false;
			}
		}
		
		//Si es mas tarde no se puede
		if (now.getTime().after(act.getTime())) {
			return false;
		}
		
		act.add(act.DAY_OF_YEAR, -7);
		//Y luego siete dias antes vale tbn
		
		if (act.getTime().getYear() == now.getTime().getYear() && 
				act.getTime().getMonth() == now.getTime().getMonth() &&
				act.getTime().getDate() == now.getTime().getDate()) {
			return true;
		}
		
		if (now.getTime().after(act.getTime())) {
			return true;
		}
		
		return false;
	}
	
	public void reservarInstalacion(Instalacion instalacion, 
			Date dia, int hora, int socioId) {
		try {
			Connection c = getConnection();
			
			String query = "INSERT INTO RESERVA VALUES(?,?,?,?,?)";
			
			PreparedStatement pst = null;
		    pst = c.prepareStatement(query);
		    		    
		    pst.setInt(1, socioId);
		    pst.setString(2, instalacion.getNombre());
		    pst.setDate(3, dia);
		    pst.setInt(4, hora);
		    pst.setInt(5, Instalacion.VALIDA);
		    
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
			System.err.println("Error reservando instalacion");
		}
	}

}
