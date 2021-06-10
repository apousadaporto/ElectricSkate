package clases;

public class Cliente extends Usuario {

	// Atributos
	private Patinete patinete;

	// Constructor
	public Cliente(String nombre, String apellidos, int edad, String dni, String email, Patinete patinete) {
		super(nombre, apellidos, edad, dni, email);
		this.patinete = patinete;
	}

	// Getters y Setters
	public Patinete getPatinete() {
		return patinete;
	}

	public void setPatinete(Patinete patinete) {
		this.patinete = patinete;
	}

}
