package giis.demo.igu.dialogs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import giis.demo.igu.VentanaSocio;
import giis.demo.model.Actividad;
import giis.demo.model.data.ModelSocio;

public class DialogVerActividadesSocio extends JDialog {
	
	private ModelSocio model = null;
	private int socioId;
	private DefaultListModel<Actividad> modelList;
	
	private JLabel lblActividadesDelSocio;
	private JScrollPane scPaneList;
	private JList<Actividad> listActividades;
	private JButton btnBorrarse;
	private JButton btnVolver;

	/**
	 * Create the dialog.
	 */
	public DialogVerActividadesSocio(ModelSocio model, int socioId) {
		this.model = model;
		this.socioId = socioId;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		getContentPane().add(getLblActividadesDelSocio());
		getContentPane().add(getScPaneList());
		getContentPane().add(getBtnBorrarse());
		getContentPane().add(getBtnVolver());
		
	}
	private JLabel getLblActividadesDelSocio() {
		if (lblActividadesDelSocio == null) {
			lblActividadesDelSocio = new JLabel("Actividades del socio con id: " + socioId
					+ ", " + model.getNombreSocio(socioId));
			lblActividadesDelSocio.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblActividadesDelSocio.setBounds(10, 21, 270, 32);
		}
		return lblActividadesDelSocio;
	}
	private JScrollPane getScPaneList() {
		if (scPaneList == null) {
			scPaneList = new JScrollPane();
			scPaneList.setBounds(10, 64, 270, 166);
			scPaneList.setViewportView(getListActividades());
		}
		return scPaneList;
	}
	private JList<Actividad> getListActividades() {
		if (listActividades == null) {
			listActividades = new JList<Actividad>();
			
			modelList = new DefaultListModel<>();
			listActividades.setModel(modelList);
			listActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			showActividades();
		}
		return listActividades;
	}
	private void showActividades() {
		List<Actividad> actividades = model.showActividadesActivasForSocio(socioId);
		modelList.clear();
		modelList.addAll(actividades);
	}
	private JButton getBtnBorrarse() {
		if (btnBorrarse == null) {
			btnBorrarse = new JButton("Desapuntarse");
			btnBorrarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminar();
					showActividades();
				}
			});
			btnBorrarse.setBackground(Color.WHITE);
			btnBorrarse.setBounds(300, 89, 126, 32);
		}
		return btnBorrarse;
	}
	private void eliminar() {
		if (listActividades.getSelectedValue() == null ) {
			VentanaSocio.showMessage("Escoja una actividad.",
					"Aviso - Actividad no seleccionada", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int actId = listActividades.getSelectedValue().getId();
		model.eliminarReservaActividad(socioId, actId);
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
			btnVolver.setBounds(300, 190, 126, 32);
		}
		return btnVolver;
	}
}
