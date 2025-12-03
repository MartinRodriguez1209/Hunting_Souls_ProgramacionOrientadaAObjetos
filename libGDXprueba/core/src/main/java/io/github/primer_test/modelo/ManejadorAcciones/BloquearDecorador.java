package io.github.primer_test.modelo.ManejadorAcciones;

import java.util.Random;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class BloquearDecorador implements IAccionDecorador {
	private final IAccionDecorador estrategiaDecorada;

	public BloquearDecorador(IAccionDecorador estrategiaDecorada) {
		this.estrategiaDecorada = estrategiaDecorada;
	}

	@Override
	public void ejecutarEstrategia(Unidad unidadObjetivo, Integer valor) {
		if (new Random().nextInt(100) < unidadObjetivo.getProbabilidadBloqueo())
			return;
		this.estrategiaDecorada.ejecutarEstrategia(unidadObjetivo, valor);
	}

}
