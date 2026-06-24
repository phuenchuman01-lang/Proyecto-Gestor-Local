package modelo;

import  persistencia.JsonManager;
import java.util.ArrayList;
import java.util.List;

public class HistorialCambios {

    private static HistorialCambios instancia;
    private List<String> registros;

    private HistorialCambios() {
        // Cambiar esto.
        // En lugar de inicializar vacío, debe llamar a JsonManager.cargarHistorial();
        this.registros = new ArrayList<>();
    }

    public static HistorialCambios getInstance() {
        if (instancia == null) {
            instancia = new HistorialCambios();
        }
        return instancia;
    }

    public void registrarAccion(String nombreUsuario, String accion, int numeroCasillero) {
        // Implementar la lógica.
        // 1. Crear el String con LocalDateTime, usuario, accion y casillero.
        // 2. Hacer registros.add(nuevoRegistro)
        // 3. Llamar a JsonManager.guardarHistorial(registros) para que sea permanente.
    }

    public List<String> obtenerHistorialCompleto() {
        return registros;
    }
}