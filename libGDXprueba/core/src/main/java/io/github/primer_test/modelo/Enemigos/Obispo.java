package io.github.primer_test.modelo.Enemigos;

import java.util.Random;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class Obispo implements IEstadoBoss {

	private Random rand = new Random();


    public Obispo(BossFinal boss){
        boss.setAssetId("obispo.png");
        boss.borrarDecoradores();
        boss.aplicarHabilidadArmadura(10);
    }
    
	@Override
	public void transicionA(BossFinal boss) {
		if (boss.getVida()<100 && boss.getVida()<=50) {
			boss.transicionA(new BossOriginal(boss));
			String mensaje = "BossOriginal.png";
			System.out.println(mensaje);
			boss.pcs.firePropertyChange(TiposNotificaciones.transicionA.toString(), mensaje, null);
			System.out.println("El Obispo se transforma en la forma original del Boss");
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
			maldicion(boss,jugador);
			return "El Obispo maldice al jugador";
		case 1:
			rezar(boss);
			return "El obispo se cura a si mismo";
		case 2:
			oracion(boss,jugador);
			return "El Obispo ora e inflige daño al jugador";
		}
		return null;
	}
	
	public void maldicion(BossFinal boss,Jugador jugador) {
		new AccionAtaque(30, "Maldición", 5).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Obispo maldice al jugador y lo daña\n",null);
		
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "maldicion", null);
	}
	public void rezar(BossFinal boss) {
	boss.recibirCuracion(15);
	boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Obispo reza y se cura a sí mismo\n",null);
	
	}
	
	public void oracion(BossFinal boss, Jugador jugador) {
		new AccionAtaque(35,"Oración",10).ejecutar(jugador);
		boss.pcs.firePropertyChange(TiposNotificaciones.turnoRealizado.toString(),"El Obispo ora e inflige daño al jugador\n",null);
		boss.pcs.firePropertyChange(TiposNotificaciones.ataque.toString(), "oracion", null);
	}


}

