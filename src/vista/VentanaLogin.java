package vista;

import controlador.SessionController;
import javax.swing.*;
import java.awt.*;

public class VentanaLogin {
    private JFrame frame;
    private SessionController session;

    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnIngresar;
    private JButton btnRegistrar;

    public VentanaLogin(SessionController session) {
        this.session = session;

        frame = new JFrame("Casillas Seguras - Inicio de Sesión");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        JPanel panelLogin = new JPanel(new GridLayout(4, 2, 10, 10)); // Cambiamos a 4 filas

        panelLogin.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        panelLogin.add(txtUsuario);

        panelLogin.add(new JLabel("Contraseña:"));
        txtClave = new JPasswordField(15);
        panelLogin.add(txtClave);

        btnRegistrar = new JButton("Crear Estudiante");
        panelLogin.add(btnRegistrar);

        btnIngresar = new JButton("Ingresar");
        panelLogin.add(btnIngresar);

        frame.add(panelLogin);

        // Eventos
        btnIngresar.addActionListener(e -> intentarLogin());
        btnRegistrar.addActionListener(e -> {
            frame.dispose();
            new VentanaRegistro(session).mostrarVentana(); // Abre la ventana de registro
        });
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void intentarLogin() {
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());

        if (session.iniciarSesion(u, p)) {
            frame.dispose();

            // ROLES
            if (session.getUsuarioActual().getRol().equals("DOCENTE")) {
                new MenuDocente(session).mostrarVentana();
            } else {
                new MenuEstudiante(session).mostrarVentana();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}