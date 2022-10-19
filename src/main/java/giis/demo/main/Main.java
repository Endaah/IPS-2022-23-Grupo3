package giis.demo.main;
import java.awt.EventQueue;

import giis.demo.igu.VentanaAcceso;
import giis.demo.model.*;
import giis.demo.util.Db;

public class Main {

	public static void main(String[] args) {
		initializeLogic();
		initializeDB();
		initializeGUI();
	}
	
	private static void crearVentanaAcceso() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAcceso frame = new VentanaAcceso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void initializeLogic() {
		GymControlador.cargarRecursos();
		GymControlador.cargarInstalaciones();
		GymControlador.cargarTiposDeActividad();
		GymControlador.cargarActividades();
	}
	
	private static void initializeDB() {
		Db.getDriver();
	}
	
	private static void initializeGUI() {
		crearVentanaAcceso();
	}
	
}