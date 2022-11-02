package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
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

import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.Socio;
import giis.demo.util.Db;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class DialogReservarInstalacion extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JCalendar calendar;
	private JLabel lblHora;
	private JSpinner spnHora;
	private JLabel lblSocios;
	private JComboBox<Socio> cbSocios;
	private JLabel lblInstalaciones;
	private JComboBox<Instalacion> cbInstalaciones;
	private JRadioButton rdbt1hora;
	private JRadioButton rdbt2horas;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblDuracion;

	/**
	 * Create the dialog.
	 */
	public DialogReservarInstalacion() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getCalendar());
		contentPanel.add(getLblHora());
		contentPanel.add(getSpnHora());
		contentPanel.add(getLblSocios());
		contentPanel.add(getCbSocios());
		contentPanel.add(getLblInstalaciones());
		contentPanel.add(getCbInstalaciones());
		contentPanel.add(getRdbt1hora());
		contentPanel.add(getRdbt2horas());
		contentPanel.add(getLblDuracion());
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
		String motivo = "";
		if (!(motivo = validacionCampos()).isBlank()) {
			JOptionPane.showMessageDialog(this, "Hay campos con contenido no válido:" + motivo);
			return;
		}
		
		int idSocio = ((Socio) getCbSocios().getSelectedItem()).getId();
		LocalDate fecha = new java.sql.Date(getCalendar().getDate().getTime()).toLocalDate();
		int hora = (int) getSpnHora().getValue();
		boolean larga = getRdbt2horas().isSelected();
		
		if (!((Instalacion) getCbInstalaciones().getSelectedItem()).reservar(idSocio, fecha, hora, larga)) {
			JOptionPane.showMessageDialog(this, "No se ha podido efectuar la reserva");
		}
		dispose();
	}
	private String validacionCampos() {
		String motivo = "";
		if (getCbSocios().getSelectedIndex() == -1)
			motivo += "\nSocio no escogido";
		if (getCbInstalaciones().getSelectedIndex() == -1)
			motivo += "\nInstalacion no escogida";
		if (getCalendar().getDate().getTime() == LocalDate.now().toEpochDay())
			motivo += "\nFecha no escogida";
		if (!getRdbt1hora().isSelected() && !getRdbt2horas().isSelected())
			motivo += "\nDuracion no escogida";
		return motivo;
	}
	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(26, 70, 206, 152);
		}
		return calendar;
	}
	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("Hora:");
			lblHora.setFont(new Font("Arial", Font.PLAIN, 14));
			lblHora.setBounds(277, 90, 45, 13);
		}
		return lblHora;
	}
	private JSpinner getSpnHora() {
		if (spnHora == null) {
			spnHora = new JSpinner();
			spnHora.setModel(new SpinnerNumberModel(8, 8, 22, 1));
			spnHora.setFont(new Font("Arial", Font.PLAIN, 14));
			spnHora.setBounds(277, 111, 45, 21);
		}
		return spnHora;
	}
	private JLabel getLblSocios() {
		if (lblSocios == null) {
			lblSocios = new JLabel("Reserva para:");
			lblSocios.setFont(new Font("Arial", Font.PLAIN, 14));
			lblSocios.setBounds(26, 10, 111, 13);
		}
		return lblSocios;
	}
	private JComboBox<Socio> getCbSocios() {
		if (cbSocios == null) {
			cbSocios = new JComboBox<Socio>();
			cbSocios.setBounds(26, 27, 111, 21);
			cbSocios.setModel(new DefaultComboBoxModel<Socio>(Db.getSocios().toArray(new Socio[Db.getSocios().size()])));
		}
		return cbSocios;
	}
	private JLabel getLblInstalaciones() {
		if (lblInstalaciones == null) {
			lblInstalaciones = new JLabel("De:");
			lblInstalaciones.setFont(new Font("Arial", Font.PLAIN, 14));
			lblInstalaciones.setBounds(147, 11, 85, 13);
		}
		return lblInstalaciones;
	}
	private JComboBox<Instalacion> getCbInstalaciones() {
		if (cbInstalaciones == null) {
			cbInstalaciones = new JComboBox<Instalacion>();
			cbInstalaciones.setBounds(147, 27, 175, 21);
			cbInstalaciones.setModel(
					new DefaultComboBoxModel<Instalacion>(
							GymControlador.getInstalacionesDisponibles().values().toArray(
									new Instalacion[GymControlador.getInstalacionesDisponibles().values().size()])));
		}
		return cbInstalaciones;
	}
	private JRadioButton getRdbt1hora() {
		if (rdbt1hora == null) {
			rdbt1hora = new JRadioButton("1 hora");
			buttonGroup.add(rdbt1hora);
			rdbt1hora.setBounds(256, 162, 103, 21);
		}
		return rdbt1hora;
	}
	private JRadioButton getRdbt2horas() {
		if (rdbt2horas == null) {
			rdbt2horas = new JRadioButton("2 horas");
			buttonGroup.add(rdbt2horas);
			rdbt2horas.setBounds(256, 185, 103, 21);
		}
		return rdbt2horas;
	}
	private JLabel getLblDuracion() {
		if (lblDuracion == null) {
			lblDuracion = new JLabel("Duracion:");
			lblDuracion.setFont(new Font("Arial", Font.PLAIN, 14));
			lblDuracion.setBounds(254, 143, 91, 13);
		}
		return lblDuracion;
	}
}
