package controlador;

import modelo.Casillero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CasilleroControllerTest {

    private CasilleroController controlador;

    @BeforeEach
    void setUp() {
        controlador = new CasilleroController();

        for (Casillero c : controlador.obtenerTodosLosCasilleros()) {
            c.liberar();
        }
        persistencia.JsonManager.guardarCasilleros(controlador.obtenerTodosLosCasilleros());
    }

    @Test
    void testRegistrarCasilleroExitosamente() {
        controlador.registrarCasillero(1, "alumnoTest");

        Casillero c = controlador.obtenerTodosLosCasilleros().get(0);

        assertFalse(c.estaDisponible(), "El casillero debería aparecer como ocupado");
        assertEquals("alumnoTest", c.getUsernameDueño(), "El dueño del casillero debe coincidir con el registrado");
    }

    @Test
    void testLiberarCasillero() {
        controlador.registrarCasillero(2, "alumnoTest2");

        controlador.liberarCasillero(2);

        Casillero c = controlador.obtenerTodosLosCasilleros().get(1);

        assertTrue(c.estaDisponible(), "El casillero debería estar disponible tras liberarse");
        assertNull(c.getUsernameDueño(), "El nombre del dueño debería ser null tras liberarse");
    }
}