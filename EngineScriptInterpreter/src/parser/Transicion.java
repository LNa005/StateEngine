package parser;

public class Transicion {
    public String eventoObjetivo; // Guardará por ejemplo: "ruido"
    public String estadoDestino;  // Guardará por ejemplo: "alerta"

    public Transicion(String eventoObjetivo, String estadoDestino) {
        this.eventoObjetivo = eventoObjetivo;
        this.estadoDestino = estadoDestino;
    }
}