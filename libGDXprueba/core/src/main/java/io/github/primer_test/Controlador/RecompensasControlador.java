package io.github.primer_test.Controlador;

import java.util.ArrayList;

import io.github.primer_test.Recompensas.AplicadorRecompensas;
import io.github.primer_test.Recompensas.Recompensa;
import io.github.primer_test.Recompensas.RecompensasDelNivel;
import io.github.primer_test.Recompensas.SistemaRecompensas;
import io.github.primer_test.Vista.RecompensasVista;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Partida.AdministradorPartida;
import io.github.primer_test.modelo.Usuario.Jugador;

public class RecompensasControlador {
	private RecompensasVista vista;
	private SistemaRecompensas sistema;
	private AplicadorRecompensas aplicador;
	private Jugador jugador;
	private AdministradorPartida adminPartida;
	private RecompensasDelNivel recompensasActuales;
	private ArrayList<Recompensa> opcionesHabilidadesActuales;

	public RecompensasControlador(Jugador jugador, AdministradorPartida adminPartida) {
		this.jugador = jugador;
		this.adminPartida = adminPartida;
		this.vista = new RecompensasVista();
		this.sistema = new SistemaRecompensas();
		this.aplicador = new AplicadorRecompensas();
	}

	public void mostrarRecompensas(ArrayList<Enemigov3> enemigosDerrrotados, int nivelCompletado) {
		recompensasActuales = sistema.generarRecompensasCompletas(enemigosDerrrotados, nivelCompletado);
		opcionesHabilidadesActuales = recompensasActuales.getOpcionesHabilidades();
		vista.iniciarSecuenciaRecompensas(recompensasActuales, this);
		vista.show();
	}

	public void aplicarRecompensa(Recompensa recompensa) {
		aplicador.aplicar(recompensa, jugador);
	}

	public void aplicarHabilidad(int indice) {
		if (indice >= 0 && indice < opcionesHabilidadesActuales.size()) {
			Recompensa habilidadSeleccionada = opcionesHabilidadesActuales.get(indice);
			aplicador.aplicar(habilidadSeleccionada, jugador);
		}
	}

	public void completarRecompensas() {
		vista.show();
		adminPartida.procesarAvanceDeNivel();
	}

	public void render() {
		vista.render();
	}

	public void dispose() {
		if (vista != null) {
			vista.dispose();
		}
	}

	public RecompensasDelNivel getRecompensasActuales() {
		return recompensasActuales;
	}
	public RecompensasVista getVista() {
        return vista;
    }
	public Jugador getJugador() {
		return jugador;
	}
}