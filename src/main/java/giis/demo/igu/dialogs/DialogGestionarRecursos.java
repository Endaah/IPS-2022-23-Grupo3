package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import giis.demo.model.Instalacion;
import giis.demo.model.Recurso;
import giis.demo.model.data.ModelActividadRecursos;

public class DialogGestionarRecursos extends JDialog {
	
	private ModelActividadRecursos model;
	private Instalacion instalacion;
	private String nombreInstalacion;

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNombreInstalacion;
	private JScrollPane scPaneTiene;
	private JScrollPane scPaneNoTiene;
	private JLabel lblTiene;
	private JLabel lblNoTiene;
	private JList<Recurso> listTiene;
	private JList<Recurso> listNoTiene;
	private JButton btnEliminarRecurso;
	private JButton btnAñadirRecurso;
	private JSpinner spCantidad;
	private JLabel lblCantidad;
	private JButton btnVolver;
	

	private DefaultListModel<Recurso> modelListTiene;
	private DefaultListModel<Recurso> modelListNoTiene;

	/**
	 * Create the dialog.
	 */
	public DialogGestionarRecursos(ModelActividadRecursos modelActividadRecursos, Instalacion instalacion) {
		setTitle("Gestion de Recursos");
		model = modelActividadRecursos;
		this.instalacion = instalacion;
		this.nombreInstalacion = instalacion.getNombre();
		
		setBounds(100, 100, 700, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblNombreInstalacion());
		contentPanel.add(getScPaneTiene());
		contentPanel.add(getScPaneNoTiene());
		contentPanel.add(getLblTiene());
		contentPanel.add(getLblNoTiene());
		contentPanel.add(getBtnEliminarRecurso());
		contentPanel.add(getBtnAñadirRecurso());
		contentPanel.add(getSpCantidad());
		contentPanel.add(getLblCantidad());
		contentPanel.add(getBtnVolver());
	}
	private JLabel getLblNombreInstalacion() {
		if (lblNombreInstalacion == null) {
			lblNombreInstalacion = new JLabel("Nombre de la Instalación: " + nombreInstalacion);
			lblNombreInstalacion.setBounds(26, 23, 342, 26);
		}
		return lblNombreInstalacion;
	}
	private JScrollPane getScPaneTiene() {
		if (scPaneTiene == null) {
			scPaneTiene = new JScrollPane();
			scPaneTiene.setBounds(26, 93, 249, 199);
			scPaneTiene.setViewportView(getListTiene());
		}
		return scPaneTiene;
	}
	private JScrollPane getScPaneNoTiene() {
		if (scPaneNoTiene == null) {
			scPaneNoTiene = new JScrollPane();
			scPaneNoTiene.setBounds(309, 93, 249, 199);
			scPaneNoTiene.setViewportView(getListNoTiene());
		}
		return scPaneNoTiene;
	}
	private JLabel getLblTiene() {
		if (lblTiene == null) {
			lblTiene = new JLabel("Recursos que utiliza la instalación");
			lblTiene.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblTiene.setHorizontalAlignment(SwingConstants.CENTER);
			lblTiene.setBounds(26, 57, 249, 26);
		}
		return lblTiene;
	}
	private JLabel getLblNoTiene() {
		if (lblNoTiene == null) {
			lblNoTiene = new JLabel("Recursos que la instalación no utiliza");
			lblNoTiene.setHorizontalAlignment(SwingConstants.CENTER);
			lblNoTiene.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNoTiene.setBounds(309, 57, 249, 26);
		}
		return lblNoTiene;
	}
	private JList<Recurso> getListTiene() {
		if (listTiene == null) {
			listTiene = new JList<Recurso>();
			listTiene.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			modelListTiene = new DefaultListModel<Recurso>();
			listTiene.setModel(modelListTiene);
			showRecursosTiene();
		}
		return listTiene;
	}
	private JList<Recurso> getListNoTiene() {
		if (listNoTiene == null) {
			listNoTiene = new JList<Recurso>();
			listNoTiene.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			modelListNoTiene = new DefaultListModel<Recurso>();
			listNoTiene.setModel(modelListNoTiene);
			showRecursosNoTiene();
		}
		return listNoTiene;
	}
	
	private void actualizarListas() {
		showRecursosTiene();
		showRecursosNoTiene();
	}
	
	private void showRecursosTiene() {
		List <Recurso> recursos = model.getRecursosTiene(nombreInstalacion);
		modelListTiene.clear();
		modelListTiene.addAll(recursos);
	}
	
	private void showRecursosNoTiene() {
		List <Recurso> todos = model.getTodosLosRecursos();
		List <Recurso> recursosTiene = model.getRecursosTiene(nombreInstalacion);
		modelListNoTiene.clear();
		List <Recurso> recursosNoTiene = new ArrayList<>();
		for (Recurso r : todos) {
			if (!recursosTiene.contains(r)) {
				recursosNoTiene.add(r);
			}
		}
		modelListNoTiene.addAll(recursosNoTiene);
	}
	
	private JButton getBtnEliminarRecurso() {
		if (btnEliminarRecurso == null) {
			btnEliminarRecurso = new JButton("Eliminar este recurso");
			btnEliminarRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (listTiene.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(contentPanel, 
								"Seleccione un recurso", "No hay recurso seleccionado", 
								JOptionPane.DEFAULT_OPTION);
						return;
					}
					Recurso eliminar = listTiene.getSelectedValue();
					model.eliminarRecursoDeInstalacion(eliminar.getNombre(), nombreInstalacion);
					actualizarListas();
				}
			});
			btnEliminarRecurso.setBackground(Color.YELLOW);
			btnEliminarRecurso.setBounds(26, 303, 249, 26);
		}
		return btnEliminarRecurso;
	}
	private JButton getBtnAñadirRecurso() {
		if (btnAñadirRecurso == null) {
			btnAñadirRecurso = new JButton("Añadir este recurso");
			btnAñadirRecurso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int cantidad = (int)spCantidad.getValue();
					if (cantidad <= 0) {
						JOptionPane.showMessageDialog(contentPanel, 
								"La cantidad debe ser mayor a 0", "Cantidad erronea", 
								JOptionPane.DEFAULT_OPTION);
						return;
					}
					
					if (listNoTiene.getSelectedValue() == null) {
						JOptionPane.showMessageDialog(contentPanel, 
								"Seleccione un recurso", "No hay recurso seleccionado", 
								JOptionPane.DEFAULT_OPTION);
						return;
					}
					String nombreRecurso = listNoTiene.getSelectedValue().getNombre();
					
					model.AñadirRecursoAInstalacion(nombreRecurso, nombreInstalacion, cantidad);
					actualizarListas();
				}
			});
			btnAñadirRecurso.setBackground(Color.GREEN);
			btnAñadirRecurso.setBounds(378, 303, 180, 26);
		}
		return btnAñadirRecurso;
	}
	private JSpinner getSpCantidad() {
		if (spCantidad == null) {
			spCantidad = new JSpinner();
			spCantidad.setModel(new SpinnerNumberModel(1, 1, null, 1));
			spCantidad.setBounds(309, 312, 59, 26);
		}
		return spCantidad;
	}
	private JLabel getLblCantidad() {
		if (lblCantidad == null) {
			lblCantidad = new JLabel("Cantidad:");
			lblCantidad.setBounds(309, 291, 59, 14);
		}
		return lblCantidad;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVolver.setBackground(Color.RED);
			btnVolver.setBounds(585, 305, 89, 23);
		}
		return btnVolver;
	}
}
