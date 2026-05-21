package main.launcher;

import main.controlador.SessionController;
import main.vista.VentanaLogin;

public class Launcher {
    public static void main(String[] args) {
        // 1. Creamos el main.controlador.controlador
        SessionController sesionGlobal = new SessionController();

        // 2. Le pasamos el main.controlador.controlador a la ventana
        VentanaLogin ventana = new VentanaLogin(sesionGlobal);
        ventana.mostrarVentana();
    }
}
