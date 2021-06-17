package clases;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

import principal.GestionSistema;

// Librerías para restar fechas
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionPatinete {

	public static void main(String[] args) {

		menuPatinete();

	}

	public static void menuPatinete() {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		Connection conexion = null;

		try {
			// Creamos una conexión con la base de datos electricskate
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");

			// Llama al método que captura excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}

		System.out.println();
		System.out.println("GESTIONAR PATINETES");
		System.out.println();
		System.out.println("Seleccione una operación:");
		System.out.println();
		System.out.println("1. Añadir nuevo patinete");
		System.out.println("2. Alquilar patinete");
		System.out.println("3. Devolver patinete");
		System.out.println("4. Listar patinetes alquilados");
		System.out.println("5. Exportar listado de patinetes alquilados a fichero.txt");
		System.out.println("6. Listar patinetes totales");
		System.out.println("7. Exportar listado de patinetes totales a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println("M - Volver al menú.");
		System.out.println("");
		System.out.print("Introduzca una opcion: ");
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
			String listadoPatinetesAlquilados = obtenerPatinetesAlquilados(conexion, "electricskate");
			exportarListadoPatinetesAlquilados(listadoPatinetesAlquilados);
			break;

		case "6":
			listarPatinetes(conexion, "electricskate");
			break;

		case "7":
			String listadoPatinetes = obtenerPatinetes(conexion, "electricskate");
			exportarListadoPatinetes(listadoPatinetes);
			break;
			
		case "M":
			GestionSistema.menu();

		default:
			System.out.println("Por favor, seleccione una de las opciones disponibles.");
			// Vuelve al menú de patinete
			menuPatinete();
			break;
		}
		// Cerramos el teclado
		teclado.close();

	}

	// Método para crear un patinete en la BBDD
	private static void insertarPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		int kmRecorridos = 0;
		boolean disponible = true;
		int kmViaje = 0;

		// Recogida de datos introducidos por el usuario
		System.out.println();
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
						+ color + "'," + kmRecorridos + ",'" + numeroSerie + "'," + disponible + ", NULL)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mensaje que confirma la ejecución de la consulta
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
			// Mensaje que informa que no se ha realizado la ejecución de la consulta
			System.out.println();
			System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
					+ ", no se ha introducido en la base de datos.");
			// Vuelve al menú de patinete
			menuPatinete();

			// Si el usuario introduce una opción no válida
		} else {
			System.out.println();
			System.out.println("La opción introducida no es válida");
			// Vuelve al menú de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// Método para alquilar un patinete
	private static void alquilarPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println();
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

		// Si el usuario desea registrar el alquiler del patinete
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

				// Mensaje que confirma la ejecución de la consulta
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
			// Mensaje que informa que no se ha realizado la ejecución de la consulta
			System.out.println();
			System.out.println("Alquiler no realizado.");
			// Vuelve al menú de patinete
			menuPatinete();

			// Si el usuario introduce una opción no válida
		} else {
			System.out.println();
			System.out.println("La opción introducida no es válida");
			// Vuelve al menú de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// Método para gestionar la devolución de un patinete
	private static void devolverPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println();
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

		// Si el usuario desea registrar la devolución del patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexión
			Statement stmt = null;

			try {
				// Se realiza la conexión
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "UPDATE " + nombreBBDD
						+ ".patinete SET Disponible = true, DniUsuario = '', KmRecorridos = (KmRecorridos + " + kmViaje
						+ ") where NumeroSerie = '" + numeroSerie + "'";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// https://www.delftstack.com/es/howto/java/java-subtract-dates/
				LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);
				// Calcula la diferencia en días entre las fechas introducidas
				long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

				// Mensaje que confirma la ejecución de la consulta
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

			// Si el usuario introduce una opción no válida
		} else {
			// Mensaje que informa que no se ha realizado la ejecución de la consulta
			System.out.println();
			System.out.println("La opción introducida no es válida");
			// Vuelve al menú de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// Método para obtener el listado de patinetes alquilados
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
			// y su método en el cual le pasamos por parámetro la variable local
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
				// Comprueba que la variable numeroDniUsuario no sea null ni un String vacío
				if (numeroDniUsuario != null && !numeroDniUsuario.trim().isEmpty()) {
					result += "DNI del usuario que tiene el patinete alquilado: " + numeroDniUsuario + "\n";
				}
				result += "*************************************" + "\n";
			}

			// Llama al método que captura excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}

		return result;
	}

	// Método que devuelve el listado de patinetes alquilados
	private static String listarPatinetesAlquilados(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES ALQUILADOS");
		System.out.println();

		// Llama al método que almacena la información de los patinetes alquilados
		String result = obtenerPatinetesAlquilados(conexion, nombreBBDD);

		// Muestra por pantalla los patinetes alquilados
		System.out.println(result);

		System.out.print("M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();

		// Si el usuario desea volver al menú
		if (respuesta.equals("M")) {
			// Vuelve al menú de patinete
			menuPatinete();

		} else {
			System.out.println("La opción introducida no es válida");
			// Vuelve al menú de patinete
			menuPatinete();
		}

		// Cerramos el teclado
		teclado.close();

		return result;
	}

	// Método para obtener el listado de todos los patinetes
	private static String obtenerPatinetes(Connection conexion, String nombreBBDD) {
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Obejto de tipo Statement para establecer la conexión
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete ORDER BY marca";

		try {

			// Objeto de tipo Statement para establecer la conexión
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la información, a través del objeto
			// stmt
			// y su método en el cual le pasamos por parámetro la variable local
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

				// Comprueba que la variable numeroDniUsuario no sea null ni un String vacío
				if (numeroDniUsuario != null && !numeroDniUsuario.trim().isEmpty()) {
					result += "DNI del usuario que tiene el patinete alquilado: " + numeroDniUsuario + "\n";
				}
				result += "*************************************" + "\n";
			}
			// Llama al método que captura excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}

		return result;
	}

	// Método que devuelve el listado de todos los patinetes
	private static String listarPatinetes(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES TOTALES");
		System.out.println();

		// Llama al método que almacena la información de todos los patinetes
		String result = obtenerPatinetes(conexion, nombreBBDD);
		// Muestra por pantalla todo los patinetes
		System.out.println(result);

		System.out.print("M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario desea volver al menú
		if (respuesta.equals("M")) {
			// Vuelve al menú de patinete
			menuPatinete();

		} else {
			System.out.println("La opción introducida no es válida");
			// Vuelve al menú de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();

		return result;
	}

	// Método para guardar el listado de patinetes alquilados en un fichero
	private static void exportarListadoPatinetesAlquilados(String listado) {

		try {

			// Seleccionamos la ruta y la carpeta en la cual se guardará el archivo
			File ruta = new File("C:" + File.separator + "informes");
			ruta.mkdir();

			// Creamos la ruta del archivo
			ruta = new File("C:" + File.separator + "informes" + File.separator + "Listado_patinetes_alquilados.txt");

			// Creamos la carpeta
			try {
				ruta.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Creamos el archivo con un objeto de tipo FileWriter
			FileWriter fichero = new FileWriter(ruta);

			// Leemos el buffer
			BufferedWriter buffer = new BufferedWriter(fichero);

			// Escribimos el texto en el buffer
			buffer.write("LISTADO DE PATINETES ALQUILADOS");
			buffer.newLine();
			buffer.write(listado);

			// Mensaje informativo
			System.out.println("El listado de patinetes alquilados ha sido exportado con éxito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Método para guardar el listado de patinetes en un fichero
	private static void exportarListadoPatinetes(String listado) {

		try {

			// Seleccionamos la ruta y la carpeta en la cual se guardará el archivo
			File ruta = new File("C:" + File.separator + "informes");
			ruta.mkdir();

			// Creamos la ruta del archivo
			ruta = new File("C:" + File.separator + "informes" + File.separator + "Listado_patinetes.txt");

			// Creamos la carpeta
			try {
				ruta.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Creamos el archivo con un objeto de tipo FileWriter
			FileWriter fichero = new FileWriter(ruta);

			// Leemos el buffer
			BufferedWriter buffer = new BufferedWriter(fichero);

			// Escribimos el texto en el buffer
			buffer.write("LISTADO DE PATINETES");
			buffer.newLine();
			buffer.write(listado);

			// Mensaje informativo
			System.out.println("El listado de patinetes ha sido exportado con éxito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
