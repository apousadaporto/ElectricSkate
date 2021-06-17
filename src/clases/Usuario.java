/**
 * @author Andrea Pousada
 * @version 1.0
 */

package clases;

// Superclase abstracta Usuario
public abstract class Usuario {

	// Atributos de clase Usuario
	private String nombre;
	private String apellidos;
	private int edad;
	private String dni;
	private String email;

	// Constructor por defecto
	public Usuario(String nombre, String apellidos, int edad, String dni, String email) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.dni = dni;
		this.email = email;
	}

	// Getters & Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
