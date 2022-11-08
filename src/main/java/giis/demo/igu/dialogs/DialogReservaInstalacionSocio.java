package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.VentanaSocio;
import giis.demo.model.Instalacion;
import giis.demo.model.data.ModelSocio;

public class DialogReservaInstalacionSocio extends JDialog {
	
	private ModelSocio model = null;
	private Instalacion instalacion;
	private LocalDate dia;
	private int hora;
	private int socioId;

	private final JPanel contentPane = new JPanel();
	private JRadioButton rdBtnUnaHora;
	private JRadioButton rdbtnDosHoras;
	private JLabel lblNewLabel;
	private JButton btnReservar;
	private JButton btnCancelar;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();

	
	/**
	 * Create the dialog.
	 */
	public DialogReservaInstalacionSocio(ModelSocio model, Instalacion instalacion,
			LocalDate dia, int horaInicial, int socioId) {
		this.model = model;
		this.instalacion = instalacion;
		this.dia = dia;
		this.hora = horaInicial;
		this.socioId = socioId;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setLayout(null);
		contentPane.add(getRdBtnUnaHora());
		contentPane.add(getRdbtnDosHoras());
		contentPane.add(getLblNewLabel());
		contentPane.add(getBtnReservar());
		contentPane.add(getBtnCancelar());
	}
	private JRadioButton getRdBtnUnaHora() {
		if (rdBtnUnaHora == null) {
			rdBtnUnaHora = new JRadioButton("Una hora");
			rdBtnUnaHora.setSelected(true);
			rdBtnUnaHora.setBounds(75, 115, 94, 23);
			buttonGroup.add(rdBtnUnaHora);
		}
		return rdBtnUnaHora;
	}
	private JRadioButton getRdbtnDosHoras() {
		if (rdbtnDosHoras == null) {
			rdbtnDosHoras = new JRadioButton("Dos horas");
			rdbtnDosHoras.setBounds(262, 115, 94, 23);
			buttonGroup.add(rdbtnDosHoras);
		}
		return rdbtnDosHoras;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("¿Cuantas horas quiere reservar?");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(111, 58, 194, 23);
		}
		return lblNewLabel;
	}
	private JButton getBtnReservar() {
		if (btnReservar == null) {
			btnReservar = new JButton("Reservar");
			btnReservar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reservar();
				}
			});
			btnReservar.setBackground(Color.GREEN);
			btnReservar.setBounds(66, 201, 89, 23);
		}
		return btnReservar;
	}

	private void reservar() {
		
		//CRITERIOS DE ACEPTACION
		boolean dosHoras = rdbtnDosHoras.isSelected();
		if (dosHoras) {
			if (!model.checkInstalacionLibre(
					instalacion, java.sql.Date.valueOf(dia), hora+1)) {
				VentanaSocio.showMessage("Solo puede reservar una hora esta instalación", 
						"Segunda hora ocupada", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		if (!model.checkPuedoReservar(java.sql.Date.valueOf(dia), hora)) {
			VentanaSocio.showMessage("Solo puede reservar entre 7 dias y una hora antes la instalación", 
					"No puede reservar", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (dosHoras) {
			if (!model.checkPuedoReservar(java.sql.Date.valueOf(dia), hora)) {
				VentanaSocio.showMessage("Solo puede reservar entre 7 dias y una hora antes la instalación", 
						"No puede reservar", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		if (!model.checkSocioPuedeApuntarse(
				java.sql.Date.valueOf(dia), hora, hora+1, socioId)) {
			VentanaSocio.showMessage("Estas apuntado a una actividad", 
					"No puede reservar", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (dosHoras) {
			if (!model.checkSocioPuedeApuntarse(
					java.sql.Date.valueOf(dia), hora, hora+2, socioId)) {
				VentanaSocio.showMessage("Estas apuntado a una actividad", 
						"No puede reservar", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		if (model.checkSocioTieneOtrasReservas(
				java.sql.Date.valueOf(dia), socioId, hora)) {
			VentanaSocio.showMessage("Tienes otra reserva a esta hora", 
					"No puede reservar", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (dosHoras) {
			if (model.checkSocioTieneOtrasReservas(
					java.sql.Date.valueOf(dia), socioId, hora+1)) {
				VentanaSocio.showMessage("Tienes otra reserva a esta hora", 
						"No puede reservar", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		//Anotamos la reserva si todos los criterios de aceptacion se cumplen 
		model.reservarInstalacion(instalacion, 
				java.sql.Date.valueOf(dia), hora, socioId);
		
		if (dosHoras) {
			model.reservarInstalacion(instalacion, 
					java.sql.Date.valueOf(dia), hora+1, socioId);
		}
	}
	
	
	private JButton getBtnCancelar() {
		if (btnCancelar == null) {
			btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnCancelar.setBackground(Color.RED);
			btnCancelar.setBounds(267, 201, 89, 23);
		}
		return btnCancelar;
	}
}
