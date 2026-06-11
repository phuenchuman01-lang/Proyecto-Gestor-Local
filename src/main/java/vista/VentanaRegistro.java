package vista;

import controlador.SessionController;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistro {
    private JFrame frame;
    private SessionController session;
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnRegistrar;
    private JButton btnVolver;

    public VentanaRegistro(SessionController session) {
        this.session = session;
        frame = new JFrame("Casillas Seguras - Nuevo Estudiante");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Nuevo Usuario:"));
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario);

        panel.add(new JLabel("Nueva Contraseña:"));
        txtClave = new JPasswordField(15);
        panel.add(txtClave);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        btnRegistrar = new JButton("Registrar");
        panel.add(btnRegistrar);

        frame.add(panel);

        // Eventos
        btnVolver.addActionListener(e -> volverAlLogin());
        btnRegistrar.addActionListener(e -> intentarRegistro());
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void volverAlLogin() {
        frame.dispose();
        new VentanaLogin(session).mostrarVentana();
    }

    private void intentarRegistro() {
        try {
            String u = txtUsuario.getText();
            String p = new String(txtClave.getPassword());

            session.registrarEstudiante(u, p);
            JOptionPane.showMessageDialog(frame, "¡Estudiante registrado con éxito!");
            volverAlLogin(); // Si sale bien, lo mandamos a loguearse

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}