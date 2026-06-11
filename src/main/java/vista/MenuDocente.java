package vista;

import controlador.CasilleroController;
import controlador.SessionController;
import modelo.Casillero;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuDocente {
    private JFrame frame = new JFrame("Panel de Administración - Docente");
    private SessionController session;
    private CasilleroController casilleroCtrl;
    private JPanel panelCasilleros;

    public MenuDocente(SessionController session) {
        this.session = session;
        // Instanciamos el main.controlador.controlador para leer los casilleros desde el JSON
        this.casilleroCtrl = new CasilleroController();

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Panel de Control Docente: " + session.getUsuarioActual().getUsername(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(titulo, BorderLayout.NORTH);

        // Panel tipo grilla para los 10 casilleros (2 filas de 5)
        panelCasilleros = new JPanel(new GridLayout(2, 5, 10, 10));
        panelCasilleros.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        frame.add(panelCasilleros, BorderLayout.CENTER);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            session.cerrarSesion();
            frame.dispose();
            new VentanaLogin(session).mostrarVentana();
        });
        frame.add(btnCerrarSesion, BorderLayout.SOUTH);

        // Dibujamos los casilleros
        cargarVistaCasilleros();
    }

    private void cargarVistaCasilleros() {
        panelCasilleros.removeAll();
        List<Casillero> casilleros = casilleroCtrl.obtenerTodosLosCasilleros();

        for (Casillero c : casilleros) {
            JButton btnCasillero = new JButton();
            btnCasillero.setLayout(new BorderLayout());

            JLabel lblNumero = new JLabel("Casillero #" + c.getNumero(), SwingConstants.CENTER);
            lblNumero.setFont(new Font("Arial", Font.BOLD, 14));

            JLabel lblEstado = new JLabel(c.estaDisponible() ? "DISPONIBLE" : "Dueño: " + c.getUsernameDueño(), SwingConstants.CENTER);

            // Colores visuales para identificar rápidamente el estado
            if (c.estaDisponible()) {
                btnCasillero.setBackground(new Color(144, 238, 144)); // Verde claro
            } else {
                btnCasillero.setBackground(new Color(255, 182, 193)); // Rojo/Rosa claro
            }

            btnCasillero.add(lblNumero, BorderLayout.NORTH);
            btnCasillero.add(lblEstado, BorderLayout.CENTER);

            // Al hacer clic, abre las opciones de administrador para ese casillero específico
            btnCasillero.addActionListener(e -> mostrarOpcionesAdmin(c.getNumero()));
            panelCasilleros.add(btnCasillero);
        }

        panelCasilleros.revalidate();
        panelCasilleros.repaint();
    }

    private void mostrarOpcionesAdmin(int numero) {
        Casillero c = casilleroCtrl.obtenerTodosLosCasilleros().get(numero - 1);

        JDialog dialog = new JDialog(frame, "Gestión de Casillero #" + numero, true);
        dialog.setSize(450, 350);
        dialog.setLayout(new BorderLayout(5, 5));
        dialog.setLocationRelativeTo(frame);

        if (c.estaDisponible()) {
            // --- VISTA PARA CASILLERO VACÍO ---
            JLabel lblInfo = new JLabel("El casillero #" + numero + " está actualmente disponible.", SwingConstants.CENTER);
            dialog.add(lblInfo, BorderLayout.CENTER);

            JPanel panelBotones = new JPanel();
            JButton btnEnlazar = new JButton("Enlazar a Alumno");
            JButton btnCerrar = new JButton("Cerrar");

            btnEnlazar.addActionListener(e -> {
                String username = JOptionPane.showInputDialog(dialog, "Ingrese el nombre de usuario del alumno:");
                if (username != null && !username.trim().isEmpty()) {
                    try {
                        casilleroCtrl.enlazarEstudianteACasillero(numero, username.trim());
                        JOptionPane.showMessageDialog(dialog, "¡Casillero enlazado exitosamente!");
                        dialog.dispose();
                        cargarVistaCasilleros(); // Refrescar la main.controlador.controlador.vista de botones
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnCerrar.addActionListener(e -> dialog.dispose());
            panelBotones.add(btnEnlazar);
            panelBotones.add(btnCerrar);
            dialog.add(panelBotones, BorderLayout.SOUTH);

        } else {
            // --- VISTA PARA CASILLERO OCUPADO ---
            JPanel panelTop = new JPanel(new GridLayout(2, 1));
            panelTop.add(new JLabel("Dueño actual: " + c.getUsernameDueño(), SwingConstants.CENTER));
            panelTop.add(new JLabel("Objetos guardados:", SwingConstants.CENTER));
            dialog.add(panelTop, BorderLayout.NORTH);

            DefaultListModel<String> modeloLista = new DefaultListModel<>();
            for (String obj : c.getObjetos()) modeloLista.addElement(obj);
            JList<String> listaObjetos = new JList<>(modeloLista);
            dialog.add(new JScrollPane(listaObjetos), BorderLayout.CENTER);

            JPanel panelBotones = new JPanel(new GridLayout(3, 2, 5, 5));
            JButton btnAgregar = new JButton("Agregar Objeto");
            JButton btnQuitar = new JButton("Quitar Objeto");
            JButton btnDesenlazar = new JButton("Desenlazar (Liberar)");
            JButton btnMover = new JButton("Mover Casillero");
            JButton btnCerrar = new JButton("Guardar y Cerrar");

            btnAgregar.addActionListener(e -> {
                String obj = JOptionPane.showInputDialog(dialog, "Nombre del objeto:");
                if (obj != null && !obj.trim().isEmpty()) {
                    casilleroCtrl.agregarObjetoACasillero(numero, obj);
                    modeloLista.addElement(obj);
                }
            });

            btnQuitar.addActionListener(e -> {
                String sel = listaObjetos.getSelectedValue();
                if (sel != null) {
                    casilleroCtrl.quitarObjetoDeCasillero(numero, sel);
                    modeloLista.removeElement(sel);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Seleccione un objeto de la lista.");
                }
            });

            btnDesenlazar.addActionListener(e -> {
                int resp = JOptionPane.showConfirmDialog(dialog, "¿Seguro que desea desenlazar este casillero y borrar sus objetos?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    casilleroCtrl.liberarCasillero(numero);
                    dialog.dispose();
                    cargarVistaCasilleros();
                }
            });

            btnMover.addActionListener(e -> {
                String destStr = JOptionPane.showInputDialog(dialog, "Mover contenido completo al casillero número (1-10):");
                if (destStr != null) {
                    try {
                        int dest = Integer.parseInt(destStr.trim());
                        casilleroCtrl.moverContenidoDeCasillero(numero, dest);
                        JOptionPane.showMessageDialog(dialog, "Contenido movido con éxito al casillero #" + dest);
                        dialog.dispose();
                        cargarVistaCasilleros();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(dialog, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            btnCerrar.addActionListener(e -> dialog.dispose());

            panelBotones.add(btnAgregar);
            panelBotones.add(btnQuitar);
            panelBotones.add(btnDesenlazar);
            panelBotones.add(btnMover);
            panelBotones.add(btnCerrar);

            dialog.add(panelBotones, BorderLayout.SOUTH);
        }
        dialog.setVisible(true);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}