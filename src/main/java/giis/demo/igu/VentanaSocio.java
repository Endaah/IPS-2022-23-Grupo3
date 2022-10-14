package giis.demo.igu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import giis.demo.model.Activity;
import giis.demo.model.ModelSocio;
import javax.swing.ScrollPaneConstants;

public class VentanaSocio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int INITIALDAY = 19;
	private static final int INITIALMONTH = 10;
	private static final int INITIALYEAR = 2022;
	
	private static final int MONTHCORRECTION = 1;
	private static final int YEARCORRECTION = 1900;
	
	private JPanel contentPane;
	
	private ModelSocio model = null;
	private JLabel lblActividades;
	private JLabel lblDia;
	private JSpinner spDay;
	private JLabel lblMonth;
	private JSpinner spMonth;
	private JSpinner spYear;
	private JLabel lblYear;
	private JButton btnFecha;
	private JScrollPane scPaneList;
	private JList<Activity> actList;
	private DefaultListModel<Activity> modelList;

	/**
	 * Create the frame.
	 */
	public VentanaSocio(ModelSocio ms) {
		
		if(ms == null) {
			throw new IllegalArgumentException("El modelo de socio no exixte");
		}
		else {
			model = ms;
		}
		
		setTitle("Aplicacion del gimnasio - Socio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblActividades());
		contentPane.add(getLblDia());
		contentPane.add(getSpDay());
		contentPane.add(getLblMonth());
		contentPane.add(getSpMonth());
		contentPane.add(getSpYear());
		contentPane.add(getLblYear());
		contentPane.add(getBtnFecha());
		contentPane.add(getScPaneList());
		
		this.setVisible(true);
	}
	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades disponibles");
			lblActividades.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblActividades.setBounds(24, 45, 223, 35);
		}
		return lblActividades;
	}
	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Selecciona un dia:");
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDia.setBounds(257, 50, 127, 27);
		}
		return lblDia;
	}
	private JSpinner getSpDay() {
		if (spDay == null) {
			spDay = new JSpinner();
			spDay.setModel(new SpinnerNumberModel(INITIALDAY, 1, 31, 1));
			spDay.setBounds(385, 55, 41, 20);
		}
		return spDay;
	}
	private JLabel getLblMonth() {
		if (lblMonth == null) {
			lblMonth = new JLabel("Selecciona un mes:");
			lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblMonth.setBounds(256, 90, 127, 27);
		}
		return lblMonth;
	}
	private JSpinner getSpMonth() {
		if (spMonth == null) {
			spMonth = new JSpinner();
			spMonth.setModel(new SpinnerNumberModel(INITIALMONTH, 1, 12, 1));
			spMonth.setBounds(385, 95, 41, 20);
		}
		return spMonth;
	}
	
	private JSpinner getSpYear() {
		if (spYear == null) {
			spYear = new JSpinner();
			spYear.setModel(new SpinnerNumberModel(INITIALYEAR, INITIALYEAR, 2023, 1));
			spYear.setBounds(369, 133, 57, 20);
		}
		return spYear;
	}
	private JLabel getLblYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Año:");
			lblYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblYear.setBounds(257, 128, 41, 27);
		}
		return lblYear;
	}
	private JButton getBtnFecha() {
		if (btnFecha == null) {
			btnFecha = new JButton("Ver actividades");
			btnFecha.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int day = (int)spDay.getValue();
					int month = (int)spMonth.getValue();
					int year = (int)spYear.getValue();
					if (!comprobarFecha(day, month, year)) {
						showMessage("Esta fecha no existe, Introduce una fecha correcta",
								"Aviso - Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
					}
					else {
						//Actualizar lista de actividades
						Date date = new Date(year-YEARCORRECTION, month-MONTHCORRECTION, day);
						List <Activity> activities = model.getListActivitiesFor(date);
						modelList.clear();
						modelList.addAll(activities);
					}
				}
			});
			btnFecha.setBackground(Color.WHITE);
			btnFecha.setBounds(257, 166, 107, 23);
		}
		return btnFecha;
	}
	
	
	
	private boolean comprobarFecha(int day, int month, int year) {
		if(day <= 28) {
			return true;
		}
		if(day <= 30 && month != 2) {
			return true;
		}
		if (month != 4 && month != 6 && month != 9 && month != 11 && month != 2) {
			return true;
		}
		return false;
	}
	
	private static void showMessage(String message, String title, int type) {
	    JOptionPane pane = new JOptionPane(message,type,JOptionPane.DEFAULT_OPTION);
	    pane.setOptions(new Object[] {"ACEPTAR"}); //fija este valor para que no dependa del idioma
	    JDialog d = pane.createDialog(pane, title);
	    d.setLocation(200,200);
	    d.setVisible(true);
	}
	private JScrollPane getScPaneList() {
		if (scPaneList == null) {
			scPaneList = new JScrollPane();
			scPaneList.setBounds(34, 95, 189, 306);
			scPaneList.setViewportView(getActList());
		}
		return scPaneList;
	}
	private JList<Activity> getActList() {
		if (actList == null) {
			actList = new JList<Activity>();
			Date date = new Date(INITIALYEAR-YEARCORRECTION, 
					INITIALMONTH-MONTHCORRECTION, INITIALDAY);
			
			modelList = new DefaultListModel<>();
			actList.setModel(modelList);
			
			actList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return actList;
	}
}
