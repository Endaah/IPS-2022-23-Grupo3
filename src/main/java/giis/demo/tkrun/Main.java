package giis.demo.tkrun;

import java.awt.EventQueue;

import giis.demo.tkrun.igu.Ventanas.VentanaAcceso;
import giis.demo.tkrun.logica.GymControlador;

public class Main {

	private static GymControlador gC;
	
	public static void main(String[] args) {
		gC = new GymControlador();
		crearVentanaAcceso();
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
	
	public static GymControlador getInstanceControlador() {
		return gC;
	}
	
}
