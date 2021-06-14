package principal;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Scanner;

import clases.GestionCliente;
import clases.GestionPatinete;
import clases.GestionEmpleado;

public class GestionSistema {

	public static void main(String[] args) {
		
		try {

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");

			login();	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
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
		System.out.println("Bienvenido admin");
		System.out.println("");
		System.out.println("   1. Gestionar clientes");
		System.out.println("");
		System.out.println("   2. Gestionar patinetes");
		System.out.println("");
		System.out.println("   3. Gestionar empleados");
		System.out.println("");
		System.out.println("");
		System.out.println("C. Cerrar sesión");
		System.out.println("");
		System.out.print("Introduzca una operación: ");
		String opc = sc2.nextLine().toUpperCase();
		
		switch(opc) {
		
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
			System.out.println("Cerrando sesión...");
			login();
			break;
			
		default:
			System.out.println("");
			System.out.println("¡ERROR! Debes introducir una opción válida.");
			break;
			
		}
		}while(bucle == false);
	
	}
	
	
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
			System.out.println("Por favor, inicie sesión para continuar.");
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
				System.out.print("            1. Inicia sesión: ");
				opc = sc.nextInt();

				if (opc == 1) {
					bucle2 = true;

				} else {
					System.out.println("");
					System.out.println("¡Error! Debes introducir la opción 1.");
				}

			} catch (Exception e) {
				System.out.println("");
				System.out.println("¡Error! No ha introducido una opción válida.");
			}
			
		} while (bucle2 == false);
		
		
		String u = "admin";
		String c = "12345678";
		boolean bucle = false;
		Scanner sc2 = new Scanner(System.in);
		
		do {
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("*******************************");
		System.out.println("*******  INICIA SESIÓN  *******");
		System.out.println("*******************************");
		System.out.println("");
		System.out.print("       Introduzca su usuario: ");
		String usuario = sc2.next();
		System.out.println("");
		System.out.print("       Introduzca su contraseña: ");
		String passwd = sc2.next();
		System.out.println("");
		
		if(usuario.equals(u) && passwd.equals(c)) {
			System.out.println("");
			System.out.println("INICIANDO SESIÓN...");
			menu();
			bucle = true;
		
		}else {
			System.out.println("");
			System.out.println("Los datos introducidos son incorrectos, inténtelo de nuevo...");
		}
		
		
		}while(bucle == false);
	}
	

}
