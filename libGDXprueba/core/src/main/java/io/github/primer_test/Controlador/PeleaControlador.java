package io.github.primer_test.Controlador;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.primer_test.Vista.ManoVista;
import io.github.primer_test.Vista.PeleaVista;
import io.github.primer_test.Vista.UnidadVista;
import io.github.primer_test.modelo.Cartas.Carta;
import io.github.primer_test.modelo.Cartas.ManoCartas;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Enemigos.Unidad;
import io.github.primer_test.modelo.Enemigos.Logica.AdministradorTurnoEnemigos;
import io.github.primer_test.modelo.Partida.MiJuegoPrincipal;
import io.github.primer_test.modelo.Partida.ResultadoPeleaListener;
import io.github.primer_test.modelo.Usuario.AdministradorTurnoJugador;
import io.github.primer_test.modelo.Usuario.Jugador;

public class PeleaControlador extends InputAdapter implements PropertyChangeListener {

	protected final PeleaVista peleaView;
	private final ArrayList<Enemigov3> enemigos;
	protected final Jugador jugador;
	protected final AdministradorTurnoJugador administradorTurnoJugador;
	protected final AdministradorTurnoEnemigos administradorEnemigos;
	private MiJuegoPrincipal mainPelea; // Referencia al main para pausar
	private final ManoVista vistaDeLaMano;
	private ArrayList<Enemigov3> enemigosDerrrotados;
	private ResultadoPeleaListener listenerResultado;

	// controlador de la pelea actualmente recibe la lista de enemigos y el jugador
	public PeleaControlador(PeleaVista peleaView, ArrayList<Enemigov3> enemigos, Jugador jugador,
			MiJuegoPrincipal mainPelea, ResultadoPeleaListener listenerResultado) {

		this.peleaView = peleaView;
		this.enemigos = enemigos;
		this.jugador = jugador;
		this.mainPelea = mainPelea;
		this.listenerResultado = listenerResultado;
		this.enemigosDerrrotados = new ArrayList<>();
		this.administradorTurnoJugador = new AdministradorTurnoJugador();
		this.administradorTurnoJugador.addPropertyChangeListener(this);
		this.administradorEnemigos = new AdministradorTurnoEnemigos(enemigos, jugador);
		administradorEnemigos.addPropertyChangeListener(this);
		this.jugador.addPropertyChangeListener(this);

		for (Enemigov3 enemigov3 : enemigos) {
			enemigov3.addPropertyChangeListener(this);
		}

		peleaView.getBotonTerminarTurno().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (mainPelea.isJuegoEnPausa()) {
					return;
				}
				administradorTurnoJugador.terminarTurno(jugador);
				finalizarTurnoJugador();
			}
		});

		peleaView.getBotonPausa().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mainPelea.pausarJuego();
			}
		});

		// busco la lista de UnidadVista de peleaVista para poder crear los listeners
		ArrayList<UnidadVista> vistasDeEnemigos = peleaView.getVistasEnemigos();
		for (UnidadVista unidadVista : vistasDeEnemigos) {
			unidadVista.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					if (mainPelea.isJuegoEnPausa())
						return;
					Enemigov3 enemigoModelo = (Enemigov3) unidadVista.getEnemigoModelo();
					administradorTurnoJugador.seleccionarUnidadObjetivo(enemigoModelo, jugador);
				}
			});
		}

		ManoCartas manoDelJugador = jugador.getManoCartas();
		vistaDeLaMano = new ManoVista(manoDelJugador, this.administradorTurnoJugador, jugador, mainPelea);
		this.peleaView.getCartasTable().addActor(vistaDeLaMano);

		// aca creo el listener del jugador
		UnidadVista jugadorVista = peleaView.getJugadorVista();

		jugadorVista.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (mainPelea.isJuegoEnPausa()) {
					return;
				}
				administradorTurnoJugador.seleccionarUnidadObjetivo(jugador, jugador);
			}
		});

		administradorTurnoJugador.iniciarPelea(jugador);
		vistaDeLaMano.actualizarVista();
	}

	public void dibujar() {
		// Pasar el estado de pausa directamente al método dibujar
		peleaView.dibujar(mainPelea.isJuegoEnPausa());
	}

	public Jugador getJugador() {
		return jugador;
	}

	protected void iniciarTurnoJugador() {
		if (mainPelea.isJuegoEnPausa()) {
			return;
		}
		if (administradorTurnoJugador.iniciarTurno(jugador)) {
			vistaDeLaMano.actualizarVista();
			peleaView.habilitarInteraccionJugador(true);
			peleaView.getCajaTextoTable().appendText(" Turno del Jugador\n");
		} else {
			finalizarTurnoJugador();
		}
	}

	private void finalizarTurnoJugador() {
		if (mainPelea.isJuegoEnPausa()) {
			return;
		}

		administradorTurnoJugador.terminarTurno(jugador);
		peleaView.habilitarInteraccionJugador(false);
		peleaView.getCajaTextoTable().appendText("Fin del turno del Jugador\n");
		administradorEnemigos.ejecutarTurnos();

	}

	public void casoVictoria() {
		peleaView.getCajaTextoTable().appendText("\n¡VICTORIA!\nHas derrotado a todos los enemigos.");
		peleaView.habilitarInteraccionJugador(false); // Desactiva botones y cartas
		listenerResultado.onPeleaTerminada(true, this.enemigosDerrrotados);
	}

	public void casoDerrota() {
		peleaView.getCajaTextoTable().appendText("\n¡DERROTA!\nHas caído en combate.");
		peleaView.habilitarInteraccionJugador(false); // Desactiva botones y cartas. Agregar botones para reiniciar y
		listenerResultado.onPeleaTerminada(false, this.enemigosDerrrotados);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propiedad = evt.getPropertyName();
		Object valorViejo = evt.getOldValue();
		Object valorNuevo = evt.getNewValue();

		try {
			TiposNotificaciones notificacion = TiposNotificaciones.valueOf(propiedad);

			switch (notificacion) {

			case manaInsuficiente:
				peleaView.getCajaTextoTable().appendText("¡ERROR! " + valorNuevo + "\n");
				break;

			case turnoRealizado:
				peleaView.getCajaTextoTable().appendText((String) valorViejo);
				break;

			case muerte:
				Unidad unidadMuerta = (Unidad) valorViejo;
				if (unidadMuerta == this.jugador) {
					casoDerrota();
				} else {
					if (unidadMuerta instanceof Enemigov3) {
						enemigosDerrrotados.add((Enemigov3) unidadMuerta);
					}
					enemigos.remove(unidadMuerta);
					if (enemigos.isEmpty()) {
						casoVictoria();
					}
				}
				break;
			case turnoUnEnemigoRealizado:
				Enemigov3 enemigoQueActuo = (Enemigov3) evt.getOldValue();
				peleaView.getCajaTextoTable().appendText("Turno de: " + enemigoQueActuo.getNombre() + "\n");
				break;

			case turnoEnemigosTerminado:

				iniciarTurnoJugador();
				break;
			case cartaElegida:
				Carta cartaSeleccionada = (Carta) valorNuevo;
				peleaView.getCajaTextoTable().appendText("Carta elegida: " + cartaSeleccionada.getNombre() + ".\n");
				break;
			case errorObjetivo:
				peleaView.getCajaTextoTable()
						.appendText("¡ERROR! " + propiedad.substring(6) + ": " + valorNuevo + "\n");
				break;
			}

		} catch (IllegalArgumentException e) {
			System.out.println("Evento no reconocido o no manejado: " + propiedad);
		}
	}
}