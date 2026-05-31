package main.launcher;

import main.controlador.SessionController;
import main.vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {
        SessionController sesionGlobal = new SessionController();
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);
        ventana.mostrarVentana();
    }
}
