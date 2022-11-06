package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.VentanaSocio;
import giis.demo.model.ReservaInstalacion;
import giis.demo.model.data.ModelSocio;
import javax.swing.JTextField;

public class DialogVerReservasSocio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private int userId;
	private ModelSocio model;
	private JScrollPane scPaneLista;
	private JList<ReservaInstalacion> listReservas;
	private JLabel lblTitulo;
	private JButton btnEliminarReserva;
	
	private DefaultListModel<ReservaInstalacion> modelList;
	private JButton btnVolver;
	private JLabel lblNewLabel;
	private JTextField txPrecio;

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
		contentPanel.add(getBtnVolver());
		contentPanel.add(getLblNewLabel());
		contentPanel.add(getTxPrecio());
	}
	private JScrollPane getScPaneLista() {
		if (scPaneLista == null) {
			scPaneLista = new JScrollPane();
			scPaneLista.setBounds(50, 76, 303, 249);
			scPaneLista.setViewportView(getListReservas());
		}
		return scPaneLista;
	}
	private JList<ReservaInstalacion> getListReservas() {
		if (listReservas == null) {
			listReservas = new JList<>();
			
			modelList = new DefaultListModel<>();
			listReservas.setModel(modelList);
			listReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			showReservas();
		}
		return listReservas;
	}
	
	private void showPrecio() {
		int precio = 0;
		for(int i = 0; i < model.getListReservasFor(userId).size(); i++) {
			precio = precio + model.getListReservasFor(userId).get(i).getPrecio();
		}
		txPrecio.setText(precio+"€");
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
			btnEliminarReserva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					borrarReserva();
					showReservas();
				}
			});
			btnEliminarReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnEliminarReserva.setBackground(Color.WHITE);
			btnEliminarReserva.setBounds(379, 126, 185, 57);
		}
		return btnEliminarReserva;
	}
	
	
	private void borrarReserva() {
		int res = JOptionPane.showConfirmDialog(
				rootPane, "¿Está seguro que quiere anular su reserva?");
		if (res != JOptionPane.YES_OPTION) {
			return;
		}
		ReservaInstalacion reserva = listReservas.getSelectedValue();
		
		if (!model.checkPuedoBorrarReserva(reserva.getFecha())) {
			VentanaSocio.showMessage("Puede anular como muy tarde el dia anterior a la reserva", 
					"No puede anular la reserva", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		model.anularReservaInstalacion(
				reserva.getInstalacion(), reserva.getFecha(), reserva.getHora());
	}
	
	
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnVolver.setBackground(Color.RED);
			btnVolver.setBounds(379, 268, 185, 57);
		}
		return btnVolver;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Importe restante:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel.setBounds(359, 32, 109, 22);
		}
		return lblNewLabel;
	}
	private JTextField getTxPrecio() {
		if (txPrecio == null) {
			txPrecio = new JTextField();
			txPrecio.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txPrecio.setEditable(false);
			txPrecio.setBounds(468, 22, 65, 47);
			txPrecio.setColumns(10);
		}
		return txPrecio;
	}
}