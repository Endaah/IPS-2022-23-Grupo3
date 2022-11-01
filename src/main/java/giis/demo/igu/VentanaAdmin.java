package giis.demo.igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.dialogs.DialogActividad;
import giis.demo.igu.dialogs.DialogReservarInstalacion;
import giis.demo.igu.dialogs.DialogReservarInstalacionAdmin;
import giis.demo.igu.dialogs.DialogTipoActividad;
import giis.demo.util.Db;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel lblAdmin;
	private JButton btnCrearTipoActividad;
	private JButton btnCrearActividad;
	private JButton btnReservarInstalacion;
	private JButton btnNewButton;
	
	/**
	 * Create the frame.
	 */
	public VentanaAdmin() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				terminate();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 747, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblAdmin());
		contentPane.add(getBtnCrearTipoActividad());
		contentPane.add(getBtnCrearActividad());
		contentPane.add(getBtnReservarInstalacion());
		contentPane.add(getBtnNewButton());
	}
	private void terminate() {
		Db.shutdown();
		System.exit(0);
	}
	private void abrirDialogoActividad() {
		try {
			DialogTipoActividad dialog = new DialogTipoActividad();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void abrirDialogoCrearActividad() {
		try {
			DialogActividad dialog = new DialogActividad(this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void abrirDialogoReservaInstalacionSocio() {
		try {
			DialogReservarInstalacion dialog = new DialogReservarInstalacion();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void abrirDialogoReservaInstalacionTerceros() {
		try {
			DialogReservarInstalacionAdmin dialog = new DialogReservarInstalacionAdmin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private JLabel getLblAdmin() {
		if (lblAdmin == null) {
			lblAdmin = new JLabel("ADMIN");
			lblAdmin.setFont(new Font("Arial Black", Font.BOLD, 16));
			lblAdmin.setBounds(5, 5, 72, 23);
		}
		return lblAdmin;
	}
	private JButton getBtnCrearTipoActividad() {
		if (btnCrearTipoActividad == null) {
			btnCrearTipoActividad = new JButton("Crear Tipo de Actividad");
			btnCrearTipoActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoActividad();
				}
			});
			btnCrearTipoActividad.setBounds(15, 39, 186, 42);
		}
		return btnCrearTipoActividad;
	}
	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("CrearActividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoCrearActividad();
				}

			});
			btnCrearActividad.setBounds(15, 107, 186, 35);
		}
		return btnCrearActividad;
	}
	private JButton getBtnReservarInstalacion() {
		if (btnReservarInstalacion == null) {
			btnReservarInstalacion = new JButton("Reservar Instalacion socio");
			btnReservarInstalacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoReservaInstalacionSocio();
				}
			});
			btnReservarInstalacion.setBounds(21, 166, 180, 35);
		}
		return btnReservarInstalacion;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("Reservar Instalacion terceros");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoReservaInstalacionTerceros();
				}
			});
			btnNewButton.setBounds(228, 166, 170, 35);
		}
		return btnNewButton;
	}
}
