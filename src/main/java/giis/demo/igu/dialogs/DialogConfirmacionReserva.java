package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.Socio;
import giis.demo.util.Db;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogConfirmacionReserva extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private DialogReservarInstalacion dri;
	private JButton boton1;
	private int boton2Id;
	private JTextField tfBuscarSocios;
	private JList<Socio> listSocios;
	private JRadioButton rdbt1hora;
	private JRadioButton rdbt2horas;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * Create the dialog.
	 */
	public DialogConfirmacionReserva(DialogReservarInstalacion dri, JButton boton1, int boton2) {
		setResizable(false);
		this.dri = dri;
		this.boton1 = boton1;
		this.boton2Id = boton2;
		setBounds(100, 100, 320, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane spSocios = new JScrollPane();
			spSocios.setBounds(10, 29, 284, 83);
			contentPanel.add(spSocios);
			spSocios.setColumnHeaderView(getTfBuscarSocios());
			{
				listSocios = new JList<Socio>();
				listSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				listSocios.setFont(new Font("Arial", Font.PLAIN, 12));
				spSocios.setViewportView(listSocios);
				listSocios.setModel(getModeloSocios());
			}
		}
		{
			JLabel lblSocio = new JLabel("Reserva para:");
			lblSocio.setFont(new Font("Arial", Font.PLAIN, 14));
			lblSocio.setBounds(10, 11, 178, 14);
			contentPanel.add(lblSocio);
		}
		contentPanel.add(getRdbt1hora());
		contentPanel.add(getRdbt2horas());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okConfirmar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void okConfirmar() {
		String motivo;
		if (!(motivo = validacionCampos()).isBlank()) {
			JOptionPane.showMessageDialog(this, "Hay campos con contenido no válido:" + motivo);
			return;
		}
		
		String[] tiempo = boton1.getActionCommand().split(" ");
		
		Instalacion i = dri.getInstalacionSeleccionada();
		LocalDate fecha = java.sql.Date.valueOf(tiempo[0]).toLocalDate();
		int hora = Integer.parseInt(tiempo[1]);
		boolean larga = getRdbt2horas().isSelected();
		
		motivo = i.reservar(listSocios.getSelectedValue().getId(), fecha, hora, larga);
		if (!motivo.isBlank())
			JOptionPane.showMessageDialog(this, "No se ha podido efectuar la reserva:\n" + motivo);
		else {
			dri.setHoraReservada(boton1, listSocios.getSelectedValue());
			if (larga) {
				dri.setHoraReservada(boton2Id, listSocios.getSelectedValue());
			}
				
		}
		
		dispose();
	}
	private String validacionCampos() {
		String motivo = "";
		if (listSocios.getSelectedValue() == null)
			motivo += "\nNo se ha seleccionado ningún socio.";
		if (!getRdbt1hora().isSelected() && !getRdbt2horas().isSelected())
			motivo += "\nNo se ha seleccionado duración de la reserva.";
		return motivo;
	}
	private JTextField getTfBuscarSocios() {
		if (tfBuscarSocios == null) {
			tfBuscarSocios = new JTextField();
			tfBuscarSocios.setFont(new Font("Arial", Font.PLAIN, 14));
			tfBuscarSocios.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					listSocios.setModel(getModeloSocios());
				}
			});
			tfBuscarSocios.setColumns(10);
		}
		return tfBuscarSocios;
	}
	private DefaultListModel<Socio> getModeloSocios() {
		DefaultListModel<Socio> model = new DefaultListModel<Socio>();
		for (Socio s : GymControlador.buscarSocios(Db.getSocios(), tfBuscarSocios.getText())) {
			model.addElement(s);
		} return model;
	}
	private JRadioButton getRdbt1hora() {
		if (rdbt1hora == null) {
			rdbt1hora = new JRadioButton("1 hora");
			buttonGroup.add(rdbt1hora);
			rdbt1hora.setBounds(120, 140, 109, 23);
		}
		return rdbt1hora;
	}
	private JRadioButton getRdbt2horas() {
		if (rdbt2horas == null) {
			rdbt2horas = new JRadioButton("2 horas");
			buttonGroup.add(rdbt2horas);
			rdbt2horas.setBounds(120, 182, 109, 23);
		}
		return rdbt2horas;
	}
}
