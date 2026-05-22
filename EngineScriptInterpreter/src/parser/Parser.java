package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lexer.Token;
import lexer.TokenType;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Método auxiliar para mirar el token actual
    private Token peek() {
        return tokens.get(position);
    }

    // Método auxiliar para avanzar al siguiente token
    private Token advance() {
        Token current = peek();
        if (current.type != TokenType.EOF) {
            position++;
        }
        return current;
    }

    public Map<String, EstadoNodo> parse() {
        Map<String, EstadoNodo> mapaEstados = new HashMap<>();
        EstadoNodo estadoActual = null;

        // Recorremos todos los tokens hasta llegar al final del archivo (EOF)
        while (peek().type != TokenType.EOF) {
            Token tokenActual = peek();

            // 1. Si encontramos la palabra ESTADO
            if (tokenActual.type == TokenType.ESTADO) {
                advance(); // Consumimos la palabra "ESTADO"
                
                Token nombreToken = advance(); // Leemos el nombre del estado (ej: "inicial")
                String nombreEstado = nombreToken.value;
                
                // Creamos el nodo y lo metemos en nuestro mapa
                estadoActual = new EstadoNodo(nombreEstado);
                mapaEstados.put(nombreEstado, estadoActual);
                continue;
            }

            // 2. Si encontramos la palabra SI dentro de un estado
            if (tokenActual.type == TokenType.SI) {
                advance(); // Consumimos "SI"
                advance(); // Consumimos la palabra "evento" (asumimos que siempre es así)
                advance(); // Consumimos el "=="
                
                Token textoToken = advance(); // Leemos el texto del evento (ej: "ruido")
                String evento = textoToken.value;
                
                advance(); // Consumimos el "IR_A"
                
                Token destinoToken = advance(); // Leemos el estado destino (ej: "alerta")
                String destino = destinoToken.value;

                // Si estamos dentro de un estado válido, le añadimos esta regla
                if (estadoActual != null) {
                    estadoActual.añadirTransicion(new Transicion(evento, destino));
                }
                continue;
            }

            // Si es cualquier otra cosa, avanzamos para no quedarnos atascados
            advance();
        }

        return mapaEstados;
    }
}