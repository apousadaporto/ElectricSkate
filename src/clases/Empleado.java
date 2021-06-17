package clases;

public class Empleado extends Usuario {

	// Atributos
	private String nombreUsuario;
	private String password;
	
	// Constructor
	public Empleado(String nombre, String apellidos, int edad, String dni, String email, String nombreUsuario, String password) {
		super(nombre, apellidos, edad, dni, email);
		this.nombreUsuario = nombreUsuario;
		this.password = password;
	}
	
	// Getters & Setters
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
