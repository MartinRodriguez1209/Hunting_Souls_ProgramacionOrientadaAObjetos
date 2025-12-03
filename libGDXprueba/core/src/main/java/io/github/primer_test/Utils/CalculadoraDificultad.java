package io.github.primer_test.Utils;

public class CalculadoraDificultad {

	public static double calcularMultiplicadorLineal(int dificultad) {
		int nivel = Math.max(1, dificultad);
		// la dificultad aumenta linealmente en un 0,25%
		return 1.0 + (0.25 * (nivel - 1));
	}
}
