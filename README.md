# MP2YUGIOH
Yu-Gi-Oh! Duelo — MP2 
Este proyecto es un simulador de duelos por turnos inspirado en Yu-Gi-Oh!, desarrollado en Java con interfaz gráfica (Swing). El objetivo es reducir los Puntos de Vida (LP) del adversario de 8000 a 0, o ganar si el oponente se queda sin cartas en su mazo.
El sistema permite a dos jugadores enfrentarse usando cartas de monstruos, cartas mágicas y cartas de trampa. Los monstruos tienen estadísticas de ataque y defensa; las mágicas activan efectos inmediatos como recuperar salud, robar cartas o destruir el campo enemigo; y las trampas se colocan boca abajo y se activan automáticamente cuando se cumple su condición.

## Cómo ejecutar
Compilar todos los archivos desde la carpeta src/:
---- bash 
---- javac *.java
---- java Main

Al iniciar, se abrirá una ventana donde se ingresan los nombres de los dos usuarios. El juego baraja automáticamente un mazo de 50 cartas, reparte 25 a cada jugador y elige al azar quién empieza.

## Cómo se juega
Durante el duelo, el jugador activo puede jugar una carta de su mano y realizar un ataque por turno. Si se invoca un monstruo de nivel 5 o 6 se debe sacrificar uno propio; para nivel 7 o más, se sacrifican dos. Los monstruos pueden atacar a las criaturas del oponente o directamente a sus LP si el campo enemigo está vacío. Al terminar, se presiona "Pasar Turno" para que juegue el rival.

## Elaboradores 

Juan David Correa Zapata — 2459431
Juelz Santiago Zapata Bedoya — 2559814
