// NivelesControlador.java 
package io.github.primer_test.Controlador;

import io.github.primer_test.Vista.PeleaVista;
import io.github.primer_test.modelo.Partida.GeneradorNiveles;
import io.github.primer_test.modelo.Partida.Nivel;
import io.github.primer_test.modelo.Usuario.Jugador;

public class NivelesControlador {

    private final Jugador jugador;
    private final GeneradorNiveles generador; // Usamos nuestro generador
    private int numeroNivelActual = 1; // Empezamos en el nivel 1

    public NivelesControlador(Jugador jugador) {
        this.jugador = jugador;
        this.generador = new GeneradorNiveles();
        cargarSiguienteNivel(); // Inicia el primer nivel
    }

    public void cargarSiguienteNivel() {
        // Le pedimos al generador que cree el nivel actual
        Nivel nivel = generador.crearNivel(numeroNivelActual);

        if (nivel != null) {
            // Si el nivel existe, iniciamos la pelea
            System.out.println("Cargando Nivel " + numeroNivelActual + "...");
            
            PeleaVista nuevaPeleaView = new PeleaVista();
            new PeleaControlador(nuevaPeleaView, nivel.getEnemigos(), this.jugador);
            
            numeroNivelActual++; // Preparamos el contador para el siguiente nivel
        } else {
            // Si es null, significa que no hay más niveles
            manejarFinDeJuego();
        }
    }
    
    private void manejarFinDeJuego() {
        System.out.println("¡Felicidades! Has completado todos los niveles.");
    }
}