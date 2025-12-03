package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;
import java.util.Random;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.Acciones.AccionAturdir;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class Cardenal extends Enemigov3 {

	public static final int VIDA_BASE = 40;
	public static final int ESCUDO_BASE = 60;
	public static final String NOMBRE = "Cardenal";

	public Cardenal() {
		super(VIDA_BASE, VIDA_BASE, NOMBRE, ESCUDO_BASE);
	}

	@Override
	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {
		Random rnd = new Random();

		int rollAtaque = rnd.nextInt(100);
		if (rollAtaque <= 10) {
			excomulgar(jugador);
		} else if (rollAtaque <= 70) {
			oracion(jugador);
		} else if (rollAtaque <= 80) {
			maldicion(jugador);			
		} else {		
			golpeConCruz(jugador);
		}
	}

	public void oracion(Jugador usuario) {
		new AccionAtaque(15, "Oracion de cardenal", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Uso mi oracion contra el jugador\n",
				null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "oracion", null);
	}

	public void maldicion(Jugador usuario) {
		new AccionAturdir(1, "Maldicion de cardenal", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),
				"Uso mi maldicion contra el personaje\n", null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "maldicion", null);
	}

	public void golpeConCruz(Jugador usuario) {
		new AccionAtaque(20, "Golpe con cruz de cardenal", 3).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),
				"Uso mi golpe de cruz contra el personaje\n", null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "golpeConCruz", null);
	}

	public void excomulgar(Jugador usuario) {
		new AccionAtaque(30, "Excomulgar de cardenal", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Excomulgo al personaje\n", null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "excomulgar", null);
	}

	@Override
	protected void morirSonido() {
		this.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(), "muerte", null);
	}
}