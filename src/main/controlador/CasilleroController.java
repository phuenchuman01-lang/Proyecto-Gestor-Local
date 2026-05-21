package main.controlador;

import main.modelo.Casillero;
import main.persistencia.JsonManager;
import java.util.List;

public class CasilleroController {

    private final List<Casillero> listaCasilleros;

    public CasilleroController() {  //Carga los 10 casilleros disponibles de forma automatica.
        this.listaCasilleros = JsonManager.cargarCasilleros();
    }

    public List<Casillero> obtenerTodosLosCasilleros() {        //Retorna los casilleros para la main.controlador.controlador.vista del admin.
        return this.listaCasilleros;
    }

    public Casillero buscarCasilleroPorEstudiante(String username) {    // Busca si un estudiante ya posee un casillero asignado
        for (Casillero c : listaCasilleros) {
            if (c.getUsernameDueño() != null && c.getUsernameDueño().equals(username)) {
                return c;
            }
        }
        return null;        //Estudiante sin casillero.
    }

    // ==========================================
    //   OPERACIONES DEL ESTUDIANTE / ADMIN
    // ==========================================

    public void registrarCasillero(int numero, String username) {
        // Buscar el casillero usando obtenerCasilleroPorNumero(numero).
        // Si es null, lanzar IllegalArgumentException.
        // Si esta Disponible, advertir que ya está ocupado.
        // Validar que el estudiante no tenga otro casillero. Si tiene, lanzar IllegalStateException.
        // Ocupar el casillero con el username.
        // Guardar los cambios para JsonManager
    }

    public void agregarObjetoACasillero(int numero, String objeto) {
        // Buscar el casillero por número. Si es null, lanzar excepción.
        // Agregar el objeto al casillero
        // Guardar los cambios en el JSON.
    }

    public void quitarObjetoDeCasillero(int numero, String objeto) {
        // Buscar el casillero por número.
        // Llamar a quitar Objeto en el casillero.
        // Si el métod retorna false (no se eliminó), lanzar IllegalArgumentException indicando que no existía el objeto.
        // Guardar los cambios en el JSON.
    }

    public void liberarCasillero(int numero) {
        Casillero c = obtenerCasilleroPorNumero(numero);
        if (c == null) throw new IllegalArgumentException("Casillero no encontrado.");

        c.liberar();
        JsonManager.guardarCasilleros(listaCasilleros);
    }

    // ==========================================
    //   OPERACIONES EXCLUSIVAS DEL ADMINISTRADOR
    // ==========================================

    public void moverContenidoDeCasillero(int numeroOrigen, int numeroDestino) {
        // Obtener casillero 'origen' y 'destino'. Validar que ambos existan.
        // Si el origen estaDisponible(), lanzar IllegalStateException (no hay nada que mover).
        // Si el destino NO esta Disponible, lanzar IllegalStateException (colisión).
        // Pasar los objetos al nuevo casillero de destino y verificar que este correcto para poder liberar el casillero de origen
        // Liberar el casillero de origen.
        // Guardar en el JSON.
    }

    public void enlazarEstudianteACasillero(int numero, String username) {
        // Validar que exista el casillero y que esté disponible.
        // Verificar que el estudiante no tenga ya otro casillero.
        // Ocupar el casillero.
        // Guardar en el JSON.
    }

    //metodo auxiliar

    private Casillero obtenerCasilleroPorNumero(int numero) {
        for (Casillero c : listaCasilleros) {
            if (c.getNumero() == numero) return c;
        }
        return null;
    }
}