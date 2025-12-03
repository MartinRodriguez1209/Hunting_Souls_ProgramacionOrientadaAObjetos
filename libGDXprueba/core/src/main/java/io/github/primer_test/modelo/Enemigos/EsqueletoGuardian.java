package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;
import java.util.Random;

import io.github.primer_test.Recompensas.Recompensa;
import io.github.primer_test.Recompensas.TipoRecompensa;
import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class EsqueletoGuardian extends Enemigov3 {

	public static final int VIDA_BASE = 30;
	public static final int ESCUDO_BASE = 0;
	public static final String NOMBRE = "Esqueleto guardian";
	public static final int PROBABILIDAD_BLOQUEAR_BASE = 30;
	public static final int PROBABILIDAD_BLOQUEAR_MAX = 90;

	protected Integer probabilidadDeBloquear;


	public EsqueletoGuardian() {
		super(VIDA_BASE, VIDA_BASE, NOMBRE, ESCUDO_BASE);
		this.probabilidadDeBloquear = PROBABILIDAD_BLOQUEAR_BASE;
		this.aplicarHabilidadBloqueo(this.probabilidadDeBloquear);
	}

	@Override
	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {
		Random rnd = new Random();

		int rollAtaque = rnd.nextInt(100);

		if (rollAtaque <= 15) {
			espadazo(jugador);
		} else if (rollAtaque <= 70) {
			golpeoseo(jugador);
		} else {
			golpeconescudo(jugador);
		}

	}

	public void golpeoseo(Jugador usuario) {
		new AccionAtaque(5, "Golpe Oseo", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Golpeo con mis huesos\n", null);
		
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "golpeoseo", null);
	}

	public void golpeconescudo(Jugador Usuario) {
		new AccionAtaque(12, "Golpe con escudo", 5).ejecutar(Usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Golpeo con mi escudo\n", null);

		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(),"golpeconescudo", null);

	}

	public void espadazo(Jugador usuario) {
		new AccionAtaque(20, "Espadazo de hueso pesado", 5).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Levanto mi espada y ataco\n", null);

		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(),"espadazo", null);
	}

	@Override
	protected void morirSonido() {
		
		this.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(),"muerte", null);
	}
	
	@Override
	public Recompensa getRecompensaHabilidad() {
		return new Recompensa(TipoRecompensa.HABILIDAD_BLOQUEO, 40);
	}
}
