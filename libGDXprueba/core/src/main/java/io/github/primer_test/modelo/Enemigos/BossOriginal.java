package io.github.primer_test.modelo.Enemigos;

import java.util.Random;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;
import io.github.primer_test.modelo.Acciones.AccionCuracion;


public class BossOriginal implements IEstadoBoss {

	private Random rand = new Random();
	

	public BossOriginal(BossFinal boss) {
		boss.setAssetId("BossOriginal.png");
		boss.borrarDecoradores();
		boss.aplicarHabilidadAbsorcion(10);
		boss.aplicarHabilidadArmadura(5);
		boss.aplicarHabilidadBloqueo(5);
	}

	@Override
	public void transicionA(BossFinal boss) {
		if (boss.getVida() < 50 && boss.getVida() <= 0) {
			boss.estaVivo(boss);
			boss.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(),"muerte",null);
			// no cambia, muere.
		}
	}

	@Override
	public Boolean estaVivo(BossFinal boss) {
		return boss.getVida() > 0;
	}

	@Override
	public String accion(BossFinal boss, Jugador jugador) {
		int actuar = rand.nextInt(2); // atacar o defender
		switch (actuar) {
		case 0:
			daleRo(boss,jugador);
			return "El Boss grita DALE RO DALE RO";
		case 1:
			garrasDemoniacas(boss,jugador);
			return "El Boss usa sus garras y daña al jugador";
		case 2:
			grunido(boss);
			return "El Boss gruñe y recupera salud";
		}
		return null;

	}
	public void daleRo(BossFinal boss, Jugador jugador) {
		new AccionAtaque(25,"Dale Ro",5).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Boss grita DALE RO DALE RO\n", null);
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "daleRo", null);
	}
	
	public void garrasDemoniacas(BossFinal boss, Jugador jugador) {
		new AccionAtaque(40,"Garras Demoniacas",5).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Boss usa sus garras y daña al jugador\n",null);
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "garrasDemoniacas", null);
	}
	
	public void grunido(BossFinal boss) {
		boss.recibirCuracion(25);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Boss gruñe y recupera salud", null);
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "grunido", null);
	}
	

}
