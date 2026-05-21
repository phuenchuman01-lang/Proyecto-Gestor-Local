package main.persistencia;   //JSON principal donde consultamos los datos de los usuarios y de los casilleros
                        //Como el nombre lo dice, el Manager del programa. :D
//Ver si es que falto algo, por si acaso. xd
import main.modelo.Usuario;
import main.modelo.Estudiante;
import main.modelo.Docente;
import main.modelo.Casillero;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonManager {

    private static final String RUTA_USUARIOS = "usuarios.json";
    private static final String RUTA_CASILLEROS = "casilleros.json";

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
            // Si el archivo no existe, creamos el admin por defecto
            lista.add(new Docente("admin", "admin123"));
            guardarUsuarios(lista);
            return lista;
        }

        String[] bloques = contenido.split("\\}");
        for (String bloque : bloques) {
            if (!bloque.contains("username")) continue;
            String user = extraerValor(bloque, "username");
            String pass = extraerValor(bloque, "password");
            String rol = extraerValor(bloque, "rol");

            if ("DOCENTE".equals(rol)) {
                lista.add(new Docente(user, pass));
            } else {
                lista.add(new Estudiante(user, pass));
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

        // Inicializamos los 10 por defecto
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