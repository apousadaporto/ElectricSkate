/**
 * @author Andrea Pousada
 * @version 1.0
 */

package clases;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

import principal.GestionSistema;

// Librerias para restar fechas
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GestionPatinete {

	public static void menuPatinete() {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		Connection conexion = null;

		try {
			// Creamos una conexion con la base de datos electricskate
			conexion = DriverManager.getConnection(Utilidades.URL_BBDD, Utilidades.USER_BBDD, Utilidades.PASSWD_BBDD);
			// Llama al metodo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		System.out.println();
		System.out.println("GESTIONAR PATINETES");
		System.out.println();
		System.out.println("Seleccione una operacion:");
		System.out.println();
		System.out.println("1. Anyadir nuevo patinete");
		System.out.println("2. Alquilar patinete");
		System.out.println("3. Devolver patinete");
		System.out.println("4. Listar patinetes alquilados");
		System.out.println("5. Exportar listado de patinetes alquilados a fichero.txt");
		System.out.println("6. Listar patinetes totales");
		System.out.println("7. Exportar listado de patinetes totales a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println(" 	.+o+.               \n" + "       `-:/o-             \n" + "        `/:/-            \n"
				+ "      -/:+o+           \n" + "   ``-::/+so            \n" + "   /```:++o`            \n"
				+ "   +   syss`            \n" + "   /  +y::s+`           \n" + " -. .s/  -/..``          \n"
				+ "  +   --      `:         \n" + ".oyy-`::``````.           \n" + "-yhy:/://///:oh.      	");
		System.out.println();
		System.out.println("M - Volver al menu.");
		System.out.println("");
		System.out.print("Introduzca una opcion: ");
		String opcion = teclado.nextLine().toUpperCase();

		switch (opcion) {

		case "1":
			insertarPatinete(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "2":
			System.out.println(listarPatinetesDisponibles(conexion, Utilidades.NOMBRE_BBDD));
			alquilarPatinete(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "3":
			devolverPatinete(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "4":
			listarPatinetesAlquilados(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "5":
			String listadoPatinetesAlquilados = obtenerPatinetesAlquilados(conexion, Utilidades.NOMBRE_BBDD);
			exportarListadoPatinetesAlquilados(conexion, Utilidades.NOMBRE_BBDD, listadoPatinetesAlquilados);
			break;

		case "6":
			listarPatinetes(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "7":
			String listadoPatinetes = obtenerPatinetes(conexion, Utilidades.NOMBRE_BBDD);
			exportarListadoPatinetes(conexion, Utilidades.NOMBRE_BBDD, listadoPatinetes);
			break;

		case "M":
			// Volvemos al menu principal
			GestionSistema.menu();

		default:
			System.out.println();
			System.out.println("Por favor, seleccione una de las opciones disponibles.");
			// Vuelve al menu de patinete
			menuPatinete();
			break;
		}
		// Cerramos el teclado
		teclado.close();

	}

	// Metodo para crear un patinete en la BBDD
	private static void insertarPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		int kmRecorridos = 0;
		boolean disponible = true;

		// Recogida de datos introducidos por el usuario
		System.out.println();
		System.out.println("ANYADIR PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Marca: ");
		String marca = teclado.nextLine();
		System.out.print("Modelo: ");
		String modelo = teclado.nextLine();
		System.out.print("Color: ");
		String color = teclado.nextLine();
		System.out.print("Numero de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.println();
		System.out.print("R - Registrar patinete o M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexion
			Statement stmt = null;

			try {
				// Se realiza la conexion
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".patinete VALUES " + "('" + marca + "','" + modelo + "','"
						+ color + "'," + kmRecorridos + ",'" + numeroSerie + "'," + disponible + ", NULL)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mensaje que confirma la ejecucion de la consulta
				System.out.println();
				System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
						+ ", ha sido introducido correctamente en la base de datos.");

				// Cerramos la conexion
				stmt.close();

				// Volvemos al menu principal
				GestionSistema.menu();

				// Llama al metodo que captura excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}

			// Si el usuario desea volver al menu
		} else if (respuesta.equals("M")) {
			// Mensaje que informa que no se ha realizado la ejecucion de la consulta
			System.out.println();
			System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
					+ ", no se ha introducido en la base de datos.");

			// Volvemos al menu principal
			GestionSistema.menu();

			// Si el usuario introduce una opcion no valida
		} else {
			System.out.println();
			System.out.println("La opcion introducida no es valida");
			// Vuelve al menu de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// Metodo para alquilar un patinete
	private static void alquilarPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println();
		System.out.println("ALQUILAR PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Numero de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.print("Dni usuario: ");
		String dniUsuario = teclado.nextLine();
		System.out.print("Fecha del alquiler (AAAA-MM-DD): ");
		String fechaA = teclado.nextLine();
		System.out.print("Fecha de devolucion (AAAA-MM-DD): ");
		String fechaD = teclado.nextLine();
		System.out.println();
		System.out.print("R - Registrar alquiler o M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el alquiler del patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexion
			Statement stmt = null;

			try {
				// Se realiza la conexion
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "UPDATE " + nombreBBDD + ".patinete SET Disponible = false, DniUsuario = '" + dniUsuario
						+ "' where NumeroSerie = '" + numeroSerie + "'";

				String query1 = "UPDATE " + nombreBBDD + ".cliente SET NumeroSeriePatinete = '" + numeroSerie
						+ "' where Dni = '" + dniUsuario + "'";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);

				try {
					// Transformamos las fechas introducidas por teclado a LocalDate, para poder
					// calcular la diferencia de dias entre ellas
					LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
					LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);

					// Calcula la diferencia en dias entre las fechas introducidas
					long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

					// Mensaje que confirma la ejecucion de la consulta
					System.out.println();
					System.out.println("El alquiler ha sido realizado con exito.");
					System.out.println("El patinete " + numeroSerie + ", ha sido alquilado al cliente con DNI: "
							+ dniUsuario + ", durante " + diff + " dia/s.");

				} catch (DateTimeParseException e) {
					System.out.println(
							"El formato de fecha debe ser AAAA/MM/DD.\n" + "Por favor, introduzca la fecha correcta.");
				}

				// Cerramos la conexion
				stmt.close();

				// Volvemos al menu principal
				GestionSistema.menu();

				// Llama al metodo que captura excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}

			// Si el usuario desea volver al menu
		} else if (respuesta.equals("M")) {
			// Mensaje que informa que no se ha realizado la ejecucion de la consulta
			System.out.println();
			System.out.println("Alquiler no realizado.");

			// Volvemos al menu principal
			GestionSistema.menu();

			// Si el usuario introduce una opcion no valida
		} else {
			System.out.println();
			System.out.println("La opcion introducida no es valida");
			// Vuelve al menu de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// Metodo para gestionar la devolucion de un patinete
	private static void devolverPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println();
		System.out.println("DEVOLVER PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("Numero de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.print("Dni usuario: ");
		String dniUsuario = teclado.nextLine();
		System.out.print("Km viaje: ");
		String kmViaje = teclado.nextLine();
		System.out.print("Fecha del alquiler (AAAA-MM-DD): ");
		String fechaA = teclado.nextLine();
		System.out.print("Fecha de devolucion (AAAA-MM-DD): ");
		String fechaD = teclado.nextLine();
		// Confirmamos que el campo "kmViaje" sea correcto
		if (!Utilidades.esEntero(kmViaje)) {
			System.out.println();
			System.out.println("El campo \"Km viaje\" tiene que ser un numero entero.");
			devolverPatinete(conexion, Utilidades.NOMBRE_BBDD);
		}
		System.out.println();
		System.out.print("R - Registrar devolucion o M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();
		// Si el usuario desea registrar la devolucion del patinete
		if (respuesta.equals("R") && existePatinete(conexion, nombreBBDD, numeroSerie)) {

			// Objeto Statement que establece la conexion
			Statement stmt = null;

			try {
				// Se realiza la conexion
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "UPDATE " + nombreBBDD
						+ ".patinete SET Disponible = true, DniUsuario = '', KmRecorridos = (KmRecorridos + " + kmViaje
						+ ") where NumeroSerie = '" + numeroSerie + "'";

				String query1 = "UPDATE " + nombreBBDD + ".cliente SET NumeroSeriePatinete = '' where Dni = '"
						+ dniUsuario + "'";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);

				try {
					// Transformamos las fechas introducidas por teclado a LocalDate, para poder
					// calcular la diferencia de dias entre ellas
					LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
					LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);
					// Calcula la diferencia en dias entre las fechas introducidas
					long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

					// Mensaje que confirma la ejecucion de la consulta
					System.out.println();
					System.out.println("La devolucion ha sido realizada con exito.");
					System.out.println("El patinete " + numeroSerie + ", ha sido devuelto por el cliente con DNI: "
							+ dniUsuario + ". Ha tenido el patinete en alquiler durante " + diff + " dias.");
				} catch (DateTimeParseException e) {
					System.out.println(
							"El formato de fecha debe ser AAAA/MM/DD.\n" + "Por favor, introduzca la fecha correcta.");
				}
				// Cerramos la conexion
				stmt.close();

				// Volvemos al menu principal
				GestionSistema.menu();

				// Llama al metodo que captura excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}

			// Si el usuario desea volver al menu
		} else if (respuesta.equals("M")) {
			System.out.println();
			System.out.println("Devolucion no realizada.");

			// Volvemos al menu principal
			GestionSistema.menu();

			// Si el usuario introduce una opcion no valida
		} else {
			// Mensaje que informa que no se ha realizado la ejecucion de la consulta
			System.out.println();
			System.out.println("Numero de serie del patinete no encontrado en la base de datos");
			// Vuelve al menu de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// Metodo para obtener el listado de patinetes alquilados
	private static String obtenerPatinetesAlquilados(Connection conexion, String nombreBBDD) {

		System.out.println();
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Objeto de tipo Statement para establecer la conexion
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete " + "where Disponible = false ORDER BY marca";

		try {

			// Objeto de tipo Statement para establecer la conexion
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto
			// stmt y su metodo en el cual le pasamos por parametro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Mientras rs siga recibiendo informacion almacena el resultado en la variable
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
				result += "Numero de serie: " + numeroSerie + "\n";

				String numeroDniUsuario = rs.getString(7);
				// Comprueba que la variable numeroDniUsuario no sea null ni un String vacio
				if (numeroDniUsuario != null && !numeroDniUsuario.trim().isEmpty()) {
					result += "Alquilado por el usuario con DNI: " + numeroDniUsuario + "\n";
				}
				result += "*************************************" + "\n";
			}

			// Llama al metodo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return result;
	}

	// Metodo que devuelve el listado de patinetes alquilados
	private static String listarPatinetesAlquilados(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES ALQUILADOS");
		System.out.println();

		// Llama al metodo que almacena la informacion de los patinetes alquilados
		String result = obtenerPatinetesAlquilados(conexion, nombreBBDD);

		// Muestra por pantalla los patinetes alquilados
		System.out.println(result);

		System.out.print("M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();

		// Si el usuario desea volver al menu
		if (respuesta.equals("M")) {

			// Volvemos al menu principal
			GestionSistema.menu();

		} else {
			System.out.println("La opcion introducida no es valida");
			// Vuelve al menu de patinete
			menuPatinete();
		}

		// Cerramos el teclado
		teclado.close();

		return result;
	}

	// Metodo para obtener el listado de patinetes disponibles
	private static String listarPatinetesDisponibles(Connection conexion, String nombreBBDD) {

		System.out.println();
		// Variable que almacena el resultado de las consultas
		String result = "Patinetes disponibles: \n";

		// Objeto de tipo Statement para establecer la conexion
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete " + "where Disponible = true ";

		try {

			// Objeto de tipo Statement para establecer la conexion
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto
			// stmt y su metodo en el cual le pasamos por parametro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Mientras rs siga recibiendo informacion almacena el resultado en la variable
			while (rs.next()) {

				String numeroSerie = rs.getString(5);
				result += "Numero de serie: " + numeroSerie + "\n";

			}

			// Llama al metodo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return result;
	}

	// Metodo para obtener el listado de todos los patinetes
	private static String obtenerPatinetes(Connection conexion, String nombreBBDD) {

		System.out.println();
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Obejto de tipo Statement para establecer la conexion
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete ORDER BY marca";

		try {

			// Objeto de tipo Statement para establecer la conexion
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto
			// stmt y su metodo en el cual le pasamos por parametro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Mientras rs siga recibiendo informacion almacena el resultado en la variable
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
				result += "Numero de serie: " + numeroSerie + "\n";

				String numeroDniUsuario = rs.getString(7);

				// Comprueba que la variable numeroDniUsuario no sea null ni un String vacio
				if (numeroDniUsuario != null && !numeroDniUsuario.trim().isEmpty()) {
					result += "Alquilado por el usuario con DNI: " + numeroDniUsuario + "\n";
				}
				result += "*************************************" + "\n";
			}
			// Llama al metodo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return result;
	}

	// Metodo que devuelve el listado de todos los patinetes
	private static String listarPatinetes(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES TOTALES");
		System.out.println();

		// Llama al metodo que almacena la informacion de todos los patinetes
		String result = obtenerPatinetes(conexion, nombreBBDD);
		// Muestra por pantalla todo los patinetes
		System.out.println(result);

		System.out.print("M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario desea volver al menu
		if (respuesta.equals("M")) {

			// Volvemos al menu principal
			GestionSistema.menu();

		} else {
			System.out.println("La opcion introducida no es valida");

			// Volvemos al menu de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();

		return result;
	}

	// Metodo para guardar el listado de patinetes alquilados en un fichero
	private static void exportarListadoPatinetesAlquilados(Connection conexion, String nombreBBDD, String listado) {

		try {

			System.out.println(listado);

			// Seleccionamos la ruta y la carpeta en la cual se guardara el archivo
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
			System.out.println();
			System.out.println("El listado de patinetes alquilados ha sido exportado con exito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		GestionSistema.menu();
	}

	// Metodo para guardar el listado de patinetes en un fichero
	private static void exportarListadoPatinetes(Connection conexion, String nombreBBDD, String listado) {

		try {

			System.out.println(listado);

			// Seleccionamos la ruta y la carpeta en la cual se guardara el archivo
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
			System.out.println();
			System.out.println("El listado de patinetes ha sido exportado con exito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		GestionSistema.menu();
	}

	// Metodo que comprueba que haya un patinete registrado con el numero de serie
	// que nos pasan
	// por parametro
	private static boolean existePatinete(Connection conexion, String nombreBBDD, String numeroSerie) {

		// Variable que almacena la consulta a la base de datos
		String compruebaPatinete = "select 1 " + "from " + nombreBBDD + ".patinete" + " where NumeroSerie = '"
				+ numeroSerie + "'";

		// Objeto de tipo Statement para establecer la conexion
		Statement stmt = null;

		try {
			// Creamos la consulta a la BBDD
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto stmt
			// y su metodo en el cual le pasamos por parametro la variable local
			ResultSet rs = stmt.executeQuery(compruebaPatinete);

			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					return true;
				}
			}

			// Llamada al metodo que controla las posibles excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return false;
	}

}
