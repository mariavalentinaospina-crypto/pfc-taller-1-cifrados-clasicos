package taller

import scala.annotation.tailrec

/**
 * Taller 1 — cifrados clásicos con recursión.
 *
 * Solo se cifran las 26 letras minúsculas del alfabeto inglés; cualquier otro
 * carácter se copia sin cambio.
 */
class CifradosClasicos {

  type Mensaje = String
  type Clave = String

  // Una frecuencia asocia cada letra con las veces que aparece.
  type Frecuencias = List[(Char, Int)]

  val letras = 26
  val primera = 'a'.toInt

  def esMinuscula(c: Char): Boolean = c >= 'a' && c <= 'z'

  // Punto 1 -------------------------------------------------------------------

  /** César con recursión lineal: una operación pendiente por letra. */
  def cesar(m: Mensaje, k: Int): Mensaje = {

    def cifrar(c: Char): Char = {
      if (esMinuscula(c)) {
        val pos = c - 'a'
        val nuevo = ((pos + k) % 26 + 26) % 26
        ('a' + nuevo).toChar
      } else {
        c
      }
    }

    if (m.isEmpty) {
      ""
    } else {
      cifrar(m.head) + cesar(m.tail, k)
    }
  }

  // Punto 2 -------------------------------------------------------------------

  /**
   * El mismo César como proceso iterativo: espacio constante.
   * Cuando la función esté escrita, anótela con @tailrec: el compilador
   * comprueba que la llamada recursiva sea lo último que hace.
   */
  @tailrec
  final def cesarCola(m: Mensaje, k: Int, acc: Mensaje = ""): Mensaje = {
    if (m.isEmpty) {
      acc
    } else {
      val c = m.head
      val cCifrado = if (esMinuscula(c)) {
        val desplazamiento = ((c.toInt - primera + k) % letras + letras) % letras
        (desplazamiento + primera).toChar
      } else c
      cesarCola(m.tail, k, acc + cCifrado)
    }
  }

  // Punto 3 -------------------------------------------------------------------

  /**
   * Cuenta las letras minúsculas del mensaje, de mayor a menor frecuencia y,
   * en empate, en orden alfabético. El recorrido es recursivo de cola.
   */
  def frecuencias(m: Mensaje): Frecuencias = {

    @tailrec
    def contar(
                mensaje: Mensaje,
                frecuenciasActuales: Map[Char, Int]
              ): Map[Char, Int] = {

      if (mensaje.isEmpty) {
        frecuenciasActuales
      } else {
        val caracter = mensaje.head
        val resto = mensaje.tail

        if (caracter >= 'a' && caracter <= 'z') {
          val cantidad = frecuenciasActuales.getOrElse(caracter, 0)

          contar(
            resto,
            frecuenciasActuales + (caracter -> (cantidad + 1))
          )
        } else {
          contar(resto, frecuenciasActuales)
        }
      }
    }

    val resultado = contar(m, Map.empty)

    resultado.toList.sortBy {
      case (letra, cantidad) => (-cantidad, letra)
    }
  }

  // Punto 4 -------------------------------------------------------------------

  /**
   * Supone que la letra más frecuente del mensaje cifrado es la 'e' del
   * original y devuelve la distancia entre las dos. Sin letras, cero.
   */
  def desplazamientoProbable(m: Mensaje): Int = {
    val frecs = frecuencias(m)
    if (frecs.isEmpty) 0
    else {
      val letraMasFrecuente = frecs.head._1
      ((letraMasFrecuente - 'e') % 26 + 26) % 26
    }
  }

  def romperCesar(m: Mensaje): Mensaje = {
    val desplazamiento = desplazamientoProbable(m)
    cesar(m, -desplazamiento)
  }

  // Punto 5 -------------------------------------------------------------------

  /**
   * Cuántos mensajes de longitud n se forman con a letras sin dos iguales
   * seguidas.
   */
  def combinaciones(n: Int, a: Int): BigInt = {
    if (n == 0) {
      BigInt(1)
    } else if (n == 1) {
      BigInt(a)
    } else {
      BigInt(a - 1) * combinaciones(n - 1, a)
    }
  }

  /**
   * Vigenère: cada letra se corre según la letra de la clave que le toca. Lo
   * que no es letra minúscula se copia y no consume clave.
   */
  def vigenere(m: Mensaje, clave: Clave): Mensaje = {
    if (m.isEmpty) {
      ""
    } else {
      val caracter = m.head
      if (clave.isEmpty) {
        m
      } else if (esMinuscula(caracter)) {
        val avance = clave.head - 'a'
        val cifrado = ((caracter - 'a' + avance) % letras + 'a').toChar
        cifrado + vigenere(m.tail, clave.tail + clave.head)
      } else {
        caracter + vigenere(m.tail, clave)
      }
    }
  }
}