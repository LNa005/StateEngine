package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (position < input.length()) {
            char current = input.charAt(position);

            // 1. Ignorar espacios, tabulaciones y saltos de línea
            if (Character.isWhitespace(current)) {
                position++;
                continue;
            }

            // 2. Detectar si es el operador "=="
            if (current == '=' && position + 1 < input.length() && input.charAt(position + 1) == '=') {
                tokens.add(new Token(TokenType.OPERADOR_IGUAL, "=="));
                position += 2;
                continue;
            }

            // 3. Detectar texto entre comillas (ej: "ruido")
            if (current == '"') {
                StringBuilder sb = new StringBuilder();
                position++; // Saltamos la comilla de apertura
                while (position < input.length() && input.charAt(position) != '"') {
                    sb.append(input.charAt(position));
                    position++;
                }
                position++; // Saltamos la comilla de cierre
                tokens.add(new Token(TokenType.TEXTO, sb.toString()));
                continue;
            }

            // 4. Detectar palabras (Palabras clave o Identificadores)
            if (Character.isLetter(current) || current == '_') {
                StringBuilder sb = new StringBuilder();
                while (position < input.length() && (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
                    sb.append(input.charAt(position));
                    position++;
                }
                String palabra = sb.toString();

                // Comprobamos si es una palabra reservada de nuestro idioma
                if (palabra.equals("ESTADO")) {
                    tokens.add(new Token(TokenType.ESTADO, palabra));
                } else if (palabra.equals("SI")) {
                    tokens.add(new Token(TokenType.SI, palabra));
                } else if (palabra.equals("IR_A")) {
                    tokens.add(new Token(TokenType.ACCION_IR_A, palabra));
                } else {
                    // Si no es ninguna de las anteriores, es un nombre (ej: inicial, alerta)
                    tokens.add(new Token(TokenType.IDENTIFICADOR, palabra));
                }
                continue;
            }

            // Si encuentra un carácter raro que no entiende, se lo salta para no romperse
            position++;
        }

        // Al final, añadimos la ficha de fin de archivo
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}