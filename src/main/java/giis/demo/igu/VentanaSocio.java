package giis.demo.igu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import giis.demo.igu.dialogs.DialogReservaInstalacionSocio;
import giis.demo.igu.dialogs.DialogVerActividadesSocio;
import giis.demo.igu.dialogs.DialogVerReservasSocio;
import giis.demo.model.Actividad;
import giis.demo.model.GrupoReservas;
import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.ReservaInstalacion;
import giis.demo.model.data.ModelSocio;
import javax.swing.JTextArea;

public class VentanaSocio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Calendar today = Calendar.getInstance();
	
	public static final int MONTHCORRECTION = 1;
	public static final int YEARCORRECTION = 1900;
	
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
	private JList<Actividad> actList;
	private DefaultListModel<Actividad> modelList;
	private JButton btnApuntarse;
	private JButton btnBorrar;
	private JButton btnReservarInstalacion;
	private JButton btnVerReservas;
	private JLabel lblInstalacionesDisponibles;
	private JScrollPane scPaneList_1;
	private JList<Instalacion> actList1;
	private DefaultListModel<Instalacion> modelList1;
	private DefaultListModel<Integer> modelo;
	private JButton btnVerInstalaciones;
	private JButton btnVerActividades;
	private JScrollPane scrollPane;
	private JList<Integer> listHoras;
	private JLabel lblNewLabel_1;
	private JButton btnDisponibilidad;

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
		
		today.setTime(new Date(System.currentTimeMillis()));
		
		setTitle("Aplicacion del gimnasio - Socio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 550);
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
		contentPane.add(getBtnApuntarse());
//		contentPane.add(getBtnBorrar());
		contentPane.add(getBtnReservarInstalacion());
		contentPane.add(getBtnVerReservas());
		contentPane.add(getLblInstalacionesDisponibles());
		contentPane.add(getScPaneList_1());
		contentPane.add(getBtnVerInstalaciones());
		contentPane.add(getBtnVerActividades());
		contentPane.add(getScrollPane());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getBtnDisponibilidad());
		
		this.setVisible(true);
	}
	private JLabel getLblActividades() {
		if (lblActividades == null) {
			lblActividades = new JLabel("Actividades disponibles");
			lblActividades.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblActividades.setBounds(34, 46, 223, 35);
		}
		return lblActividades;
	}
	private JLabel getLblDia() {
		if (lblDia == null) {
			lblDia = new JLabel("Selecciona un dia:");
			lblDia.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblDia.setBounds(456, 55, 127, 27);
		}
		return lblDia;
	}
	private JSpinner getSpDay() {
		if (spDay == null) {
			spDay = new JSpinner();
			spDay.setModel(new SpinnerNumberModel(today.getTime().getDate(), 1, 31, 1));
			spDay.setBounds(584, 61, 41, 20);
		}
		return spDay;
	}
	private JLabel getLblMonth() {
		if (lblMonth == null) {
			lblMonth = new JLabel("Selecciona un mes:");
			lblMonth.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblMonth.setBounds(456, 92, 127, 27);
		}
		return lblMonth;
	}
	private JSpinner getSpMonth() {
		if (spMonth == null) {
			spMonth = new JSpinner();
			spMonth.setModel(new SpinnerNumberModel(today.getTime().getMonth()+MONTHCORRECTION, 1, 12, 1));
			spMonth.setBounds(584, 98, 41, 20);
		}
		return spMonth;
	}
	
	private JSpinner getSpYear() {
		if (spYear == null) {
			spYear = new JSpinner();
			spYear.setModel(new SpinnerNumberModel(today.getTime().getYear()+YEARCORRECTION,
					today.getTime().getYear()+YEARCORRECTION, 2023, 1));
			spYear.setBounds(568, 135, 57, 20);
		}
		return spYear;
	}
	private JLabel getLblYear() {
		if (lblYear == null) {
			lblYear = new JLabel("Año:");
			lblYear.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblYear.setBounds(456, 129, 41, 27);
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
					showActivities(year, month, day);
				}
			});
			btnFecha.setBackground(Color.WHITE);
			btnFecha.setBounds(290, 55, 142, 23);
		}
		return btnFecha;
	}
	
	
	private JScrollPane getScPaneList() {
		if (scPaneList == null) {
			scPaneList = new JScrollPane();
			scPaneList.setBounds(34, 95, 398, 108);
			scPaneList.setViewportView(getActList());
		}
		return scPaneList;
	}
	private JList<Actividad> getActList() {
		if (actList == null) {
			actList = new JList<Actividad>();
			Date date = today.getTime();
			
			modelList = new DefaultListModel<>();
			actList.setModel(modelList);
			
			actList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			showActivities(date.getYear()+YEARCORRECTION, date.getMonth()+MONTHCORRECTION, date.getDate());
		}
		return actList;
	}
	private void showActivities(int year, int month, int day) {
		if (!model.comprobarFechaCorrecta(day, month, year)) {
			showMessage("Esta fecha no existe, Introduce una fecha correcta",
					"Aviso - Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
		}
		else {
			//Actualizar lista de actividades
			LocalDate date = LocalDate.of(year, month, day);
			List <Actividad> activities = model.getListActivitiesFor(java.sql.Date.valueOf(date));
			modelList.clear();
			modelList.addAll(activities);
		}
	}

	
	public static void showMessage(String message, String title, int type) {
	    JOptionPane pane = new JOptionPane(message,type,JOptionPane.DEFAULT_OPTION);
	    pane.setOptions(new Object[] {"ACEPTAR"}); //fija este valor para que no dependa del idioma
	    JDialog d = pane.createDialog(pane, title);
	    d.setLocation(200,200);
	    d.setVisible(true);
	}
	private JButton getBtnApuntarse() {
		if (btnApuntarse == null) {
			btnApuntarse = new JButton("Apuntarse");
			btnApuntarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reservar();
				}
			});
			btnApuntarse.setBackground(Color.GREEN);
			btnApuntarse.setBounds(34, 213, 142, 35);
		}
		return btnApuntarse;
	}
	
	private void reservar() {
		if (actList.getSelectedValue() == null ||
				actList.getSelectedValue().getPlazas() == Actividad.ACTIVIDADILIMITADA) {
			showMessage("Asegurese de que ha escogido una actividad con limite de plazas.",
					"Aviso - Actividad no reservable", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (actList.getSelectedValue().getPlazas() == 0) {
			showMessage("Esta actividad esta completa",
					"Aviso - Actividad sin plazas libres", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Actividad actividad = actList.getSelectedValue();
		if (!model.checkPuedoApuntarme(actividad.getDia(), actividad.getIni())) {
			showMessage("No puedes apuntarte a esta actividad, debe ser maximo un "
					+ "dia antes y minimo una hora antes de comenzar", 
					"Aviso - Imposible apuntar", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int actId = actList.getSelectedValue().getId();
		int userId = model.askForIdSocio();
		if (!model.checkSocioPuedeApuntarse(actividad.getDia(), actividad.getIni(), 
				actividad.getFin(), userId)) {
			showMessage("No puedes apuntarte a esta actividad, estas opcupado!!!", 
					"Aviso - Imposible apuntar", JOptionPane.WARNING_MESSAGE);
			return;
		}
		model.reservarActividad(actId, userId);
		model.restarPlaza(actId);
	}
	
//	private JButton getBtnBorrar() {
//		if (btnBorrar == null) {
//			btnBorrar = new JButton("Borrarme");
//			btnBorrar.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					eliminar();
//				}
//			});
//			btnBorrar.setBackground(Color.RED);
//			btnBorrar.setBounds(290, 213, 142, 34);
//		}
//		return btnBorrar;
//	}
	
//	private void eliminar() {
//		if (actList.getSelectedValue() == null ||
//				actList.getSelectedValue().getPlazas() == Actividad.ACTIVIDADILIMITADA) {
//			showMessage("Asegurese de que ha escogido una actividad con limite de plazas.",
//					"Aviso - Actividad no reservable", JOptionPane.WARNING_MESSAGE);
//			return;
//		}
//		int actId = actList.getSelectedValue().getId();
//		int userId = model.askForIdSocio();
//		model.eliminarReservaActividad(userId, actId);
//	}
	private JButton getBtnReservarInstalacion() {
		if (btnReservarInstalacion == null) {
			btnReservarInstalacion = new JButton("Reservar Instalación");
			btnReservarInstalacion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reservar1();
				}
			});
			btnReservarInstalacion.setBackground(Color.GREEN);
			btnReservarInstalacion.setBounds(34, 444, 142, 35);
		}
		return btnReservarInstalacion;
	}
	
	private void reservar1() {
		try {
			int userId = model.askForIdSocio();
			//Mario, cuando hagas el ver instalaciones, conecta esto 
			//a la instalación seleccionada, dia y hora de los spinners
			LocalDate d = LocalDate.of((int)spYear.getValue(), (int)spMonth.getValue(), (int)spDay.getValue());
			if(!getActList1().getSelectedValue().getAbierta()) {
				JOptionPane.showMessageDialog(this, "La instalación se encuentra cerrada");
			}else {
			DialogReservaInstalacionSocio dialog = 
					new DialogReservaInstalacionSocio(model, 
							getActList1().getSelectedValue(),d,getListHoras().getSelectedValue(),userId);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setLocationRelativeTo(contentPane);
			dialog.setModal(true);
			dialog.setVisible(true);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	private JButton getBtnVerReservas() {
		if (btnVerReservas == null) {
			btnVerReservas = new JButton("Ver mis reservas");
			btnVerReservas.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int userId = model.askForIdSocio();
					DialogVerReservasSocio dialog = new DialogVerReservasSocio(model, userId);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(contentPane);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
			});
			btnVerReservas.setBackground(Color.WHITE);
			btnVerReservas.setBounds(456, 430, 169, 35);
		}
		return btnVerReservas;
	}
	private JLabel getLblInstalacionesDisponibles() {
		if (lblInstalacionesDisponibles == null) {
			lblInstalacionesDisponibles = new JLabel("Instalaciones disponibles");
			lblInstalacionesDisponibles.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblInstalacionesDisponibles.setBounds(34, 261, 245, 35);
		}
		return lblInstalacionesDisponibles;
	}
	private JScrollPane getScPaneList_1() {
		if (scPaneList_1 == null) {
			scPaneList_1 = new JScrollPane();
			scPaneList_1.setBounds(34, 306, 398, 108);
			scPaneList_1.setViewportView(getActList1());
		}
		return scPaneList_1;
	}
	private JList<Instalacion> getActList1() {
		if (actList1 == null) {
			Date date = today.getTime();
			actList1 = new JList<Instalacion>();
			modelList1 = new DefaultListModel<>();
			actList1.setModel(modelList1);
			
			actList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			showInstalaciones(date.getYear()+YEARCORRECTION, date.getMonth()+MONTHCORRECTION, date.getDate());
		}
		return actList1;
	}
	private JButton getBtnVerInstalaciones() {
		if (btnVerInstalaciones == null) {
			btnVerInstalaciones = new JButton("Ver instalaciones");
			btnVerInstalaciones.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showInstalaciones((int)spYear.getValue(), (int)spMonth.getValue(),(int)spDay.getValue());
				}
			});
			btnVerInstalaciones.setBackground(Color.WHITE);
			btnVerInstalaciones.setBounds(290, 270, 142, 23);
		}
		return btnVerInstalaciones;
	}
	
	private void showInstalaciones(int year, int month, int day) {
		if (!model.comprobarFechaCorrecta(day, month, year)) {
			showMessage("Esta fecha no existe, Introduce una fecha correcta",
					"Aviso - Fecha incorrecta", JOptionPane.WARNING_MESSAGE);
		}
		else {
			LocalDate date = LocalDate.of(year, month, day);
			List<Instalacion> ins = new ArrayList<Instalacion>();
			Instalacion[] instalaciones =  GymControlador.getInstalaciones().values().toArray(
					new Instalacion[GymControlador.getInstalaciones().values().size()]);
			for(int i =0; i < instalaciones.length; i++) {
				ins.add(instalaciones[i]);
			}
			modelList1.clear();
			modelList1.addAll(ins);
			
			
		}
	}
	private JButton getBtnVerActividades() {
		if (btnVerActividades == null) {
			btnVerActividades = new JButton("Ver mis actividades");
			btnVerActividades.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int userId = model.askForIdSocio();
					DialogVerActividadesSocio dialog = new DialogVerActividadesSocio(model, userId);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(contentPane);
					dialog.setModal(true);
					dialog.setVisible(true);
				}
			});
			btnVerActividades.setBackground(Color.WHITE);
			btnVerActividades.setBounds(456, 168, 169, 27);
		}
		return btnVerActividades;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(456, 306, 169, 56);
			scrollPane.setViewportView(getListHoras());
		}
		return scrollPane;
	}
	private JList<Integer> getListHoras() {
		if (listHoras == null) {
			listHoras = new JList<Integer>();
			
			modelo = new DefaultListModel<>();
			listHoras.setModel(modelo);
			
			listHoras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return listHoras;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Horas disponibles :");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
			lblNewLabel_1.setBounds(456, 275, 113, 20);
		}
		return lblNewLabel_1;
	}
	private JButton getBtnDisponibilidad() {
		if (btnDisponibilidad == null) {
			btnDisponibilidad = new JButton("Ver disponibilidad");
			btnDisponibilidad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showHoras();
				}

			});
			btnDisponibilidad.setBounds(456, 372, 169, 27);
		}
		return btnDisponibilidad;
	}
	
	private void showHoras() {
		modelo.removeAllElements();
		Instalacion ins = getActList1().getSelectedValue();
		GrupoReservas[] g = ins.getReservas();
		ArrayList<ReservaInstalacion> r = new ArrayList<ReservaInstalacion>();
		ArrayList<ReservaInstalacion> r2 = new ArrayList<ReservaInstalacion>();
		for(GrupoReservas g1 : g) {
			for(ReservaInstalacion r1 : g1.getReservas()) {
				r.add(r1);
				r2.add(r1);
			}
		}
		int a = r.size();
		LocalDate date = LocalDate.of((int)spYear.getValue(), (int)spMonth.getValue(),(int)spDay.getValue());
		for(int i = 0; i < a ;i++) {
			if(r2.get(i).getFecha() != date) {
				r.remove(r2.get(i));
			}
		}
		
		ArrayList<Integer> i = new ArrayList<Integer>();
		for(int j = 8; j <23 ; j++){
			i.add(j);
		}
		for(ReservaInstalacion r1 : r) {
			for(Integer u : i) {
				if(r1.getHora() == u) {
					i.remove(u);
				}
			}
		}
		
		modelo.addAll(i);
		
	}
}