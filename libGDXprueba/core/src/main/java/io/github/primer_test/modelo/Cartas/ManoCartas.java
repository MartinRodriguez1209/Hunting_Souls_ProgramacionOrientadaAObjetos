package io.github.primer_test.modelo.Cartas;

import java.util.LinkedList;

public class ManoCartas {

	private final LinkedList<Carta> manoCartas;
	private final int tamanioMaximo;

	public ManoCartas(int tamanioMaximo) {
		this.manoCartas = new LinkedList<Carta>();
		this.tamanioMaximo = tamanioMaximo;
	}

	public Boolean agregarCarta(Carta carta) {
		if (manoCartas.size() < tamanioMaximo) {
			manoCartas.add(carta);
			return true;
		} else
			return false;
	}

	public void removerCarta(Carta carta) {
		manoCartas.remove(carta);

	}

	public LinkedList<Carta> getManoCartas() {
		return manoCartas;
	}

	public int getSize() {
		return manoCartas.size();
	}

}
