package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;
import java.util.Random;

import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.Acciones.AccionDefensa;
import io.github.primer_test.modelo.Usuario.Jugador;

public class Sacerdote extends Enemigov3 {
	public Sacerdote(Integer vidaActual, Integer vidaTotal, String nombre, Integer escudo,
			Integer probabilidadRobarVida, Integer probabilidadAturdir) {
		super(vidaActual, vidaTotal, nombre, escudo);
		// TODO Auto-generated constructor stub
	}

	public void aguabendita(Jugador usuario) {
		System.out.println("El enemigo te tiró agua bendita, infligiendote daño normal");
		new AccionAtaque(10, "Agua bendita", 3).ejecutar(usuario);
	}

	public void exorcismo(Jugador usuario) {
		System.out.println("El enemigo te exorcizó, infligiendote mucho daño");
		new AccionAtaque(20, "Exorcismo", 5).ejecutar(usuario);
	}

	public void maldicion(Jugador usuario) {
		System.out.println("El enemigo te lanzó una maldición que te va bajando la vida cada turno");
		new AccionAtaque(5, "Lanzamiento de maldición", 1).ejecutar(usuario);
	}

	public void rezar(Jugador usuario) {
		System.out.println("El enemigo rezó y subió su bloqueo");

		new AccionDefensa(20, "Rezar").ejecutar(usuario);
	}

	public void curarAliado(Jugador aliado) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'curarAliado'");
	}

	/*
	 * public AccionAtaque confesion(Jugador usuario) { // TODO Auto-generated
	 * method stub throw new
	 * UnsupportedOperationException("Unimplemented method 'confesion'"); }
	 */

	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {

		Random rnd = new Random();
		switch (rnd.nextInt(7) + 1) {
		case 1:
			aguabendita(jugador);
			break;

		case 2:
			exorcismo(jugador);
			break;

		case 3:
			maldicion(jugador);
			break;
		case 4:
			rezar(jugador);
			break;
		case 5:
			/*
			 * if (!enemigosEnCombate.isEmpty()) { Random rand = new Random(); int index =
			 * rand.nextInt(enemigosEnCombate.size()); Enemigov3 aliado =
			 * enemigosEnCombate.get(index); curarAliado(aliado); } else {
			 * System.out.println("No hay aliados para curar."); aguabendita(jugador); //
			 * Acción alternativa si no hay aliados } break;
			 */
		}
	}
}
