package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.VentanaAdmin;
import giis.demo.model.Instalacion;
import giis.demo.model.Recurso;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class DialogTipoActividad extends JDialog {
	
	private static final long serialVersionUID = 1L;

	private VentanaAdmin vA;
	
	private final JPanel contentPanel = new JPanel();
	private JScrollPane spListaRecursos;
	private JList<Recurso> listRecursos;
	private JTextField tfNombreAct;
	private JLabel lblNombreAct;
	private JLabel lblIntensidadAct;
	private JComboBox<String> cbIntensidadAct;
	private JLabel lblRecursos;
	private JScrollPane spListaInstalaciones;
	private JLabel lblInstalaciones;
	private JList<Instalacion> listInstalaciones;

	/**
	 * Create the dialog.
	 */
	public DialogTipoActividad(VentanaAdmin vA) {
		this.vA = vA;
		setBounds(100, 100, 666, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getSpListaRecursos());
		contentPanel.add(getTfNombreAct());
		contentPanel.add(getLblNombreAct());
		contentPanel.add(getLblIntensidadAct());
		contentPanel.add(getCbIntensidadAct());
		contentPanel.add(getLblRecursos());
		contentPanel.add(getSpListaInstalaciones());
		contentPanel.add(getLblInstalaciones());
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
				|| getCbIntensidadAct().getSelectedItem().toString().equals("Elegir intensidad")) {
			JOptionPane.showMessageDialog(this, "Introduzca un nombre de actividad y seleccione intensidad de la misma");
			return;
		}
		Instalacion i = getListInstalaciones().getSelectedValue();
		if (i == null) {
			JOptionPane.showMessageDialog(this, "Seleccione una instalación donde llevar a cabo la actividad");
			return;
		}
		else {
			for (Recurso r : getListRecursos().getSelectedValuesList()) {
				if (i.getRecurso() != r) {
					JOptionPane.showMessageDialog(this, "El recurso seleccionado debe estar disponible en la instalación seleccionada");
					return;
				}
			}
		}
		crearTipoActividad();
		dispose();
	}
	private void crearTipoActividad() {
		
		List<Recurso> selected = getListRecursos().getSelectedValuesList();
		String nombre = getTfNombreAct().getText();
		String intensidad = getCbIntensidadAct().getSelectedItem().toString();
		String instalacion = getListInstalaciones().getSelectedValue().getNombre();
		vA.getControlador().addTipoActividad(selected, nombre, intensidad, instalacion);
	}
	private JScrollPane getSpListaRecursos() {
		if (spListaRecursos == null) {
			spListaRecursos = new JScrollPane();
			spListaRecursos.setBounds(10, 145, 324, 92);
			spListaRecursos.setViewportView(getListRecursos());
		}
		return spListaRecursos;
	}
	private JList<Recurso> getListRecursos() {
		if (listRecursos == null) {
			listRecursos = new JList<Recurso>();
			listRecursos.setFont(new Font("Arial", Font.PLAIN, 12));
			listRecursos.setModel(getModelRecursos());
		}
		return listRecursos;
	}
	private DefaultListModel<Recurso> getModelRecursos() {
		DefaultListModel<Recurso> model = new DefaultListModel<Recurso>();
		for (Recurso r : vA.getControlador().getRecursosDisponibles()) {
			model.addElement(r);
		}
		return model;
	}
	private JTextField getTfNombreAct() {
		if (tfNombreAct == null) {
			tfNombreAct = new JTextField();
			tfNombreAct.setBounds(367, 75, 242, 20);
			tfNombreAct.setColumns(10);
		}
		return tfNombreAct;
	}
	private JLabel getLblNombreAct() {
		if (lblNombreAct == null) {
			lblNombreAct = new JLabel("Nombre de la actividad:");
			lblNombreAct.setFont(new Font("Arial", Font.PLAIN, 12));
			lblNombreAct.setBounds(367, 50, 162, 14);
		}
		return lblNombreAct;
	}
	private JLabel getLblIntensidadAct() {
		if (lblIntensidadAct == null) {
			lblIntensidadAct = new JLabel("Intensidad de la actividad:");
			lblIntensidadAct.setFont(new Font("Arial", Font.PLAIN, 12));
			lblIntensidadAct.setBounds(367, 142, 162, 14);
		}
		return lblIntensidadAct;
	}
	private JComboBox<String> getCbIntensidadAct() {
		if (cbIntensidadAct == null) {
			cbIntensidadAct = new JComboBox<String>();
			cbIntensidadAct.setModel(new DefaultComboBoxModel<String>(new String[] {"Elegir intensidad", "Alta", "Media", "Baja"}));
			cbIntensidadAct.setBounds(367, 167, 242, 22);
		}
		return cbIntensidadAct;
	}
	private JLabel getLblRecursos() {
		if (lblRecursos == null) {
			lblRecursos = new JLabel("Recursos a usar:");
			lblRecursos.setFont(new Font("Arial", Font.PLAIN, 12));
			lblRecursos.setBounds(10, 131, 162, 14);
		}
		return lblRecursos;
	}
	private JScrollPane getSpListaInstalaciones() {
		if (spListaInstalaciones == null) {
			spListaInstalaciones = new JScrollPane();
			spListaInstalaciones.setBounds(10, 28, 324, 92);
			spListaInstalaciones.setViewportView(getListInstalaciones());
		}
		return spListaInstalaciones;
	}
	private JLabel getLblInstalaciones() {
		if (lblInstalaciones == null) {
			lblInstalaciones = new JLabel("Instalacion a usar:");
			lblInstalaciones.setFont(new Font("Arial", Font.PLAIN, 12));
			lblInstalaciones.setBounds(10, 13, 162, 14);
		}
		return lblInstalaciones;
	}
	private JList<Instalacion> getListInstalaciones() {
		if (listInstalaciones == null) {
			listInstalaciones = new JList<Instalacion>();
			listInstalaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listInstalaciones.setModel(getModelInstalaciones());
		}
		return listInstalaciones;
	}
	private DefaultListModel<Instalacion> getModelInstalaciones() {
		DefaultListModel<Instalacion> model = new DefaultListModel<Instalacion>();
		for (Instalacion i : vA.getControlador().getInstalacionesDisponibles()) {
			model.addElement(i);
		}
		return model;
	}
}
