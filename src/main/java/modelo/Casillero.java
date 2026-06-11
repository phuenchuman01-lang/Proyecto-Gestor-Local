package modelo;

import java.util.ArrayList;
import java.util.List;

public class Casillero {
    private int numero;
    private String usernameDueño;
    private List<String> objetos;

    public Casillero(int numero) {
        this.numero = numero;
        this.usernameDueño = null;
        this.objetos = new ArrayList<>();
    }

    public boolean estaDisponible() {
        return this.usernameDueño == null;
    }

    public void ocupar(String username) {
        this.usernameDueño = username;
    }

    public void liberar() {
        this.usernameDueño = null;
        this.objetos.clear();
    }

    public void agregarObjeto(String objeto) {
        this.objetos.add(objeto);
    }

    public boolean quitarObjeto(String objeto) {
        return this.objetos.remove(objeto);
    }

    public int getNumero() { return numero; }

    public String getUsernameDueño() { return usernameDueño; }
    public void setUsernameDueño(String usernameDueño) { this.usernameDueño = usernameDueño; }

    public List<String> getObjetos() { return objetos; }
    public void setObjetos(List<String> objetos) { this.objetos = objetos; }
}