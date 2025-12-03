package io.github.primer_test.modelo.ManejadorAcciones;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class ArmaduraDecorador implements IAccionDecorador {

	private final IAccionDecorador estrategiaDecorada;

	public ArmaduraDecorador(IAccionDecorador estrategiaDecorada) {
		this.estrategiaDecorada = estrategiaDecorada;

	}

	@Override
	public void ejecutarEstrategia(Unidad unidadObjetivo, Integer valor) {
		Integer armadura = unidadObjetivo.getArmadura();
		Integer danioFinal = Math.max(0, valor - armadura);
		this.estrategiaDecorada.ejecutarEstrategia(unidadObjetivo, danioFinal);
	}

}
