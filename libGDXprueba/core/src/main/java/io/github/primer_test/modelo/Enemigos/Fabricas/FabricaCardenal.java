package io.github.primer_test.modelo.Enemigos.Fabricas;

import io.github.primer_test.Utils.CalculadoraDificultad;
import io.github.primer_test.modelo.Enemigos.Cardenal;
import io.github.primer_test.modelo.Enemigos.Enemigov3;

public class FabricaCardenal extends FabricaEnemigos {
	@Override
	public Enemigov3 crearEnemigov3(Integer dificultad) {
		Cardenal cardenal = new Cardenal();
		double multiplicador = CalculadoraDificultad.calcularMultiplicadorLineal(dificultad);
		cardenal.setDificultad(dificultad);
		if (multiplicador > 1.0) {
			int vidaEscalada = (int) (Cardenal.VIDA_BASE * multiplicador);
			cardenal.setVidaTotal(vidaEscalada);
			cardenal.setVidaActual(vidaEscalada);
			int escudoEscalado = (int) (Cardenal.ESCUDO_BASE * multiplicador);
			cardenal.setEscudo(escudoEscalado);
		}
		return cardenal;
	}
}
