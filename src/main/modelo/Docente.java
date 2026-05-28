package main.modelo;

public class Docente extends main.modelo.Usuario {
    public Docente(String matricula, String nombre, String username, String password) {
        super(matricula, nombre, username, password, "docente", "N/A");
    }
}