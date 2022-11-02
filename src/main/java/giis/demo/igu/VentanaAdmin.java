package giis.demo.igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.dialogs.DialogActividad;
import giis.demo.igu.dialogs.DialogReservarInstalacion;
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
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class VentanaAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel lblAdmin;
	private JButton btnCrearTipoActividad;
	private JButton btnCrearActividad;
	private JButton btnReservarInstalacion;
	private JPanel pnPrincipal;
	private JPanel pnAdminIzquierda;
	private JPanel pnAmdinCentro;
	private JPanel pnAdminDerecha;
	private JLabel lblActividades;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	
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
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getLblAdmin(), BorderLayout.NORTH);
		contentPane.add(getPnPrincipal());
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
	private JLabel getLblAdmin() {
		if (lblAdmin == null) {
			lblAdmin = new JLabel("ADMINISTRADOR");
			lblAdmin.setHorizontalAlignment(SwingConstants.CENTER);
			lblAdmin.setFont(new Font("Arial Black", Font.BOLD, 16));
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
		}
		return btnCrearActividad;
	}
	private JButton getBtnReservarInstalacion() {
		if (btnReservarInstalacion == null) {
			btnReservarInstalacion = new JButton("Reservar Instalacion");
			btnReservarInstalacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoReservaInstalacionSocio();
				}
			});
		}
		return btnReservarInstalacion;
	}
	private JPanel getPnPrincipal() {
		if (pnPrincipal == null) {
			pnPrincipal = new JPanel();
			pnPrincipal.add(getPnAdminIzquierda());
			pnPrincipal.add(getPnAmdinCentro());
			pnPrincipal.add(getPnAdminDerecha());
		}
		return pnPrincipal;
	}
	private JPanel getPnAdminIzquierda() {
		if (pnAdminIzquierda == null) {
			pnAdminIzquierda = new JPanel();
			pnAdminIzquierda.setLayout(new BorderLayout(0, 0));
			pnAdminIzquierda.add(getLblActividades(), BorderLayout.NORTH);
			pnAdminIzquierda.add(getPanel(), BorderLayout.CENTER);
			getPnPrincipal().setLayout(new GridLayout(0, 3, 0, 0));
		}
		return pnAdminIzquierda;
	}
	private JPanel getPnAmdinCentro() {
		if (pnAmdinCentro == null) {
			pnAmdinCentro = new JPanel();
			pnAmdinCentro.add(getBtnReservarInstalacion());
			pnAmdinCentro.add(getBtnCrearActividad());
			pnAmdinCentro.add(getBtnCrearTipoActividad());
		}
		return pnAmdinCentro;
	}
	private JPanel getPnAdminDerecha() {
		if (pnAdminDerecha == null) {
			pnAdminDerecha = new JPanel();
		}
		return pnAdminDerecha;
	}
	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades");
			lblActividades.setHorizontalAlignment(SwingConstants.CENTER);
			lblActividades.setFont(new Font("Arial Black", Font.BOLD, 14));
		}
		return lblActividades;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new GridLayout(2, 0, 0, 0));
			panel.add(getPanel_1());
			panel.add(getPanel_2());
		}
		return panel;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
		}
		return panel_1;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
		}
		return panel_2;
	}
}
