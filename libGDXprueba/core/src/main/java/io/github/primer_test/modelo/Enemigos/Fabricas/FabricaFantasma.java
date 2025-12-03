package io.github.primer_test.modelo.Enemigos.Fabricas;

import io.github.primer_test.Utils.CalculadoraDificultad;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Enemigos.Fantasma;

public class FabricaFantasma extends FabricaEnemigos {
	@Override
	public Enemigov3 crearEnemigov3(Integer dificultad) {
		Fantasma fantasma = new Fantasma();
		double multiplicador = CalculadoraDificultad.calcularMultiplicadorLineal(dificultad);
		fantasma.setDificultad(dificultad);
		if (multiplicador > 1.0) {

			int vidaEscalada = (int) (Fantasma.VIDA_BASE * multiplicador);
			fantasma.setVidaTotal(vidaEscalada);
			fantasma.setVidaActual(vidaEscalada);
			int probAbsorberEscalada = Math.min(Fantasma.PROBABILIDAD_ABSORBER_MAX,
					(int) (Fantasma.PROBABILIDAD_ABSORBER_BASE * multiplicador));
			fantasma.setProbabilidadAbsorber(probAbsorberEscalada);
		}
		return fantasma;
	}
}
