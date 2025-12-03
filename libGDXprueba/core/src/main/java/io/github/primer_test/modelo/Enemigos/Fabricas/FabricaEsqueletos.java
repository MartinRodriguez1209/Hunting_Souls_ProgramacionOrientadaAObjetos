package io.github.primer_test.modelo.Enemigos.Fabricas;

import io.github.primer_test.Utils.CalculadoraDificultad;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Enemigos.EsqueletoGuardian;

public class FabricaEsqueletos extends FabricaEnemigos {

	@Override
	public Enemigov3 crearEnemigov3(Integer dificultad) {
		EsqueletoGuardian esqueletoGuardian = new EsqueletoGuardian();
		double multiplicador = CalculadoraDificultad.calcularMultiplicadorLineal(dificultad);
		esqueletoGuardian.setDificultad(dificultad);
		if (multiplicador > 1.0) {
			int vidaEscalada = (int) (EsqueletoGuardian.VIDA_BASE * multiplicador);
			esqueletoGuardian.setVidaTotal(vidaEscalada);
			esqueletoGuardian.setVidaActual(vidaEscalada);
			int escudoEscalado = (int) (EsqueletoGuardian.ESCUDO_BASE * multiplicador);
			esqueletoGuardian.setEscudo(escudoEscalado);
			int probabilidadBloqueoEscalada = Math.min(EsqueletoGuardian.PROBABILIDAD_BLOQUEAR_MAX,
					(int) (EsqueletoGuardian.PROBABILIDAD_BLOQUEAR_BASE * multiplicador));
			esqueletoGuardian.setProbabilidadBloqueo(probabilidadBloqueoEscalada);
		}

		return esqueletoGuardian;
	}

}
