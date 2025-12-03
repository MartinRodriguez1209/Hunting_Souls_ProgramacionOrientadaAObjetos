package io.github.primer_test.modelo.Cartas;

import io.github.primer_test.modelo.Acciones.Accion;
import io.github.primer_test.modelo.Acciones.AccionCuracion;
import io.github.primer_test.modelo.ENUMS.TipoObjetivos;

public class CartaCuracion extends Carta {

	private Integer valorCuracion;

	public CartaCuracion(String nombre, String descripcion, Integer costoMana, String imagenPath,
			Integer valorCuracion) {
		super(nombre, descripcion, costoMana, imagenPath);
		this.valorCuracion = valorCuracion;
		this.objetivo = TipoObjetivos.JUGADOR;
	}

	@Override
	public Accion realizarAccion() {
		pcs.firePropertyChange("cartaUsada", this, null);
		return new AccionCuracion(valorCuracion, this.nombre);
	}

	@Override
	public Integer getCuracion() {
		return valorCuracion;
	}

	@Override
	public String getTipoCarta() {
		return "CURACION";
	}

	@Override
	public Integer getAtaque() {
		return null;
	}

	@Override
	public Integer getProbabilidadCritico() {
		return null;
	}

	@Override
	public Integer getDefensa() {
		return null;
	}
}
