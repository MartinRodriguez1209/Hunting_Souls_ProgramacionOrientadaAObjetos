package io.github.primer_test.modelo.Enemigos;

import java.util.Random;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;


public class CulturistaFantasmal implements IEstadoBoss {

	private Random rand = new Random();
	
	public CulturistaFantasmal(BossFinal boss){
		boss.setAssetId("culturistafantasmal_idle.png");
		boss.borrarDecoradores();
		boss.aplicarHabilidadAbsorcion(10);
		boss.aplicarHabilidadArmadura(5);
		
	}


	@Override
	public void transicionA(BossFinal boss) {
		if (boss.getVidaActual()<150 && boss.getVidaActual()<=100) {
			boss.transicionA(new Obispo(boss));
			String mensaje = "obispo.png";
			System.out.println(mensaje);
			boss.pcs.firePropertyChange(TiposNotificaciones.transicionA.toString(),mensaje, null);
			System.out.println("El Culturista Fantasmal se transfroma en Obispo");
		}
	}

	@Override
	public Boolean estaVivo(BossFinal boss) {
		return boss.getVida() > 0;
	}

	@Override
	public String accion(BossFinal boss, Jugador jugador) {
		int actuar = rand.nextInt(2); 
		switch (actuar) {
		case 0:
			flexearMusculos(boss);
			return "Muestra sus musculos fantasmales y regenera escudo\n";
		case 1:
			golpeFantasmal(boss,jugador);
			return "Golpeo directamente a su alma";
		case 2:
			levantarPesas(boss,jugador);
			return "Uso mis pesas y golpeo al jugador\n";
		}
		return null;

	}
	
	public void flexearMusculos(BossFinal boss) {
		boss.recibirEscudo(10);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Culturista Fantasmal flexea sus biceps y regenera escudo\null",null);
	}
	
	public void golpeFantasmal(BossFinal boss, Jugador jugador) {
		new AccionAtaque(10,"Golpe fantasmal", 2).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Culturista Fantasmal golpea el alma del jugador\n", null);
		
	}
	
	public void levantarPesas(BossFinal boss, Jugador jugador) {
		new AccionAtaque(20,"Levantamiento de pesas", 2).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Culturista fantasmal golpea al jugador con una pesa\n",null);
	}

	
	
}
