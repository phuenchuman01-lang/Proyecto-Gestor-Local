package modelo;

public class Docente extends Usuario {
    public Docente(String matricula, String nombre, String username, String password) {
        super(matricula, nombre, username, password, "docente", "N/A");
    }
}