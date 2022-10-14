package giis.demo.model;

import java.sql.Date;

public class Activity {
	
	private int id;
	private String nombre;
	private Date dia;
	private int ini;
	private int fin;
	private int plazas;
	
	public Activity(int a_id, String ta_nombre, Date a_dia, int a_ini, int a_fin, int plazas) {
		this.id = a_id;
		this.nombre = ta_nombre;
		this.dia = a_dia;
		this.ini = a_ini;
		this.fin = a_fin;
		this.plazas = plazas;
	}

	public int getA_id() {return id;}
	public void setA_id(int a_id) {	this.id = a_id;}
	public String getTa_nombre() {return nombre;	}
	public void setTa_nombre(String ta_nombre) {this.nombre = ta_nombre;}
	public Date getA_dia() {return dia;}
	public void setA_dia(Date a_dia) {this.dia = a_dia;}
	public int getA_ini() {return ini;}
	public void setA_ini(int a_ini) {this.ini = a_ini;}
	public int getA_fin() {return fin;}
	public void setA_fin(int a_fin) {this.fin = a_fin;}

	@Override
	public String toString() {
		String aux = "Actividad con ";
		
		if (plazas == ModelSocio.ACTIVIDADILIMITADA) {
			aux += "PLAZAS ILIMITADAS,";
		}
		else {
			aux += plazas;
			aux += " PLAZAS DE LIMITE,";
		}
		
		aux += " con id: ";
		aux += id;
		aux += ", ";
		aux += nombre;
		aux += ", ";
		aux += dia.toString();
		aux += ", de ";
		aux += ini;
		aux += ", a ";
		aux += fin;
		
		return aux;
	}
	
	

}
