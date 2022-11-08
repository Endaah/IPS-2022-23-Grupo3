package giis.demo.igu.dialogs;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import com.toedter.calendar.JCalendar;

import giis.demo.igu.VentanaAdmin;
import giis.demo.model.GrupoReservas;
import giis.demo.model.Instalacion;
import giis.demo.model.ReservaInstalacion;
import giis.demo.model.Socio;
import giis.demo.util.Db;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

public class DialogReservarInstalacion extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private VentanaAdmin vA;
	private Instalacion instalacionSeleccionada;
	
	private JLabel lblInstalacion;
	private JScrollPane spCalendarioReservas;
	private JPanel pnDias;
	private JPanel pnCalendarioInner;
	private JPanel pnHoras;
	private JPanel pnBotones;
	
	/**
	 * Create the dialog.
	 */
	public DialogReservarInstalacion(Instalacion seleccionada, VentanaAdmin vA) {
		setResizable(false);
		this.instalacionSeleccionada = seleccionada;
		this.vA = vA;
		setTitle("Reserva de Instalación");
		setBounds(100, 100, 772, 424);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblInstalacion());
		contentPanel.add(getSpCalendarioReservas());
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
		}
	}
	private void okConfirmar() {
		vA.actualizarListaSocios();
		vA.actualizarListaAlquileres();
		dispose();
	}
	private void abrirConfirmacionReserva(JButton boton, int boton2) {
		try {
			DialogConfirmacionReserva dialog = new DialogConfirmacionReserva(this, boton, boton2);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Instalacion getInstalacionSeleccionada() {
		return this.instalacionSeleccionada;
	}
	private JLabel getLblInstalacion() {
		if (lblInstalacion == null) {
			lblInstalacion = new JLabel("Instalación a reservar: " + instalacionSeleccionada.getNombre());
			lblInstalacion.setFont(new Font("Arial", Font.PLAIN, 16));
			lblInstalacion.setBounds(10, 11, 736, 33);
		}
		return lblInstalacion;
	}
	private JScrollPane getSpCalendarioReservas() {
		if (spCalendarioReservas == null) {
			spCalendarioReservas = new JScrollPane();
			spCalendarioReservas.setBounds(10, 55, 736, 286);
			spCalendarioReservas.setColumnHeaderView(getPnDias());
			spCalendarioReservas.setViewportView(getPnCalendarioInner());
		}
		return spCalendarioReservas;
	}
	private JPanel getPnDias() {
		if (pnDias == null) {
			pnDias = new JPanel();
			addLabelDias();
		}
		return pnDias;
	}
	private void addLabelDias() {
		getPnDias().setLayout(new GridLayout(1, 0, 10, 0));
		pnDias.add(new JLabel("Horas"));
		
		for (int i = 0; i < 7; i++)
			pnDias.add(createJLabelDias(i));
	}
	private JLabel createJLabelDias(int i) {
		JLabel label = new JLabel();
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		
		LocalDate aux = LocalDate.now().plusDays(i);
		label.setText(aux.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("es", "ES")) + " " + aux.getDayOfMonth());
		return label;
	}
	private JPanel getPnCalendarioInner() {
		if (pnCalendarioInner == null) {
			pnCalendarioInner = new JPanel();
			pnCalendarioInner.setLayout(new BorderLayout(0, 0));
			pnCalendarioInner.add(getPnHoras(), BorderLayout.WEST);
			pnCalendarioInner.add(getPnBotones(), BorderLayout.CENTER);
		}
		return pnCalendarioInner;
	}
	private JPanel getPnHoras() {
		if (pnHoras == null) {
			pnHoras = new JPanel();
			pnHoras.setLayout(new GridLayout(0, 1, 0, 15));
			addLabelHoras();
		}
		return pnHoras;
	}
	private void addLabelHoras() {
		for (int i = 8; i < 23; i++) {
			pnHoras.add(createJLabelHoras(i));
		}
	}
	private JLabel createJLabelHoras(int i) {
		JLabel label = new JLabel();
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		
		label.setText(i + ":00");
		return label;
	}
	private JPanel getPnBotones() {
		if (pnBotones == null) {
			pnBotones = new JPanel();
			pnBotones.setLayout(new GridLayout(15, 7, 0, 0));
			addBotones();
		}
		return pnBotones;
	}
	private void addBotones() {
		for (int i = 8; i < 23; i++)
			for (int j = 0; j < 7; j++)
				pnBotones.add(crearBoton(i, j));
	}
	private JButton crearBoton(int i, int j) {
		JButton boton = new JButton();
		LocalDate dia = LocalDate.now().plusDays(j);
		int hora = i;
		
		boton.setName(String.valueOf(i * 7 + j));;
		
		boolean ocupada = false;
		for (GrupoReservas gr : instalacionSeleccionada.getReservas()) {
			for (ReservaInstalacion rI : gr.getReservas())
				if (rI.getFecha().equals(dia) && rI.getHora() == hora && rI.getAnulada() == 0) {
						ocupada = true;
						break;
				}	
		}
		if (ocupada) {
			setHoraOcupada(boton);
		} else {
			boton.setBackground(Color.white);
			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					abrirConfirmacionReserva((JButton) e.getSource(), Integer.parseInt(((JButton) e.getSource()).getName()) + 7);
				}
			});
		}
		
		if (!ocupada && dia.equals(LocalDate.now()) && hora <= LocalDateTime.now().getHour() + 1) {
			boton.setEnabled(false);
			boton.setBackground(Color.lightGray);
		}
		
		boton.setActionCommand(dia.toString() + " " + hora);
		return boton;
	}
	public void setHoraOcupada(JButton boton) {
		boton.setBackground(Color.red);
		boton.setEnabled(false);
	}
	public void setHoraOcupada(int botonId) {
		for (Component boton : getPnBotones().getComponents()) {
			if (Integer.parseInt(boton.getName()) == botonId)
				setHoraOcupada((JButton) boton);
		}
	}
}
