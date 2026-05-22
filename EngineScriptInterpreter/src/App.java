import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lexer.Lexer;
import lexer.Token;
import parser.EstadoNodo;
import parser.Parser;
import interpreter.Interpreter;

public class App {
    public static void main(String[] args) {
        try {
            // 1. Leer el archivo con nuestro lenguaje script
            String contenido = Files.readString(Path.of("src/comportamiento.txt"));

            // 2. Pasar el texto al Lexer para obtener los tokens
            Lexer lexer = new Lexer(contenido);
            List<Token> tokens = lexer.tokenize();

            // 3. Pasar los tokens al Parser para estructurar el mapa de estados
            Parser parser = new Parser(tokens);
            Map<String, EstadoNodo> mapaEstados = parser.parse();

            // 4. Inicializar el Intérprete arrancando en el estado "inicial"
            Interpreter interprete = new Interpreter(mapaEstados, "inicial");

            // 5. Crear el bucle interactivo por consola
            Scanner scanner = new Scanner(System.in);
            System.out.println("=================================================");
            System.out.println("  SISTEMA DE SCRIPTS INTERPRETADO ARRANCADO      ");
            System.out.println("=================================================");
            System.out.println("Estado inicial de la entidad: INICIAL");
            System.out.println("Escribe un evento (o 'salir' para terminar) y pulsa Enter.\n");

            while (true) {
                System.out.print("[" + interprete.getEstadoActual().toUpperCase() + "] Introduce evento > ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("salir")) {
                    System.out.println("Cerrando el motor de simulación...");
                    break;
                }

                // Enviamos lo que has escrito al intérprete
                interprete.enviarEvento(input);
                System.out.println();
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error crítico en la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
    }
}