package io.github.primer_test.modelo.Cartas;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import io.github.primer_test.modelo.Acciones.Accion;
import io.github.primer_test.modelo.ENUMS.TipoObjetivos;

public abstract class Carta {

	protected Integer id;
	protected String nombre;
	protected String descripcion;
	protected Integer costoMana;
	protected String imagenPath; // aca va la ruta de la imagen
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	protected TipoObjetivos objetivo;

	public Carta(String nombre, String descripcion, Integer costoMana, String imagenPath) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costoMana = costoMana;
		this.imagenPath = imagenPath;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getCostoMana() {
		return costoMana;
	}

	public String getImagenPath() {
		return imagenPath;
	}

	public TipoObjetivos getObjetivo() {
		return objetivo;
	}

	@Override
	public String toString() {
		return this.nombre + ": " + this.descripcion;
	}

	public abstract Accion realizarAccion();

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public abstract String getTipoCarta();

	public abstract Integer getAtaque();

	public abstract Integer getProbabilidadCritico();

	public abstract Integer getCuracion();

	public abstract Integer getDefensa();

}
