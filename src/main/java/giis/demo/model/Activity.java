package giis.demo.model;

import java.sql.Date;

public class Activity {
public final static int ACTIVIDADILIMITADA = 0;
	
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

	@Override
	public String toString() {
		String aux = "ID: ";
		aux += id;
		aux += ", (";
		aux += ini;
		aux += "-";
		aux += fin;
		aux += ") ";
		if (plazas == ACTIVIDADILIMITADA) {
			aux += "SIN LIMITE DE PLAZAS, ";
		} else {
			aux += plazas;
			aux += " PLAZAS, ";
		}
		aux += nombre;
		return aux;
	}

	public int getId() {return id;}
	public String getNombre() {return nombre;}
	public Date getDia() {return dia;}
	public int getIni() {return ini;}
	public int getFin() {return fin;}
	public int getPlazas() {return plazas;}
	public void setId(int id) {this.id = id;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public void setDia(Date dia) {this.dia = dia;}
	public void setIni(int ini) {this.ini = ini;}
	public void setFin(int fin) {this.fin = fin;}
	public void setPlazas(int plazas) {this.plazas = plazas;}
	
}
