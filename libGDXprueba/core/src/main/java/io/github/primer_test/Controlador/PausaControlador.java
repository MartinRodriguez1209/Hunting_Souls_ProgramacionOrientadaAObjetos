package io.github.primer_test.Controlador;

import com.badlogic.gdx.Gdx;

import io.github.primer_test.Vista.PausaVista;
import io.github.primer_test.modelo.Partida.MiJuegoPrincipal;

public class PausaControlador {
	private MiJuegoPrincipal main;
	private PausaVista vista;
	private boolean visible = false;

	public PausaControlador(MiJuegoPrincipal main) {
		this.main = main;
	}

	// Método para conectar con la vista (llamado desde MainPruebaPelea)
	public void setVista(PausaVista vista) {
		this.vista = vista;
	}

	// Control de estado - responsabilidad del controlador
	public void mostrar() {
		visible = true;
		if (vista != null) {
			vista.setVisible(true);
			// Configurar input processor para capturar interacciones del menú
			Gdx.input.setInputProcessor(vista.getStage());
		}
	}

	public void ocultar() {
		visible = false;
		if (vista != null) {
			vista.setVisible(false);
		}
	}

	public boolean esVisible() {
		return visible;
	}

	// Renderizado condicional controlado desde aquí
	public void render() {
		if (visible && vista != null) {
			vista.render();
		}
	}

	public void reanudar() {
		main.reanudarJuego();
	}

	public void volverAlMenu() {
		main.volverAlMenuPrincipal();
	}

	public void salir() {
		main.salirJuego();
	}
}
