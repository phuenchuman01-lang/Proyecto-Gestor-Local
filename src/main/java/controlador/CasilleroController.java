package controlador;

import modelo.Casillero;
import persistencia.JsonManager;
import java.util.List;
import modelo.HistorialCambios;

public class CasilleroController {

    private final List<Casillero> listaCasilleros;
    private final HistorialCambios historial;

    public CasilleroController() {  //Carga los 10 casilleros disponibles de forma automatica.
        this.listaCasilleros = JsonManager.cargarCasilleros();
        this.historial = HistorialCambios.getInstance();
    }

    public List<Casillero> obtenerTodosLosCasilleros() {        //Retorna los casilleros para la vista del admin.
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
        Casillero c = obtenerCasilleroPorNumero(numero);

        //Cambiar las IllegalArgumentException e IllegalStateException por las excepciones personalizadas creadas

        if (c == null) throw new IllegalArgumentException("Casillero no existente");

        if (!c.estaDisponible()){
            throw new IllegalArgumentException("Casillero ya ocupado");
        }

        Casillero casilleroPrevio = buscarCasilleroPorEstudiante(username);
        if (casilleroPrevio != null) {
            throw new IllegalStateException("El estudiante ya posee un casillero");
        }

        c.ocupar(username);
        JsonManager.guardarCasilleros(listaCasilleros);

        historial.registrarAccion(username, "Registró y ocupó el casillero", numero);
    }

    public void agregarObjetoACasillero(int numero, String objeto) {
        // Cambiar excepción por CasilleroNoEncontradoException
        Casillero c = obtenerCasilleroPorNumero(numero);
        if (c == null) throw new IllegalArgumentException("Casillero no encontrado.");

        c.agregarObjeto(objeto);
        JsonManager.guardarCasilleros(listaCasilleros);

        String user = (c.getUsernameDueño() != null) ? c.getUsernameDueño() : "Admin";
        historial.registrarAccion(user, "Agregó el objeto: " + objeto, numero);
    }

    public void quitarObjetoDeCasillero(int numero, String objeto) {
        Casillero c = obtenerCasilleroPorNumero(numero);
        //Cambiar excepción
        if (c == null) throw new IllegalArgumentException("Casillero no encontrado.");

        boolean eliminado = c.quitarObjeto(objeto);
        if (!eliminado) {
            throw new IllegalArgumentException("El objeto no existía en el casillero.");
        }
        JsonManager.guardarCasilleros(listaCasilleros);

        String user = (c.getUsernameDueño() != null) ? c.getUsernameDueño() : "Admin";
        historial.registrarAccion(user, "Quitó el objeto: " + objeto, numero);
    }

    public void liberarCasillero(int numero) {
        Casillero c = obtenerCasilleroPorNumero(numero);
        //Cambiar excepción
        if (c == null) throw new IllegalArgumentException("Casillero no encontrado.");
        String exDueño = c.getUsernameDueño();

        c.liberar();
        JsonManager.guardarCasilleros(listaCasilleros);

        historial.registrarAccion(exDueño != null ? exDueño : "Admin", "Liberó el casillero", numero);
    }

    // ==========================================
    //   OPERACIONES EXCLUSIVAS DEL ADMINISTRADOR
    // ==========================================

    public void moverContenidoDeCasillero(int numeroOrigen, int numeroDestino) {
        Casillero origen = obtenerCasilleroPorNumero(numeroOrigen);
        Casillero destino = obtenerCasilleroPorNumero(numeroDestino);
        // Cambiar excepciones genéricas por personalizadas
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Uno o ambos casilleros no han sido encontrados.");
        }

        if (origen.estaDisponible()){
            throw new IllegalStateException("El casillero esta vacío, no hay objetos que mover");
        }

        if (!destino.estaDisponible()){
            throw new IllegalStateException("El casillero esta ocupado por otro estudiante");
        }

        String dueño = origen.getUsernameDueño();
        List<String> objetos = origen.getObjetos();

        destino.ocupar(dueño);
        for (String obj : objetos) {
            destino.agregarObjeto(obj);
        }

        origen.liberar();
        JsonManager.guardarCasilleros(listaCasilleros);

        historial.registrarAccion("Admin", "Movió el contenido completo al casillero #" + numeroDestino, numeroOrigen);
    }

    public void enlazarEstudianteACasillero(int numero, String username) {
        Casillero c = obtenerCasilleroPorNumero(numero);
        // Cambiar excepciones
        if (c == null) throw new IllegalArgumentException("Casillero no encontrado.");
        if (!c.estaDisponible()) throw  new IllegalStateException("El casillero se encuentra ocupado");

        Casillero casilleroPrevio = buscarCasilleroPorEstudiante(username);
        if (casilleroPrevio != null) {
            throw new IllegalStateException("El estudiante ya tiene otro casillero enlazado.");
        }
        c.ocupar(username);
        JsonManager.guardarCasilleros(listaCasilleros);

        historial.registrarAccion("Admin", "Enlazó forzosamente el casillero al estudiante " + username, numero);
    }

    //metodo auxiliar

    private Casillero obtenerCasilleroPorNumero(int numero) {
        for (Casillero c : listaCasilleros) {
            if (c.getNumero() == numero) return c;
        }
        return null;
    }
}