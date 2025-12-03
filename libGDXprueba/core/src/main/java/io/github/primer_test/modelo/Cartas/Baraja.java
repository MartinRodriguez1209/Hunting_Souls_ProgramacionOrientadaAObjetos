package io.github.primer_test.modelo.Cartas;

import java.util.LinkedList;
import java.util.Random;

public class Baraja {

	LinkedList<Carta> baraja = new LinkedList<Carta>();

	public Baraja() {

	}

	// saco 5 cartas de mi baraja y las devuelvo, las cartas se eliminan de mi
	// baraja
	public LinkedList<Carta> getManoDeCartas() {
		LinkedList<Carta> mano = new LinkedList<Carta>();
		Random rnd = new Random();
		for (int i = 0; i < 5; i++) {
			mano.add(baraja.remove(rnd.nextInt(baraja.size() - 1)));

		}
		return mano;
	}

	public Integer getCantidadDeCartas() {
		return baraja.size();
	}

	public LinkedList<Carta> robarCartas() {
		LinkedList<Carta> cartas = new LinkedList<Carta>();
		Random rnd = new Random();
		for (int i = 0; i < 2; i++) {
			cartas.add(baraja.remove(rnd.nextInt(baraja.size() - 1)));
		}
		return cartas;
	}

	public Carta robarCarta() {
		Random rnd = new Random();
		return baraja.remove(rnd.nextInt(baraja.size() - 1));
	}

	public void agregarCarta(Carta carta) {
		baraja.add(carta);
	}

}
