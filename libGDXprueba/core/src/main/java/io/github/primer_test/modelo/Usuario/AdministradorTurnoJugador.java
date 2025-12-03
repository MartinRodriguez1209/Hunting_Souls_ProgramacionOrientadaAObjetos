package io.github.primer_test.modelo.Usuario;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import io.github.primer_test.modelo.Cartas.Carta;
import io.github.primer_test.modelo.ENUMS.TipoObjetivos;
import io.github.primer_test.modelo.ENUMS.TipoUnidad;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Unidad;

// Clase destinada a manejar la logica de como realiza su turno el jugador
// Sirve sacarla para que el controlador no tenga que saber las reglas del jugador
// Se puede reutilizar en otros controladores de peleas distintas

public class AdministradorTurnoJugador {

	private Carta cartaElegida;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

	public AdministradorTurnoJugador() {
		cartaElegida = null;
	}

	public void seleccionarCarta(Carta cartaElegida, Jugador jugador) {

		this.cartaElegida = cartaElegida;
		pcs.firePropertyChange(TiposNotificaciones.cartaElegida.toString(), null, cartaElegida);
	}

	public void seleccionarUnidadObjetivo(Unidad unidadElegida, Jugador jugador) {
		if (this.cartaElegida == null) {
			pcs.firePropertyChange(TiposNotificaciones.cartaNula.toString(), null, "Debes elegir una carta primero");
			return;
		}
		int costoMana = this.cartaElegida.getCostoMana();
		Carta cartaUsada = this.cartaElegida;
		if (!jugador.puedePagarMana(costoMana)) {
			pcs.firePropertyChange(TiposNotificaciones.manaInsuficiente.toString(), null,
					"¡No tienes suficiente maná!");
			return;
		}
		TipoObjetivos objetivoPermitido = this.cartaElegida.getObjetivo();
		TipoUnidad faccionDelObjetivo = unidadElegida.getFaccion();
		if (objetivoPermitido == TipoObjetivos.ENEMIGO && faccionDelObjetivo == TipoUnidad.JUGADOR) {
			pcs.firePropertyChange(TiposNotificaciones.errorObjetivo.toString(), null,
					"esa carta solo se puede usar en enemigos");
			return;
		}

		if (objetivoPermitido == TipoObjetivos.JUGADOR && faccionDelObjetivo == TipoUnidad.ENEMIGO) {
			pcs.firePropertyChange(TiposNotificaciones.errorObjetivo.toString(), null,
					"esa carta solo se puede usar en aliados");
			return;
		}

		try {
			cartaUsada.realizarAccion().ejecutar(unidadElegida);
			jugador.gastarMana(costoMana);
			jugador.removerCartaMano(cartaUsada);
			jugador.getBaraja().agregarCarta(cartaUsada);
			this.cartaElegida = null;

		} catch (Exception e) {
			// aca puede ser la solucion a que no se pueda curar a un enemigo hay que ver
			pcs.firePropertyChange("error2", null, e.getMessage());
		}

	}

	public void iniciarPelea(Jugador jugador) {
		jugador.robarPrimeraMano();
	}

	public void terminarTurno(Jugador jugador) {

	}

	public Boolean iniciarTurno(Jugador jugador) {
		if (jugador.prepararTurno()) {
			System.out.println("turno iniciado");
			return true;
		} else {
			System.out.println("JUGADOR ATURDIDO");
			return false;
		}

	}

}
