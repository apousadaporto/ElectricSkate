package principal;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Scanner;


public class GestionSistema {

	public static void main(String[] args) {
		
		try {

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/electricskate", "root", "");

			System.out.println("�Conexi�n establecida correctamente!");
			login();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	
	
	public static void login() {

		boolean bucle1 = false;
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
			System.out.println("Por favor, inicie sesi�n para continuar.");
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
				System.out.print("            1. Inicia sesi�n: ");
				opc = sc.nextInt();

				if (opc == 1) {
					bucle1 = true;

				} else {
					System.out.println("");
					System.out.println("�Error! Debes introducir la opci�n 1.");
				}

			} catch (Exception e) {
				System.out.println("");
				System.out.println("�Error! No ha introducido una opci�n v�lida.");
			}

		} while (bucle1 == false);
		

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
		System.out.println("*******  INICIA SESI�N  *******");
		System.out.println("*******************************");
		System.out.println("");
		System.out.print("       Introduzca su usuario: ");
		String usuario = sc2.next();
		System.out.println("");
		System.out.print("       Introduzca su contrase�a: ");
		String passwd = sc2.next();
		System.out.println("");
		
		if(usuario.equals(u) && passwd.equals(c)) {
			System.out.println("");
			System.out.println("INICIANDO SESI�N...");
			bucle = true;
		
		}else {
			System.out.println("");
			System.out.println("Los datos introducidos son incorrectos, int�ntelo de nuevo...");
		}
		
		
		}while(bucle == false);
		
		
		
	}
	
	

}
