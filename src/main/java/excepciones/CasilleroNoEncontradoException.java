package excepciones;

public class CasilleroNoEncontradoException extends RuntimeException {
    public CasilleroNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}