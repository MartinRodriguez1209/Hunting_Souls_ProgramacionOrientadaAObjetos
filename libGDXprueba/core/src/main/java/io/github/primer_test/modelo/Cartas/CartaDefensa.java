package io.github.primer_test.modelo.Cartas;

import io.github.primer_test.modelo.Acciones.Accion;
import io.github.primer_test.modelo.Acciones.AccionDefensa;
import io.github.primer_test.modelo.ENUMS.TipoObjetivos;

public class CartaDefensa extends Carta {

	private Integer defensa;

	public CartaDefensa(String nombre, String descripcion, Integer costoMana, String imagenPath, Integer defensa) {
		super(nombre, descripcion, costoMana, imagenPath);
		this.defensa = defensa;
		this.objetivo = TipoObjetivos.JUGADOR;
	}

	@Override
	public Accion realizarAccion() {
		pcs.firePropertyChange("cartaUsada", this, null);
		return new AccionDefensa(defensa, this.nombre);
	}

	@Override
	public Integer getDefensa() {
		return defensa;
	}

	@Override
	public Integer getCuracion() {
		return null;
	}

	@Override
	public String getTipoCarta() {
		return "DEFENSA";
	}

	@Override
	public Integer getAtaque() {
		return null;
	}

	@Override
	public Integer getProbabilidadCritico() {
		return null;
	}

}
