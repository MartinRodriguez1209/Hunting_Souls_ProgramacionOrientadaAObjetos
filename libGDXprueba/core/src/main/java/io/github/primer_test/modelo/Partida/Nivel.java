// Nivel.java 
package io.github.primer_test.modelo.Partida;

import java.util.ArrayList;

import io.github.primer_test.Recompensas.RecompensasDelNivel;
import io.github.primer_test.modelo.Enemigos.Enemigov3;

public class Nivel {

	private final String fondo;
	private final ArrayList<Enemigov3> enemigos;
	private RecompensasDelNivel recompensas;// TODO

	public Nivel(ArrayList<Enemigov3> enemigos, String fondo) {
		this.enemigos = enemigos;
		this.fondo = fondo;

	}

	public String getRutaFondo() {
		return fondo;
	}

	public ArrayList<Enemigov3> getEnemigos() {
		return enemigos;
	}
}