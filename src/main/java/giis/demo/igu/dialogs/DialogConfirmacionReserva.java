package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DialogConfirmacionReserva extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private DialogReservarInstalacion dri;
	private JTextField tfBuscarSocios;
	
	/**
	 * Create the dialog.
	 */
	public DialogConfirmacionReserva(DialogReservarInstalacion dri) {
		setResizable(false);
		this.dri = dri;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane spSocios = new JScrollPane();
			spSocios.setBounds(10, 11, 414, 67);
			contentPanel.add(spSocios);
			spSocios.setColumnHeaderView(getTfBuscarSocios());
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
	}
	private JTextField getTfBuscarSocios() {
		if (tfBuscarSocios == null) {
			tfBuscarSocios = new JTextField();
			tfBuscarSocios.setColumns(10);
		}
		return tfBuscarSocios;
	}
}
