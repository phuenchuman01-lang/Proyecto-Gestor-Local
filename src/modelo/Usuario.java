package modelo;     //Katherine, necesito que crees la super clase, es la clase padre de Estudiante y Docente, y es
                    // la que se encarga de validar las credenciales de los usuarios. :D

public class Usuario {      //por aqui falta poner la classe como abstracta, ya que se usara de plantilla para las distintas cuentas de usuario de los alumnos. solamente eso.
    private String username;
    private String password;
    private String rol;


    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public boolean validarCredenciales(String u, String p) {
        return this.username.equals(u) && this.password.equals(p);
    }

    public String getRol() { return this.rol; }     //Por aqui me encargue de agregar unicamente el public stringPassword. y reordenar un poco el codigo.
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
}