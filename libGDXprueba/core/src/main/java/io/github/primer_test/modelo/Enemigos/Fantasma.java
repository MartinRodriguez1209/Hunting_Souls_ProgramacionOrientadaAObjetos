package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;

import io.github.primer_test.Recompensas.Recompensa;
import io.github.primer_test.Recompensas.TipoRecompensa;
import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class Fantasma extends Enemigov3 {

	public static final int VIDA_BASE = 70;
	public static final int ESCUDO_BASE = 0;
	public static final int PROBABILIDAD_ABSORBER_BASE = 10;
	public static final String NOMBRE = "Fantasma";
	public static final int PROBABILIDAD_ABSORBER_MAX = 80;

	public Fantasma() {
		super(VIDA_BASE, VIDA_BASE, NOMBRE, ESCUDO_BASE);
		this.aplicarHabilidadAbsorcion(PROBABILIDAD_ABSORBER_BASE);

	}

	@Override
	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {
		new AccionAtaque(20, "golpe fantasmal", 1).ejecutar(jugador);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "ataque", null);
	}

	@Override
	protected void morirSonido() {		
		this.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(), "muerte", null);
	}
	@Override
	public Recompensa getRecompensaHabilidad() {
        return new Recompensa(TipoRecompensa.HABILIDAD_ABSORCION, 15);
    }
}
