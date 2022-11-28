package giis.demo.igu.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;

import giis.demo.igu.VentanaAdmin;
import giis.demo.model.Actividad;
import giis.demo.model.GymControlador;
import giis.demo.model.Instalacion;
import giis.demo.model.TipoActividad;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class DialogActividad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel_1;
	private JCalendar calendar;
	private JLabel lblNewLabel_2;
	private JSpinner spnIni;
	private JSpinner spnFin;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JSpinner spnPlazas;
	private JLabel lblNewLabel_5;
	private VentanaAdmin vA;
	private JComboBox cmbTipos;
	private JLabel lblNewLabel;
	private JComboBox cmbIns;

	

	/**
	 * Create the dialog.
	 */
	public DialogActividad(VentanaAdmin v) {
		setResizable(false);
		setBounds(100, 100, 586, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		this.vA = v;
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblNewLabel_1());
		contentPanel.add(getCalendar());
		contentPanel.add(getLblNewLabel_2());
		contentPanel.add(getSpnIni());
		contentPanel.add(getSpnFin());
		contentPanel.add(getLblNewLabel_3());
		contentPanel.add(getLblNewLabel_4());
		contentPanel.add(getSpnPlazas());
		cargarTipos();
		cargarIns();
		contentPanel.add(getLblNewLabel_5());
		contentPanel.add(getCmbTipos());
		contentPanel.add(getLblNewLabel());
		contentPanel.add(getCmbIns());
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
		if (!comprobarRecurso1()) {
			JOptionPane.showMessageDialog(this, "Elija una instalación que tenga esos recursos");
			return;
		}
		
		if (!comprobarDisponibilidad()) {
			JOptionPane.showMessageDialog(this, "No se pudo crear la actividad, ya hay otra planificada a esa hora");
			return;
		}
		
		if (!comprobarFecha()) {
			JOptionPane.showMessageDialog(this, "No se pudo crear la actividad, fecha errónea");
			return;
		}
		crearActividad();
		vA.actualizarListaActividades();
		dispose();
	}
	private boolean comprobarRecurso1() {
		for(int i = 0; i < ((TipoActividad)getCmbTipos().getSelectedItem()).getRecurso().size();i++) {
			boolean x = false;
			for(int j = 0; j < ((Instalacion)getCmbIns().getSelectedItem()).getRecurso().length; j++) {
				if(((TipoActividad)getCmbTipos().getSelectedItem()).getRecurso().get(i).getNombre() == 
						 ((Instalacion)getCmbIns().getSelectedItem()).getRecurso()[j].getNombre()) {
					x = true;
				}
			}
			if(x == false) {
				return false;
			}
		}
		return true;
	}
	private boolean comprobarFecha() {
		LocalDate d = LocalDate.now();
		java.sql.Date dia = new Date(getCalendar().getDate().getTime());
		LocalDate fecha = dia.toLocalDate();
		if(fecha.getYear() < d.getYear()) {
			
			return false;
		}else if((fecha.getYear() == d.getYear()) && (fecha.getMonthValue() < d.getMonthValue())){
			
			return false;
		}else if((fecha.getYear() == d.getYear()) && (fecha.getMonthValue() == d.getMonthValue()) && (fecha.getDayOfMonth() < d.getDayOfMonth())) {
			
			return false;
		}
		return true;
	}
	private boolean comprobarDisponibilidad() {
		Instalacion i = (Instalacion) getCmbIns().getSelectedItem();
		java.sql.Date dia = new Date(getCalendar().getDate().getTime());
		return GymControlador.comprobarDisponibilidad(i, dia.toLocalDate(), (int)getSpnIni().getValue(), (int)getSpnFin().getValue());
	}
	private void cargarTipos() {
		Collection<TipoActividad> tipos = GymControlador.getTiposActividadDisponibles().values();
		getCmbTipos().setModel(new DefaultComboBoxModel<TipoActividad>(tipos.toArray(new TipoActividad[tipos.size()])));
	}
	
	private void cargarIns() {
		Collection<Instalacion> tipos = GymControlador.getInstalacionesDisponibles().values();
		getCmbIns().setModel(new DefaultComboBoxModel<Instalacion>(tipos.toArray(new Instalacion[tipos.size()])));
	}
	
	private void crearActividad() {
		TipoActividad ta = (TipoActividad) getCmbTipos().getSelectedItem();
		String nombre = ta.getNombre();
		int id = (GymControlador.getActividadesExistentes().get(GymControlador.getActividadesExistentes().size() - 1).getId())+1;
		java.sql.Date date = new java.sql.Date(calendar.getDate().getTime());
		int hini = (int)getSpnIni().getValue();
		int hfin = (int)getSpnFin().getValue();
		int plazas = Actividad.ACTIVIDADILIMITADA;
		Instalacion instalacion = ((Instalacion) getCmbIns().getSelectedItem());
		if (instalacion.getRecurso().length >= 0) {
			int menor = Integer.MAX_VALUE;
			for (int i : instalacion.getCantidades().values()) {
				menor = menor > i ? i : menor;
			} plazas = menor;
		} else {
			plazas = (int) getSpnPlazas().getValue();
		}
		// TODO: Añadir seleccion de instalacion y sustituir el null por el nombre en este método addActividad() v
		GymControlador.addActividad(id,nombre,date,hini,hfin,plazas,instalacion.getNombre());
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(41, 27, 71, 19);
		}
		return lblNewLabel_1;
	}
	private JCalendar getCalendar() {
		if (calendar == null) {
			calendar = new JCalendar();
			calendar.setBounds(41, 96, 157, 91);
		}
		return calendar;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("Fecha:");
			lblNewLabel_2.setBounds(41, 73, 77, 13);
		}
		return lblNewLabel_2;
	}
	private JSpinner getSpnIni() {
		if (spnIni == null) {
			spnIni = new JSpinner();
			spnIni.setModel(new SpinnerNumberModel(7, 7, 23, 1));
			spnIni.setBounds(242, 106, 30, 20);
		}
		return spnIni;
	}
	private JSpinner getSpnFin() {
		if (spnFin == null) {
			spnFin = new JSpinner();
			spnFin.setModel(new SpinnerNumberModel(7, 7, 23, 1));
			spnFin.setBounds(327, 106, 30, 20);
		}
		return spnFin;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("Hora inicio:");
			lblNewLabel_3.setBounds(215, 73, 61, 13);
		}
		return lblNewLabel_3;
	}
	private JLabel getLblNewLabel_4() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("Hora fin:");
			lblNewLabel_4.setBounds(312, 73, 45, 13);
		}
		return lblNewLabel_4;
	}
	private JSpinner getSpnPlazas() {
		if (spnPlazas == null) {
			spnPlazas = new JSpinner();
			spnPlazas.setEnabled(false);
			spnPlazas.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spnPlazas.setBounds(327, 161, 30, 20);
		}
		return spnPlazas;
	}
	private JLabel getLblNewLabel_5() {
		if (lblNewLabel_5 == null) {
			lblNewLabel_5 = new JLabel("Plazas:");
			lblNewLabel_5.setEnabled(false);
			lblNewLabel_5.setBounds(266, 164, 61, 13);
		}
		return lblNewLabel_5;
	}
	private JComboBox getCmbTipos() {
		if (cmbTipos == null) {
			cmbTipos = new JComboBox();
			cmbTipos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					comprobarRecurso();
				}
			});
			cmbTipos.setBounds(109, 26, 218, 20);
		}
		return cmbTipos;
	}
	private void comprobarRecurso() {
		TipoActividad ta = (TipoActividad) getCmbTipos().getSelectedItem();
		if (ta.usaRecurso() && (getSpnPlazas().isEnabled() && getLblNewLabel_5().isEnabled())) {
			getSpnPlazas().setValue(0);
			getSpnPlazas().setEnabled(false);
			getLblNewLabel_5().setEnabled(false);
		} else if (!ta.usaRecurso() && (!getSpnPlazas().isEnabled() && !getLblNewLabel_5().isEnabled())) {
			getSpnPlazas().setEnabled(true);
			getLblNewLabel_5().setEnabled(true);
		}
			
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Instalación:");
			lblNewLabel.setBounds(347, 30, 84, 13);
		}
		return lblNewLabel;
	}
	private JComboBox getCmbIns() {
		if (cmbIns == null) {
			cmbIns = new JComboBox();
			cmbIns.setBounds(416, 26, 143, 20);
		}
		return cmbIns;
	}
}
