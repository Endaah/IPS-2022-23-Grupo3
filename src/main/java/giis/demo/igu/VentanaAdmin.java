package giis.demo.igu;

import giis.demo.igu.dialogs.*;
import giis.demo.main.Main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.*;
import giis.demo.igu.*;
import giis.demo.model.*;
import giis.demo.main.*;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaAdmin extends JFrame {

	private GymControlador gC;
	
	private JPanel contentPane;
	private JLabel lblAdmin;
	private JButton btnCrearTipoActividad;
	private JButton btnCrearActividad;
	
	public GymControlador getControlador() {
		return gC;
	}
	
	/**
	 * Create the frame.
	 */
	public VentanaAdmin() {
		this.gC = Main.getInstanceControlador();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 819, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblAdmin());
		contentPane.add(getBtnCrearTipoActividad());
		contentPane.add(getBtnCrearActividad());
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
			btnCrearTipoActividad.setBounds(15, 55, 145, 42);
		}
		return btnCrearTipoActividad;
	}
	private void abrirDialogoActividad() {
		try {
			DialogTipoActividad dialog = new DialogTipoActividad(this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private JButton getBtnCrearActividad() {
		if (btnCrearActividad == null) {
			btnCrearActividad = new JButton("CrearActividad");
			btnCrearActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoCrearActividad();
				}

			});
			btnCrearActividad.setBounds(15, 107, 145, 35);
		}
		return btnCrearActividad;
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
}