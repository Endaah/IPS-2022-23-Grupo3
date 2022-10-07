package giis.demo.tkrun.logica.roles;

import java.util.List;

import giis.demo.tkrun.logica.Recurso;
import giis.demo.tkrun.logica.TipoActividad;

public class Admin {

	public static void crearTipoActividad(List<Recurso> recursos, String nombre, String intensidad, String instalacion) {
		TipoActividad actividad = new TipoActividad(recursos, nombre, intensidad, instalacion);
		
	}
	
}
