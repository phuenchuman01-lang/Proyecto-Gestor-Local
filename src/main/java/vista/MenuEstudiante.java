package vista;

import controlador.CasilleroController;
import controlador.SessionController;
import modelo.Casillero;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MenuEstudiante {
    private JFrame frame = new JFrame("Panel de Estudiante - Gestor Local");
    private SessionController session;
    private CasilleroController casilleroCtrl;
    private JPanel panelCentro;

    public MenuEstudiante(SessionController session) {
        this.session = session;
        this.casilleroCtrl = new CasilleroController();

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bienvenido Usuario: " + session.getUsuarioActual().getUsername(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titulo, BorderLayout.NORTH);

        panelCentro = new JPanel(new GridBagLayout());
        frame.add(panelCentro, BorderLayout.CENTER);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            session.cerrarSesion();
            frame.dispose();
            new VentanaLogin(session).mostrarVentana();
        });
        frame.add(btnCerrarSesion, BorderLayout.SOUTH);
        cargarOpcionesCasillero();
    }

    // --- LÓGICA DINÁMICA DE LOS BOTONES ---

    private void cargarOpcionesCasillero() {
        panelCentro.removeAll(); // Limpiamos el panel central

        String miUsuario = session.getUsuarioActual().getUsername();
        Casillero miCasillero = casilleroCtrl.buscarCasilleroPorEstudiante(miUsuario);

        if (miCasillero == null) {
            // El alumno NO tiene casillero
            JButton btnRegistrar = new JButton("Registrar Casillero");
            btnRegistrar.setFont(new Font("Arial", Font.BOLD, 16));
            btnRegistrar.addActionListener(e -> mostrarVentanaRegistro());
            panelCentro.add(btnRegistrar);
        } else {
            // El alumno YA TIENE casillero
            JButton btnAdministrar = new JButton("Administrar Casillero #" + miCasillero.getNumero());
            btnAdministrar.setFont(new Font("Arial", Font.BOLD, 16));
            btnAdministrar.addActionListener(e -> mostrarVentanaAdministracion(miCasillero.getNumero()));
            panelCentro.add(btnAdministrar);
        }

        panelCentro.revalidate();
        panelCentro.repaint();
    }

    // --- ACCIONES SECUNDARIAS (VENTANAS EMERGENTES) ---

    private void mostrarVentanaRegistro() {
        List<String> opciones = new ArrayList<>();
        for (Casillero c : casilleroCtrl.obtenerTodosLosCasilleros()) {
            if (c.estaDisponible()) {
                opciones.add("Casillero " + c.getNumero() + " - DISPONIBLE");
            } else {
                opciones.add("Casillero " + c.getNumero() + " - OCUPADO");
            }
        }

        String seleccion = (String) JOptionPane.showInputDialog(
                frame, "Selecciona un casillero disponible:\n\n",
                "Lista de Casilleros", JOptionPane.PLAIN_MESSAGE, null,
                opciones.toArray(), opciones.get(0)
        );

        if (seleccion != null) {
            if (seleccion.contains("OCUPADO")) {
                JOptionPane.showMessageDialog(frame, "Ese casillero ya está ocupado por otro alumno.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int numero = Integer.parseInt(seleccion.split(" ")[1]);
                try {
                    casilleroCtrl.registrarCasillero(numero, session.getUsuarioActual().getUsername());
                    JOptionPane.showMessageDialog(frame, "¡Casillero #" + numero + " registrado exitosamente a tu nombre!");
                    cargarOpcionesCasillero(); // Actualiza la pantalla para mostrar el botón "Administrar"
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void mostrarVentanaAdministracion(int numeroCasillero) {
        JDialog dialog = new JDialog(frame, "Administrando Casillero #" + numeroCasillero, true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(frame);
        Casillero c = casilleroCtrl.obtenerTodosLosCasilleros().get(numeroCasillero - 1);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for(String obj : c.getObjetos()) modeloLista.addElement(obj);
        JList<String> listaObjetos = new JList<>(modeloLista);
        dialog.add(new JScrollPane(listaObjetos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(2, 2, 5, 5));
        JButton btnAgregar = new JButton("Agregar Objeto");
        JButton btnQuitar = new JButton("Eliminar Objeto");
        JButton btnTerminar = new JButton("Terminar Casillero");
        JButton btnCerrar = new JButton("Guardar y Cerrar");

        btnAgregar.addActionListener(e -> {
            String obj = JOptionPane.showInputDialog(dialog, "Nombre del objeto a guardar:");
            if (obj != null && !obj.trim().isEmpty()) {
                casilleroCtrl.agregarObjetoACasillero(numeroCasillero, obj);
                modeloLista.addElement(obj);
            }
        });

        btnQuitar.addActionListener(e -> {
            String seleccionado = listaObjetos.getSelectedValue();
            if (seleccionado != null) {
                casilleroCtrl.quitarObjetoDeCasillero(numeroCasillero, seleccionado);
                modeloLista.removeElement(seleccionado);
            } else {
                JOptionPane.showMessageDialog(dialog, "Selecciona un objeto de la lista primero.");
            }
        });

        btnTerminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "¿Seguro que deseas terminar el uso de este casillero?\nTodos tus objetos serán eliminados y el casillero quedará libre.",
                    "Confirmar Liberación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                casilleroCtrl.liberarCasillero(numeroCasillero);
                dialog.dispose();
                cargarOpcionesCasillero();
            }
        });

        btnCerrar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnQuitar);
        panelBotones.add(btnTerminar);
        panelBotones.add(btnCerrar);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}