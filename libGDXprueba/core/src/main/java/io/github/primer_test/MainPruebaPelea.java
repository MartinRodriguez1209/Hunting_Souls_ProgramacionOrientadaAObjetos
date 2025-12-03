package io.github.primer_test;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import io.github.primer_test.Controlador.PausaControlador;
import io.github.primer_test.Controlador.PeleaControlador;
import io.github.primer_test.Utils.JsonManager;
import io.github.primer_test.Vista.PausaVista;
import io.github.primer_test.Vista.PeleaVista;
import io.github.primer_test.modelo.Enemigos.BossFinal;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaCardenal;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaCulturista;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaEnemigos;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaMonja;
import io.github.primer_test.modelo.Usuario.Jugador;

public class MainPruebaPelea extends ApplicationAdapter {

	private PeleaVista peleaVista;
	private PeleaControlador peleaControlador;
	private boolean juegoEnPausa = false;
	private PausaVista pausaVista;
	private PausaControlador pausaControlador;
	private boolean pausaVistaActiva = false;
	private MainMenu menu;
	private MainMenu menuPrincipal;

	public MainPruebaPelea(MainMenu menuPrincipal) {
		this.menuPrincipal = menuPrincipal;
	}

	@Override
	public void create() {

		// prueba de pasar 3 enemigos distintos a la vista de peleas
		// TODO: con 4 enemigos se rompen las proporciones revisar

		JsonManager jsonManager = JsonManager.getInstanciaJsonManager();
		FabricaEnemigos fabricaCulturista = new FabricaCulturista();
		FabricaEnemigos fabricaMonja = new FabricaMonja();
		FabricaEnemigos fabricaCardenal = new FabricaCardenal();
		BossFinal boss = new BossFinal();
		ArrayList<Enemigov3> listaPruebaList = new ArrayList<Enemigov3>();

		listaPruebaList.add(fabricaCulturista.crearEnemigov3(1));
		listaPruebaList.add(fabricaCulturista.crearEnemigov3(1));
		Jugador jugadorPruebaJugador = new Jugador("martin", 100, 100, 10, 10, 10);
		peleaVista = new PeleaVista(listaPruebaList, jugadorPruebaJugador);
		peleaControlador = new PeleaControlador(peleaVista, listaPruebaList, jugadorPruebaJugador, this);
		pausaControlador = new PausaControlador(this);
		pausaVista = new PausaVista(pausaControlador);
		pausaVistaActiva = true;
		pausaVista.show();
		pausaControlador.setVista(pausaVista);
	}

	public void pausarJuego() {
		juegoEnPausa = true;
		pausaControlador.mostrar();
	}

	public void reanudarJuego() {
		juegoEnPausa = false;
		Gdx.input.setInputProcessor(peleaVista.getStage());
		pausaControlador.ocultar();
	}

	public boolean isJuegoEnPausa() {
		return juegoEnPausa;
	}

	public void volverAlMenuPrincipal() {
		this.dispose();
		menuPrincipal.mostrarMenu();
	}

	public void salirJuego() {
		Gdx.app.exit();
	}

	public void continuarAlSiguienteNivel() {
		// por ahora volvemos al menu principal
		this.volverAlMenuPrincipal();
	}

	@Override
	public void render() {
		peleaControlador.dibujar();
		pausaControlador.render();

		if (peleaControlador.getRecompensasControlador() != null) {
			peleaControlador.getRecompensasControlador().render();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (pausaVista != null) {
			pausaVista.resize(width, height);
		}
	}

	@Override
	public void dispose() {
		if (pausaVista != null && pausaVistaActiva) {
			pausaVista.dispose();
			pausaVistaActiva = false;
		}

		if (peleaControlador != null && peleaControlador.getRecompensasControlador() != null) {
			peleaControlador.getRecompensasControlador().dispose();
		}
	}
}
