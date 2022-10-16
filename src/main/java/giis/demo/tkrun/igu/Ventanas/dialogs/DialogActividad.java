package giis.demo.tkrun.igu.Ventanas.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;

import giis.demo.tkrun.igu.Ventanas.VentanaAdmin;
import giis.demo.tkrun.logica.Recurso;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class DialogActividad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JLabel lblNewLabel;
	private JTextField txtNombre;
	private JLabel lblNewLabel_1;
	private JCalendar calendar;
	private JLabel lblNewLabel_2;
	private JSpinner spnIni;
	private JSpinner spnFin;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JSpinner spnPlazas;
	private JLabel lblNewLabel_5;
	private VentanaAdmin vA;

	

	/**
	 * Create the dialog.
	 */
	public DialogActividad(VentanaAdmin v) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		this.vA = v;
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getTxtId());
		contentPanel.add(getLblNewLabel());
		contentPanel.add(getTxtNombre());
		contentPanel.add(getLblNewLabel_1());
		contentPanel.add(getCalendar());
		contentPanel.add(getLblNewLabel_2());
		contentPanel.add(getSpnIni());
		contentPanel.add(getSpnFin());
		contentPanel.add(getLblNewLabel_3());
		contentPanel.add(getLblNewLabel_4());
		contentPanel.add(getSpnPlazas());
		contentPanel.add(getLblNewLabel_5());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void okConfirmar() {
		if (getTxtId().getText().isBlank()
				|| getTxtNombre().getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "Introduzca un nombre de actividad e id");
			return;
		}
		crearActividad();
		dispose();
	}
	
private void crearActividad() {
		
		String nombre = getTxtNombre().getText();
		int id = Integer.parseInt(getTxtId().getText());
		java.sql.Date date = (java.sql.Date)calendar.getDate();
		int hini = (int)getSpnIni().getValue();
		int hfin = (int)getSpnFin().getValue();
		int plazas = (int)getSpnPlazas().getValue();
		vA.getControlador().addActividad(id,nombre,date,hini,hfin,plazas);
	}
	private JTextField getTxtId() {
		if (txtId == null) {
			txtId = new JTextField();
			txtId.setText("");
			txtId.setBounds(71, 27, 96, 19);
			txtId.setColumns(10);
		}
		return txtId;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("ID: ");
			lblNewLabel.setBounds(21, 27, 71, 19);
		}
		return lblNewLabel;
	}
	private JTextField getTxtNombre() {
		if (txtNombre == null) {
			txtNombre = new JTextField();
			txtNombre.setBounds(282, 27, 114, 19);
			txtNombre.setColumns(10);
		}
		return txtNombre;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(201, 27, 71, 19);
		}
		return lblNewLabel_1;
	}
	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(41, 96, 157, 91);
		}
		return calendar;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Fecha:");
			lblNewLabel_2.setBounds(41, 73, 77, 13);
		}
		return lblNewLabel_2;
	}
	private JSpinner getSpnIni() {
		if (spnIni == null) {
			spnIni = new JSpinner();
			spnIni.setModel(new SpinnerNumberModel(7, 7, 23, 1));
			spnIni.setBounds(242, 106, 30, 20);
		}
		return spnIni;
	}
	private JSpinner getSpnFin() {
		if (spnFin == null) {
			spnFin = new JSpinner();
			spnFin.setModel(new SpinnerNumberModel(7, 7, 23, 1));
			spnFin.setBounds(327, 106, 30, 20);
		}
		return spnFin;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Hora inicio:");
			lblNewLabel_3.setBounds(215, 73, 61, 13);
		}
		return lblNewLabel_3;
	}
	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("Hora fin:");
			lblNewLabel_4.setBounds(312, 73, 45, 13);
		}
		return lblNewLabel_4;
	}
	private JSpinner getSpnPlazas() {
		if (spnPlazas == null) {
			spnPlazas = new JSpinner();
			spnPlazas.setModel(new SpinnerNumberModel(new Integer(-1), new Integer(-1), null, new Integer(1)));
			spnPlazas.setBounds(327, 161, 30, 20);
		}
		return spnPlazas;
	}
	private JLabel getLblNewLabel_5() {
		if (lblNewLabel_5 == null) {
			lblNewLabel_5 = new JLabel("Plazas:");
			lblNewLabel_5.setBounds(266, 164, 61, 13);
		}
		return lblNewLabel_5;
	}
}
