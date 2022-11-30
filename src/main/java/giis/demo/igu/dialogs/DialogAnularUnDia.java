package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.util.Db;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DialogAnularUnDia extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JCalendar calendar;
	private JLabel lblNewLabel;


	/**
	 * Create the dialog.
	 */
	public DialogAnularUnDia() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						anularDia();
					}
				});
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
		contentPanel.add(getCalendar());
		contentPanel.add(getLblNewLabel());
	}
	
	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar(new Locale("es","ES"));
			calendar.setBounds(97, 60, 222, 120);
			calendar.setMinSelectableDate(new Date(System.currentTimeMillis()));
			calendar.getDayChooser().addDateEvaluator(new EvaluadorDeDias(Arrays.asList(DayOfWeek.MONDAY)));
			calendar.setCalendar(Calendar.getInstance());
			
		}
		return calendar;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Elija un dia:");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel.setBounds(149, 10, 181, 38);
		}
		return lblNewLabel;
	}
	
	private void anularDia() {
		String confirmacion = "Se cancelarán las siguientes actividades:\n"; 
		LocalDate l = LocalDate.of(calendar.getYearChooser().getYear(),calendar.getMonthChooser().getMonth(),calendar.getDayChooser().getDay());
		for (Actividad a : Db.getActividadesDia(l)) {
			confirmacion += a.toString() + "\n";
		}
		
		for (Actividad a : Db.getActividadesDia(l)) {
			GymControlador.anularActividad(a);
			if (!GymControlador.getSociosDe(a).isBlank()) {
				String res = "Socios apuntados a la actividad " + a.toString() + "\n" + GymControlador.getSociosDe(a);
				JOptionPane.showMessageDialog(this, res);
			}
		}
	}
}
