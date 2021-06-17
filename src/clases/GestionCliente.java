/**
 * @author Cinzia Picciau
 * @version 1.0
 */

package clases;

import java.util.Scanner;

import principal.GestionSistema;

import java.io.*;
import java.sql.*;

public class GestionCliente {

	// Menu que muestra las opciones para trabajar con los clientes
	public static void menuClientes() {
		
		// Realizamos la conexion a la base de datos
		Connection conexion = null;	
		try {
			conexion = DriverManager.getConnection(Utilidades.URL_BBDD, Utilidades.USER_BBDD, Utilidades.PASSWD_BBDD);
		// Controlamos las posibles excepciones
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("GESTIONAR CLIENTES");
		System.out.println();
		System.out.println("Selecciona una operacion:");
		System.out.println("1. Anyadir nuevo cliente");
		System.out.println("2. Consultar cliente");
		System.out.println("3. Listar clientes");
		System.out.println("4. Exportar listado de clientes a fichero.txt");
		System.out.println();
		System.out.println();
		System.out.println(
				"`````````````..`````````````\n"
				+"`````````.smMMMMms-`````````\n"
				+"````````:NMhhdmNNMM:````````\n"
				+"````````yh:```````yy````````\n"
				+"````````os````````oo````````\n"
				+"`````````h+``````/d`````````\n"
				+"``````````oy+::+yo``````````\n"
				+"```````:+ss+////+ss+:```````\n"
				+"````:hMMMMMMMMMMMMMMMMh:````\n"
				+"```oMMMMMMMMMMMMMMMMMMMMs```\n"
				+"``.MMMMMMMMMMMMMMMMMMMMMM-``\n"
				+"``-MMMMMMMMMMMMMMMMMMMMMM:``");
		System.out.println();
		System.out.println();
		System.out.println("M - Volver al menu");
		System.out.println();
		System.out.print("Introduzca su eleccion: ");
		String opcion = teclado.nextLine().toUpperCase();
		switch (opcion) {
		case "1":
			insertarCliente(conexion, Utilidades.NOMBRE_BBDD);
			break;
		case "2":
			buscarCliente(conexion, Utilidades.NOMBRE_BBDD);
			break;
		case "3":
			listarClientes(conexion, Utilidades.NOMBRE_BBDD);
			break;
		case "4":
			String listadoClientes = obtenerClientes(conexion, Utilidades.NOMBRE_BBDD);
			exportarListadoClientes(listadoClientes);
			break;
		case "M":
			GestionSistema.menu();
			break;
		default:
			System.out.println("Opcion no disponible");
			menuClientes();
			break;
		}
		
		teclado.close();
	}

	// Metodo para insertar los valores en los campos de la tabla
	private static void insertarCliente(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		// Solicitud y recogida de datos al usuario por teclado
		System.out.println();
		System.out.println("ANYADIR CLIENTE");
		System.out.println();
		System.out.println("Introduzca los siguientes datos: ");
		System.out.println();
		System.out.print("Nombre: ");
		String nombre = teclado.nextLine();
		System.out.print("Apellidos: ");
		String apellidos = teclado.nextLine();
		System.out.print("Edad: ");
		String edad = teclado.nextLine();
		System.out.print("DNI: ");
		String dni = teclado.nextLine();
		System.out.print("E-mail: ");
		String email = teclado.nextLine();
		// Confirmamos que el campo "edad" sea correcto
		if (!esEntero(edad)) {
			System.out.println();
			System.out.println("El campo \"edad\" tiene que ser un numero entero.");
			insertarCliente(conexion,Utilidades.NOMBRE_BBDD);
		}
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellidos: " + apellidos);
		System.out.println("Edad: " + edad);
		System.out.println("DNI: " + dni);
		System.out.println("E-mail: " + email);
		System.out.println();
		System.out.print("R - Registrar cliente o M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario confirma que quiere registrar el nuevo cliente
		if (respuesta.equals("R")) {
			// Objeto de tipo Statement para establecer la conexion
			Statement stmt = null;

			try {
				// Realizamos la conexion
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".cliente VALUES " + "('" + nombre + "','" + apellidos
						+ "'," + edad + ",'" + dni + "','" + email + "', null)";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mostramos un mensaje por pantalla al haber almacenado los valores
				// correctamente
				System.out.println();
				System.out.println("El cliente " + nombre + " " + apellidos
						+ " ha sido introducido correctamente en la base de datos.");

				// Cierre de la conexion
				stmt.close();

				// Volvemos al menu
				GestionSistema.menu();

				// Llamada al metodo que controla las posibles excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}
			// Comprobamos que el usuario quiera volver al menu sin registrar el cliente
		} else if (respuesta.equals("M")) {
			System.out.println("Registro cancelado");
			GestionSistema.menu();
		} else {
			System.out.println("Opcion no valida");
			menuClientes();
		}

		teclado.close();
	}

	// Metodo para buscar un cliente en la BDOO por su DNI
	private static void buscarCliente(Connection conexion, String nombreBBDD) {

		Scanner teclado = new Scanner(System.in);

		System.out.println();
		System.out.println("CONSULTAR CLIENTE");
		System.out.println();
		System.out.print("Introduzca el DNI del cliente para buscar: ");
		// Variable con el valor del DNI
		String dniUsuario = teclado.nextLine();
		System.out.println();
		System.out.println("DNI: " + dniUsuario);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.print("B - Buscar cliente o M - Volver al menu: ");
		String respuesta = teclado.nextLine().toUpperCase();
		// Si el usuario confirma que quiere registrar el nuevo cliente
		if (respuesta.equals("B") && existeCliente(conexion, nombreBBDD, dniUsuario)) {
			// Variable que almacena el resultado de las consultas
			String result = "";

			// Obejto de tipo Statement para establecer la conexion
			Statement stmt = null;

			// Variable local para realizar la consulta
			String query = "select * " + "from " + nombreBBDD + ".cliente" + " WHERE Dni = '" + dniUsuario + "'";

			try {
				// Realizamos la conexion
				stmt = conexion.createStatement();

				// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto stmt
				// y su metodo en el cual le pasamos por parametro la variable local
				ResultSet rs = stmt.executeQuery(query);

				// Bucle, mientras el objeto rs siga recibiendo informacion, almacenamos el
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
					// Comprueba que la variable numeroSeriePatinete no sea null ni un String vacio
					if (numeroSeriePatinete != null && !numeroSeriePatinete.trim().isEmpty()) {
					result += "Numero de serie del patinete alquilado: " + numeroSeriePatinete + "\n";

					}
					result += "*************************************" + "\n";
				}

				// Mostramos por pantalla los resultados obtenidos
				System.out.println();
				System.out.println(result);

				// Cierre de la conexion
				stmt.close();

				// Volvemos al menu
				GestionSistema.menu();

				// Llamada al metodo que controla las posibles excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}
			// Comprobamos que el usuario quiera volver al menu sin registrar el cliente
		} else if (respuesta.equals("M")) {
			System.out.println();
			System.out.println("Busqueda cancelada");
			// Volvemos al menu
			GestionSistema.menu();
			// En caso de que no haya coincidencia con el dni introducido por el usuario
		} else {
			System.out.println();
			System.out.println("Dni no encontrado en la base de datos");
			menuClientes();
		}

		teclado.close();
	}

	private static String obtenerClientes(Connection conexion, String nombreBBDD) {
		// Variable que almacena el resultado de las consultas
		String result = "Resultado de la busqueda: \n";

		// Objeto de tipo Statement para establecer la conexion
		Statement stmt = null;

		// Variable local para realizar la consulta
		String query = "select * " + "from " + nombreBBDD + ".cliente" + " ORDER BY nombre";

		try {

			// Objeto de tipo Statement para establecer la conexion con el objeto de tipo
			// Connection
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto stmt
			// y su metodo en el cual le pasamos por parametro la variable local
			ResultSet rs = stmt.executeQuery(query);

			// Bucle, mientras el objeto rs siga recibiendo informacion, almacenamos el
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
				// Comprueba que la variable numeroSeriePatinete no sea null ni un String vacio
				if (numeroSeriePatinete != null && !numeroSeriePatinete.trim().isEmpty()) {
				result += "Numero de serie del patinete alquilado: " + numeroSeriePatinete + "\n";
				}
				
				result += "*************************************" + "\n";

			}

			// Control de posibles excepciones SQL
		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		}

		return result;
	}
	
	// Metodo que devuelve el listado de los clientes
		private static String listarClientes(Connection conexion, String nombreBBDD) {

			Scanner teclado = new Scanner(System.in);

			System.out.println();
			System.out.println("LISTAR CLIENTES");
			System.out.println();

			String result = obtenerClientes(conexion, nombreBBDD);

			System.out.println(result);

			System.out.print("M - Volver al menu: ");
			String respuesta = teclado.nextLine().toUpperCase();
			if (respuesta.equals("M")) {
				// Volvemos al menu
				GestionSistema.menu();
			} else {
				// Si el usuario introduce un valor diferente a "M"
				System.out.println("Opcion no valida");
				menuClientes();
			}

			teclado.close();

			return result;
		}

	// Metodo para guardar el listado de ordenadores en un fichero
	private static void exportarListadoClientes(String listado) {

		try {

			// Seleccionamos la ruta y la carpeta en la cual ira nuestro archivo
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
			System.out.println("El listado de clientes ha sido exportado con exito.");
			System.out.println();

			// Cierre del stream
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		GestionSistema.menu();
	}

	// Metodo para asegurarnos que nos introduzcan un numero entero
		private static boolean esEntero(String valor) {

			// Convertirmos el String que nos pasen a int
			try {
				Integer.valueOf(valor);
				// Controlamos las excepciones en caso de que el argumento sea un caracter o un
				// texto
			} catch (Exception ex) {
				return false;
			}
			return true;
		}
	
		// Metodo que comprueba que haya un cliente registrado con el dni que nos pasan por parametro 
		private static boolean existeCliente(Connection conexion, String nombreBBDD, String dni) {

		// Variable que almacena la consulta a la base de datos
		String compruebaCliente = "select 1 " + "from " + nombreBBDD + ".cliente" + " WHERE Dni = '" + dni + "'";

		// Objeto de tipo Statement para establecer la conexion
		Statement stmt = null;

		try {
			// Creamos la consulta a la BBDD
			stmt = conexion.createStatement();

			// Objeto de tipo ResulSet para recibir la informacion, a traves del objeto stmt
			// y su metodo en el cual le pasamos por parametro la variable local
			ResultSet rs = stmt.executeQuery(compruebaCliente);

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