package io.github.primer_test.modelo.Cartas;

import io.github.primer_test.modelo.Acciones.Accion;
import io.github.primer_test.modelo.Acciones.AccionMatar;

public class CartaMatar extends Carta {

	public CartaMatar(String nombre, String descripcion, Integer costoMana, String imagenPath) {
		super(nombre, descripcion, costoMana, imagenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Accion realizarAccion() {
		pcs.firePropertyChange("cartaUsada", this, null);
		return new AccionMatar(1, this.nombre);
	}

}
