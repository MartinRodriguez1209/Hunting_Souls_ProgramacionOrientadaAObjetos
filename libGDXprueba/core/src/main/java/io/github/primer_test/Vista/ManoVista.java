package io.github.primer_test.Vista;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.primer_test.modelo.Cartas.Carta;
import io.github.primer_test.modelo.Cartas.ManoCartas;
import io.github.primer_test.modelo.Partida.MiJuegoPrincipal;
import io.github.primer_test.modelo.Usuario.AdministradorTurnoJugador;
import io.github.primer_test.modelo.Usuario.Jugador;

public class ManoVista extends Group {

	private final ManoCartas manoModelo;
	private final AdministradorTurnoJugador administrador;
	private final Jugador jugador;
	private final MiJuegoPrincipal mainPelea;
	private final Sound efectoSonidoCarta;

	public ManoVista(ManoCartas manoModelo, AdministradorTurnoJugador admin, Jugador jugador,
			MiJuegoPrincipal mainPelea) {
		this.manoModelo = manoModelo;
		this.administrador = admin;
		this.jugador = jugador;
		this.mainPelea = mainPelea;

		efectoSonidoCarta = Gdx.audio.newSound(Gdx.files.internal("CartaClick.mp3"));

		actualizarVista();
	}

	public void actualizarVista() {
		this.clearChildren();

		LinkedList<Carta> cartas = manoModelo.getManoCartas();
		int iterador = 0;

		for (final Carta cartaActual : cartas) {
			final CartaVista cartaLocal = new CartaVista();
			cartaLocal.setCarta(cartaActual);

			cartaLocal.addListener(new ClickListener() {
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					cartaLocal.setZIndex(100); // Pone la carta al frente
					cartaLocal.sizeBy(20, 20);
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					cartaLocal.sizeBy(-20, -20);
				}

				@Override
				public void clicked(InputEvent event, float x, float y) {
					// Verificar si el juego está pausado antes de ejecutar
					if (mainPelea.isJuegoEnPausa()) {
						return; // No procesar si está pausado
					}
					efectoSonidoCarta.play();
					administrador.seleccionarCarta(cartaActual, jugador);
				}
			});

			this.addActor(cartaLocal);
			cartaLocal.setPosition(iterador * 120, 10);
			iterador++;
		}
	}
}