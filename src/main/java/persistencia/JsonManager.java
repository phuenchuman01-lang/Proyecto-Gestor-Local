package persistencia;

import modelo.Usuario;
import modelo.Estudiante;
import modelo.Docente;
import modelo.Casillero;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

    private static final String RUTA_USUARIOS = "usuarios.json";
    private static final String RUTA_CASILLEROS = "casilleros.json";
    private static final String RUTA_HISTORIAL = "historial.json";
    // ==========================================
    //   MÉTODOS PARA USUARIOS
    // ==========================================
    public static void guardarUsuarios(List<Usuario> usuarios) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            sb.append("  {\n");
            sb.append("    \"username\": \"").append(u.getUsername()).append("\",\n");
            sb.append("    \"password\": \"").append(u.getPassword()).append("\",\n");
            sb.append("    \"rol\": \"").append(u.getRol()).append("\"\n");
            sb.append("  }");
            if (i < usuarios.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        escribirArchivo(RUTA_USUARIOS, sb.toString());
    }

    public static List<Usuario> cargarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String contenido = leerArchivo(RUTA_USUARIOS);

        if (contenido == null || contenido.trim().isEmpty()) {
            lista.add(new Docente("0000", "Administrador", "admin", "admin123"));
            guardarUsuarios(lista);
            return lista;
        }

        String[] bloques = contenido.split("\\}");
        for (String bloque : bloques) {
            if (!bloque.contains("username")) continue;
            String user = extraerValor(bloque, "username");
            String pass = extraerValor(bloque, "password");
            String rol = extraerValor(bloque, "rol");

            if ("DOCENTE".equalsIgnoreCase(rol)) {
                lista.add(new Docente("Pendiente", "Docente Importado", user, pass));
            } else {
                lista.add(new Estudiante("Pendiente", "Estudiante Importado", user, pass, "Sin Carrera"));
            }
        }
        return lista;
    }

    // ==========================================
    //   MÉTODOS PARA CASILLEROS
    // ==========================================
    public static void guardarCasilleros(List<Casillero> casilleros) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < casilleros.size(); i++) {
            Casillero c = casilleros.get(i);
            sb.append("  {\n");
            sb.append("    \"numero\": ").append(c.getNumero()).append(",\n");

            String dueño = (c.getUsernameDueño() == null) ? "null" : "\"" + c.getUsernameDueño() + "\"";
            sb.append("    \"usernameDueño\": ").append(dueño).append(",\n");

            sb.append("    \"objetos\": [");
            List<String> objs = c.getObjetos();
            for (int j = 0; j < objs.size(); j++) {
                sb.append("\"").append(objs.get(j)).append("\"");
                if (j < objs.size() - 1) sb.append(",");
            }
            sb.append("]\n");
            sb.append("  }");
            if (i < casilleros.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        escribirArchivo(RUTA_CASILLEROS, sb.toString());
    }

    public static List<Casillero> cargarCasilleros() {
        List<Casillero> lista = new ArrayList<>();
        String contenido = leerArchivo(RUTA_CASILLEROS);

        if (contenido == null || contenido.trim().isEmpty()) {
            for (int i = 1; i <= 10; i++) {
                lista.add(new Casillero(i));
            }
            guardarCasilleros(lista);
            return lista;
        }

        String[] bloques = contenido.split("\\}");
        for (String bloque : bloques) {
            if (!bloque.contains("numero")) continue;

            int numero = Integer.parseInt(extraerValor(bloque, "numero"));
            String dueño = extraerValor(bloque, "usernameDueño"); // Puede retornar null
            List<String> objetos = extraerLista(bloque, "objetos");

            Casillero c = new Casillero(numero);
            if (dueño != null && !dueño.equals("null")) {
                c.ocupar(dueño);
            }
            c.setObjetos(objetos);
            lista.add(c);
        }
        return lista;
    }

    // ==========================================
    //   PROCESADORES DE TEXTO (PARSERS MANUALES)
    // ==========================================
    private static String extraerValor(String bloque, String llave) {
        String objetivo = "\"" + llave + "\":";
        int inicioLlave = bloque.indexOf(objetivo);
        if (inicioLlave == -1) return null;

        String sub = bloque.substring(inicioLlave + objetivo.length()).trim();

        if (sub.startsWith("null")) {
            return null;
        }
        if (sub.startsWith("\"")) {
            int finComilla = sub.indexOf("\"", 1);
            return sub.substring(1, finComilla);
        }

        // Si es un número numérico (sin comillas)
        int finTexto = sub.indexOf(",");
        if (finTexto == -1) finTexto = sub.indexOf("\n");
        if (finTexto == -1) finTexto = sub.length();
        return sub.substring(0, finTexto).trim();
    }

    private static List<String> extraerLista(String bloque, String llave) {
        List<String> lista = new ArrayList<>();
        String objetivo = "\"" + llave + "\":";
        int inicioLlave = bloque.indexOf(objetivo);
        if (inicioLlave == -1) return lista;

        int corcheteInicio = bloque.indexOf("[", inicioLlave);
        int corcheteFin = bloque.indexOf("]", corcheteInicio);
        if (corcheteInicio == -1 || corcheteFin == -1) return lista;

        String contenidoArreglo = bloque.substring(corcheteInicio + 1, corcheteFin).trim();
        if (contenidoArreglo.isEmpty()) return lista;

        String[] elementos = contenidoArreglo.split(",");
        for (String el : elementos) {
            el = el.trim();
            if (el.startsWith("\"") && el.endsWith("\"")) {
                lista.add(el.substring(1, el.length() - 1));
            }
        }
        return lista;
    }

    // ==========================================
    //   MÉTODOS PARA HISTORIAL (AUDITORÍA)
    // ==========================================

    // Implementar el guardado de la lista de strings en historial.json
    public static void guardarHistorial(List<String> historial) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int indiceRegistro = 0; indiceRegistro < historial.size(); indiceRegistro++) {
            sb.append("  \"").append(historial.get(indiceRegistro)).append("\"");
            if (indiceRegistro < historial.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]");
        escribirArchivo(RUTA_HISTORIAL, sb.toString());
    }

    // Implementar la lectura de historial.json y retornar la lista
    public static List<String> cargarHistorial() {
        List<String> lista = new ArrayList<>();
        String contenido = leerArchivo(RUTA_HISTORIAL);

        if (contenido == null || contenido.trim().isEmpty() || contenido.equals("[\n]")) {
            return lista; // Retorna lista vacía si no hay archivo o está vacío
        }

        String[] lineas = contenido.split("\n");
        for (String linea : lineas) {
            linea = linea.trim();
            if (linea.startsWith("\"")) {
                int inicio = linea.indexOf("\"") + 1;
                int fin = linea.lastIndexOf("\"");
                if (inicio > 0 && fin > inicio) {
                    lista.add(linea.substring(inicio, fin));
                }
            }
        }
        return lista;
    }
    // ==========================================
    //   MANEJO DE ENTRADA Y SALIDA (I/O)
    // ==========================================
    private static void escribirArchivo(String ruta, String datos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write(datos);
        } catch (IOException e) {
            System.out.println("Error al guardar datos en: " + ruta);
        }
    }

    private static String leerArchivo(String ruta) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        } catch (IOException e) {
            return null;
        }
        return sb.toString();
    }
}