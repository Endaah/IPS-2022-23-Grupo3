package giis.demo.igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.ModelSocio;

public class VentanaAcceso extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JPanel panelBotones;
	private JButton btnAdministrador;
	private JButton btnSocio;


	/**
	 * Create the frame.
	 */
	public VentanaAcceso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
		
		this.setVisible(true);
		
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			panel.add(getLblNewLabel());
			panel.add(getPanelBotones());
		}
		return panel;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Como quieres acceder :");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel.setBounds(113, 45, 198, 37);
		}
		return lblNewLabel;
	}
	private JPanel getPanelBotones() {
		if (panelBotones == null) {
			panelBotones = new JPanel();
			panelBotones.setBackground(Color.WHITE);
			panelBotones.setBounds(89, 111, 249, 81);
			panelBotones.setLayout(new GridLayout(0, 2, 0, 0));
			panelBotones.add(getBtnAdministrador());
			panelBotones.add(getBtnSocio());
		}
		return panelBotones;
	}
	private JButton getBtnAdministrador() {
		if (btnAdministrador == null) {
			btnAdministrador = new JButton("Administrador");
			btnAdministrador.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btnAdministrador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return btnAdministrador;
	}
	private JButton getBtnSocio() {
		if (btnSocio == null) {
			btnSocio = new JButton("Socio");
			btnSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new VentanaSocio(new ModelSocio());
				}
			});
			btnSocio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		}
		return btnSocio;
	}
}
