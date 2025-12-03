package io.github.primer_test.modelo.Partida;

import java.util.ArrayList;

public class Partida {
	private final ArrayList<Nivel> niveles;
	private int nivelActual;
	private final GeneradorNiveles generador;

	public Partida() {
		this.generador = new GeneradorNiveles();
		this.niveles = new ArrayList<>();
		this.nivelActual = 0;
		generarNivelesPartidaBase();
	}

	public void generarNivelesPartidaBase() {
		int cantidadNiveles = 4;
		for (int i = 1; i <= cantidadNiveles; i++) {
			Nivel nivel = generador.crearNivel(i);
			this.niveles.add(nivel);
		}

	}

	public Nivel getNivelActual() {
		return niveles.get(nivelActual);
	}

	public void avanzarNivel() {
		this.nivelActual++;
	}

	public Boolean completeTodosNiveles() {
		return this.nivelActual >= this.niveles.size();
	}

	public int getNivelActualIndice() {
		return nivelActual;
	}

}
