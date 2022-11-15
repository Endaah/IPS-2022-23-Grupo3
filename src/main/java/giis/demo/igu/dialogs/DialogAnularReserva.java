package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.VentanaAdmin;
import giis.demo.model.GrupoReservas;
import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.ReservaInstalacion;
import giis.demo.model.Socio;
import giis.demo.util.Db;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class DialogAnularReserva extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VentanaAdmin vA;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField tfSocios;
	private JList<Socio> listSocios;
	private JLabel lblInstalaciones;
	private JList<GrupoReservas> listReservas;

	/**
	 * Create the dialog.
	 */
	public DialogAnularReserva(VentanaAdmin vA) {
		setResizable(false);
		this.vA = vA;
		setBounds(100, 100, 499, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane spSocios = new JScrollPane();
			spSocios.setBounds(10, 34, 463, 173);
			contentPanel.add(spSocios);
			{
				tfSocios = new JTextField();
				tfSocios.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						getListSocios().setModel(getModelSocios());
					}
				});
				tfSocios.setFont(new Font("Arial", Font.PLAIN, 14));
				spSocios.setColumnHeaderView(tfSocios);
				tfSocios.setColumns(10);
			}
			spSocios.setViewportView(getListSocios());
		}
		{
			JLabel lblSocios = new JLabel("Socios con reserva:");
			lblSocios.setFont(new Font("Arial", Font.PLAIN, 14));
			lblSocios.setBounds(10, 11, 197, 14);
			contentPanel.add(lblSocios);
		}
		{
			JScrollPane spInstalaciones = new JScrollPane();
			spInstalaciones.setBounds(10, 253, 463, 140);
			contentPanel.add(spInstalaciones);
			{
				listReservas = new JList<GrupoReservas>();
				spInstalaciones.setViewportView(listReservas);
			}
		}
		{
			lblInstalaciones = new JLabel("Instalaciones reservadas:");
			lblInstalaciones.setFont(new Font("Arial", Font.PLAIN, 14));
			lblInstalaciones.setBounds(10, 233, 416, 14);
			contentPanel.add(lblInstalaciones);
		}
		{
			JButton btnAnularReserva = new JButton("Anular Reserva");
			btnAnularReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (GrupoReservas gr : listReservas.getSelectedValuesList())
						if (gr != null) {
							GymControlador.getInstalacionesDisponibles().get(gr.getReservas()[0].getInstalacion()).anularReserva(gr);
							Socio s = getListSocios().getSelectedValue();
							mostrarDatosSocio(s);
							listReservas.setModel(getModelReservas(listSocios.getSelectedValue()));
							if (getModelReservas(listSocios.getSelectedValue()).isEmpty())
								listSocios.setModel(getModelSocios());
						}
				}
			});
			btnAnularReserva.setFont(new Font("Arial", Font.PLAIN, 14));
			btnAnularReserva.setBounds(10, 394, 463, 23);
			contentPanel.add(btnAnularReserva);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						vA.actualizarListaSocios();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	private JList<Socio> getListSocios() {
		if (listSocios == null) {
			listSocios = new JList<Socio>();
			listSocios.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (listSocios.getSelectedValue() != null) {
						lblInstalaciones.setText("Instalaciones reservadas del socio " + listSocios.getSelectedValue().getNombre() + ":");
						listReservas.setModel(getModelReservas(listSocios.getSelectedValue()));
					}
				}
			});
			listSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listSocios.setModel(getModelSocios());
		}
		return listSocios;
	}
	private DefaultListModel<Socio> getModelSocios() {
		DefaultListModel<Socio> model = new DefaultListModel<Socio>();
		for (Socio s : GymControlador.buscarSocios(Db.getSociosConReservaAnulable(), tfSocios.getText())) {
			model.addElement(s);
		} return model;
	}
	private DefaultListModel<GrupoReservas> getModelReservas(Socio socio) {
		DefaultListModel<GrupoReservas> model = new DefaultListModel<GrupoReservas>();
		for (GrupoReservas gr : Db.getReservasSocio(socio.getId()))
			if (gr.getReservas()[0].getAnulada() == 0 && gr.getReservas()[0].getFecha().isAfter(LocalDate.now())) model.addElement(gr);
		return model;
	}
	private void mostrarDatosSocio(Socio s) {
		JOptionPane.showMessageDialog(this, "Se ha anulado la reserva del socio con id: " + s.getId()
			+ "\nNombre del socio: " + s.getNombre()
			+ "\nContactar con el socio via: " + s.getEmail());
	}
}
