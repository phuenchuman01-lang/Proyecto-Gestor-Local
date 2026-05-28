package main.modelo;

public class Estudiante extends main.modelo.Usuario {
    public Estudiante(String matricula, String nombre, String username, String password, String carrera) {
        super(matricula, nombre, username, password, "estudiante", carrera);
    }
}