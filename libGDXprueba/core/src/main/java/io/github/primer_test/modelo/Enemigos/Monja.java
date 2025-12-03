package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.Acciones.AccionAturdir;
import io.github.primer_test.modelo.Acciones.AccionCuracion;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class Monja extends Enemigov3 {

	public static final int VIDA_BASE = 40;
	public static final int ESCUDO_BASE = 40;
	public static final String NOMBRE = "Monja";
	public static final int PROBABILIDAD_CURAR_BASE = 50;
	public static final int PROBABILIDAD_CURAR_MAX = 90;
	public int probabilidadCurar;

	public Monja() {
		super(VIDA_BASE, VIDA_BASE, NOMBRE, ESCUDO_BASE);
		this.probabilidadCurar = 50;
	}

	@Override
	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {
		Random rnd = new Random();
		
		ArrayList<Enemigov3> aliadosyMonja = enemigosEnCombate.stream() 
				.filter(e -> e.estoyVivo() && e.getVidaActual() < e.getVidaTotal())
	            .collect(Collectors.toCollection(ArrayList::new));

		boolean intentarCurar = rnd.nextInt(100) < this.probabilidadCurar;

		if (intentarCurar && !aliadosyMonja.isEmpty()) {
			Enemigov3 enemigoConMenosVida = aliadosyMonja.get(0);
			
			for (Enemigov3 enemigo : aliadosyMonja) {
				if (enemigo.getVidaActual() < enemigoConMenosVida.getVidaActual()) {
					enemigoConMenosVida = enemigo;
				}
			}
			curar(enemigoConMenosVida);
		}

		int rollAtaque = rnd.nextInt(100);

		if (rollAtaque <= 10) {
			gritosagrado(jugador);
		} else if (rollAtaque <= 70) {
			bibliazo(jugador);
		} else {
			rezar();
		}
	}

	public void rezar() {
		this.recibirCuracion(15);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Me curo a mi misma\n", null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "rezar", null);
	}

	public void bibliazo(Jugador usuario) {
		new AccionAtaque(20, "Bibliazo", 1).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Golpeo con la biblia\n", null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "bibliazo", null);
	}

	public void curar(Enemigov3 enemigo) {
		new AccionCuracion(15, "curacion de monja").ejecutar(enemigo);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Curo a mi aliado con menos vida\n",
				null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "curar", null);
	}

	public void setProbabilidadCurar(int probabilidadCurar) {
		this.probabilidadCurar = probabilidadCurar;
	}

	public void gritosagrado(Jugador usuario) {
		new AccionAturdir(0, "Grito Sagrado", 2).ejecutar(usuario);
		this.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(), "Grito y aturdo al jugador\n", null);
		this.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "gritosagrado", null);
	}
	
	@Override
	protected void morirSonido() {
		this.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(), "muerte", null);
	}

}