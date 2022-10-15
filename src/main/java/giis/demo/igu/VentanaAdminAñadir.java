package giis.demo.igu;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import com.toedter.calendar.JCalendar;

public class VentanaAdminAñadir extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JRadioButton rdbtnSiLimite;
	private JRadioButton rdbtnNoLimite;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JComboBox cmbTipoActividad;
	private JLabel lblNewLabel_2;
	private JComboBox cmbRecursos;
	private JLabel lblNewLabel_3;
	private JButton btnAceptar;
	private JCalendar calendar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAdminAñadir frame = new VentanaAdminAñadir();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaAdminAñadir() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 417);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			panel.add(getRdbtnSiLimite());
			panel.add(getRdbtnNoLimite());
			panel.add(getLblNewLabel());
			panel.add(getLblNewLabel_1());
			panel.add(getCmbTipoActividad());
			panel.add(getLblNewLabel_2());
			panel.add(getCmbRecursos());
			panel.add(getLblNewLabel_3());
			panel.add(getBtnAceptar());
			panel.add(getCalendar());
		}
		return panel;
	}
	private JRadioButton getRdbtnSiLimite() {
		if (rdbtnSiLimite == null) {
			rdbtnSiLimite = new JRadioButton("Si");
			buttonGroup.add(rdbtnSiLimite);
			rdbtnSiLimite.setBounds(143, 247, 39, 23);
		}
		return rdbtnSiLimite;
	}
	private JRadioButton getRdbtnNoLimite() {
		if (rdbtnNoLimite == null) {
			rdbtnNoLimite = new JRadioButton("No");
			rdbtnNoLimite.setSelected(true);
			buttonGroup.add(rdbtnNoLimite);
			rdbtnNoLimite.setBounds(181, 247, 46, 23);
		}
		return rdbtnNoLimite;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Plazas limitadas :");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(23, 245, 120, 23);
		}
		return lblNewLabel;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Seleccionar tipo de actividad :");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(10, 11, 189, 28);
		}
		return lblNewLabel_1;
	}
	private JComboBox getCmbTipoActividad() {
		if (cmbTipoActividad == null) {
			cmbTipoActividad = new JComboBox();
			cmbTipoActividad.setBounds(204, 16, 169, 23);
		}
		return cmbTipoActividad;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Hora :");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(23, 212, 170, 23);
		}
		return lblNewLabel_2;
	}
	private JComboBox getCmbRecursos() {
		if (cmbRecursos == null) {
			cmbRecursos = new JComboBox();
			cmbRecursos.setBounds(143, 214, 147, 23);
		}
		return cmbRecursos;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Seleccione una fecha :");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel_3.setBounds(10, 49, 147, 23);
		}
		return lblNewLabel_3;
	}
	private JButton getBtnAceptar() {
		if (btnAceptar == null) {
			btnAceptar = new JButton("Aceptar");
			btnAceptar.setBounds(430, 337, 89, 23);
		}
		return btnAceptar;
	}
	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(156, 49, 220, 152);
		}
		return calendar;
	}
}
