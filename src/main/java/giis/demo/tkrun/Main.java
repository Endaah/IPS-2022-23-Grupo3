package giis.demo.tkrun;

import java.awt.EventQueue;
import giis.demo.tkrun.igu.Ventanas.VentanaAcceso;
import giis.demo.tkrun.logica.Actividad;
import giis.demo.tkrun.logica.GymControlador;
import giis.demo.tkrun.logica.Recurso;
import giis.demo.tkrun.logica.TipoActividad;
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
		/* TODO: QUITAR
		System.out.println("--- Recursos cargados ---");
		for (Recurso r : gC.getRecursosDisponibles()) {
			System.out.println(r.toString());
		}
		System.out.println("--- Tipos de actividad cargados ---");
		for (TipoActividad ta : gC.getTiposActividadDisponibles()) {
			System.out.println(ta.toString());
		}*/
		System.out.println("--- Actividades cargadas ---");
		for (Actividad a : gC.getActividadesDisponibles()) {
			System.out.println(a.toString());
		}
	}
	
	private static void initializeDB() {
		Db.getDriver();
	}
	
	private static void initializeGUI() {
		crearVentanaAcceso();
	}
	
}
