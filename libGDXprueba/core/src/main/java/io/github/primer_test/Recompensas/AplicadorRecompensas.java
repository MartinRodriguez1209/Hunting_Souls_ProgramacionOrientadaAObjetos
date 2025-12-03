package io.github.primer_test.Recompensas;

import io.github.primer_test.modelo.Usuario.Jugador;

public class AplicadorRecompensas {
    
    public void aplicar(Recompensa recompensa, Jugador jugador) {
        switch (recompensa.getTipo()) {
            case VIDA_EXTRA:
                aplicarVidaExtra(recompensa, jugador);
                break;
                
            case MANA_EXTRA:
                aplicarManaExtra(recompensa, jugador);
                break;
                
            case ESCUDO_EXTRA:
                aplicarEscudoExtra(recompensa, jugador);
                break;
                
            case HABILIDAD_ABSORCION:
                jugador.aplicarHabilidadAbsorcion(recompensa.getValor());
                break;
                
            case HABILIDAD_BLOQUEO:
                jugador.aplicarHabilidadBloqueo(recompensa.getValor());
                break;
            default:
                System.out.println("Tipo de recompensa no reconocido: " + recompensa.getTipo());
        }
        
        jugador.mostrarEstadoHabilidades();
    }
    
    private void aplicarVidaExtra(Recompensa recompensa, Jugador jugador) {
    jugador.aumentarVidaMaxima(recompensa.getValor());
}

private void aplicarManaExtra(Recompensa recompensa, Jugador jugador) {
    jugador.aumentarManaMaximo(recompensa.getValor());
}

    private void aplicarEscudoExtra(Recompensa recompensa, Jugador jugador) {
        jugador.recibirEscudo(recompensa.getValor());
    }
}
