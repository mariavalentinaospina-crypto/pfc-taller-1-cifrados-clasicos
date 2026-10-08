# Taller 1 — Cifrados clásicos con recursión

Fundamentos de Programación Funcional y Concurrente
Escuela de Ingeniería de Sistemas y Computación, Universidad del Valle
Profesor: Carlos Andrés Delgado Saavedra

El enunciado completo, con los ejemplos de cada punto, la rúbrica y la ecuación de calificación, es el PDF publicado en el campus virtual. Este archivo dice cómo está armado el proyecto y cómo se entrega.

## Integrantes

| Nombre completo                 | Código  |
|---------------------------------|---------|
| MARIA VALENTINA OSPINA ESPINOSA | 2559760 |
| JOHN FREDDY HURTADO VALENCIA    | 2559863 |
| KAREN DAYANA SEPUVELDA RENDON   | 2559773 |



## Cómo está organizado el proyecto

```
app/src/main/scala/taller/
    CifradosClasicos.scala    aquí van los cinco puntos
    App.scala                 programa de arranque

app/src/test/scala/taller/
    CifradosClasicosTest.scala   las pruebas

docs/                         los informes, en Markdown
```

El código está en `main`. Los informes de proceso y de corrección están en `docs/`, en Markdown, con la notación matemática en LaTeX y los diagramas en mermaid.

## Informes

- [Informe de corrección](docs/informe-correccion.md)
- [Informe de proceso](docs/informe-proceso.md)

## Cómo se ejecuta

```
./gradlew test    # revisa las reglas del curso y corre las pruebas
./gradlew run     # corre el programa
```

La primera vez se demora: Gradle descarga el compilador de Scala y la versión de Java que necesita. Al terminar, `./gradlew test` deja un informe navegable en `app/build/reports/tests/test/index.html`.

## Los cinco puntos

| Punto | Función | Recursión |
|---|---|---|
| 1 | `cesar(m: Mensaje, k: Int): Mensaje` | lineal |
| 2 | `cesarCola(m: Mensaje, k: Int, acc: Mensaje = ""): Mensaje` | de cola, con `@tailrec` |
| 3 | `frecuencias(m: Mensaje): Frecuencias` | de cola |
| 4 | `desplazamientoProbable(m: Mensaje): Int` y `romperCesar(m: Mensaje): Mensaje` | — |
| 5 | `combinaciones(n: Int, a: Int): BigInt` y `vigenere(m: Mensaje, clave: Clave): Mensaje` | — |

## Reglas del código

Sin `var`, sin `while`, sin `return` y sin estado mutable. Todo se resuelve con `val`, recursión y llamados a funciones. Las funciones auxiliares se escriben dentro de cada función.