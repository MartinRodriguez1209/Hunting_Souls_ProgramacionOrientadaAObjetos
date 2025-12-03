package io.github.primer_test.modelo.Acciones;

import io.github.primer_test.modelo.Enemigos.Unidad;

// clase para manejar las acciones (ataques, curaciones, efectos magicos)

public abstract class Accion {

	protected Integer valor;
	protected String nombre;

	public Accion(int valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}

	public Integer getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	public abstract void ejecutar(Unidad objetivo);

}
