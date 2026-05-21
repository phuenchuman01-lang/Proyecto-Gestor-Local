package modelo;

import java.util.ArrayList;
import java.util.List;

public class Casillero {
    private int numero;
    private String usernameDueño;
    private List<String> objetos;

    public Casillero(int numero) {
        this.numero = numero;
        this.usernameDueño = null;
        this.objetos = new ArrayList<>();
    }

    public boolean estaDisponible() {
        // Retornar 'true' si el usernameDueño es null, o 'false' si ya tiene un nombre.
        return false; // Reemplazar esta línea
    }

    public void ocupar(String username) {
        // Asignar el parámetro 'username' al atributo de la clase 'usernameDueño'.
    }

    public void liberar() {
        // Convertir el 'usernameDueño' a null.
        // Limpiar la lista de objetos usando el métod .clear() de la lista
    }

    public void agregarObjeto(String objeto) {
        // Añadir el parámetro 'objeto' a la lista 'objetos' para el casillero de un alumno al que le corresponda.
    }

    public boolean quitarObjeto(String objeto) {
        // Remover el 'objeto' de la lista y retornar el resultado de esa operación booleana.
        return false; // Reemplazar esta línea
    }

    //Estos son los getters y setters que deben ir para poder modificar el archivo JSON que estamos utilizando como manager
    //principal del programa
    public int getNumero() { return numero; }
    public String getUsernameDueño() { return usernameDueño; }
    public void setUsernameDueño(String usernameDueño) { this.usernameDueño = usernameDueño; }
    public List<String> getObjetos() { return objetos; }
    public void setObjetos(List<String> objetos) { this.objetos = objetos; }
}