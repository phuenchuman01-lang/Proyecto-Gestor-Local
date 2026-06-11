package modelo;

public abstract class Usuario {
    private String username;
    private String password;
    private String rol;
    private String matricula;
    private String nombre;
    private String carrera;

    public Usuario(String matricula, String nombre, String username, String password, String rol, String carrera) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.carrera = carrera;
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
}