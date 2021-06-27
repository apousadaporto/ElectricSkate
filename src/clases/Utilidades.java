/**
 * @author Cinzia Picciau
 * @version 1.0
 */

package clases;

import java.sql.SQLException;

public class Utilidades {

	// Constante que almacena el nombre de la base de datos
	public static final String NOMBRE_BBDD = "electricskate";

	// Constante que almacena la URL de la base de datos
	public static final String URL_BBDD = "jdbc:mysql://localhost:3306/electricskate";

	// Constante que almacena el usuario de acceso la base de datos
	public static final String USER_BBDD = "root";

	// Constante que almacena la password de acceso a la base de datos
	public static final String PASSWD_BBDD = "";

	// Metodo que controla las posibles excepciones SQL
	public static void printSQLException(SQLException ex) {

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

	// Metodo para asegurarnos que nos introduzcan un numero entero
	public static boolean esEntero(String valor) {

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

}
