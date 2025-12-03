package io.github.primer_test.modelo.Enemigos;

import java.util.Random;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class ReyEsqueleto implements IEstadoBoss {

	private Random rand = new Random();


	public ReyEsqueleto(BossFinal boss) {
		boss.setAssetId("reyesqueleto_idle.png");
		boss.borrarDecoradores();
		boss.aplicarHabilidadBloqueo(10);

	}

	@Override
	public void transicionA(BossFinal boss) {
		if (boss.getVidaActual() < 200 && boss.getVidaActual() <= 150) {
			boss.transicionA(new CulturistaFantasmal(boss));
			String mensaje = "culturistafantasmal_idle.png";
			System.out.println(mensaje);
			boss.pcs.firePropertyChange(TiposNotificaciones.transicionA.toString(), mensaje, null);
			// Cambia de
			// estado si
			// pierde 50 de
			// vida
			// Cuando la vida sea por ejemplo 101 y cambie de estado a los 100, el ataque
			// infligirá solo 1 de daño.
			System.out.println("El Rey Esqueleto se transforma en Culturista Fantasmal");
		}
	}

	@Override
	public Boolean estaVivo(BossFinal boss) {
		return boss.getVidaActual() > 0;
	}

	@Override
	public String accion(BossFinal boss, Jugador jugador) {
		int actuar = rand.nextInt(1); // atacar o defender
		switch (actuar) {
		case 0:
			golpeoseo(boss,jugador);
			return "El Rey Esqueleto arroja un hueso que impacta al jugador";
		case 1:
			espadazo(boss,jugador);
			return "El Rey Esqueleto usa su enorme espada y corta al jugador";
		}
		return null;

	}

	public void golpeoseo(BossFinal boss,Jugador jugador) {
		new AccionAtaque(15, "Golpe Oseo", 2).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Rey Esqueleto arroja un hueso que impacta al jugador\n",null);
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "golpeoseo", null);
	}

	public void espadazo(BossFinal boss, Jugador jugador) {
		new AccionAtaque(30, "Espadazo de hueso pesado", 5).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Rey Esqueleto usa su enorme espada y corta al jugador\n",null);
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(),"espadazo", null);
	}



}
