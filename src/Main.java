import modelo.Usuario;
import modelo.Estudiante;

public class Main {
    public static void main(String[] args) {

        // Aplicamos Polimorfismo
        // apuntando a una instancia de la subclase concreta Estudiante
        Usuario user1 = new Estudiante("katherine", "ufro2026");

        System.out.println("--- Bienvenido a Casillas-Seguras (Prueba de Consola) ---");
        System.out.println("Usuario registrado: " + user1.getUsername());
        System.out.println("Rol asignado automáticamente: " + user1.getRol());

        String intentoUsuario = "katherine";
        String intentoPass = "ufro2026";

        if (user1.validarCredenciales(intentoUsuario, intentoPass)) {
            System.out.println("Login exitoso. Rol verificado: " + user1.getRol());
        } else {
            System.out.println("Error: Credenciales incorrectas.");
        }

        System.out.println("\nProbando con contraseña errónea...");
        if (user1.validarCredenciales("katherine", "12345")) {
            System.out.println("Login exitoso.");
        } else {
            System.out.println("Acceso denegado. La herencia y el encapsulado funcionan correctamente.");
        }
    }
}