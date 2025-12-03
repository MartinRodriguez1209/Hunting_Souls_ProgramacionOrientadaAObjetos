package io.github.primer_test.modelo.Cartas;

import io.github.primer_test.modelo.Acciones.Accion;
import io.github.primer_test.modelo.Acciones.AccionAtaque;
import io.github.primer_test.modelo.ENUMS.TipoObjetivos;

public class CartaAtaque extends Carta {

	private Integer valorAtaque;
	private Integer probabilidadCritico;

	public CartaAtaque(String nombre, String descripcion, Integer costoMana, String imagenPath, Integer valorAtaque,
			Integer probabilidadCritico) {
		super(nombre, descripcion, costoMana, imagenPath);
		this.valorAtaque = valorAtaque;
		this.probabilidadCritico = probabilidadCritico;
		this.objetivo = TipoObjetivos.ENEMIGO;
	}

	@Override
	public Accion realizarAccion() {
		pcs.firePropertyChange("cartaUsada", this, null);
		return new AccionAtaque(valorAtaque, this.nombre, probabilidadCritico);
	}

	public Integer getAtaque() {
		return valorAtaque;
	}

	@Override
	public String getTipoCarta() {
		return "ATAQUE";
	}

	@Override
	public Integer getProbabilidadCritico() {
		return probabilidadCritico;
	}

	@Override
	public Integer getCuracion() {
		return null;
	}

	@Override
	public Integer getDefensa() {
		return null;
	}
}