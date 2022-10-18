package giis.demo.main;

import java.awt.EventQueue;

import giis.demo.igu.VentanaAcceso;
import giis.demo.model.GymControlador;
import giis.demo.util.Db;

public class Main {

	private static GymControlador gC;
	
	public static void main(String[] args) {
		initializeLogic();
		initializeDB();
		initializeGUI();
	}
	
	public static GymControlador getInstanceControlador() {
		return gC;
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
		gC = new GymControlador();
		gC.cargarRecursos();
		gC.cargarInstalaciones();
		gC.cargarTiposDeActividad();
		gC.cargarActividades();
	}
	
	private static void initializeDB() {
		Db.getDriver();
	}
	
	private static void initializeGUI() {
		crearVentanaAcceso();
	}
	
}
