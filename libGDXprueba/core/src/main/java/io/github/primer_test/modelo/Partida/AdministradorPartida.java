package io.github.primer_test.modelo.Partida;

import java.util.ArrayList;

import io.github.primer_test.Controlador.PeleaControlador;
import io.github.primer_test.Controlador.RecompensasControlador;
import io.github.primer_test.Vista.PeleaVista;
import io.github.primer_test.Vista.RecompensasVista;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Historial.Historial;
import io.github.primer_test.modelo.Usuario.Jugador;

public class AdministradorPartida implements ResultadoPeleaListener {

	private final MiJuegoPrincipal juegoPrincipal;
	private final Partida partida;
	private final Jugador jugador;
	private PeleaVista vistaActualDePelea;
	private PeleaControlador controladorActualDePelea;
	private final Historial historial;
	private String rutaFondo;
	private static final int nivel_final = 3;

	private RecompensasControlador recompensasControlador;
	private RecompensasVista vistaActualDeRecompensas;

	public AdministradorPartida(MiJuegoPrincipal juegoPrincipal, String nombreJugador) {
		this.juegoPrincipal = juegoPrincipal;
		this.jugador = new Jugador(nombreJugador, 70, 70, 10, 10, 10);
		this.historial = new Historial();
		this.historial.subscribirJugador(this.jugador);
		this.partida = new Partida();
	}

	public void iniciarPartida() {
		iniciarPeleaSiguienteNivel();
	}

	private void victoria() {
		limpiarVistaPeleaActual();
		limpiarVistaRecompensasActual();
		this.historial.setVictoria();
		this.historial.guardarHistorial();
		juegoPrincipal.mostrarPantallaVictoria();
	}

	private void derrota() {
		System.out.println("DERROTA");
		limpiarVistaPeleaActual();
		limpiarVistaRecompensasActual();
		this.historial.setDerrota();
		this.historial.guardarHistorial();
		juegoPrincipal.mostrarPantallaGameOver();
	}

	private void iniciarPeleaSiguienteNivel() {
		Nivel nivelActual = partida.getNivelActual();
		if (nivelActual == null) {
			victoria();
			return;
		}
		limpiarVistaPeleaActual();
		limpiarVistaRecompensasActual();
		ArrayList<Enemigov3> enemigos = nivelActual.getEnemigos();
		this.historial.subscrbirAPelea(enemigos);
		rutaFondo = nivelActual.getRutaFondo();
		vistaActualDePelea = new PeleaVista(new ArrayList<>(enemigos), jugador, rutaFondo);
		controladorActualDePelea = new PeleaControlador(vistaActualDePelea, enemigos, jugador, juegoPrincipal, this);
		juegoPrincipal.setVistaPeleaActiva(vistaActualDePelea, controladorActualDePelea);
	}

	@Override
	public void onPeleaTerminada(boolean victoria, ArrayList<Enemigov3> enemigosDerrotados) {
		if (victoria) {
			int nivelCompletado = partida.getNivelActualIndice();
			if (nivelCompletado == nivel_final) {
				victoria();
			} else {
				limpiarVistaPeleaActual();
				this.recompensasControlador = new RecompensasControlador(this.jugador, this);
				this.vistaActualDeRecompensas = this.recompensasControlador.getVista();
				this.recompensasControlador.mostrarRecompensas(enemigosDerrotados, nivelCompletado);
				juegoPrincipal.setVistaRecompensasActiva(this.vistaActualDeRecompensas, this.recompensasControlador);
			}
		} else {
			derrota();
		}
	}

	public void procesarAvanceDeNivel() {
		partida.avanzarNivel();
		iniciarPeleaSiguienteNivel();
	}

	protected void limpiarVistaPeleaActual() {
		if (vistaActualDePelea != null) {
			juegoPrincipal.limpiarVistaPeleaActual();
			vistaActualDePelea = null;
			controladorActualDePelea = null;
		}
	}

	protected void limpiarVistaRecompensasActual() {
		juegoPrincipal.limpiarVistaRecompensasActual();
        recompensasControlador = null;
        vistaActualDeRecompensas = null;
	}
}