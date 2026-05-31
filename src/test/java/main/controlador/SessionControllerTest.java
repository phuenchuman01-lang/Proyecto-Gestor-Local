package main.controlador;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    private SessionController controlador;

    @BeforeEach
    void setUp() {

        controlador = new SessionController();

    }

    @Test
    void testRegistrarEstudianteCamposVaciosLanzaExcepcion() {

        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarEstudiante("", "123");
        }, "Debería lanzar excepción si el usuario está vacío");

        assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarEstudiante("Pablo", null);
        }, "Debería lanzar excepción si la contraseña es null");

    }

    @Test
    void testIniciarSesionCamposVaciosRetornaFalse() {

        assertFalse(controlador.iniciarSesion("", "1234"), "Usuario vacío debe fallar");
        assertFalse(controlador.iniciarSesion(null, null), "Campos nulos deben fallar");

    }

    @Test
    void testRegistroYBloqueoDuplicados() {
        String testUser = "testAlumno69";
        String testPass = "clave123";

        try {
            controlador.registrarEstudiante(testUser, testPass);
        } catch (IllegalArgumentException _) {

        }

        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            controlador.registrarEstudiante(testUser, "otraClave");
        });

        assertTrue(excepcion.getMessage().contains("ya existe"),
                "El mensaje de error debe indicar que el usuario ya existe");
    }

    @Test
    void testFlujoCompletoLoginYCerrarSesion() {

        boolean loginExitoso = controlador.iniciarSesion("admin", "admin123");

        if (loginExitoso) {

            assertTrue(controlador.hayUsuario(), "El sistema debe indicar que hay un usuario activo");
            assertEquals("admin", controlador.getNombreUsuario(), "El nombre de usuario debe ser admin");

            controlador.cerrarSesion();

            assertFalse(controlador.hayUsuario(), "Ya no debe haber usuario activo");
            assertNull(controlador.getUsuarioActual(), "El objeto usuarioActual debe ser null");
            assertEquals("Invitado", controlador.getNombreUsuario(), "Al no haber sesión, debe retornar 'Invitado'");
        }
    }
}