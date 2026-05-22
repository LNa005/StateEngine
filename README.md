#  Anti-Herramientas: Script Engine Interpreter
## Un Motor de Máquinas de Estados Interpretadas con Arquitectura de Compiladores

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)
![Status](https://img.shields.io/badge/Status-Active-blue?style=flat-square)

---

## 📋 Descripción General

**Anti-Herramientas** es un sistema de intérprete de scripts declarativos diseñado para definir y ejecutar máquinas de estados finitos (FSM) en tiempo de ejecución. En lugar de codificar lógica de transiciones de estados directamente en tu aplicación, especifícalas en un lenguaje declarativo limpio, intuitivo y agnóstico del dominio.

Esto permite:
- **Separación de concerns**: Lógica de negocio fuera del código
- **Prototipado rápido**: Cambia comportamientos sin recompilar
- **Mantenibilidad**: Scripts legibles y auditable
- **Escalabilidad**: Reutiliza el motor en múltiples contextos

### Caso de uso ideal:
```
Sistemas de IA/Agents → Workflows de juegos → Sistemas de NPC
Automatización de procesos → Máquinas de negocio → Simuladores
```

---

## Arquitectura: Compilador en 3 Fases

Este proyecto implementa una arquitectura clásica de compiladores dividida en **3 capas independientes**:

### 1️⃣ **Lexer** (Análisis Léxico)
Transforma el texto crudo en tokens.
```java
// Input: ESTADO inicial
//        SI evento == "ruido" IR_A alerta

// Output: [ESTADO, IDENTIFICADOR(inicial), SI, IDENTIFICADOR(evento), 
//          OPERADOR_IGUAL, TEXTO(ruido), ACCION_IR_A, IDENTIFICADOR(alerta), EOF]
```

### 2️⃣ **Parser** (Análisis Sintáctico)
Valida la estructura y construye una representación en árbol (AST-like).
```java
// Input: Lista de tokens
// Output: Map<String, EstadoNodo> con transiciones mapeadas
```

### 3️⃣ **Interpreter** (Ejecución)
Ejecuta el AST de forma interactiva, procesando eventos y cambiando estados.
```java
// Input: Evento por consola
// Output: Cambio de estado si hay regla, o ignore
```

**Ventaja**: Cada componente es independiente, reutilizable y testeable.

---

## 🚀 Uso Rápido

### Compilación
```bash
cd EngineScriptInterpreter
javac -d bin src/**/*.java
```

### Ejecución
```bash
java -cp bin App
```

### Output esperado
```
=================================================
  SISTEMA DE SCRIPTS INTERPRETADO ARRANCADO      
=================================================
Estado inicial de la entidad: INICIAL
Escribe un evento (o 'salir' para terminar) y pulsa Enter.

[INICIAL] Introduce evento > ruido
[Intérprete] ¡Regla activada! Cambiando al estado: ALERTA

[ALERTA] Introduce evento > calma
[Intérprete] ¡Regla activada! Cambiando al estado: INICIAL
```

---

##  Lenguaje de Scripts

### Sintaxis

```
ESTADO <nombre>
    SI evento == "<valor>" IR_A <estado_destino>
    SI evento == "<otro_valor>" IR_A <otro_estado>

ESTADO <otro_estado>
    SI evento == "<condicion>" IR_A <estado_vuelta>
```

### Ejemplo completo: Comportamiento de NPC

```
ESTADO patrulla
    SI evento == "enemigo_detectado" IR_A combate
    SI evento == "refugio_cercano" IR_A huida

ESTADO combate
    SI evento == "enemigo_derrotado" IR_A patrulla
    SI evento == "hp_bajo" IR_A huida

ESTADO huida
    SI evento == "distancia_segura" IR_A patrulla
    SI evento == "trampa" IR_A combate
```

---

##  Estructura del Proyecto

```
Anti-Herramientas/
└── EngineScriptInterpreter/
    ├── src/
    │   ├── App.java                 # Punto de entrada
    │   ├── comportamiento.txt       # Script ejecutable
    │   ├── lexer/
    │   │   ├── Lexer.java           # Tokenizador
    │   │   ├── Token.java           # Estructura de token
    │   │   └── TokenType.java       # Enumeración de tipos
    │   ├── parser/
    │   │   ├── Parser.java          # Parser sintáctico
    │   │   ├── EstadoNodo.java      # Representación de estado
    │   │   └── Transicion.java      # Regla de transición
    │   └── interpreter/
    │       └── Interpreter.java     # Motor de ejecución
    ├── bin/                         # Bytecode compilado
    ├── lib/                         # Librerías externas
    └── README.md
```

---

## Flujo de Ejecución

```
┌──────────────────┐
│comportamiento.txt│
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│    LEXER         │ → Tokenización
├──────────────────┤
│ [ESTADO, INICIAL,│
│  SI, EVENTO, ==, │
│  RUIDO, IR_A,    │
│  ALERTA, ...]    │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│    PARSER        │ → Análisis sintáctico
├──────────────────┤
│ {                │
│   "inicial": {   │
│     transiciones:│
│       [RUIDO →   │
│        ALERTA]   │
│   },             │
│   "alerta": {...}│
│ }                │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ INTERPRETER      │ → Ejecución interactiva
├──────────────────┤
│ Usuario:         │
│ "ruido"          │
│       ▼          │
│ [inicial] →      │
│ [alerta]  ✓      │
└──────────────────┘
```

---

##  Extensibilidad

### Agregar nuevas acciones
Modifica `Interpreter.enviarEvento()` para soportar:
- Ejecución de métodos externos
- Persistencia de datos
- Logging avanzado
- Eventos con parámetros

### Ejemplo: Soporte para acciones ejecutables

```java
// Extensión futura
ESTADO combate
    SI evento == "golpe" IR_A combate HACER log("Combatiendo")
    SI evento == "derrota" IR_A muerte HACER delete_entity()
```

---

## Requisitos

- **Java 11+**
- **VS Code** (opcional, con extensión Java)

---

## Comparativa con alternativas

| Característica | Anti-Herramientas | Hardcoded | State Libraries |
|---|---|---|---|
| **Sin recompilación**    | ✅ | ❌ | ✅ |
| **Lenguaje declarativo** | ✅ | ❌ | ❌ |
| **Arquitectura limpia**  | ✅ | ❌ | ⚠️ |
| **Control total**        | ✅ | ✅ | ❌ |
| **Depuración fácil**     | ✅ | ❌ | ⚠️ |

---

## Concepto Educativo

Este proyecto es un **excelente caso de estudio** para aprender:
- 🔤 **Análisis léxico**: Tokenización de lenguajes
- 🌳 **Análisis sintáctico**: Construcción de AST
- ⚙️ **Interpretación**: Ejecución de bytecode
- 🏛️ **Arquitectura de compiladores**: Separación de capas

---

## Licencia

MIT License - Siéntete libre de usar, modificar y distribuir.

---

## Contribuciones

¿Ideas para mejorar Anti-Herramientas?
- Agregar parámetros a eventos
- Soportar expresiones condicionales más complejas
- Debugger interactivo
- Compilador a código Java

---

## Contacto

Preguntas o sugerencias sobre la arquitectura del intérprete:
**¡Abre un issue o una PR!**

---


