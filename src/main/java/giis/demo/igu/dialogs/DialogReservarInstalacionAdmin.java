package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.Socio;
import giis.demo.util.Db;
import javax.swing.JTextField;

public class DialogReservarInstalacionAdmin extends JDialog {
	
private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JCalendar calendar;
	private JLabel lblHora;
	private JSpinner spnHora;
	private JLabel lblSocios;
	private JLabel lblInstalaciones;
	private JComboBox<Instalacion> cbInstalaciones;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JSpinner spnHora_1;
	private JLabel lblNewLabel;
	private JRadioButton rdbtnTodo;
	private JTextField txNombre;

	/**
	 * Create the dialog.
	 */
	public DialogReservarInstalacionAdmin() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getCalendar());
		contentPanel.add(getLblHora());
		contentPanel.add(getSpnHora());
		contentPanel.add(getLblSocios());
		contentPanel.add(getLblInstalaciones());
		contentPanel.add(getCbInstalaciones());
		contentPanel.add(getSpnHora_1());
		contentPanel.add(getLblNewLabel());
		contentPanel.add(getRdbtnTodo());
		contentPanel.add(getTxNombre());
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
		
		int n = 666;
		LocalDate fecha = new java.sql.Date(getCalendar().getDate().getTime()).toLocalDate();
		int hora = (int) getSpnHora().getValue();
		boolean todo = getRdbtnTodo().isSelected();
		if (!((Instalacion) getCbInstalaciones().getSelectedItem()).reservar(n, fecha, hora, todo)) {
			JOptionPane.showMessageDialog(this, "No se ha podido efectuar la reserva");
		}
		dispose();
	}
	private String validacionCampos() {
		String motivo = "";
		if (txNombre.getText().isBlank())
			motivo += "\nNombre no introducido";
		if (getCbInstalaciones().getSelectedIndex() == -1)
			motivo += "\nInstalacion no escogida";
		if (getCalendar().getDate().getTime() == LocalDate.now().toEpochDay())
			motivo += "\nFecha no escogida";
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
			lblHora = new JLabel("Inicio:");
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
	private JSpinner getSpnHora_1() {
		if (spnHora_1 == null) {
			spnHora_1 = new JSpinner();
			spnHora_1.setModel(new SpinnerNumberModel(9, 9, 23, 1));
			spnHora_1.setFont(new Font("Arial", Font.PLAIN, 14));
			spnHora_1.setBounds(345, 111, 45, 21);
		}
		return spnHora_1;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Final:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(345, 89, 45, 13);
		}
		return lblNewLabel;
	}
	private JRadioButton getRdbtnTodo() {
		if (rdbtnTodo == null) {
			rdbtnTodo = new JRadioButton("Todo el día");
			rdbtnTodo.setBounds(277, 146, 103, 21);
		}
		return rdbtnTodo;
	}
	private JTextField getTxNombre() {
		if (txNombre == null) {
			txNombre = new JTextField();
			txNombre.setBounds(26, 27, 111, 21);
			txNombre.setColumns(10);
		}
		return txNombre;
	}
}
