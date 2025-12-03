package io.github.primer_test.modelo.Enemigos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

import io.github.primer_test.Utils.JsonManager;
import io.github.primer_test.modelo.ENUMS.TipoAcciones;
import io.github.primer_test.modelo.ENUMS.TipoUnidad;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.ManejadorAcciones.AbsorberDecorador;
import io.github.primer_test.modelo.ManejadorAcciones.ArmaduraDecorador;
import io.github.primer_test.modelo.ManejadorAcciones.BloquearDecorador;
import io.github.primer_test.modelo.ManejadorAcciones.IAccionDecorador;

public abstract class Unidad {

	protected String nombre;
	protected Integer vidaActual;
	protected Integer vidaTotal;
	protected Integer escudo;
	protected String assetID;
	protected Integer turnosAturdido = 0; // variable que indica la cantidad de turnos aturdido
	protected int assetCols; // cant columnas del spritesheet
	protected int assetRows; // cant filas del spritesheet
	private Integer armadura = 0;
	private Integer probabilidadBloqueo = 0;
	private Integer probabilidadAbsorber = 0;
	private int dificultad = 1;
	protected boolean estoyVivo;
	protected TipoUnidad faccion;

	// Este map va a tener las estrategias linkeadas a las distintas acciones, por
	// defecto en esta clase se asignan los basicos
	protected Map<TipoAcciones, IAccionDecorador> manejadores;

	protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Unidad(Integer vidaActual, Integer vidaTotal, String nombre, Integer escudo) {

		this.vidaActual = vidaActual;
		this.vidaTotal = vidaTotal;
		this.nombre = nombre;
		this.escudo = escudo;
		JsonManager jsonManager = JsonManager.getInstanciaJsonManager();
		this.assetID = jsonManager.getPathEnemigoSprite(this.getClass().getSimpleName());
		this.assetCols = jsonManager.getRowsEnemigoSprite(this.getClass().getSimpleName());
		this.assetRows = jsonManager.getColsEnemigoSpriteInteger(this.getClass().getSimpleName());
		this.manejadores = new HashMap<TipoAcciones, IAccionDecorador>();
		this.manejadores.put(TipoAcciones.DANIO, this::ejecutarDanioBase);
		estoyVivo = true;

	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public TipoUnidad getFaccion() {
		return faccion;
	}

	private void ejecutarDanioBase(Unidad unidadObjetivo, Integer valor) {
		int escudoActual = this.getEscudo();
		int danioFinalALaVida = valor;

		if (escudoActual > 0) {
			int danioAbsorbido = Math.min(escudoActual, valor);
			danioFinalALaVida = valor - danioAbsorbido;
			this.setEscudo(escudoActual - danioAbsorbido);
		}

		if (danioFinalALaVida > 0) {
			this.setVidaActual(this.getVidaActual() - danioFinalALaVida);
		}
	}

	public void borrarDecoradores() {
		this.manejadores.put(TipoAcciones.DANIO, this::ejecutarDanioBase);
	}

	public void recibirDanio(int cantidadDanio) {
		Integer vidaAnterior = this.vidaActual;
		Integer escudoAnterior = this.escudo;
		this.manejadores.get(TipoAcciones.DANIO).ejecutarEstrategia(this, cantidadDanio);
		pcs.firePropertyChange(TiposNotificaciones.vidaDaniada.toString(), vidaAnterior, this.vidaActual);
		pcs.firePropertyChange(TiposNotificaciones.escudoDaniado.toString(), escudoAnterior, this.escudo);
	}

	public void recibirCuracion(int cantidadCuracion) {
		Integer vidaAnterior = this.vidaActual;
		setVidaActual(getVidaActual() + cantidadCuracion);
		pcs.firePropertyChange(TiposNotificaciones.vidaCurada.toString(), vidaAnterior, this.vidaActual);
	}

	public void setArmadura(Integer armadura) {
		this.armadura = armadura;
	}

	public void recibirAturdir(int cantidadTurnos) {
		Integer turnosAturdidoAnterior = this.turnosAturdido;
		this.turnosAturdido = this.turnosAturdido + cantidadTurnos;
		// pcs.firePropertyChange(TiposNotificaciones.aturdido.toString(),
		// turnosAturdidoAnterior, this.turnosAturdido);
	}

	public void recibirEscudo(int cantidadEscudo) {
		Integer escudoAnterior = this.escudo;
		this.escudo = this.escudo + cantidadEscudo;
		pcs.firePropertyChange(TiposNotificaciones.escudoAumentado.toString(), escudoAnterior, this.escudo);
	}

	protected void muerte() {
		estoyVivo = false;
		pcs.firePropertyChange(TiposNotificaciones.muerte.toString(), this, null);
	}

	public boolean estaAturdido() {
		return this.turnosAturdido > 0;
	}

	public void reducirTurnoAturdimiento() {
		if (this.turnosAturdido > 0) {
			this.turnosAturdido--;
		}
	}

	public String getAssetString() {
		return this.assetID;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getVida() {
		return this.vidaActual;
	}

	public Integer getVidaTotal() {
		return this.vidaTotal;
	}

	public void setProbabilidadAbsorber(Integer probabilidadAbsorber) {
		this.probabilidadAbsorber = probabilidadAbsorber;
	}

	public void setProbabilidadBloqueo(Integer probabilidadBloqueo) {
		this.probabilidadBloqueo = probabilidadBloqueo;
	}

	public void setVidaTotal(Integer vidaTotal) {
		this.vidaTotal = vidaTotal;
	}

	public Integer getEscudo() {
		return this.escudo;
	}

	public void setEscudo(Integer escudo) {
		this.escudo = escudo;
	}

	public Integer getColsSheet() {
		return this.assetCols;
	}

	public void setVidaActual(Integer nuevaVida) {
		this.vidaActual = Math.max(0, Math.min(this.vidaTotal, nuevaVida));
		if (this.vidaActual <= 0) {
			this.muerte();
			this.morirSonido();
		}
	}

	public Integer getVidaActual() {
		return vidaActual;
	}

	public int getAssetRows() {
		return assetRows;
	}

	public void aplicarHabilidadArmadura(Integer valorArmadura) {
		IAccionDecorador estrategiaActual = this.manejadores.get(TipoAcciones.DANIO);
		IAccionDecorador estrategiaArmaduraDecorada = new ArmaduraDecorador(estrategiaActual);
		this.manejadores.put(TipoAcciones.DANIO, estrategiaArmaduraDecorada);
		this.armadura = valorArmadura;
	}

	public void aplicarHabilidadBloqueo(Integer probabilidadBloqueo) {
		IAccionDecorador estrategiaActual = this.manejadores.get(TipoAcciones.DANIO);
		IAccionDecorador estrategiaBloquearDecorada = new BloquearDecorador(estrategiaActual);
		this.manejadores.put(TipoAcciones.DANIO, estrategiaBloquearDecorada);
		this.probabilidadBloqueo = probabilidadBloqueo;
	}

	public void aplicarHabilidadAbsorcion(Integer probabilidadAbsorber) {
		IAccionDecorador estrategiaActual = this.manejadores.get(TipoAcciones.DANIO);
		IAccionDecorador estrategiaAbsorberDecorada = new AbsorberDecorador(estrategiaActual);
		this.manejadores.put(TipoAcciones.DANIO, estrategiaAbsorberDecorada);
		this.probabilidadAbsorber = probabilidadAbsorber;
	}

	public void setDificultad(int dificultad) {
		this.dificultad = dificultad;
	}

	public int getDificultad() {
		return dificultad;
	}

	public Integer getArmadura() {
		return this.armadura;
	}

	public Integer getProbabilidadBloqueo() {
		return this.probabilidadBloqueo;
	}

	public Integer getProbabilidadAbsorber() {
		return probabilidadAbsorber;
	}

	@Override
	public String toString() {
		return this.nombre;
	}

	public Boolean estoyVivo() {
		return this.estoyVivo;
	}

	protected abstract void morirSonido();

}
