package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;
import java.util.Random;

import io.github.primer_test.Recompensas.Recompensa;
import io.github.primer_test.Recompensas.TipoRecompensa;
import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.Acciones.AccionAturdir;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class CulturistaEncapuchado extends Enemigov3 {

	public static final int VIDA_BASE = 20;
	public static final int ESCUDO_BASE = 0;
	public static final int PROBABILIDAD_BLOQUEAR_BASE = 20;
	public static final String NOMBRE = "Culturista";
	public static final int PROBABILIDAD_BLOQUEAR_MAX = 80;
	public static final int ARMADURA_BASE = 5;
	public static final int PROBABILIDAD_ATURDIR_BASE = 20;

	protected Integer probabilidadAturdir;
	private Integer armadura;
	private Integer probabilidadDeBloquear;

	public CulturistaEncapuchado() {
		super(VIDA_BASE, VIDA_BASE, "Culturista encapuchado", ESCUDO_BASE);
		this.probabilidadAturdir = PROBABILIDAD_ATURDIR_BASE;
		this.armadura = ARMADURA_BASE;
		this.probabilidadDeBloquear = PROBABILIDAD_BLOQUEAR_BASE;
		this.aplicarHabilidadArmadura(this.armadura);
		this.aplicarHabilidadBloqueo(this.probabilidadDeBloquear);
	}

	@Override
	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {
		Random rnd = new Random();

		boolean intentarAturdir = rnd.nextInt(100) < this.probabilidadAturdir;
		if (intentarAturdir) {
			embestida(jugador);
			return;
		}

		int rollAtaque = rnd.nextInt(100) + 1;

		if (rollAtaque <= 15) {
			aplastar(jugador);
		} else if (rollAtaque <= 70) {
			levantarPesas(jugador);
		} else {
			flexearMusculos();
		}
	}

	public void levantarPesas(Jugador usuario) {
		new AccionAtaque(10, "Levantamiento de pesas", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),
				"Uso mis pesas y golpeo al enemigo\n", null);

		//SonidoData datosSonido = SfxDataManager.getInstancia().getSfxData(this.getClass().getSimpleName(), "levantarPesas");
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "levantarPesas",null);
	}

	public void flexearMusculos() {
		this.recibirEscudo(20);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),
				"Muestra sus musculos y regenera escudo\n", null);

		
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(),  "flexearMusculos",null);
	}

	public void embestida(Jugador usuario) {
		new AccionAturdir(20, "Embestida", 1).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Uso mi embestida\n", null);

		
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "embestida", null);

	}

	public void aplastar(Jugador usuario) {
		new AccionAtaque(25, "Aplastar", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Aplasto al personaje\n", null);

		
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "aplastar",null);
	}

	@Override
	protected void morirSonido() {
		
		this.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(), "muerte",null);
	}
	@Override
	public Recompensa getRecompensaHabilidad() {
		return new Recompensa(TipoRecompensa.HABILIDAD_BLOQUEO, 20);
	}
}
