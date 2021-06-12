package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
// Restar fechas
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionPatinete {

	public static void main(String[] args) {

		menuPatinete();

	}

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
		System.out.println("Seleccione una operación:");
		System.out.println();
		System.out.println("1. Añadir nuevo patinete");
		System.out.println("2. Nueva reserva");
		System.out.println("3. Devolver patinete");
		System.out.println("4. Listar patinetes alquilados");
		System.out.println("5. Exportar listado de patinetes alquilados a fichero.txt");
		System.out.println("6. Listar patinetes totales");
		System.out.println("7. Exportar listado de patinetes totales a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println("M - Volver al menú.");
		String opcion = teclado.nextLine().toUpperCase();

		switch (opcion) {

		case "1":

			insertarPatinete(conexion, "electricskate");

			break;

		case "2":

			alquilarPatinete(conexion, "electricskate");

			break;

		case "3":

			devolverPatinete(conexion, "electricskate");

			break;

		case "4":

			listarPatinetesAlquilados(conexion, "electricskate");

			break;

		case "5":

			break;

		case "6":

			break;

		case "7":

			break;

		default:

			System.out.println("Por favor, seleccione una de las opciones disponibles.");
			menuPatinete();

			break;
		}

		teclado.close();

		// listado patinete (select)

		// devolver patinete (update)

	}

	// Método para crear un patinete en la BBDD
	private static void insertarPatinete(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		int kmRecorridos = 0;
		boolean disponible = true;
		int kmViaje = 0;

		// Recogida de datos introducidos por el usuario
		System.out.println("AÑADIR PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Marca: ");
		String marca = teclado.nextLine();
		System.out.print("Modelo: ");
		String modelo = teclado.nextLine();
		System.out.print("Color: ");
		String color = teclado.nextLine();
		System.out.print("Número de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.println();
		System.out.print("R - Registrar patinete o M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexión
			Statement stmt = null;

			try {
				// Se realiza la conexión
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".patinete VALUES " + "('" + marca + "','" + modelo + "','"
						+ color + "'," + kmRecorridos + ",'" + numeroSerie + "'," + disponible + ", " + kmViaje
						+ ", null)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mensaje de confirmación
				System.out.println();
				System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
						+ ", ha sido introducido correctamente en la base de datos.");

				// Cerramos la conexión
				stmt.close();

				// Llama al método que captura excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}

			// Si el usuario desea volver al menú
		} else if (respuesta.equals("M")) {

			System.out.println();
			System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
					+ ", no se ha introducido en la base de datos.");
			menuPatinete();

			// SSi el usuario introduce una opción no válida
		} else {

			System.out.println();
			System.out.println("La opción introducida no es válida");
			menuPatinete();

		}
	}

	// Método para alquilar un patinete
	private static void alquilarPatinete(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println("ALQUILAR PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Número de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.print("Dni usuario: ");
		String dniUsuario = teclado.nextLine();
		System.out.print("Fecha del alquiler (AAAA-MM-DD): ");
		String fechaA = teclado.nextLine();
		System.out.print("Fecha de devolución (AAAA-MM-DD): ");
		String fechaD = teclado.nextLine();

		System.out.println();
		System.out.print("R - Registrar alquiler o M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexión
			Statement stmt = null;

			try {
				// Se realiza la conexión
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "UPDATE " + nombreBBDD + ".patinete SET Disponible = false, DniUsuario = '" + dniUsuario
						+ "' where NumeroSerie = '" + numeroSerie + "'";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// https://www.delftstack.com/es/howto/java/java-subtract-dates/
				LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);
				// Calcula la diferencia en días entre las fechas introducidas
				long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

				// Mensaje de confirmación
				System.out.println();
				System.out.println("El alquiler ha sido realizado con éxito.");
				System.out.println("El patinete " + numeroSerie + ", ha sido alquilado al usuario " + dniUsuario
						+ " durante " + diff + " días.");

				// Cerramos la conexión
				stmt.close();

				// Llama al método que captura excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}

			// Si el usuario desea volver al menú
		} else if (respuesta.equals("M")) {

			System.out.println();
			System.out.println("Alquiler no realizado.");
			menuPatinete();

			// SSi el usuario introduce una opción no válida
		} else {

			System.out.println();
			System.out.println("La opción introducida no es válida");
			menuPatinete();

		}
	}

	private static void devolverPatinete(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println("DEVOLVER PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Número de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.print("Dni usuario: ");
		String dniUsuario = teclado.nextLine();
		System.out.print("Km viaje: ");
		int kmViaje = teclado.nextInt();
		teclado.nextLine();
		System.out.print("Fecha del alquiler (AAAA-MM-DD): ");
		String fechaA = teclado.nextLine();
		System.out.print("Fecha de devolución (AAAA-MM-DD): ");
		String fechaD = teclado.nextLine();

		System.out.println();
		System.out.print("R - Registrar devolución o M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexión
			Statement stmt = null;

			try {
				// Se realiza la conexión
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "UPDATE " + nombreBBDD
						+ ".patinete SET Disponible = true, DniUsuario = null, KmRecorridos = (KmRecorridos + "
						+ kmViaje + ") where NumeroSerie = '" + numeroSerie + "'";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// https://www.delftstack.com/es/howto/java/java-subtract-dates/
				LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);
				// Calcula la diferencia en días entre las fechas introducidas
				long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

				// Mensaje de confirmación
				System.out.println();
				System.out.println("La devolución ha sido realizada con éxito.");
				System.out.println("El patinete " + numeroSerie + ", ha sido devuelto por el usuario " + dniUsuario
						+ ". Ha tenido el patinete en alquiler durante " + diff + " días.");

				// Cerramos la conexión
				stmt.close();

				// Llama al método que captura excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}

			// Si el usuario desea volver al menú
		} else if (respuesta.equals("M")) {

			System.out.println();
			System.out.println("Alquiler no realizado.");
			menuPatinete();

			// SSi el usuario introduce una opción no válida
		} else {

			System.out.println();
			System.out.println("La opción introducida no es válida");
			menuPatinete();

		}
	}

	private static String obtenerPatinetesAlquilados(Connection conexion, String nombreBBDD) {
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Obejto de tipo Statement para establecer la conexión
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete " + "where Disponible = false ORDER BY marca";

		try {

			// Objeto de tipo Statement para establecer la conexión
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la información, a través del objeto
			// stmt
			// y su método en el cual le pasamos por paá·metro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Mientras rs siga recibiendo información almacena el resultado en la variable
			while (rs.next()) {

				String marca = rs.getString(1);
				result += "Marca: " + marca + "\n";

				String modelo = rs.getString(2);
				result += "Modelo: " + modelo + "\n";

				String color = rs.getString(3);
				result += "Color: " + color + "\n";

				String kmRecorridos = rs.getString(4);
				result += "Km recorridos: " + kmRecorridos + "\n";

				String numeroSerie = rs.getString(5);
				result += "Número de serie: " + numeroSerie + "\n";

				String numeroDniUsuario = rs.getString(7);
				result += "DNI del usuario que tiene el patinete alquilado: " + numeroDniUsuario + "\n";

				result += "*************************************" + "\n";

			}

			// Llama al método que captura excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}

		return result;
	}

	// Método que devuelve el listado de los clientes
	private static String listarPatinetesAlquilados(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES ALQUILADOS");
		System.out.println();

		String result = obtenerPatinetesAlquilados(conexion, nombreBBDD);

		System.out.println(result);

		System.out.print("M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		if (respuesta.equals("M")) {
			// menuPrincipal();
		} else {
			System.out.println("La opción introducida no es válida");
			menuPatinete();
		}

		teclado.close();

		return result;
	}

	// Método para capturar excepciones de SQL
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
