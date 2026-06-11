package controlador;

import modelo.Usuario;
import java.util.List;
import modelo.Estudiante;
import persistencia.JsonManager;


public class SessionController {

    private final List<Usuario> usuariosRegistrados;
    private Usuario usuarioActual;

    public SessionController() {
        this.usuariosRegistrados = JsonManager.cargarUsuarios();
    }

    public boolean iniciarSesion(String user, String password) {
        if (user == null || user.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false;
        }
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.validarCredenciales(user, password)) {
                this.usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }

    public void registrarEstudiante(String user, String password) {
        if (user == null || user.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario y la contraseña no pueden estar vacíos.");
        }

        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getUsername().equalsIgnoreCase(user)){
                throw new IllegalArgumentException("El usuario ya existe en el sistema.");
            }
        }

        Usuario nuevoEstudiante = new Estudiante("Pendiente", "Sin Nombre", user, password, "Sin Carrera");
        usuariosRegistrados.add(nuevoEstudiante);
        JsonManager.guardarUsuarios(usuariosRegistrados);
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public String getNombreUsuario() { return usuarioActual != null ? usuarioActual.getUsername() : "Invitado"; }
    public boolean hayUsuario() { return usuarioActual != null; }
}