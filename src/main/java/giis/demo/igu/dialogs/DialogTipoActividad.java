package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import giis.demo.model.*;
import giis.demo.igu.*;

public class DialogTipoActividad extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VentanaAdmin vA;
	
	private final JPanel contentPanel = new JPanel();
	private JScrollPane spListaRecursos;
	private JList listRecursos;
	private JTextField tfNombreAct;
	private JLabel lblNombreAct;
	private JLabel lblIntensidadAct;
	private JComboBox<String> cbIntensidadAct;
	private JLabel lblInstalacionAct;
	private JTextField tfInstalacionAct;

	/**
	 * Create the dialog.
	 */
	public DialogTipoActividad(VentanaAdmin vA) {
		this.vA = vA;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getSpListaRecursos());
		contentPanel.add(getTfNombreAct());
		contentPanel.add(getLblNombreAct());
		contentPanel.add(getLblIntensidadAct());
		contentPanel.add(getCbIntensidadAct());
		contentPanel.add(getLblInstalacionAct());
		contentPanel.add(getTfInstalacionAct());
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
		if (getTfNombreAct().getText().isBlank()
				|| getTfInstalacionAct().getText().isBlank()
				|| getCbIntensidadAct().getSelectedItem().toString().equals("Elegir intensidad")) {
			JOptionPane.showMessageDialog(this, "Introduzca un nombre de actividad e instalación");
			return;
		}
		crearTipoActividad();
		dispose();
	}
	private void crearTipoActividad() {
		
		List<Recurso> selected = this.getListRecursos().getSelectedValuesList();
		String nombre = getTfNombreAct().getText();
		String intensidad = getCbIntensidadAct().getSelectedItem().toString();
		String instalacion = getTfInstalacionAct().getText();
		vA.getControlador().addTipoActividad(selected, nombre, intensidad, instalacion);
	}
	private JScrollPane getSpListaRecursos() {
		if (spListaRecursos == null) {
			spListaRecursos = new JScrollPane();
			spListaRecursos.setBounds(10, 11, 162, 206);
			spListaRecursos.setViewportView(getListRecursos());
		}
		return spListaRecursos;
	}
	private JList getListRecursos() {
		if (listRecursos == null) {
			listRecursos = new JList();
			listRecursos.setFont(new Font("Arial", Font.PLAIN, 12));
		}
		return listRecursos;
	}
	private JTextField getTfNombreAct() {
		if (tfNombreAct == null) {
			tfNombreAct = new JTextField();
			tfNombreAct.setBounds(182, 27, 242, 20);
			tfNombreAct.setColumns(10);
		}
		return tfNombreAct;
	}
	private JLabel getLblNombreAct() {
		if (lblNombreAct == null) {
			lblNombreAct = new JLabel("Nombre de la actividad:");
			lblNombreAct.setFont(new Font("Arial", Font.PLAIN, 12));
			lblNombreAct.setBounds(182, 13, 162, 14);
		}
		return lblNombreAct;
	}
	private JLabel getLblIntensidadAct() {
		if (lblIntensidadAct == null) {
			lblIntensidadAct = new JLabel("Intensidad de la actividad:");
			lblIntensidadAct.setFont(new Font("Arial", Font.PLAIN, 12));
			lblIntensidadAct.setBounds(182, 79, 162, 14);
		}
		return lblIntensidadAct;
	}
	private JComboBox<String> getCbIntensidadAct() {
		if (cbIntensidadAct == null) {
			cbIntensidadAct = new JComboBox<String>();
			cbIntensidadAct.setModel(new DefaultComboBoxModel<String>(new String[] {"Elegir intensidad", "Alta", "Media", "Baja"}));
			cbIntensidadAct.setBounds(182, 97, 242, 22);
		}
		return cbIntensidadAct;
	}
	private JLabel getLblInstalacionAct() {
		if (lblInstalacionAct == null) {
			lblInstalacionAct = new JLabel("Nombre de la instalación:");
			lblInstalacionAct.setFont(new Font("Arial", Font.PLAIN, 12));
			lblInstalacionAct.setBounds(182, 169, 146, 14);
		}
		return lblInstalacionAct;
	}
	private JTextField getTfInstalacionAct() {
		if (tfInstalacionAct == null) {
			tfInstalacionAct = new JTextField();
			tfInstalacionAct.setBounds(182, 186, 242, 20);
			tfInstalacionAct.setColumns(10);
		}
		return tfInstalacionAct;
	}
}