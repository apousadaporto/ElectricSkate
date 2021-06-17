package clases;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class UtilidadesTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void print_sql_exception_cuando_cause_es_nulo_o_vacio_el_metodo_no_debe_fallar() {

		SQLException sqlException = new SQLException();

		Utilidades.printSQLException(sqlException);
	}
}