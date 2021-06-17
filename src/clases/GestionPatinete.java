/**
 * @author Andrea Pousada
 * @version 1.0
 */

package clases;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

import principal.GestionSistema;

// Librer�as para restar fechas
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionPatinete {

	public static void menuPatinete() {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		Connection conexion = null;

		try {
			// Creamos una conexi�n con la base de datos electricskate
			conexion = DriverManager.getConnection(Utilidades.URL_BBDD, Utilidades.USER_BBDD, Utilidades.PASSWD_BBDD);
			// Llama al m�todo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		System.out.println();
		System.out.println("GESTIONAR PATINETES");
		System.out.println();
		System.out.println("Seleccione una operaci�n:");
		System.out.println();
		System.out.println("1. A�adir nuevo patinete");
		System.out.println("2. Alquilar patinete");
		System.out.println("3. Devolver patinete");
		System.out.println("4. Listar patinetes alquilados");
		System.out.println("5. Exportar listado de patinetes alquilados a fichero.txt");
		System.out.println("6. Listar patinetes totales");
		System.out.println("7. Exportar listado de patinetes totales a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println(           
			    " 	.+o+.               \n" 
			    +"       `-:/o-             \n"     
	            +"        `/:/-            \n"     
	            +"      -/:+o+           \n"     
	            +"   ``-::/+so            \n"    
	            +"   /```:++o`            \n"    
	            +"   +   syss`            \n"    
	            +"   /  +y::s+`           \n"    
	            +" -. .s/  -/..``          \n"  
	            +"  +   --      `:         \n"   
	            +".oyy-`::``````.           \n"  
	            +"-yhy:/://///:oh.      	");
		System.out.println();
		System.out.println("M - Volver al men�.");
		System.out.println("");
		System.out.print("Introduzca una opcion: ");
		String opcion = teclado.nextLine().toUpperCase();

		switch (opcion) {

		case "1":
			insertarPatinete(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "2":
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
			exportarListadoPatinetesAlquilados(listadoPatinetesAlquilados);
			break;

		case "6":
			listarPatinetes(conexion, Utilidades.NOMBRE_BBDD);
			break;

		case "7":
			String listadoPatinetes = obtenerPatinetes(conexion, Utilidades.NOMBRE_BBDD);
			exportarListadoPatinetes(listadoPatinetes);
			break;
			
		case "M":
			// Volvemos al menu principal
			GestionSistema.menu();

		default:
			System.out.println("Por favor, seleccione una de las opciones disponibles.");
			// Vuelve al men� de patinete
			menuPatinete();
			break;
		}
		// Cerramos el teclado
		teclado.close();

	}

	// M�todo para crear un patinete en la BBDD
	private static void insertarPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		int kmRecorridos = 0;
		boolean disponible = true;
		int kmViaje = 0;

		// Recogida de datos introducidos por el usuario
		System.out.println();
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
				String query = "INSERT INTO " + nombreBBDD + ".patinete VALUES " + "('" + marca + "','" + modelo + "','"
						+ color + "'," + kmRecorridos + ",'" + numeroSerie + "'," + disponible + ", " + kmViaje
						+ ", NULL)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mensaje que confirma la ejecuci�n de la consulta
				System.out.println();
				System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
						+ ", ha sido introducido correctamente en la base de datos.");

				// Cerramos la conexi�n
				stmt.close();

				// Volvemos al menu principal
				GestionSistema.menu();

				// Llama al m�todo que captura excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}

			// Si el usuario desea volver al men�
		} else if (respuesta.equals("M")) {
			// Mensaje que informa que no se ha realizado la ejecuci�n de la consulta
			System.out.println();
			System.out.println("El patinete " + numeroSerie + " " + marca + " " + modelo + " " + color
					+ ", no se ha introducido en la base de datos.");

			// Volvemos al menu principal
			GestionSistema.menu();

			// Si el usuario introduce una opci�n no v�lida
		} else {
			System.out.println();
			System.out.println("La opci�n introducida no es v�lida");
			// Vuelve al men� de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// M�todo para alquilar un patinete
	private static void alquilarPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println();
		System.out.println("ALQUILAR PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("N�mero de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.print("Dni usuario: ");
		String dniUsuario = teclado.nextLine();
		System.out.print("Fecha del alquiler (AAAA-MM-DD): ");
		String fechaA = teclado.nextLine();
		System.out.print("Fecha de devoluci�n (AAAA-MM-DD): ");
		String fechaD = teclado.nextLine();
		System.out.println();
		System.out.print("R - Registrar alquiler o M - Volver al men�: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar el alquiler del patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexi�n
			Statement stmt = null;

			try {
				// Se realiza la conexi�n
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "UPDATE " + nombreBBDD + ".patinete SET Disponible = false, DniUsuario = '" + dniUsuario
						+ "' where NumeroSerie = '" + numeroSerie + "'";

				String query1 = "UPDATE " + nombreBBDD + ".cliente SET NumeroSeriePatinete = '" + numeroSerie
						+ "' where Dni = '" + dniUsuario + "'";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);
				stmt.executeUpdate(query1);

				// https://www.delftstack.com/es/howto/java/java-subtract-dates/
				LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);
				// Calcula la diferencia en d�as entre las fechas introducidas
				long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

				// Mensaje que confirma la ejecuci�n de la consulta
				System.out.println();
				System.out.println("El alquiler ha sido realizado con �xito.");
				System.out.println("El patinete " + numeroSerie + ", ha sido alquilado al cliente con DNI: "
						+ dniUsuario + ", durante " + diff + " d�a/s.");

				// Cerramos la conexi�n
				stmt.close();

				// Volvemos al menu principal
				GestionSistema.menu();

				// Llama al m�todo que captura excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}

			// Si el usuario desea volver al men�
		} else if (respuesta.equals("M")) {
			// Mensaje que informa que no se ha realizado la ejecuci�n de la consulta
			System.out.println();
			System.out.println("Alquiler no realizado.");

			// Volvemos al menu principal
			GestionSistema.menu();

			// Si el usuario introduce una opci�n no v�lida
		} else {
			System.out.println();
			System.out.println("La opci�n introducida no es v�lida");
			// Vuelve al men� de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// M�todo para gestionar la devoluci�n de un patinete
	private static void devolverPatinete(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		// Recogida de datos introducidos por el usuario
		System.out.println();
		System.out.println("DEVOLVER PATINETE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos:");
		System.out.println();
		System.out.print("N�mero de serie: ");
		String numeroSerie = teclado.nextLine();
		System.out.print("Dni usuario: ");
		String dniUsuario = teclado.nextLine();
		System.out.print("Km viaje: ");
		int kmViaje = teclado.nextInt();
		teclado.nextLine();
		System.out.print("Fecha del alquiler (AAAA-MM-DD): ");
		String fechaA = teclado.nextLine();
		System.out.print("Fecha de devoluci�n (AAAA-MM-DD): ");
		String fechaD = teclado.nextLine();
		System.out.println();
		System.out.print("R - Registrar devoluci�n o M - Volver al men�: ");
		String respuesta = teclado.nextLine().toUpperCase();
		System.out.println();

		// Si el usuario desea registrar la devoluci�n del patinete
		if (respuesta.equals("R")) {

			// Objeto Statement que establece la conexi�n
			Statement stmt = null;

			try {
				// Se realiza la conexi�n
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

				// https://www.delftstack.com/es/howto/java/java-subtract-dates/
				LocalDate dBefore = LocalDate.parse(fechaA, DateTimeFormatter.ISO_LOCAL_DATE);
				LocalDate dAfter = LocalDate.parse(fechaD, DateTimeFormatter.ISO_LOCAL_DATE);
				// Calcula la diferencia en d�as entre las fechas introducidas
				long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

				// Mensaje que confirma la ejecuci�n de la consulta
				System.out.println();
				System.out.println("La devoluci�n ha sido realizada con �xito.");
				System.out.println("El patinete " + numeroSerie + ", ha sido devuelto por el cliente con DNI: "
						+ dniUsuario + ". Ha tenido el patinete en alquiler durante " + diff + " d�as.");

				// Cerramos la conexi�n
				stmt.close();

				// Volvemos al menu principal
				GestionSistema.menu();

				// Llama al m�todo que captura excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}

			// Si el usuario desea volver al men�
		} else if (respuesta.equals("M")) {
			System.out.println();
			System.out.println("Alquiler no realizado.");

			// Volvemos al menu principal
			GestionSistema.menu();

			// Si el usuario introduce una opci�n no v�lida
		} else {
			// Mensaje que informa que no se ha realizado la ejecuci�n de la consulta
			System.out.println();
			System.out.println("La opci�n introducida no es v�lida");
			// Vuelve al men� de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();
	}

	// M�todo para obtener el listado de patinetes alquilados
	private static String obtenerPatinetesAlquilados(Connection conexion, String nombreBBDD) {
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Obejto de tipo Statement para establecer la conexi�n
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete " + "where Disponible = false ORDER BY marca";

		try {

			// Objeto de tipo Statement para establecer la conexi�n
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informaci�n, a trav�s del objeto
			// stmt
			// y su m�todo en el cual le pasamos por par�metro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Mientras rs siga recibiendo informaci�n almacena el resultado en la variable
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
				result += "N�mero de serie: " + numeroSerie + "\n";

				String numeroDniUsuario = rs.getString(7);
				// Comprueba que la variable numeroDniUsuario no sea null ni un String vac�o
				if (numeroDniUsuario != null && !numeroDniUsuario.trim().isEmpty()) {
					result += "Alquilado por el usuario con DNI: " + numeroDniUsuario + "\n";
				}
				result += "*************************************" + "\n";
			}

			// Llama al m�todo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return result;
	}

	// M�todo que devuelve el listado de patinetes alquilados
	private static String listarPatinetesAlquilados(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES ALQUILADOS");
		System.out.println();

		// Llama al m�todo que almacena la informaci�n de los patinetes alquilados
		String result = obtenerPatinetesAlquilados(conexion, nombreBBDD);

		// Muestra por pantalla los patinetes alquilados
		System.out.println(result);

		System.out.print("M - Volver al men�: ");
		String respuesta = teclado.nextLine().toUpperCase();

		// Si el usuario desea volver al men�
		if (respuesta.equals("M")) {

			// Volvemos al menu principal
			GestionSistema.menu();

		} else {
			System.out.println("La opci�n introducida no es v�lida");
			// Vuelve al men� de patinete
			menuPatinete();
		}

		// Cerramos el teclado
		teclado.close();

		return result;
	}

	// M�todo para obtener el listado de todos los patinetes
	private static String obtenerPatinetes(Connection conexion, String nombreBBDD) {
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Obejto de tipo Statement para establecer la conexi�n
		Statement stmt = null;

		// Variable para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".patinete ORDER BY marca";

		try {

			// Objeto de tipo Statement para establecer la conexi�n
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informaci�n, a trav�s del objeto
			// stmt
			// y su m�todo en el cual le pasamos por par�metro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Mientras rs siga recibiendo informaci�n almacena el resultado en la variable
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
				result += "N�mero de serie: " + numeroSerie + "\n";

				String numeroDniUsuario = rs.getString(7);

				// Comprueba que la variable numeroDniUsuario no sea null ni un String vac�o
				if (numeroDniUsuario != null && !numeroDniUsuario.trim().isEmpty()) {
					result += "Alquilado por el usuario con DNI: " + numeroDniUsuario + "\n";
				}
				result += "*************************************" + "\n";
			}
			// Llama al m�todo que captura excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return result;
	}

	// M�todo que devuelve el listado de todos los patinetes
	private static String listarPatinetes(Connection conexion, String nombreBBDD) {

		// Instanciamos el teclado para pedir datos al usuario
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR PATINETES TOTALES");
		System.out.println();

		// Llama al m�todo que almacena la informaci�n de todos los patinetes
		String result = obtenerPatinetes(conexion, nombreBBDD);
		// Muestra por pantalla todo los patinetes
		System.out.println(result);

		System.out.print("M - Volver al men�: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario desea volver al men�
		if (respuesta.equals("M")) {

			// Volvemos al menu principal
			GestionSistema.menu();

		} else {
			System.out.println("La opci�n introducida no es v�lida");

			// Volvemos al menu de patinete
			menuPatinete();
		}
		// Cerramos el teclado
		teclado.close();

		return result;
	}

	// M�todo para guardar el listado de patinetes alquilados en un fichero
	private static void exportarListadoPatinetesAlquilados(String listado) {

		try {

			// Seleccionamos la ruta y la carpeta en la cual se guardar� el archivo
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
			System.out.println("El listado de patinetes alquilados ha sido exportado con �xito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		GestionSistema.menu();
	}

	// M�todo para guardar el listado de patinetes en un fichero
	private static void exportarListadoPatinetes(String listado) {

		try {

			// Seleccionamos la ruta y la carpeta en la cual se guardar� el archivo
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
			System.out.println("El listado de patinetes ha sido exportado con �xito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		GestionSistema.menu();
	}

}
