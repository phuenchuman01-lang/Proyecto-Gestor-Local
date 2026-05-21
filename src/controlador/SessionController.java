package controlador;

import modelo.Usuario;      //aqui retire el import ArrayList, ya que a partir de ahora nos manejaremos dentro del archivo
                            //JSON como el principal manager del programa, y por lo tanto, no necesitaremos manejar una
                            // lista de usuarios dentro de esta clase, sino que se consultara directamente al JSON para
                            // validar las credenciales de los usuarios. :D
import modelo.Estudiante;
import java.util.List;
import persistencia.JsonManager;


public class SessionController {

    private final List<Usuario> usuariosRegistrados;
    private Usuario usuarioActual;

    public SessionController() {    //Por aqui necesito que cambies el constructor para que en lugar de crear una lista
        // de usuarios, consulte directamente al JSON para cargar los usuarios registrados.
        usuariosRegistrados.add(new Usuario("admin", "admin123", "DOCENTE"));
        usuariosRegistrados.add(new Usuario("alumno", "test1234", "ESTUDIANTE"));
    }

    public boolean iniciarSesion(String u, String p) {
        //Luih, necesito que agregues esto al bloque de IniciarSesion
        // Validar que 'u' y 'p' no sean nulos ni estén vacíos. Si lo están, retornar false.
        // Recorrer la lista 'usuariosRegistrados' con un ciclo for.
        // En cada iteración, usar el métod validarCredenciales(u, p) del usuario.
        // Si las credenciales coinciden, asignar ese usuario a 'this.usuarioActual' y retornar true.
        // Si termina el ciclo y no se encontró, retornar false
        if (u == null || u.trim().isEmpty() || p == null || p.trim().isEmpty()) {
            return false;
        }
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.validarCredenciales(u, p)) {
                this.usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }

    public void registrarEstudiante(String u, String p) {
        // Validar que 'u' y 'p' no estén vacíos. Lanzar IllegalArgumentException si lo están.
        // Recorrer 'usuariosRegistrados' para ver si ya existe alguien con el mismo 'u' (ignorar mayúsculas).
        // Si ya existe, lanzar IllegalArgumentException indicando que el usuario ya existe.
        // Crear un nuevo objeto Estudiante(u, p).
        // Añadirlo a la lista 'usuariosRegistrados'.
        // Guardar los cambios llamando a: JsonManager.guardarUsuarios(usuariosRegistrados);
        Usuario nuevoEstudiante = new Usuario(u, p, "ESTUDIANTE");
        usuariosRegistrados.add(nuevoEstudiante);
        if (u == null || u.trim().isEmpty() || p == null || p.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario y la contraseña no pueden estar vacíos.");
        }
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public Usuario getUsuarioActual() { return usuarioActual; }
    public String getNombreUsuario() { return usuarioActual != null ? usuarioActual.getUsername() : "Invitado"; }
    public boolean hayUsuario() { return usuarioActual != null; }
}