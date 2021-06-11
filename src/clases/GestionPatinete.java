package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestionPatinete {
	
	public static void menuPatinete() {

		Scanner teclado = new Scanner(System.in);

		Connection conexion = null;
		
		try {
			
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");
			
		} catch (SQLException e) {
			
			printSQLException(e);
		}

		System.out.println();
		System.out.println("GESTIONAR PATINETES");
		System.out.println();
		System.out.println("Seleccione una operaci�n:");
		System.out.println();
		System.out.println("1. A�adir nuevo patinete");
		System.out.println("2. Nueva reserva");
		System.out.println("3. Devolver patinete");
		System.out.println("4. Listar patinetes alquilados");
		System.out.println("5. Exportar listado de patinetes alquilados a fichero.txt");
		System.out.println("6. Listar patinetes totales");
		System.out.println("7. Exportar listado de patinetes totales a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println("M - Volver al men�");
		String opcion = teclado.nextLine().toUpperCase();

		switch (opcion) {

		case "1":

			insertarPatinete(conexion, "electricskate");

			break;

		case "2":

			break;

		case "3":

			break;

		case "4":

			break;

		case "5":

			break;

		case "6":

			break;

		case "7":

			break;

		default:

			System.out.println("Por favor, seleccione una de las opciones disponibles.");

			break;
		}

		teclado.close();

		// listado patinete (select)
		// alquilar patinete (update)
		// devolver patinete (update)

	}

	// M�todo para crear un patinete en la BBDD
	private static void insertarPatinete(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		int kmRecorridos = 0;
		boolean disponible = true;
		int kmViaje = 0;
		
		// Recogida de datos introducidos por el usuario
		System.out.println("A�ADIR PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Marca: ");
		String marca = teclado.nextLine();
		System.out.print("Modelo: ");
		String modelo = teclado.nextLine();
		System.out.print("Color: ");
		String color = teclado.nextLine();
		System.out.print("N�mero de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.println();
		System.out.print("R - Registrar patinete o M - Volver al men�: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexi�n
			Statement stmt = null;

			try {
				// Se realiza la conexi�n
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".patinete VALUES " + "('" + marca + "','" + modelo + "','" + color
						+ "'," + kmRecorridos + ",'" + numeroSerie + "'," + disponible + ", " + kmViaje + ", null)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mensaje de confirmaci�n
				System.out.println();
				System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + ", ha sido introducido correctamente en la base de datos.");

				// Cerramos la conexi�n
				stmt.close();

			// Llama al m�todo que captura excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}
		
		// Si el usuario desea volver al men�
		} else if (respuesta.equals("M")) {

			System.out.println();
			System.out.println(
					"El patinete " + numeroSerie + " " + marca + " " + modelo + ", no se ha introducido en la base de datos.");
			menuPatinete();

		// SSi el usuario introduce una opci�n no v�lida	
		} else {

			System.out.println();
			System.out.println("La opci�n introducida no es v�lida");
			menuPatinete();

		}
	}
	
	// M�todo para capturar excepciones de SQL
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
