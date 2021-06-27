/**
 * @author Bernat Vazquez
 * @version 1.0
 */

package principal;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Scanner;

import clases.Empleado;
import clases.GestionCliente;
import clases.GestionPatinete;
import clases.Utilidades;
import clases.GestionEmpleado;

public class GestionSistema {

	public static void main(String[] args) {

		// Llamamos al metodo login()
		login();

	}

	// Metodo con el que mostramos el menu principal del programa
	public static void menu() {
		boolean bucle = false;

		do {
			Scanner sc2 = new Scanner(System.in);
			System.out.println("");
			System.out.println("");
			System.out.println("***************************************************");
			System.out.println("****************   ElectricSkate    ***************");
			System.out.println("***************************************************");
			System.out.println("");
			System.out.println("¡Bienvenido/a " + Utilidades.nombreUsuario + "!");
			System.out.println("");
			System.out.println("   1. Gestionar clientes");
			System.out.println("");
			System.out.println("   2. Gestionar patinetes");
			System.out.println("");
			System.out.println("   3. Gestionar empleados");
			System.out.println("");
			System.out.println("");
			System.out.println("C. Cerrar sesion");
			System.out.println("");
			System.out.println("S. Salir del programa");
			System.out.println("");

			// Pedimos al usuario que introduzca una opcion y la almacenamos en un String
			System.out.print("Introduzca una operacion: ");
			String opc = sc2.nextLine().toUpperCase();

			// Dependiendo de la opcion introducida por el usuario, llamamos al menu
			// correspondiente (Cliente, Patinete, Empleados)
			switch (opc) {

			case "1":
				GestionCliente.menuClientes();
				break;

			case "2":
				GestionPatinete.menuPatinete();
				break;

			case "3":
				GestionEmpleado.menuEmpleados();
				break;

			case "C":
				System.out.println("");
				System.out.println("Cerrando sesion...");
				login();
				break;

			case "S":
				System.out.println("");
				System.out.println("Saliendo del programa...");
				System.exit(0);
				break;

			default:
				System.out.println("");
				System.out.println("ERROR! Debes introducir una opcion valida.");
				break;

			}
		} while (bucle == false);

	}

	// Metodo con el cual el usuario se logea en el sistema
	public static void login() {

		boolean bucle2 = false;
		do {
			int opc;
			Scanner sc = new Scanner(System.in);
			System.out.println("");
			System.out.println("");
			System.out.println("***************************************************");
			System.out.println("****************   ElectricSkate    ***************");
			System.out.println("***************************************************");
			System.out.println("");
			System.out.println("Bienvenido a  ElecticSkate.");
			System.out.println("Por favor, inicie sesion para continuar.");
			System.out.println("");
			System.out.println("                        ___");
			System.out.println("                         |");
			System.out.println("                         |");
			System.out.println("                         |");
			System.out.println("                \\________|");
			System.out.println("               (O)       (O)");
			System.out.println("");
			System.out.println("");
			try {
				System.out.print("1. Inicia sesion: ");
				opc = sc.nextInt();

				if (opc == 1) {
					bucle2 = true;

				} else {
					System.out.println("");
					System.out.println("Error! Debes introducir la opcion 1.");
				}

			} catch (Exception e) {
				System.out.println("");
				System.out.println("Error! No ha introducido una opcion valida.");
			}

		} while (bucle2 == false);

		String u = "admin";
		String c = "1234";
		boolean bucle = false;
		Scanner sc2 = new Scanner(System.in);

		do {
			System.out.println("");
			System.out.println("");
			System.out.println("INICIAR SESION");
			System.out.println("");

			// Pedimos usuario y contrasena al usuario
			System.out.print("Introduzca su usuario: ");
			Utilidades.nombreUsuario = sc2.next();
			System.out.println("");
			System.out.print("Introduzca su contrasena: ");
			String passwd = sc2.next();
			System.out.println("");

			// Si los datos coinciden, llamamos al metodo menu()
			if (Utilidades.nombreUsuario.equals(u) && passwd.equals(c)) {
				System.out.println("");
				System.out.println("INICIANDO SESION...");
				menu();
				bucle = true;

				// Si los datos no coinciden, se repite de nuevo el bucle y se vuelven a pedir
				// los datos al usuario
			} else {
				System.out.println("");
				System.out.println("Los datos introducidos son incorrectos, intentelo de nuevo...");
			}

		} while (bucle == false);
	}

}
