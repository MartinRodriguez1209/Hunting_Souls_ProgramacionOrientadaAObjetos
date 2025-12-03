package io.github.primer_test.modelo.Usuario;

import io.github.primer_test.modelo.Cartas.Baraja;
import io.github.primer_test.modelo.Cartas.CargarBaraja;
import io.github.primer_test.modelo.Cartas.Carta;
import io.github.primer_test.modelo.Cartas.ManoCartas;
import io.github.primer_test.modelo.ENUMS.TipoAcciones;
import io.github.primer_test.modelo.ENUMS.TipoUnidad;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Unidad;

public class Jugador extends Unidad {

	private Integer defensa;
	private ManoCartas manoCartas;
	private Integer manaMaximo;
	private Integer manaActual;
	private CargarBaraja cargarbaraja;
	private static final int CANTIDAD_CARTAS_MAX = 7;

	private Baraja baraja;

	public Jugador(String nombre, Integer vidaTotal, Integer vidaActual, Integer defensa, Integer escudo,
			Integer mana) {
		super(vidaActual, vidaTotal, nombre, escudo);
		this.defensa = defensa;
		this.manaMaximo = mana;
		this.manaActual = mana;
		this.baraja = cargarbaraja.cargar();
		this.manoCartas = new ManoCartas(CANTIDAD_CARTAS_MAX);
		this.faccion = TipoUnidad.JUGADOR;
	}

	public Integer getManaActual() {
		return manaActual;
	}

	public Baraja getBaraja() {
		return this.baraja;
	}

	public ManoCartas getManoCartas() {
		return manoCartas;
	}

	private void robarCarta() {
		Carta cartaRobada = baraja.robarCarta();
		if (cartaRobada != null) {
			if (!manoCartas.agregarCarta(cartaRobada)) {
				baraja.agregarCarta(cartaRobada);
			}
		}
	}

	public ManoCartas robarPrimeraMano() {
		for (int i = 0; i < 5; i++) {
			robarCarta();
		}
		return manoCartas;
	}

	public void removerCartaMano(Carta carta) {
		this.manoCartas.removerCarta(carta);
		this.pcs.firePropertyChange(TiposNotificaciones.cartaUsada.toString(), this, null);
	}

	public void robarCartas() {
		for (int i = 0; i < 3; i++) {
			robarCarta();
		}
	}

	public boolean puedePagarMana(Integer costo) {
		return this.manaActual >= costo;
	}

	public void gastarMana(Integer costo) {
		Integer manaAnterior = this.manaActual;
		this.manaActual = Math.max(0, this.manaActual - costo);
		this.pcs.firePropertyChange(TiposNotificaciones.manaGastado.toString(), manaAnterior, this.manaActual);
	}

	private void restaurarMana() {
		Integer manaAnterior = this.manaActual;
		this.manaActual = this.manaMaximo;

		pcs.firePropertyChange(TiposNotificaciones.manaRestaurado.toString(), manaAnterior, this.manaActual);
	}

	public boolean prepararTurno() {
		if (this.estaAturdido()) {
			this.reducirTurnoAturdimiento();
			this.pcs.firePropertyChange(TiposNotificaciones.aturdido.toString(), this, null);
			return false;
		}
		this.restaurarMana();
		this.robarCartas();
		return true;
	}

	public void mostrarEstadoHabilidades() {
		System.out.println("ESTADÍSTICAS:");
		System.out.println("Vida: " + this.getVidaActual() + "/" + this.getVidaTotal() + " HP");
		System.out.println("Maná: " + this.getManaActual() + "/" + this.manaMaximo + " MP");
		System.out.println("Escudo: " + this.getEscudo() + " puntos");

		System.out.println("HABILIDADES ESPECIALES:");
		System.out.println("Armadura: " + this.getArmadura() + " puntos");
		System.out.println("Probabilidad de bloqueo: " + this.getProbabilidadBloqueo() + "%");
		System.out.println("Puede absorber vida: " + (this.getProbabilidadAbsorber() > 0 ? "SÍ" : "NO"));
		System.out
				.println("Estrategia de daño: " + this.manejadores.get(TipoAcciones.DANIO).getClass().getSimpleName());

	}

	public void aumentarVidaMaxima(int cantidad) {
		Integer vidaTotalAnterior = this.vidaTotal;
		this.vidaTotal += cantidad;
		this.vidaActual += cantidad;
		pcs.firePropertyChange(TiposNotificaciones.vidaCurada.toString(), vidaTotalAnterior, this.vidaActual);
	}

	public void aumentarManaMaximo(int cantidad) {
		Integer manaMaximoAnterior = this.manaMaximo;
		this.manaMaximo += cantidad;
		this.manaActual += cantidad;
		pcs.firePropertyChange(TiposNotificaciones.manaRestaurado.toString(), manaMaximoAnterior, this.manaActual);
	}

	@Override
	protected void morirSonido() {
		// TODO Auto-generated method stub

	}
}
