package io.github.primer_test.modelo.Enemigos;

import java.util.ArrayList;

import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class BossFinal extends Enemigov3 {

	private IEstadoBoss estadoActualBoss;
	private String assetID;

	public BossFinal() {
		super(200, 200, "Boss Final", 0);
		this.estadoActualBoss = new ReyEsqueleto(this);
	}
	@Override
	public void turno(Jugador jugador, ArrayList<Enemigov3> enemigosEnCombate) {
		if (this.estaVivo(null)) {
			this.estadoActualBoss.transicionA(this);
			this.estadoActualBoss.accion(this, jugador);

		} else {
			System.out.println("El Boss Final ha sido derrotado");
			pcs.firePropertyChange("muerte", null, this);
		}
	}

	public Boolean estaVivo(BossFinal boss) {
		return this.vidaActual > 0;
	}

	public void transicionA(IEstadoBoss nuevoEstado) {
		this.estadoActualBoss = nuevoEstado;

	}

	public void accion(BossFinal boss, Jugador jugador) {
		estadoActualBoss.accion(this, jugador);
	}

	public void setVidaActual(int vidaActual) {
		this.vidaActual = vidaActual;
	}

	public IEstadoBoss getEstadoActualBoss() {
		return estadoActualBoss;
	}

	public void setEstadoActualBoss(IEstadoBoss estadoActualBoss) {
		this.estadoActualBoss = estadoActualBoss;
	}

	public void setAssetId(String asset) {
		this.assetID = asset;
	}

	public String getAssetId(String asset) {
		return asset;
	}
	@Override
	protected void morirSonido() {
		this.pcs.firePropertyChange(TiposNotificaciones.muerte.toString(), "muerte", null);
		
	}

}
