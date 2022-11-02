package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.Actividad;
import giis.demo.model.ReservaInstalacion;
import giis.demo.model.data.ModelSocio;

public class DialogVerReservasSocio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private int userId;
	private ModelSocio model;
	private JScrollPane scPaneLista;
	private JList listReservas;
	private JLabel lblTitulo;
	private JButton btnEliminarReserva;
	
	private DefaultListModel<ReservaInstalacion> modelList;

	/**
	 * Create the dialog.
	 */
	public DialogVerReservasSocio(ModelSocio model, int userId) {
		this.userId = userId;
		this.model = model;
		
		setBounds(100, 100, 590, 398);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getScPaneLista());
		contentPanel.add(getLblTitulo());
		contentPanel.add(getBtnEliminarReserva());
	}
	private JScrollPane getScPaneLista() {
		if (scPaneLista == null) {
			scPaneLista = new JScrollPane();
			scPaneLista.setBounds(50, 76, 303, 249);
			scPaneLista.setViewportView(getListReservas());
		}
		return scPaneLista;
	}
	private JList getListReservas() {
		if (listReservas == null) {
			listReservas = new JList();
			
			modelList = new DefaultListModel<>();
			listReservas.setModel(modelList);
			listReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			showReservas();
		}
		return listReservas;
	}
	
	private void showReservas() {
		List<ReservaInstalacion> reservas = model.getListReservasFor(userId);
		modelList.clear();
		modelList.addAll(reservas);
	}
	
	private JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel("Reservas del socio con id: " + userId + ", " + model.getNombreSocio(userId));
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblTitulo.setBounds(50, 22, 255, 32);
		}
		return lblTitulo;
	}
	private JButton getBtnEliminarReserva() {
		if (btnEliminarReserva == null) {
			btnEliminarReserva = new JButton("Anular Reserva");
			btnEliminarReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnEliminarReserva.setBackground(Color.WHITE);
			btnEliminarReserva.setBounds(379, 126, 185, 57);
		}
		return btnEliminarReserva;
	}
}
