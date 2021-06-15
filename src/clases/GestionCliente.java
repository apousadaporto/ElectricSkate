package clases;

import java.util.Scanner;

import principal.GestionSistema;

import java.io.*;
import java.sql.*;

public class GestionCliente {

	public static void main(String[] args) {

		menuClientes();

	}

	// Menú que muestra las opciones para trabajar con los clientes
	public static void menuClientes() {

		Scanner teclado = new Scanner(System.in);

		Connection conexion = null;

		try {

			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");

		} catch (SQLException e) {

			printSQLException(e);
		}

		System.out.println("GESTIONAR CLIENTES");
		System.out.println();
		System.out.println("Selecciona una operaciÛn:");
		System.out.println("1. AÒaadir nuevo cliente");
		System.out.println("2. Consultar cliente");
		System.out.println("3. Listar clientes");
		System.out.println("4. Exportar listado de clientes a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println("M - Volver al menú");
		System.out.println();
		System.out.print("Introduzca su elección: ");
		String opcion = teclado.nextLine().toUpperCase();
		switch (opcion) {
		case "1":
			insertarCliente(conexion, "electricskate");
			break;
		case "2":
			buscarCliente(conexion, "electricskate");
			break;
		case "3":
			listarClientes(conexion, "electricskate");
			break;
		case "4":
			String listadoClientes = obtenerClientes(conexion, "electricskate");
			exportarListadoClientes(listadoClientes);
			break;
		case "M":
			GestionSistema.menu();
			break;
		default:
			System.out.println("Opción no disponible");
			menuClientes();
			break;
		}
	}
	
	// MÈtodo para insertar los valores en los campos de la tabla
	private static void insertarCliente(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		// Solicitud y recogida de datos al usuario por teclado
		System.out.println();
		System.out.println("== A—ADIR CLIENTE ==");
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
		System.out.print("R - Registrar cliente o M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario confirma que quiere registrar el nuevo cliente
		if (respuesta.equals("R")) {
			// Objeto de tipo Statement para establecer la conexi√≥n
			Statement stmt = null;

			try {
				// Realizamos la conexiÛn
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".cliente VALUES " + "('" + nombre + "','" + apellidos
						+ "'," + edad + ",'" + dni + "','" + email + "',null)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mostramos un mensaje por pantalla al haber almacenado los valores
				// correctamente
				System.out.println();
				System.out.println("El cliente " + nombre + "" + apellidos
						+ " ha sido introducido correctamente en la base de datos.");

				// Cierre de la conexión
				stmt.close();

				// Llamada al método que controla las posibles excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}
			// Comprobamos que el usuario quiera volver al men˙ sin registrar el cliente
		} else if (respuesta.equals("M")) {
			System.out.println("Registro cancelado");
			menuClientes();
		} else {
			System.out.println("OpciÛn no v·lida");
			menuClientes();
		}

		teclado.close();
	}

	// MÈtodo para buscar un cliente en la BDOO por su DNI
	private static void buscarCliente(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("== CONSULTAR CLIENTE ==");
		System.out.println();
		System.out.print("Introduzca el DNI del cliente para buscar: ");
		// Variable con el valor del DNI
		String dniUsuario = teclado.nextLine();
		System.out.println();
		System.out.println("DNI: " + dniUsuario);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.print("B - Buscar cliente o M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario confirma que quiere registrar el nuevo cliente
		if (respuesta.equals("B") && existeCliente(conexion, nombreBBDD, dniUsuario)) {
			// Variable que almacena el resultado de las consultas
			String result = "";

			// Obejto de tipo Statement para establecer la conexiÛn
			Statement stmt = null;

			// Variable local para realizar la consulta
			String query = "select * " + "from " + nombreBBDD + ".cliente" + " WHERE Dni = '" + dniUsuario + "'";

			try {
				// Realizamos la conexiÛn
				stmt = conexion.createStatement();

				// Objeto de tipo ResulSet para recibir la informaciÛn, a travÈs del objeto stmt
				// y su mÈtodo en el cual le pasamos por par·metro la variable local
				ResultSet rs = stmt.executeQuery(query);

				// Bucle, mientras el objeto rs siga recibiendo informaciÛn, almacenamos el
				// resultado de cada consulta en una variable
				while (rs.next()) {

					String nombre = rs.getString(1);
					result += "Nombre: " + nombre + "\n";

					String apellidos = rs.getString(2);
					result += "Apellidos: " + apellidos + "\n";

					String edad = rs.getString(3);
					result += "Edad: " + edad + "\n";

					String dni = rs.getString(4);
					result += "DNI: " + dni + "\n";

					String email = rs.getString(5);
					result += "E-mail: " + email + "\n";

					String numeroSeriePatinete = rs.getString(6);
					result += "N˙mero de serie del patinete alquilado: " + numeroSeriePatinete + "\n";

					result += "*************************************" + "\n";
				}

				// Mostramos por pantalla los resultados obtenidos
				System.out.println();
				System.out.println(result);

				// Cierre de la conexiÛn
				stmt.close();

				// Llamada al mÈtodo que controla las posibles excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}
			// Comprobamos que el usuario quiera volver al men˙ sin registrar el cliente
		} else if (respuesta.equals("M")) {
			System.out.println();
			System.out.println("Busqueda cancelada");
		} else {
			System.out.println();
			System.out.println("Dni no encontrado en la base de datos");
			menuClientes();
		}

		teclado.close();
	}

	// M�todo que devuelve el listado de los clientes
	private static String listarClientes(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR CLIENTE");
		System.out.println();

		String result = obtenerClientes(conexion, nombreBBDD);

		System.out.println(result);

		System.out.print("M - Volver al menú: ");
		String respuesta = teclado.nextLine().toUpperCase();
		if (respuesta.equals("M")) {
			// menuPrincipal();
		} else {
			System.out.println("Opción no válida");
			menuClientes();
		}

		teclado.close();

		return result;
	}

	private static String obtenerClientes(Connection conexion, String nombreBBDD) {
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Objeto de tipo Statement para establecer la conexiÛn
		Statement stmt = null;

		// Variable local para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".cliente" + " ORDER BY nombre";

		try {

			// Objeto de tipo Statement para establecer la conexiÛn con el objeto de tipo
			// Connection
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informaciÛn, a travÈs del objeto stmt
			// y su mÈtodo en el cual le pasamos por par·metro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Bucle, mientras el objeto rs siga recibiendo informaciÛn, almacenamos el
			// resultado de cada consulta en una variable
			while (rs.next()) {

				String nombre = rs.getString(1);
				result += "Nombre: " + nombre + "\n";

				String apellidos = rs.getString(2);
				result += "Apellidos: " + apellidos + "\n";

				String edad = rs.getString(3);
				result += "Edad: " + edad + "\n";

				String dni = rs.getString(4);
				result += "DNI: " + dni + "\n";

				String email = rs.getString(5);
				result += "E-mail: " + email + "\n";

				String numeroSeriePatinete = rs.getString(6);
				result += "N˙mero de serie del patinete alquilado: " + numeroSeriePatinete + "\n";

				result += "*************************************" + "\n";

			}

			// Control de posibles excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}

		return result;
	}

	// MÈtodo para guardar el listado de ordenadores en un fichero
	private static void exportarListadoClientes(String listado) {

		try {

			// Seleccionamos la ruta y la carpeta en la cual ir· nuestro archivo
			File ruta = new File("C:" + File.separator + "informes");
			ruta.mkdir();

			// Creamos la ruta del archivo
			ruta = new File("C:" + File.separator + "informes" + File.separator + "Listado_clientes.txt");

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
			buffer.write("LISTADO DE CLIENTES");
			buffer.newLine();
			buffer.write(listado);

			// Mensaje informativo
			System.out.println("El listado de clientes ha sido exportado con Èxito.");

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean existeCliente(Connection conexion, String nombreBBDD, String dni) {

		// Sentencia con la consulta para comprobar si hay coincidencias de DNI
		String compruebaCliente = "select 1 " + "from " + nombreBBDD + ".cliente" + " WHERE Dni = '" + dni + "'";

		// Objeto de tipo Statement para establecer la conexión
		Statement stmt = null;

		try {
			// Creamos la consulta a la BBDD
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informaci�n, a trav�s del objeto stmt
			// y su m�todo en el cual le pasamos por par�metro la variable local
			ResultSet rs = stmt.executeQuery(compruebaCliente);

			while (rs.next()) {
				if (rs.getInt(1) == 1) {
					return true;
				}
			}

			// Llamada al m�todo que controla las posibles excepciones SQL
		} catch (SQLException e) {
			printSQLException(e);
		}

		return false;
	}

	// MÈtodo que controla las posibles excepciones SQL
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