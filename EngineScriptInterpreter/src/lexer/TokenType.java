package lexer;
//ahora vamos a decirle a java qué tipo de palabras existen en nuestro lenguaje inventado. para eso se usan los enum (una lista de palabras fijas).
public enum TokenType {
    ESTADO,                  // La palabra "ESTADO"
    SI,                      // La palabra "SI"
    IDENTIFICADOR,           // Nombres de estados (inicial, alerta)
    TEXTO,                   // Cosas entre comillas ("ruido", "calma")
    OPERADOR_IGUAL,          // El "=="
    ACCION_IR_A,             // La palabra "IR_A"
    EOF                      // Significa "End of File" (el final del archivo)
}