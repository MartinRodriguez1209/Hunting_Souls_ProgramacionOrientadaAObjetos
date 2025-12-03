package io.github.primer_test.modelo.Acciones;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class AccionAturdir extends Accion {
	Integer duracionDelAturdir;

	public AccionAturdir(int valor, String nombre, Integer duracionDelAturdir) {
		super(valor, nombre);
		this.duracionDelAturdir = duracionDelAturdir;
		// TODO Auto-generated constructor stub
	}

	public Integer getDuracionDelAturdir() {
		return this.duracionDelAturdir;
	}

	@Override
	public void ejecutar(Unidad objetivo) {
		objetivo.recibirDanio(this.valor);
		objetivo.recibirAturdir(this.duracionDelAturdir);
	}

}
