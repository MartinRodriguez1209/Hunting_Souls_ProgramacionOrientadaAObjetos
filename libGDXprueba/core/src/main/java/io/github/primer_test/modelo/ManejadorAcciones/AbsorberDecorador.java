package io.github.primer_test.modelo.ManejadorAcciones;

import java.util.Random;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class AbsorberDecorador implements IAccionDecorador {

	private final IAccionDecorador estrategiaDecorada;

	public AbsorberDecorador(IAccionDecorador estrategiaDecorada) {
		this.estrategiaDecorada = estrategiaDecorada;

	}

	@Override
	public void ejecutarEstrategia(Unidad unidadObjetivo, Integer valor) {
		if (new Random().nextInt(100) < unidadObjetivo.getProbabilidadAbsorber()) {
			unidadObjetivo.recibirCuracion(valor / 2);
			return;
		} else {
			estrategiaDecorada.ejecutarEstrategia(unidadObjetivo, valor);
		}
	}

}
