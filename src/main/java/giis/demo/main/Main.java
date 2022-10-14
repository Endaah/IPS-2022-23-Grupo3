package giis.demo.main;

import java.awt.EventQueue;

import giis.demo.igu.VentanaAcceso;

public class Main {

	//Launch the application.
	 
		public static void main(String[] args) {
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

}
