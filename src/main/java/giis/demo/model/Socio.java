package giis.demo.model;

public class Socio {
	
	public static final int TERCEROS = 666;
	
	private int id;
	private String nombre;
	private String email;

	public Socio(int id, String nombre, String email) {
		this.setId(id);
		this.setNombre(nombre);
		this.setEmail(email);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return nombre;
	}

}
