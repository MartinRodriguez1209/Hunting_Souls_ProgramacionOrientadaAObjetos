package io.github.primer_test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import io.github.primer_test.Controlador.MenuControlador;
import io.github.primer_test.Vista.HistorialVista;
import io.github.primer_test.Vista.IngresoUsuarioVista;
import io.github.primer_test.Vista.MenuVista;
import io.github.primer_test.Vista.TutorialVista;
import io.github.primer_test.modelo.Partida.MiJuegoPrincipal;

public class MainMenu extends ApplicationAdapter {

	private MenuVista menuVista;
	private IngresoUsuarioVista ingresoVista;
	private MenuControlador controlador;
	private Music musicaMenu;

	private boolean mostrarMenu = true;
	private boolean mostrarIngreso = false;
	private boolean mostrarPelea = false;
	private boolean mostrarTutorial = false;
	private boolean mostrarHistorial = false;

	private TutorialVista tutorialVista;
	private HistorialVista historialVista;
	private MiJuegoPrincipal pelea;

	private boolean tutorialVistaActiva = false;
	private boolean ingresoVistaActiva = false;
	private boolean historialVistaActivo = false;
	private boolean peleaActiva = false;
	private boolean musicaActiva = true;

	@Override
	public void create() {
		musicaMenu = Gdx.audio.newMusic(Gdx.files.internal("menu_theme.mp3"));
		musicaMenu.setLooping(true);
		musicaMenu.setVolume(0.5f);
		musicaMenu.play();

		controlador = new MenuControlador(this);

		menuVista = new MenuVista(controlador);
		menuVista.show();
	}

	public void mostrarMenu() {
		if (ingresoVista != null && ingresoVistaActiva) {
			ingresoVista.dispose();
			ingresoVistaActiva = false;
		}
		ingresoVista = null;

		if (pelea != null && peleaActiva) {
			pelea.dispose();
			peleaActiva = false;
		}
		pelea = null;
		if (tutorialVista != null && tutorialVistaActiva) {
            tutorialVista.dispose();
            tutorialVistaActiva = false;
        }
		if (historialVista != null && historialVistaActivo) {
            historialVista.dispose();
            historialVistaActivo = false;
        }
        historialVista = null;
        tutorialVista = null;
		menuVista = new MenuVista(controlador);
		menuVista.show();
		mostrarMenu = true;
		mostrarIngreso = false;
		mostrarPelea = false;
		mostrarTutorial = false;
		mostrarHistorial = false;
	}

	public void mostrarTutorial() {
        if (menuVista != null){
            menuVista.dispose();
		}
        tutorialVista = new TutorialVista(controlador);
        tutorialVistaActiva = true;
		tutorialVista.show();

        mostrarMenu = false;
        mostrarIngreso = false;
        mostrarPelea = false;
        mostrarTutorial = true;
    }

	public void mostrarIngreso() {
		if (menuVista != null)
			menuVista.dispose();

		ingresoVista = new IngresoUsuarioVista(controlador);
		controlador.setIngresoUsuarioVista(ingresoVista);
		ingresoVista.show();
		ingresoVistaActiva = true;

		mostrarMenu = false;
		mostrarIngreso = true;
		mostrarPelea = false;
	}
	public void mostrarHistorial() {
        if (menuVista != null){
            menuVista.dispose();
		}
        historialVista = new HistorialVista(controlador);
        historialVista.show();
        historialVistaActivo = true;

        mostrarMenu = false;
        mostrarIngreso = false;
        mostrarPelea = false;
        mostrarTutorial = false;
        mostrarHistorial = true;
    }
	 public void activarmusica() {
        if (musicaActiva) {
            musicaMenu.pause();
        } else {
            musicaMenu.play();
        }
        musicaActiva = !musicaActiva;
    }
	public void mostrarPelea() {
		if (ingresoVista != null && ingresoVistaActiva) {
			ingresoVista.dispose();
			ingresoVistaActiva = false;
		}
		ingresoVista = null;
		String nombreJugador = controlador.getNombreJugador();
		pelea = new MiJuegoPrincipal(this, nombreJugador);
		pelea.create();
		peleaActiva = true;

		mostrarMenu = false;
		mostrarIngreso = false;
		mostrarPelea = true;
	}

	@Override
	public void render() {
		if (mostrarMenu) {
			menuVista.render();
		} else if (mostrarIngreso) {
			ingresoVista.render();
		} else if (mostrarPelea) {
			pelea.render();
		}
		else if (mostrarTutorial) {
            tutorialVista.render();
        }
		else if (mostrarHistorial) {
            historialVista.render();
        }
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
		if (menuVista != null)
			menuVista.dispose();
		if (ingresoVista != null && ingresoVistaActiva)
			ingresoVista.dispose();
		if (pelea != null && peleaActiva)
			pelea.dispose();
		if (musicaMenu != null)
			musicaMenu.dispose();
		if (historialVista != null && historialVistaActivo)
        historialVista.dispose();
	}
}
