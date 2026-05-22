package parser;

import java.util.ArrayList;
import java.util.List;

public class EstadoNodo {
    public String nombreEstado; // Ejemplo: "inicial"
    public List<Transicion> transiciones; // Sus reglas asociadas

    public EstadoNodo(String nombreEstado) {
        this.nombreEstado = nombreEstado;
        this.transiciones = new ArrayList<>();
    }

    public void añadirTransicion(Transicion transicion) {
        this.transiciones.add(transicion);
    }
}