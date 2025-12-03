package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;

import io.github.primer_test.Recompensas.Recompensa;
import io.github.primer_test.modelo.ENUMS.TipoUnidad;
import io.github.primer_test.modelo.Usuario.Jugador;

public abstract class Enemigov3 extends Unidad {

	public Enemigov3(Integer vidaActual, Integer vidaTotal, String nombre, Integer escudo) {
		super(vidaActual, vidaTotal, nombre, escudo);
		this.faccion = TipoUnidad.ENEMIGO;
	}

	// Aca va la logica de lo que va a hacer cada enemigo en su turno, conoce al
	// jugador para poder realizarle ataques y la lista con los demas enemigos en el
	// combate para poder realizar acciones de tipo soporte o especiales
	public abstract void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate);

	@Override
	public String toString() {
		return this.nombre + " vida:" + this.vidaActual;
	}

	public void setEscudo(Integer escudo) {
		this.escudo = escudo;
	}

	public static class Builder {
	}

	public Recompensa getRecompensaHabilidad() {
		return null;
	}
}