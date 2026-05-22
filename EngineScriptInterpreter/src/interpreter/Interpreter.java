package interpreter;

import java.util.Map;
import parser.EstadoNodo;
import parser.Transicion;

public class Interpreter {
    private final Map<String, EstadoNodo> mapaEstados;
    private String estadoActual;

    public Interpreter(Map<String, EstadoNodo> mapaEstados, String estadoInicial) {
        this.mapaEstados = mapaEstados;
        this.estadoActual = estadoInicial;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    // Este método recibe el evento (ej: "ruido") y cambia el estado si la regla existe
    public void enviarEvento(String evento) {
        EstadoNodo nodoActual = mapaEstados.get(estadoActual);

        if (nodoActual == null) {
            System.out.println("[Error] El estado actual '" + estadoActual + "' no existe en el mapa.");
            return;
        }

        boolean transicionEncontrada = false;

        // Buscamos entre todas las transiciones del estado actual
        for (Transicion transicion : nodoActual.transiciones) {
            if (transicion.eventoObjetivo.equals(evento)) {
                // ¡Encontrada! Cambiamos el estado de la entidad al destino de la regla
                this.estadoActual = transicion.estadoDestino;
                System.out.println("[Intérprete] ¡Regla activada! Cambiando al estado: " + estadoActual.toUpperCase());
                transicionEncontrada = true;
                break;
            }
        }

        if (!transicionEncontrada) {
            System.out.println("[Intérprete] Evento '" + evento + "' recibido, pero no hay reglas para él en el estado '" + estadoActual + "'.");
        }
    }
}