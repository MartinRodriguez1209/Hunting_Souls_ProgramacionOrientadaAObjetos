package io.github.primer_test.modelo.Enemigos.Fabricas;

import io.github.primer_test.Utils.CalculadoraDificultad;
import io.github.primer_test.modelo.Enemigos.CulturistaEncapuchado;
import io.github.primer_test.modelo.Enemigos.Enemigov3;

public class FabricaCulturista extends FabricaEnemigos {
	@Override
	public Enemigov3 crearEnemigov3(Integer dificultad) {
		CulturistaEncapuchado culturista = new CulturistaEncapuchado();
		double multiplicador = CalculadoraDificultad.calcularMultiplicadorLineal(dificultad);
		culturista.setDificultad(dificultad);
		if (multiplicador > 1.0) {
			int vidaEscalada = (int) (CulturistaEncapuchado.VIDA_BASE * multiplicador);
			culturista.setVidaTotal(vidaEscalada);
			culturista.setVidaActual(vidaEscalada);
			int armaduraEscalada = (int) (CulturistaEncapuchado.ARMADURA_BASE * multiplicador);
			int probabilidadBloqueoEscalada = Math.min(CulturistaEncapuchado.PROBABILIDAD_BLOQUEAR_MAX,
					(int) (CulturistaEncapuchado.PROBABILIDAD_BLOQUEAR_BASE * multiplicador));
			culturista.setArmadura(armaduraEscalada);
			culturista.setProbabilidadBloqueo(probabilidadBloqueoEscalada);
		}
		return culturista;
	}
}
