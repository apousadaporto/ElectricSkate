/**
 * @author Bernat Vazquez
 * @version 1.0
 */

package clases;

import java.util.Scanner;

import principal.GestionSistema;

import java.io.*;
import java.sql.*;

public class GestionEmpleado {

	
	// Menu que muestra las opciones para trabajar con los empleados
	public static void menuEmpleados() {
		
		
		Scanner sc = new Scanner(System.in);

		
		// Realizamos la conexion a la base de datos
		Connection conexion = null;

		try {

			conexion = DriverManager.getConnection(Utilidades.URL_BBDD, Utilidades.USER_BBDD, Utilidades.PASSWD_BBDD);

			System.out.println("");
			System.out.println("");
			System.out.println("GESTIONAR EMPLEADOS");
			System.out.println("");
			System.out.println("   1. Anyadir nuevo empleado");
			System.out.println("");
			System.out.println("   2. Exportar listado de empleados a fichero.txt");
			System.out.println("");
			System.out.println("");
			System.out.println(
				    "```` ` `````` `.odNMMNdo.````  ````````\n"
				   +"```````  `` ``+NMMMMMMMMN+`````````````\n"
				   +"`````````````.MMMMMMMMMMMM-````````````\n"
			       +"`````````````.MMMMMMMMMMMM-````````````\n"
				   +"``````````````+MMMMMMMMMM+`````````````\n"
				   +"```````````````.sdNMMNds-``````````````\n"
				   +"```````````````````````````````````````\n"
				   +"`````````.odNMm.`-smms-`.mMNdo.````````\n"
				   +"`````` -NMMMMMm.`+mm+`.dMMMMMN-````````\n"
				   +"```````sMMMMMMMd``dm``dMMMMMMMs````````\n"
				   +"```````sMMMMMMMMh/MM/yMMMMMMMMy````````\n"
				   +"````` `sMMMMMMMMMMMMMMMMMMMMMMy````````\n"
				   +"```````-dMMMMMMMMMMMMMMMMMMMMd-````````");	        
			System.out.println("");
			System.out.println("");
			System.out.println("M. Volver al menu");
			System.out.println("");
			System.out.print("Introduzca una operacion: ");
			String opc = sc.nextLine().toUpperCase();
			
			switch(opc) {
			
			case "1":
				insertarEmpleado(conexion, Utilidades.NOMBRE_BBDD);
				break;
				
			case "2":
				exportarFicheroEmmpleados(conexion, Utilidades.NOMBRE_BBDD);
				break;
			
			case "M":
				GestionSistema.menu();
				break;
				
			default:
				System.out.println("");
				System.out.println("ERROR! Debes introducir una opcion disponible.");
				menuEmpleados();
				break;
			}
			
		} catch (SQLException e) {

			Utilidades.printSQLException(e);
		}
		
		
	}
	
	
	// Metodo para insertar un empleado en la base de datos
	public static void insertarEmpleado(Connection conexion, String nombreBBDD) {
		
		// Instanciamos un objeto de tipo Scanner
		Scanner sc = new Scanner(System.in);
		
		// Solicitud de datos al usuario
		System.out.println("");
		System.out.println("ANYADIR EMPLEADO");
		System.out.println("");
		System.out.println("  Introduzca los siguientes datos:");
		System.out.println("");
		System.out.print("      Nombre: ");
		String nombre = sc.nextLine();
		System.out.print("      Apellidos: ");
		String apellidos = sc.nextLine();
		System.out.print("      Edad: ");
		int edad = sc.nextInt();
		sc.nextLine();
		System.out.print("      DNI: ");
		String dni = sc.nextLine();
		System.out.print("      E-mail: ");
		String email = sc.nextLine();
		System.out.print("      Nombre de usuario: ");
		String nUsuario = sc.nextLine();
		System.out.print("      Contrasenya: ");
		String contrasenya = sc.nextLine();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println(        
			       " 	 .-:::----:::-.   \n"   
			      +"  `:/-`     ``    `-/:` \n"   
			      +" -/`      :+oooo/-    `/- \n"   
			     +" /:      :oooooooo/     :/  \n" 
			    +" ::      :oooooooo/      :: \n" 
			    +" o`      -oooooooo-      `o  \n"
			    +"`+       -oooooooo-       +` \n"
			    +" o`       -oooooo-       `o  \n"
			    + " ::        :oooo:        ::  \n"
			     + "  /:      .+oooo+.      :/  \n" 
			      + "  -/..:/+oooooooo+/:../-   \n" 
			       +"    `:+oooooooooooooo+:`    \n" 
			         + "       .-/+oooooo+/-.      " ); 
		
		System.out.println("");
		System.out.println("");
		System.out.println("    ===============================");
		System.out.println("    Nombre: " + nombre);
		System.out.println("    Apellidos: " + apellidos);
		System.out.println("    Edad: " + edad);
		System.out.println("    DNI: " + dni);
		System.out.println("    E-mail: " + email);
		System.out.println("    Nombre de usuario: " + nUsuario);
		System.out.println("    Contrasenya: " + contrasenya);
		System.out.println("    ===============================");
		System.out.println("");
		
		boolean bucle = true;

		//Bucle do-while para controlar la respuesta del usuario
		do {
		System.out.print("R. Registrar Cliente / M. Volver al menu: ");
		String respuesta = sc.nextLine().toUpperCase();
		
		switch(respuesta) {
		
		case "R":
			
			Statement stmt = null;
			
			try {
				// Realizamos la conexion
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".empleado VALUES ('" + nombre + "', '" + apellidos
						+ "'," + edad + ", '" + dni + "', '" + email + "', '" + nUsuario + "', '" + contrasenya + "')";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mostramos un mensaje por pantalla al haber almacenado los valores correctamente
				System.out.println();
				System.out.println("El empleado " + nombre + " " + apellidos
						+ " ha sido introducido correctamente en la base de datos.");

				// Cierre de la conexion
				stmt.close();

				// Llamada al metodo que controla las posibles excepciones SQL
			} catch (SQLException e) {
				Utilidades.printSQLException(e);
			}
			
			bucle = false;
			break;
			
		case "M":
			menuEmpleados();
			break;
		
		default:
			System.out.println("");
			System.out.println("ERROR! Debe introducir una de las opciones disponibles (R-M)");
			System.out.println("");
		}
		}while(bucle == true);
		
	}
	
	// Metodo para exportar fichero con el listado de todos los empleados
	public static void exportarFicheroEmmpleados(Connection conexion, String nombreBBDD) throws SQLException{
		
		Statement stmt = null;
		String query = "select Nombre, Apellidos, Edad, Dni, Email, NombreUsuario, Contrasenya from " + nombreBBDD + ".empleado";

		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				// Creamos un directorio para almacenar el fichero txt con el listado de empleados
				File dir = new File("C:\\empleados");
				dir.mkdir();
				
				// Creamos el archivo empleados.txt dentro del directorio que hemos creado
				File archivo = new File("C:\\empleados\\empleados.txt");
				
				// Creamos un objeto de tipo FileWriter
				FileWriter wt = new FileWriter(archivo);

				System.out.println("");
				System.out.println("===================================");
				System.out.println("======= Listado Empleados =======");
				System.out.println("===================================");
				
				// Escribimos los empleados en el fichero
				while (rs.next()) {
					
					System.out.println("");
					System.out.println("====================================");
					wt.write("====================================" + ("\n"));
					String nombre = rs.getString(1);
					System.out.println("Numero interno: " + nombre);
					wt.write("Nombre: " + nombre + ("\n"));
					String apellidos = rs.getString(2);
					System.out.println("Apellidos: " + apellidos);
					wt.write("Apellidos: " + apellidos + ("\n"));
					int edad = rs.getInt(3);
					System.out.println("Edad: " + edad + " anyos");
					wt.write("Edad: " + edad + " anyos" + ("\n"));
					String dni = rs.getString(4);
					System.out.println("DNI: " + dni);
					wt.write("DNI: " + dni + ("\n"));
					String email = rs.getString(5);
					System.out.println("Email: " + email);
					wt.write("Email: " + email + ("\n"));
					String nUsuario = rs.getString(6);
					System.out.println("Nombre de usuario: " + nUsuario);
					wt.write("Nombre de usuario: " + nUsuario + ("\n"));
					String contrasenya = rs.getString(7);
					System.out.println("Contrasenya: " + contrasenya);
					wt.write("Contrasenya: " + contrasenya + ("\n"));
					System.out.println("====================================");
					wt.write("====================================" + ("\n"));
					System.out.println("");
					
				}

				if (wt != null)
					wt.close();
			
			// Controlamos las excepciones
			} catch (IOException e) {
				System.out.println("ERROR!");
				e.printStackTrace();
			}

		} catch (SQLException e) {
			Utilidades.printSQLException(e);
		} finally {
			stmt.close();
		}
		
		System.out.println("El listado de empleados \"empleados.txt\" se ha guardado en la ruta \"C:\\empleados\".");
		
	}
}
