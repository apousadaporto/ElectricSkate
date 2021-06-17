/**
 * @author Andrea Pousada
 * @version 1.0
 */

package clases;

public class Patinete {

	// Atributos de la clase Patinete
	private String marca;
	private String modelo;
	private String color;
	private int kmRecorridos;
	private String numeroSerie;
	private boolean disponible;
	private int kmViaje;
	private String dniUsuario;

	// Constructor
	public Patinete(String marca, String modelo, String color, String numeroSerie, boolean disponible,
			int kmViaje, String dniUsuario) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.kmRecorridos = 0;
		this.numeroSerie = numeroSerie;
		this.disponible = disponible;
		this.kmViaje = kmViaje;
		this.dniUsuario = dniUsuario;
	}

	// Getters & Setters
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getKmRecorridos() {
		return kmRecorridos;
	}

	public void setKmRecorridos(int kmRecorridos) {
		this.kmRecorridos = kmRecorridos;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public int getKmViaje() {
		return kmViaje;
	}

	public void setKmViaje(int kmViaje) {
		this.kmViaje = kmViaje;
	}

	public String getDniUsuario() {
		return dniUsuario;
	}

	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}
}
