package io.github.primer_test.modelo.Enemigos;

import io.github.primer_test.modelo.Usuario.Jugador;

public interface IEstadoBoss {
	
	Boolean estaVivo(BossFinal boss);

	String accion(BossFinal boss, Jugador jugador);
	
	void transicionA(BossFinal boss);
}
