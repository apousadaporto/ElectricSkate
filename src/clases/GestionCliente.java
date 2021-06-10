package clases;

import java.util.Scanner;
import java.sql.*;

public class GestionCliente {
	
	
	// Menú que muestra las opciones para trabajar con los clientes
	public static void menuClientes() {

		Connection conexion = null;
		Scanner teclado = new Scanner(System.in);

		System.out.println("GESTIONAR CLIENTES");
		System.out.println();
		System.out.println("Selecciona una operación:");
		System.out.println("1. Añadir nuevo cliente");
		System.out.println("2. Consultar cliente");
		System.out.println("3. Listar clientes");
		System.out.println("4. Exportar listado de clientes a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println("M - Volver al menú");
		String opcion = teclado.nextLine().toUpperCase();
		switch (opcion) {
		case "1":
			insertarCliente(conexion, "ElectricSkate");
			break;
		}
		
		teclado.close();

	}

	// Método para crear un cliente en la BBDD
	// Método para insertar los valores en los campos de la tabla
	private static void insertarCliente(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		// Solicitud y recogida de datos al usuario por teclado
		System.out.println();
		System.out.println("== AÑADIR CLIENTE ==");
		System.out.println();
		System.out.println("Introduzca los siguientes datos: ");
		System.out.println();
		System.out.print("Nombre: ");
		String nombre = teclado.nextLine();
		System.out.print("Apellidos: ");
		String apellidos = teclado.nextLine();
		System.out.print("Edad: ");
		int edad = teclado.nextInt();
		teclado.nextLine();
		System.out.print("DNI: ");
		String dni = teclado.nextLine();
		System.out.print("E-mail: ");
		String email = teclado.nextLine();
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellidos: " + apellidos);
		System.out.println("Edad: " + edad);
		System.out.println("DNI: " + dni);
		System.out.println("E-mail: " + email);
		System.out.println();
		System.out.printf("R. Registrar cliente");
		System.out.println();
		System.out.print("M - Volver al menú");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario confirma que quiere registrar el nuevo cliente
		if (respuesta.equals("R")) {
		// Objeto de tipo Statement para establecer la conexión
		Statement stmt = null;

		try {
			// Realizamos la conexión
			stmt = conexion.createStatement();

			// Variable que almacena la consulta a la BBDD
			String query = "INSERT INTO " + nombreBBDD + ".ordenadores VALUES " + "(null,'" + nombre + "','" + apellidos
					+ "'," + edad + ",'" + dni + "','" + email + "')";

			// Ejecutamos la consulta
			stmt.executeUpdate(query);

			// Mostramos un mensaje por pantalla al haber almacenado los valores
			// correctamente
			System.out.println();
			System.out.println("El cliente " + nombre + "" + apellidos + " ha sido introducido correctamente en la base de datos.");

			// Cierre de la conexión
			stmt.close();

			// Llamada al método que controla las posibles excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}
		// Comprobamos que el usuario quiera volver al menú sin registrar el cliente
		}else if (respuesta.equals("M")) {
			System.out.println("Registro cancelado");
			menuClientes();
		}else {
			System.out.println("Opción no válida");
			menuClientes();
		}
	}

	// Método que controla las posibles excepciones SQL
	private static void printSQLException(SQLException ex) {

		ex.printStackTrace(System.err);
		System.err.println("SQLState: " + ex.getSQLState());
		System.err.println("Error Code: " + ex.getErrorCode());
		System.err.println("Message: " + ex.getMessage());

		Throwable t = ex.getCause();

		while (t != null) {
			System.out.println("Cause: " + t);
			t = t.getCause();
		}

	}

}
