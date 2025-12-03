package io.github.primer_test.modelo.Acciones;

import java.util.Random;

import io.github.primer_test.modelo.Enemigos.Unidad;

//clase para representar los ataques

public class AccionAtaque extends Accion {

	public AccionAtaque(Integer valor, String nombre, int critico) {
		super(valor, nombre);
		if (new Random().nextInt(10) <= critico) {
			Double x = valor + valor * 0.2;
			valor = x.intValue();
		}
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public void ejecutar(Unidad objetivo) {
		objetivo.recibirDanio(this.valor);
	}

}
