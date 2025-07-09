package modelo.dao;

/**
 * @author 50863079M
 * 
 * 
 * Implemetado como parte del Trabajo Fin de Estudios 
 * en el Grado de Ingenier�a Inform�tica de la UNIR/
 *
 * */

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import modelo.vo.EmpleadoVO;

public class EmpleadoDAOTest {

	@Test
    public void testBuscarPorDNIExacto() throws Exception {
        EmpleadoDAO dao = new EmpleadoDAO();
        List<EmpleadoVO> resultado = dao.cargaEmpleados("90055378N");
        assertFalse(resultado.isEmpty(), "Se esperaba al menos un resultado");
        assertTrue(resultado.stream().anyMatch(e -> "90055378N".equals(e.getDNI())));
    }

    @Test
    public void testBuscarPorApellido() throws Exception {
        EmpleadoDAO dao = new EmpleadoDAO();
        List<EmpleadoVO> resultado = dao.cargaEmpleados("Garc�a");
        assertTrue(resultado.stream().anyMatch(e -> e.getApellido1().contains("Garc�a") || e.getApellido2().contains("Garc�a")));
    }

    @Test
    public void testSinCoincidencias() throws Exception {
        EmpleadoDAO dao = new EmpleadoDAO();
        List<EmpleadoVO> resultado = dao.cargaEmpleados("xyz123");
        assertTrue(resultado.isEmpty(), "No deber�a haber resultados");
    }


    @Test
    public void testBusquedaConcatenada() throws Exception {
        EmpleadoDAO dao = new EmpleadoDAO();
        List<EmpleadoVO> resultado = dao.cargaEmpleados("Fernandez rueda");
        assertFalse(resultado.isEmpty(), "Deber�a encontrar coincidencias por nombre completo");
    }
}
