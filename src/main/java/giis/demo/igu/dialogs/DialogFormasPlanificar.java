package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.VentanaAdmin;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogFormasPlanificar extends JDialog {

	private VentanaAdmin vA;
	
	private final JPanel contentPanel = new JPanel();
	private JLabel lblPlanificacion;
	private JRadioButton rdbtPuntual;
	private JRadioButton rdbtSemana;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public DialogFormasPlanificar(VentanaAdmin vA) {
		this.vA = vA;
		setResizable(false);
		setBounds(100, 100, 580, 314);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblPlanificacion());
		contentPanel.add(getRdbtPuntual());
		contentPanel.add(getRdbtSemana());
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
		if (getRdbtPuntual().isSelected()) {
			abrirDialogActividadPuntual();
		} else if (getRdbtSemana().isSelected()) {
			abrirDialogActividadSemana();
		} else {
			JOptionPane.showMessageDialog(this, "Escoge una opción para continuar");
			return;
		}
		dispose();
	}
	private void abrirDialogActividadPuntual() {
		try {
			DialogActividad dialog = new DialogActividad(vA, false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void abrirDialogActividadSemana() {
		try {
			DialogActividad dialog = new DialogActividad(vA, true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private JLabel getLblPlanificacion() {
		if (lblPlanificacion == null) {
			lblPlanificacion = new JLabel("¿Cómo quieres planificar las actividades?");
			lblPlanificacion.setHorizontalAlignment(SwingConstants.CENTER);
			lblPlanificacion.setFont(new Font("Arial Black", Font.BOLD, 14));
			lblPlanificacion.setBounds(10, 11, 544, 151);
		}
		return lblPlanificacion;
	}
	private JRadioButton getRdbtPuntual() {
		if (rdbtPuntual == null) {
			rdbtPuntual = new JRadioButton("Puntualmente");
			buttonGroup.add(rdbtPuntual);
			rdbtPuntual.setBounds(115, 169, 109, 23);
		}
		return rdbtPuntual;
	}
	private JRadioButton getRdbtSemana() {
		if (rdbtSemana == null) {
			rdbtSemana = new JRadioButton("Para toda la semana");
			buttonGroup.add(rdbtSemana);
			rdbtSemana.setBounds(312, 169, 227, 23);
		}
		return rdbtSemana;
	}
}