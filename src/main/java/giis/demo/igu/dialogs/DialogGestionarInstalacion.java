package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.Socio;
import giis.demo.model.TipoActividad;
import giis.demo.util.Db;

import java.awt.GridLayout;
import java.util.Collection;
import java.util.List;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class DialogGestionarInstalacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox cmbInstalaciones;
	private JLabel lblNewLabel;
	private JButton btnAbrir;
	private JButton btnCerrar;

	

	/**
	 * Create the dialog.
	 */
	public DialogGestionarInstalacion() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.WHITE);
			contentPanel.add(panel);
			panel.setLayout(null);
			panel.add(getCmbInstalaciones());
			panel.add(getLblNewLabel());
			panel.add(getBtnAbrir());
			panel.add(getBtnCerrar());
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
		actualizarCombo();
	}
	private JComboBox getCmbInstalaciones() {
		if (cmbInstalaciones == null) {
			cmbInstalaciones = new JComboBox();
			cmbInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actualizarBotones();
				}
			});
			cmbInstalaciones.setBounds(88, 51, 247, 40);
		}
		return cmbInstalaciones;
	}
	
	private void actualizarCombo() {
		Collection<Instalacion> tipos = GymControlador.getInstalacionesDisponibles().values();
		getCmbInstalaciones().setModel(new DefaultComboBoxModel<Instalacion>(tipos.toArray(new Instalacion[tipos.size()])));
		
	}
	
	private void actualizarBotones() {
		if(((Instalacion)cmbInstalaciones.getSelectedItem()).getAbierta()) {
			btnCerrar.setEnabled(true);
			btnAbrir.setEnabled(false);
		}else {
			btnCerrar.setEnabled(false);
			btnAbrir.setEnabled(true);
		}
	}
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Instalaciones disponibles:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNewLabel.setBounds(135, 10, 247, 31);
		}
		return lblNewLabel;
	}
	private JButton getBtnAbrir() {
		if (btnAbrir == null) {
			btnAbrir = new JButton("Abrir");
			btnAbrir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((Instalacion)cmbInstalaciones.getSelectedItem()).abrir(true);
					Db.abrirInstalacion(((Instalacion)cmbInstalaciones.getSelectedItem()).getNombre());
					GymControlador.abrirInstalacion(((Instalacion)cmbInstalaciones.getSelectedItem()));
				}
			});
			btnAbrir.setBounds(88, 113, 110, 40);
		}
		return btnAbrir;
	}
	private JButton getBtnCerrar() {
		if (btnCerrar == null) {
			btnCerrar = new JButton("Cerrar");
			btnCerrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Date d = new Date(System.currentTimeMillis());
					LocalDate t = d.toLocalDate();
					List<Socio> socios = Db.getSociosActividadIlimitado(t.getDayOfMonth());
					try {
						DialogSociosEliminados dialog = new DialogSociosEliminados(socios);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					Db.desapuntarInstalacion(d, (Instalacion)cmbInstalaciones.getSelectedItem());
					Db.borrarActividades(d,(Instalacion)cmbInstalaciones.getSelectedItem());
					((Instalacion)cmbInstalaciones.getSelectedItem()).abrir(false);
					Db.cerrarInstalacion(((Instalacion)cmbInstalaciones.getSelectedItem()).getNombre());
					GymControlador.cerrarInstalacion((Instalacion)cmbInstalaciones.getSelectedItem());
				}
			});
			btnCerrar.setBounds(223, 113, 112, 40);
		}
		return btnCerrar;
	}
}
