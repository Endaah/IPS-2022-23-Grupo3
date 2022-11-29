package giis.demo.model;

import java.sql.Date;

public class Actividad {
	public final static int CANCELADA = 1;
	public final static int VALIDA = 0;
	
	public final static int ACTIVIDADILIMITADA = 999;
	
	private int id;
	private String nombre;
	private Date dia;
	private int ini;
	private int fin;
	private int plazas;
	private Instalacion instalacion;
	private int cancelada;
	private int grupo;
	
	public Actividad(int a_id, String ta_nombre, Date a_dia, int a_ini, int a_fin, int plazas, Instalacion instalacion, int cancelada, int grupo) {
		this.id = a_id;
		this.nombre = ta_nombre;
		this.dia = a_dia;
		this.ini = a_ini;
		this.fin = a_fin;
		this.plazas = plazas;
		this.instalacion = instalacion;
		this.cancelada = cancelada;
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		String str = "ID: " + id
				+ " - " + dia 
				+ " - " + ini + "-" + fin
				+ " - " + nombre + " - ";
		if (plazas == ACTIVIDADILIMITADA)
			str += "sin limite";
		else
			str += plazas + " plazas";
		str += " - Grupo " + grupo;
		
		return str;
	}

	public int getId() {return id;}
	public String getNombre() {return nombre;}
	public Date getDia() {return dia;}
	public int getIni() {return ini;}
	public int getFin() {return fin;}
	public int getPlazas() {return plazas;}
	public Instalacion getInstalacion() {return instalacion;}
	public int getCancelada() {return cancelada;}
	public int getGrupo() {return grupo;}
	public void setId(int id) {this.id = id;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public void setDia(Date dia) {this.dia = dia;}
	public void setIni(int ini) {this.ini = ini;}
	public void setFin(int fin) {this.fin = fin;}
	public void setPlazas(int plazas) {this.plazas = plazas;}
	
	public void anular() {
		this.cancelada = 1;
	}
	
}
