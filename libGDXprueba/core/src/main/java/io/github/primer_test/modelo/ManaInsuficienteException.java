package io.github.primer_test.modelo;

public class ManaInsuficienteException extends Exception {

	public ManaInsuficienteException() {
		super("Mana insuficiente para realizar esa accion");
	}

}
