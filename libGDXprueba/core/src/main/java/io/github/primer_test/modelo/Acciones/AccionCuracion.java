package io.github.primer_test.modelo.Acciones;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class AccionCuracion extends Accion {

	public AccionCuracion(int valor, String nombre) {
		super(valor, nombre);

	}

	@Override
	public String toString() {
		return "Se realiza una curacion de " + this.valor + " puntos de vida";
	}

	@Override
	public void ejecutar(Unidad objetivo) {
		objetivo.recibirCuracion(this.valor);
	}
}
