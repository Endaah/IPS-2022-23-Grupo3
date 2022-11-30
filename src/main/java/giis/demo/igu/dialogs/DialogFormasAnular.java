package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.igu.VentanaAdmin;
import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.util.Db;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class DialogFormasAnular extends JDialog {

	private VentanaAdmin vA;
	private Actividad act;
	
	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtPuntual;
	private JRadioButton rdbtGrupo;
	private JRadioButton rdbtDia;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public DialogFormasAnular(VentanaAdmin vA, Actividad a) {
		this.vA = vA;
		this.act = a;
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblFormas = new JLabel("¿Qué quieres anular?");
			lblFormas.setHorizontalAlignment(SwingConstants.CENTER);
			lblFormas.setFont(new Font("Arial Black", Font.BOLD, 14));
			lblFormas.setBounds(10, 11, 414, 82);
			contentPanel.add(lblFormas);
		}
		contentPanel.add(getRdbtPuntual());
		contentPanel.add(getRdbtGrupo());
		contentPanel.add(getRdbtDia());
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
			anularActividadPuntual();
		} else if (getRdbtGrupo().isSelected()) {
			anularActividadGrupo();
		} else if (getRdbtDia().isSelected()) {
			try {
				DialogAnularUnDia dialog = new DialogAnularUnDia();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			vA.actualizarListaActividades();
			
		} else {
			JOptionPane.showMessageDialog(this, "Escoge una opción para continuar");
			return;
		} dispose();
	}
	private void anularActividadPuntual() {
		if (act != null) {
			if (!GymControlador.anularActividad(act)) {
				JOptionPane.showMessageDialog(this, "No se puede anular una actividad pasada");
				return;
			}
			if (act.getPlazas() != -1 && !GymControlador.getSociosDe(act).isBlank()) {
				String res = "Socios apuntados a la actividad " + act.toString() + "\n" + GymControlador.getSociosDe(act);
				JOptionPane.showMessageDialog(this, res);
			}
			vA.actualizarListaActividades();
		}
	}
	private void anularActividadGrupo() {
		String confirmacion = "Se cancelarán las siguientes actividades:\n";
		for (Actividad a : Db.getActividadesDelGrupo(act.getGrupo())) {
			confirmacion += a.toString() + "\n";
		}
		int opcion = JOptionPane.showConfirmDialog(this, confirmacion);
		if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.CANCEL_OPTION)
			return;
			
		
		
		if (act != null) {
			for (Actividad a : Db.getActividadesDelGrupo(act.getGrupo())) {
				GymControlador.anularActividad(a);
				if (act.getPlazas() != -1 && !GymControlador.getSociosDe(a).isBlank()) {
					String res = "Socios apuntados a la actividad " + a.toString() + "\n" + GymControlador.getSociosDe(a);
					JOptionPane.showMessageDialog(this, res);
				}
			}
		}
		vA.actualizarListaActividades();
	}
	
	private JRadioButton getRdbtPuntual() {
		if (rdbtPuntual == null) {
			rdbtPuntual = new JRadioButton("La actividad seleccionada");
			buttonGroup.add(rdbtPuntual);
			rdbtPuntual.setBounds(119, 100, 178, 23);
		}
		return rdbtPuntual;
	}
	private JRadioButton getRdbtGrupo() {
		if (rdbtGrupo == null) {
			rdbtGrupo = new JRadioButton("El grupo de la actividad seleccionada");
			buttonGroup.add(rdbtGrupo);
			rdbtGrupo.setBounds(119, 139, 208, 23);
		}
		return rdbtGrupo;
	}
	private JRadioButton getRdbtDia() {
		if (rdbtDia == null) {
			rdbtDia = new JRadioButton("Las actividades de un día");
			buttonGroup.add(rdbtDia);
			rdbtDia.setBounds(119, 179, 178, 23);
		}
		return rdbtDia;
	}
}
