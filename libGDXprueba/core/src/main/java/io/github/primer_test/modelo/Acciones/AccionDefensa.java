package io.github.primer_test.modelo.Acciones;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class AccionDefensa extends Accion {

	// el valor seria cuanto escudo te aumenta
	public AccionDefensa(int valor, String nombre) {
		super(valor, nombre);
	}

	@Override
	public void ejecutar(Unidad objetivo) {
		objetivo.recibirEscudo(this.valor);
	}

}