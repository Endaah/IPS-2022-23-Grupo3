package giis.demo.igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.dialogs.DialogActividad;
import giis.demo.igu.dialogs.DialogReservarInstalacionAdmin;
import giis.demo.igu.dialogs.DialogTipoActividad;
import giis.demo.igu.dialogs.DialogAnularReserva;
import giis.demo.igu.dialogs.DialogReservarInstalacion;
import giis.demo.igu.dialogs.DialogTipoActividad;
import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.TipoActividad;
import giis.demo.util.Db;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;
import javax.swing.ListSelectionModel;

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
	private JLabel lblTituloActividades;
	private JPanel pnActividades;
	private JPanel pnTiposActividiad;
	private JPanel pnActividad;
	private JLabel lblTiposActividad;
	private JLabel lblActividades;
	private JScrollPane spTiposActividad;
	private JScrollPane scrollPane;
	private JList<TipoActividad> listTiposActividad;
	private JList<Actividad> listActividades;
	private Component horizontalStrutActividadesDcha;
	private JLabel lblTituloInstalaciones;
	private JPanel pnInstalaciones;
	private Component horizontalStrut;
	private JPanel pnBotonesInstalaciones;
	private JScrollPane spInstalaciones;
	private JList<Instalacion> listInstalaciones;
	private JButton btnAnularReserva;
	private JButton btnVerReservas;
	private JLabel lblPagos;
	private JPanel panel;
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
	
	private void abrirDialogoReservaInstalacionTerceros() {
		try {
			DialogReservarInstalacionAdmin dialog = new DialogReservarInstalacionAdmin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void abrirDialogoAnularReserva() {
		try {
			DialogAnularReserva dialog = new DialogAnularReserva();
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
			btnReservarInstalacion = new JButton("Reservar para socio");
			btnReservarInstalacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoReservaInstalacion();
				}
			});
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
	private void abrirDialogoReservaInstalacion() {
		try {
			Instalacion i;
			DialogReservarInstalacion dialog;
			if ((i = getListInstalaciones().getSelectedValue()) != null) {
				dialog = new DialogReservarInstalacion(i);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Seleccione una instalación para reservar.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			pnAdminIzquierda.add(getLblTituloActividades(), BorderLayout.NORTH);
			pnAdminIzquierda.add(getPnActividades(), BorderLayout.CENTER);
			pnAdminIzquierda.add(getHorizontalStrutActividadesDcha(), BorderLayout.EAST);
			pnAdminIzquierda.add(getHorizontalStrut(), BorderLayout.WEST);
			getPnPrincipal().setLayout(new GridLayout(0, 3, 0, 0));
		}
		return pnAdminIzquierda;
	}
	private JPanel getPnAmdinCentro() {
		if (pnAmdinCentro == null) {
			pnAmdinCentro = new JPanel();
			pnAmdinCentro.setLayout(new BorderLayout(0, 0));
			pnAmdinCentro.add(getLblTituloInstalaciones(), BorderLayout.NORTH);
			pnAmdinCentro.add(getPnInstalaciones(), BorderLayout.CENTER);
		}
		return pnAmdinCentro;
	}
	private JPanel getPnAdminDerecha() {
		if (pnAdminDerecha == null) {
			pnAdminDerecha = new JPanel();
			pnAdminDerecha.setLayout(new BorderLayout(0, 0));
			pnAdminDerecha.add(getLblPagos(), BorderLayout.NORTH);
			pnAdminDerecha.add(getPanel(), BorderLayout.CENTER);
		}
		return pnAdminDerecha;
	}
	private JLabel getLblTituloActividades() {
		if (lblTituloActividades == null) {
			lblTituloActividades = new JLabel("Actividades");
			lblTituloActividades.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloActividades.setFont(new Font("Arial Black", Font.BOLD, 14));
		}
		return lblTituloActividades;
	}
	private JPanel getPnActividades() {
		if (pnActividades == null) {
			pnActividades = new JPanel();
			pnActividades.setLayout(new GridLayout(2, 0, 0, 0));
			pnActividades.add(getPnTiposActividiad());
			pnActividades.add(getPnActividad());
		}
		return pnActividades;
	}
	private JPanel getPnTiposActividiad() {
		if (pnTiposActividiad == null) {
			pnTiposActividiad = new JPanel();
			pnTiposActividiad.setLayout(new BorderLayout(0, 0));
			pnTiposActividiad.add(getLblTiposActividad(), BorderLayout.NORTH);
			pnTiposActividiad.add(getBtnCrearTipoActividad(), BorderLayout.SOUTH);
			pnTiposActividiad.add(getSpTiposActividad(), BorderLayout.CENTER);
		}
		return pnTiposActividiad;
	}
	private JPanel getPnActividad() {
		if (pnActividad == null) {
			pnActividad = new JPanel();
			pnActividad.setLayout(new BorderLayout(0, 0));
			pnActividad.add(getBtnCrearActividad(), BorderLayout.SOUTH);
			pnActividad.add(getLblActividades(), BorderLayout.NORTH);
			pnActividad.add(getScrollPane(), BorderLayout.CENTER);
		}
		return pnActividad;
	}
	private JLabel getLblTiposActividad() {
		if (lblTiposActividad == null) {
			lblTiposActividad = new JLabel("Tipos de Actividad existentes:");
		}
		return lblTiposActividad;
	}
	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades existentes:");
		}
		return lblActividades;
	}
	private JScrollPane getSpTiposActividad() {
		if (spTiposActividad == null) {
			spTiposActividad = new JScrollPane();
			spTiposActividad.setViewportView(getListTiposActividad());
		}
		return spTiposActividad;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getListActividades());
		}
		return scrollPane;
	}
	private JList<TipoActividad> getListTiposActividad() {
		if (listTiposActividad == null) {
			listTiposActividad = new JList<TipoActividad>();
			listTiposActividad.setFont(new Font("Arial", Font.PLAIN, 12));
			listTiposActividad.setEnabled(false);
			listTiposActividad.setModel(getModelTipos());
		}
		return listTiposActividad;
	}
	private DefaultListModel<TipoActividad> getModelTipos() {
		DefaultListModel<TipoActividad> model = new DefaultListModel<TipoActividad>();
		for (TipoActividad ta : GymControlador.getTiposActividadDisponibles().values()) {
			model.addElement(ta);
		}
		return model;
	}
	private JList<Actividad> getListActividades() {
		if (listActividades == null) {
			listActividades = new JList<Actividad>();
			listActividades.setFont(new Font("Arial", Font.PLAIN, 12));
			listActividades.setEnabled(false);
			listActividades.setModel(getModelActividades());
		}
		return listActividades;
	}
	private DefaultListModel<Actividad> getModelActividades() {
		DefaultListModel<Actividad> model = new DefaultListModel<Actividad>();
		for (Actividad a : GymControlador.getActividadesDisponibles())
			model.addElement(a);
		return model;
	}
	private Component getHorizontalStrutActividadesDcha() {
		if (horizontalStrutActividadesDcha == null) {
			horizontalStrutActividadesDcha = Box.createHorizontalStrut(20);
		}
		return horizontalStrutActividadesDcha;
	}
	private JLabel getLblTituloInstalaciones() {
		if (lblTituloInstalaciones == null) {
			lblTituloInstalaciones = new JLabel("Instalaciones");
			lblTituloInstalaciones.setFont(new Font("Arial Black", Font.BOLD, 14));
			lblTituloInstalaciones.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblTituloInstalaciones;
	}
	private JPanel getPnInstalaciones() {
		if (pnInstalaciones == null) {
			pnInstalaciones = new JPanel();
			pnInstalaciones.setLayout(new GridLayout(2, 1, 0, 0));
			pnInstalaciones.add(getSpInstalaciones());
			pnInstalaciones.add(getPanel_1());
		}
		return pnInstalaciones;
	}
	private Component getHorizontalStrut() {
		if (horizontalStrut == null) {
			horizontalStrut = Box.createHorizontalStrut(20);
		}
		return horizontalStrut;
	}
	private JPanel getPanel_1() {
		if (pnBotonesInstalaciones == null) {
			pnBotonesInstalaciones = new JPanel();
			pnBotonesInstalaciones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnBotonesInstalaciones.add(getBtnReservarInstalacion());
			pnBotonesInstalaciones.add(getBtnVerReservas());
			pnBotonesInstalaciones.add(getBtnAnularReserva());
			pnBotonesInstalaciones.add(getBtnNewButton());
		}
		return pnBotonesInstalaciones;
	}
	private JScrollPane getSpInstalaciones() {
		if (spInstalaciones == null) {
			spInstalaciones = new JScrollPane();
			spInstalaciones.setViewportView(getListInstalaciones());
		}
		return spInstalaciones;
	}
	private JList<Instalacion> getListInstalaciones() {
		if (listInstalaciones == null) {
			listInstalaciones = new JList<Instalacion>();
			listInstalaciones.setFont(new Font("Arial", Font.PLAIN, 12));
			listInstalaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listInstalaciones.setModel(getModelInstalaciones());
		}
		return listInstalaciones;
	}
	private DefaultListModel<Instalacion> getModelInstalaciones() {
		DefaultListModel<Instalacion> model = new DefaultListModel<Instalacion>();
		for (Instalacion i : GymControlador.getInstalacionesDisponibles().values())
			model.addElement(i);
		return model;
	}
	private JButton getBtnAnularReserva() {
		if (btnAnularReserva == null) {
			btnAnularReserva = new JButton("Anular Reserva");
			btnAnularReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirDialogoAnularReserva();
				}
			});
		}
		return btnAnularReserva;
	}
	private JButton getBtnVerReservas() {
		if (btnVerReservas == null) {
			btnVerReservas = new JButton("Ver Reservas");
		}
		return btnVerReservas;
	}
	private JLabel getLblPagos() {
		if (lblPagos == null) {
			lblPagos = new JLabel("Pagos");
			lblPagos.setHorizontalAlignment(SwingConstants.CENTER);
			lblPagos.setFont(new Font("Arial Black", Font.BOLD, 14));
		}
		return lblPagos;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
		}
		return panel;
	}
}
