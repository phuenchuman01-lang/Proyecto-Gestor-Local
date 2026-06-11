package modelo;

public class Estudiante extends Usuario {
    public Estudiante(String matricula, String nombre, String username, String password, String carrera) {
        super(matricula, nombre, username, password, "estudiante", carrera);
    }
}