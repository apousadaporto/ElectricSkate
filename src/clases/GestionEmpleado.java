package clases;

import java.util.Scanner;

import principal.GestionSistema;

import java.io.*;
import java.sql.*;

public class GestionEmpleado {

	public static void main(String[] args) {
	

	}
	
	public static void menuEmpleados() {
		Scanner sc = new Scanner(System.in);

		Connection conexion = null;

		try {

			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");

			System.out.println("");
			System.out.println("");
			System.out.println("GESTIONAR EMPLEADOS");
			System.out.println("");
			System.out.println("   1. A�adir nuevo empleado");
			System.out.println("");
			System.out.println("   2. Exportar listado de empleados a fichero.txt");
			System.out.println("");
			System.out.println("");
			System.out.println("M. Volver al men�");
			System.out.println("");
			System.out.print("Introduzca una operaci�n: ");
			String opc = sc.nextLine().toUpperCase();
			
			switch(opc) {
			
			case "1":
				insertarEmpleado(conexion, "electricskate");
				break;
				
			case "2":
				exportarFicheroEmmpleados(conexion, "electricskate");
				break;
			
			case "M":
				GestionSistema.menu();
				break;
				
			default:
				System.out.println("");
				System.out.println("�ERROR! Debes introducir una opci�n disponible.");
				menuEmpleados();
				break;
			}
			
		} catch (SQLException e) {

			printSQLException(e);
		}
		
		
	}
	
	
	public static void insertarEmpleado(Connection conexion, String nombreBBDD) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("");
		System.out.println("A�ADIR EMPLEADO");
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
		System.out.print("      Contrase�a: ");
		String contrase�a = sc.nextLine();
		System.out.println("");
		System.out.println("    ===============================");
		System.out.println("    Nombre: " + nombre);
		System.out.println("    Apellidos: " + apellidos);
		System.out.println("    Edad: " + edad);
		System.out.println("    DNI: " + dni);
		System.out.println("    E-mail: " + email);
		System.out.println("    Nombre de usuario: " + nUsuario);
		System.out.println("    Contrase�a: " + contrase�a);
		System.out.println("    ===============================");
		System.out.println("");
		
		boolean bucle = true;
		
		do {
		System.out.print("R. Registrar Cliente / M. Volver al men�: ");
		String respuesta = sc.nextLine().toUpperCase();
		
		switch(respuesta) {
		
		case "R":
			
			Statement stmt = null;
			
			try {
				// Realizamos la conexion
				stmt = conexion.createStatement();

				// Variable que almacena la consulta a la BBDD
				String query = "INSERT INTO " + nombreBBDD + ".empleado VALUES ('" + nombre + "', '" + apellidos
						+ "'," + edad + ", '" + dni + "', '" + email + "', '" + nUsuario + "', '" + contrase�a + "')";

				// Ejecutamos la consulta
				stmt.executeUpdate(query);

				// Mostramos un mensaje por pantalla al haber almacenado los valores
				// correctamente
				System.out.println();
				System.out.println("El empleado " + nombre + " " + apellidos
						+ " ha sido introducido correctamente en la base de datos.");

				// Cierre de la conexión
				stmt.close();

				// Llamada al metodo que controla las posibles excepciones SQL
			} catch (SQLException e) {
				printSQLException(e);
			}
			
			bucle = false;
			break;
			
		case "M":
			menuEmpleados();
			break;
		
		default:
			System.out.println("");
			System.out.println("�ERROR! Debe introducir una de las opciones disponibles (R-M)");
			System.out.println("");
		}
		}while(bucle == true);
		
	}
	
	
	public static void exportarFicheroEmmpleados(Connection conexion, String nombreBBDD) throws SQLException{
		
		Statement stmt = null;
		String query = "select Nombre, Apellidos, Edad, Dni, Email, NombreUsuario, Contrase�a from " + nombreBBDD + ".empleado";

		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				// crear directorio para guardar los informes
				File dir = new File("C:\\empleados");
				dir.mkdir();
				File archivo = new File("C:\\empleados\\empleados.txt");
				FileWriter wt = new FileWriter(archivo);

				System.out.println("");
				System.out.println("===================================");
				System.out.println("======= Listado Empleados =======");
				System.out.println("===================================");

				while (rs.next()) {
					
					System.out.println("");
					System.out.println("====================================");
					wt.write("====================================" + ("\n"));
					String nombre = rs.getString(1);
					System.out.println("N�mero interno: " + nombre);
					wt.write("Nombre: " + nombre + ("\n"));
					String apellidos = rs.getString(2);
					System.out.println("Apellidos: " + apellidos);
					wt.write("Apellidos: " + apellidos + ("\n"));
					int edad = rs.getInt(3);
					System.out.println("Edad: " + edad + " a�os");
					wt.write("Edad: " + edad + " a�os" + ("\n"));
					String dni = rs.getString(4);
					System.out.println("DNI: " + dni);
					wt.write("DNI: " + dni + ("\n"));
					String email = rs.getString(5);
					System.out.println("Email: " + email);
					wt.write("Email: " + email + ("\n"));
					String nUsuario = rs.getString(6);
					System.out.println("Nombre de usuario: " + nUsuario);
					wt.write("Nombre de usuario: " + nUsuario + ("\n"));
					String contrase�a = rs.getString(7);
					System.out.println("Contrase�a: " + contrase�a);
					wt.write("Contrase�a: " + contrase�a + ("\n"));
					System.out.println("====================================");
					wt.write("====================================" + ("\n"));
					System.out.println("");
					System.out.println("El listado de empleados \"empleados.txt\" se ha guardado en la ruta \"C:\\empleados\".");
				}

				if (wt != null)
					wt.close();

			} catch (IOException e) {
				System.out.println("�ERROR!");
				e.printStackTrace();
			}

		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			stmt.close();
		}
		
	}
	
	
	
	
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
