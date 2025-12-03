package io.github.primer_test.modelo.Enemigos.Logica;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Usuario.Jugador;

public class AdministradorTurnoEnemigos {

	private final ArrayList<Enemigov3> enemigos;
	private final Jugador jugador;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public AdministradorTurnoEnemigos(ArrayList<Enemigov3> enemigos, Jugador jugador) {
		this.enemigos = enemigos;
		this.jugador = jugador;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void ejecutarTurnos() {
		float cumulativeDelay = 1.0f;
		final float DELAY_PER_ENEMY = 2.0f;

		for (final Enemigov3 enemigo : enemigos) {
			if (enemigo.estoyVivo()) {
				Timer.schedule(new Timer.Task() {
					@Override
					public void run() {

						enemigo.turno(jugador, enemigos);

						Gdx.app.postRunnable(() -> {
							pcs.firePropertyChange(TiposNotificaciones.turnoUnEnemigoRealizado.toString(), enemigo,
									null);
						});
					}
				}, cumulativeDelay);

				cumulativeDelay += DELAY_PER_ENEMY;
			}
		}

		Timer.schedule(new Timer.Task() {
			@Override
			public void run() {
				Gdx.app.postRunnable(() -> {
					pcs.firePropertyChange(TiposNotificaciones.turnoEnemigosTerminado.toString(), null, null);
				});
			}
		}, cumulativeDelay);
	}
}
