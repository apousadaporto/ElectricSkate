package principal;

import java.util.Scanner;

public class GestionSistema {

	public static void main(String[] args) {
		
		menuPrincipal();
		
		
	}

	private static void menuPrincipal() {
		
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
		System.out.print("            1. Inicia sesi�n: ");
		opc = sc.nextInt();
		
		
		String u = "admin";
		String c = "12345678";
		boolean bucle = false;
		
		switch(opc) {
		
		case 1:
			do {
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("     INICIA SESI�N");
				System.out.println("");
				System.out.print("         Introduzca su usuario: ");
				String usuario = sc.next();
				System.out.println("");
				System.out.print("         Introduzca su contrase�a: ");
				String passwd = sc.next();
				System.out.println("");
				
				if (u.equals(usuario) &&  c.equals(passwd)) {
					System.out.println("       Iniciando sesi�n...");
					bucle = true;
				}
				
				else {
					
					if (u.equals(usuario)) {
						
					}else {
						System.out.println("       El nombre de usuario es incorrecto");
					}
					
					if (c.equals(passwd)) {
						
					}else {
						System.out.println("       La contrase�a es incorrecta");
					}
					
				}
				
			}while(bucle == false);
			break;
			
			default:
				System.out.println("");
				System.out.println("Solo puedes introducir la opci�n n�mero 1");
				break;
		}
		
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("***************************************************");
		System.out.println("****************   ElectricSkate    ***************");
		System.out.println("***************************************************");
		System.out.println("");
		System.out.println("Bienvenido " + u);
		System.out.println("");
		System.out.println("     1. Gestionar clientes");
		System.out.println("");
		System.out.println("     2. Gestionar patinetes");
		System.out.println("");
		System.out.println("     3. Gestionar empleados");
		System.out.println("");
		System.out.println("     4. Cerrar sesi�n");
		System.out.println("");
		System.out.print("       Seleccione una operaci�n:");
		String opc2 = sc.next();
		System.out.println("");
		
	}
	
}
