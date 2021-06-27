package clases;

public class Cliente extends Usuario {

	// Atributos

	private String patinete;

	// Constructor
	public Cliente(String nombre, String apellidos, int edad, String dni, String email, String patinete) {
		super(nombre, apellidos, edad, dni, email);
		this.patinete = patinete;
	}

	// Getters y Setters
	public String getPatinete() {
		return patinete;
	}

	public void setPatinete(String patinete) {
		this.patinete = patinete;
	}
}
