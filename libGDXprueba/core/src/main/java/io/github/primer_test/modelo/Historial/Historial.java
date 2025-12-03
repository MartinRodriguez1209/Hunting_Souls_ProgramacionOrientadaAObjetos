package io.github.primer_test.modelo.Historial;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import io.github.primer_test.cartasbd.HistorialDAOimpl;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Usuario.Jugador;

public class Historial implements PropertyChangeListener {
	private Integer id;
	private String nombreJugador;
	private Integer danioRealizado;

	private Integer cantidadCartasUsadas;
	private Integer cantidadEnemigosMuertos;
	private Integer curacionRecibida;
	private Integer danioRecibido;
	private Jugador jugadorEscuchado;
	private String resultado;
	private ArrayList<Enemigov3> enemigosEscuchado;

	public Historial() {
		this.danioRealizado = 0;
		this.cantidadCartasUsadas = 0;
		this.cantidadEnemigosMuertos = 0;
		this.curacionRecibida = 0;
		this.danioRecibido = 0;
	}

	public void reiniciarHistorial() {
		this.danioRealizado = 0;
		this.cantidadCartasUsadas = 0;
		this.cantidadEnemigosMuertos = 0;
		this.curacionRecibida = 0;
		this.danioRecibido = 0;
	}

	public void guardarHistorial() {
		HistorialDAOimpl historialDAO = new HistorialDAOimpl();
		historialDAO.create(this);

	}

	public void subscrbirAPelea(ArrayList<Enemigov3> enemigos) {
		this.enemigosEscuchado = enemigos;
		for (Enemigov3 enemigo : this.enemigosEscuchado) {
			enemigo.addPropertyChangeListener(this);
		}
	}

	public void subscribirJugador(Jugador jugador) {
		this.jugadorEscuchado = jugador;
		this.nombreJugador = jugador.getNombre();
		this.jugadorEscuchado.addPropertyChangeListener(this);
	}

	public void desubscribirPelea() {
		this.jugadorEscuchado.removePropertyChangeListener(this);
		for (Enemigov3 enemigo : enemigosEscuchado) {
			enemigo.removePropertyChangeListener(this);
		}
	}

	public void setVictoria() {
		this.resultado = "VICTORIA";
	}

	public void setDerrota() {
		this.resultado = "DERROTA";
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String evento = evt.getPropertyName();
		TiposNotificaciones notificacion = TiposNotificaciones.valueOf(evento);
		Object fuenteDeNoti = evt.getSource();
		switch (notificacion) {
		case escudoDaniado:
		case vidaDaniada:
			int valorAnterior = (Integer) evt.getOldValue();
			int valorNuevo = (Integer) evt.getNewValue();
			int danioTotal = (valorAnterior - valorNuevo);
			if (fuenteDeNoti instanceof Jugador) {
				this.danioRecibido += danioTotal;
				System.out.println("HISTORIAL DAÑO RECIBIDO: " + this.danioRealizado);
			} else if (fuenteDeNoti instanceof Enemigov3) {
				this.danioRealizado += danioTotal;
				System.out.println("HISTORIAL DAÑO REALIZADO: " + this.danioRealizado);
			}
			break;
		case muerte:
			Object unidadMuertaObj = evt.getOldValue();
			if (unidadMuertaObj instanceof Enemigov3) {
				this.cantidadEnemigosMuertos++;
				Enemigov3 enemigoMuerto = (Enemigov3) unidadMuertaObj;
				enemigoMuerto.removePropertyChangeListener(this);
				System.out.println("Historial: Desuscrito del enemigo muerto: " + enemigoMuerto.getNombre());
			} else if (unidadMuertaObj instanceof Jugador) {
				Jugador jugadorMuerto = (Jugador) unidadMuertaObj;
				jugadorMuerto.removePropertyChangeListener(this);
			}
			break;
		case cartaUsada:
			this.cantidadCartasUsadas++;
			break;
		case vidaCurada:
			if (fuenteDeNoti instanceof Jugador) {
				int vidaAnterior = (Integer) evt.getOldValue();
				int vidaNueva = (Integer) evt.getNewValue();
				int curacionTotal = (vidaNueva - vidaAnterior);
				this.curacionRecibida += curacionTotal;
			}
		default:
			break;
		}
	}

	public Integer getDanioRealizado() {
		return danioRealizado;
	}

	public Integer getCantidadCartasUsadas() {
		return cantidadCartasUsadas;
	}

	public Integer getCantidadEnemigosMuertos() {
		return cantidadEnemigosMuertos;
	}

	public Integer getCuracionRecibida() {
		return curacionRecibida;
	}

	public Integer getDanioRecibido() {
		return danioRecibido;
	}

	public String getNombreJugador() {
		return this.nombreJugador;
	}

	public Integer getId() {
		return id;
	}

	public String getResultado() {
		return resultado;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDanioRealizado(Integer danioRealizado) {
		this.danioRealizado = danioRealizado;
	}

	public void setCantidadCartasUsadas(Integer cantidadCartasUsadas) {
		this.cantidadCartasUsadas = cantidadCartasUsadas;
	}

	public void setCantidadEnemigosMuertos(Integer cantidadEnemigosMuertos) {
		this.cantidadEnemigosMuertos = cantidadEnemigosMuertos;
	}

	public void setCuracionRecibida(Integer curacionRecibida) {
		this.curacionRecibida = curacionRecibida;
	}

	public void setDanioRecibido(Integer danioRecibido) {
		this.danioRecibido = danioRecibido;
	}

	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "Historial [id=" + id + ", nombreJugador=" + nombreJugador + ", danioRealizado=" + danioRealizado
				+ ", cantidadCartasUsadas=" + cantidadCartasUsadas + ", cantidadEnemigosMuertos="
				+ cantidadEnemigosMuertos + ", curacionRecibida=" + curacionRecibida + ", danioRecibido="
				+ danioRecibido + ", jugadorEscuchado=" + jugadorEscuchado + ", resultado=" + resultado
				+ ", enemigosEscuchado=" + enemigosEscuchado + "]";
	}
}
