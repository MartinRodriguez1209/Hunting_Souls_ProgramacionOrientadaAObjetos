package io.github.primer_test.modelo.Enemigos.Fabricas;

import io.github.primer_test.Utils.CalculadoraDificultad;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Enemigos.Monja;

public class FabricaMonja extends FabricaEnemigos {
	@Override
	public Enemigov3 crearEnemigov3(Integer dificultad) {
		Monja monja = new Monja();
		double multiplicador = CalculadoraDificultad.calcularMultiplicadorLineal(dificultad);
		monja.setDificultad(dificultad);
		if (multiplicador > 1.0) {
			int vidaEscalada = (int) (Monja.VIDA_BASE * multiplicador);
			int escudoEscalado = (int) (Monja.ESCUDO_BASE * multiplicador);
			int probabilidadCurarEscalada = Math.min(Monja.PROBABILIDAD_CURAR_MAX,
					(int) (Monja.PROBABILIDAD_CURAR_BASE * multiplicador));
			monja.setVidaTotal(vidaEscalada);
			monja.setVidaActual(vidaEscalada);
			monja.setEscudo(escudoEscalado);
			monja.setProbabilidadCurar(probabilidadCurarEscalada);
		}
		return monja;
	}
}
