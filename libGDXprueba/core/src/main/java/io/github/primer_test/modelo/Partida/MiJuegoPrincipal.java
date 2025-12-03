package io.github.primer_test.modelo.Partida;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import io.github.primer_test.Controlador.MenuControlador;
import io.github.primer_test.Controlador.PausaControlador;
import io.github.primer_test.Controlador.PeleaControlador;
import io.github.primer_test.Controlador.RecompensasControlador;
import io.github.primer_test.Sonidos.Sonido;
import io.github.primer_test.Sonidos.SonidoData;
import io.github.primer_test.Utils.SfxDataManager;
import io.github.primer_test.MainMenu;
import io.github.primer_test.Vista.DerrotaVista;
import io.github.primer_test.Vista.PausaVista;
import io.github.primer_test.Vista.PeleaVista;
import io.github.primer_test.Vista.RecompensasVista;
import io.github.primer_test.Vista.VictoriaVista;

public class MiJuegoPrincipal extends ApplicationAdapter {

	private AdministradorPartida gestorPartida;
	private PeleaVista vistaPeleaActiva = null;
	private RecompensasVista vistaRecompensasActiva = null;
	private PeleaControlador controladorPeleaActivo = null;
	private boolean juegoEnPausa = false;
	private boolean derrotaVistaActiva = false;
	private boolean victoriaVistaActiva = false;
	private boolean recompensasVistaActiva = false;
	private PausaControlador pausaControlador;
	private MainMenu menuPrincipal;
	private boolean pausaVistaActiva = false;
	private PausaVista pausaVista;
	private VictoriaVista victoriaVista;
	private DerrotaVista derrotaVista;
	private MenuControlador menuControlador;
	private RecompensasControlador controladorRecompensasActivo = null;
	private String nombreJugador;

	public MiJuegoPrincipal(MainMenu menuPrincipal, String nombreJugador) {
		this.menuPrincipal = menuPrincipal;
		this.nombreJugador = nombreJugador;
	}

	@Override
	public void create() {
		
		SfxDataManager sfxManager = SfxDataManager.getInstancia();
		
		if (sfxManager.isDataLoaded()) {
	        
	        
	        for (SonidoData datos : sfxManager.getAllSfxDataList()) {
	            if (!datos.getLoop()) {
	                 Sonido.precargarSfx(datos.getFileName()); 
	            }
	        }
	        Gdx.app.log("AUDIO", "Precarga de SFX finalizada con éxito.");
	    } else {
	  
	        Gdx.app.error("AUDIO_ERROR", "ERROR: El archivo sfx.json no pudo cargar datos.");
	    }
		
		gestorPartida = new AdministradorPartida(this, nombreJugador);
		gestorPartida.iniciarPartida();
		pausaControlador = new PausaControlador(this);
		pausaVista = new PausaVista(pausaControlador);
		menuControlador = new MenuControlador(menuPrincipal);
		derrotaVista = new DerrotaVista(menuControlador);
		victoriaVista = new VictoriaVista(menuControlador);
		pausaVistaActiva = true;
		pausaVista.show();
		pausaControlador.setVista(pausaVista);
		

	}

	public void setVistaPeleaActiva(PeleaVista vista, PeleaControlador controlador) {
		this.vistaPeleaActiva = vista;
		this.controladorPeleaActivo = controlador;
	}
	public void setVistaRecompensasActiva(RecompensasVista vista, RecompensasControlador controlador) {
        this.vistaRecompensasActiva = vista;
        this.controladorRecompensasActivo = controlador;
        if (vista != null) {
            this.recompensasVistaActiva = true;
            Gdx.input.setInputProcessor(vista.getStage());
        } else {
            this.recompensasVistaActiva = false;
        }
    }
	public boolean isJuegoEnPausa() {
		return juegoEnPausa;
	}

	public void pausarJuego() {
		juegoEnPausa = true;
		pausaControlador.mostrar();
	}

	public void reanudarJuego() {
		juegoEnPausa = false;
		Gdx.input.setInputProcessor(vistaPeleaActiva.getStage());
		pausaControlador.ocultar();
	}

	@Override
	public void render() {
		pausaControlador.render();
		if (vistaPeleaActiva != null && controladorPeleaActivo != null) {
			vistaPeleaActiva.dibujar(this.juegoEnPausa);
		}
		if (pausaVista != null && pausaVistaActiva) {
			Stage stagePausa = pausaVista.getStage();
			stagePausa.act(Gdx.graphics.getDeltaTime());
			stagePausa.draw();
		}
		if (vistaRecompensasActiva != null && recompensasVistaActiva) {
            vistaRecompensasActiva.render();
        }
		if (derrotaVista != null && derrotaVistaActiva) {
			derrotaVista.render();
    	}
		if (victoriaVista != null && victoriaVistaActiva) {
			victoriaVista.render();
    	}
	}

	@Override
	public void resize(int width, int height) {
		if (vistaPeleaActiva != null) {
			vistaPeleaActiva.resize(width, height);
		}

	}

	@Override
	public void dispose() {
		if (pausaVista != null && pausaVistaActiva) {
			pausaVista.dispose();
			pausaVistaActiva = false;
		}
		if (vistaRecompensasActiva != null && recompensasVistaActiva) {
            controladorRecompensasActivo.dispose();
            vistaRecompensasActiva = null;
            controladorRecompensasActivo = null;
            recompensasVistaActiva = false;
        }
		if (derrotaVista != null && derrotaVistaActiva) {
        derrotaVista.dispose();
        derrotaVistaActiva = false;
    	}
		if (victoriaVista != null && victoriaVistaActiva) {
        victoriaVista.dispose();
        victoriaVistaActiva = false;
    	}
		
		Sonido.disposeTodo();
	}

	public void mostrarPantallaVictoria() {
		System.out.println("PANTALLA VICTORIA");
		limpiarVistaPeleaActual();
		limpiarVistaRecompensasActual();
		victoriaVista.show();
		victoriaVistaActiva = true;
	}

	public void mostrarPantallaGameOver() {
		System.out.println("PANTALLA DERROTA");
		limpiarVistaPeleaActual();
		limpiarVistaRecompensasActual();
		derrotaVista.show();
		derrotaVistaActiva = true;
	}

	public void limpiarVistaPeleaActual() {
		if (vistaPeleaActiva != null) {
			vistaPeleaActiva.dispose();
			vistaPeleaActiva = null;
			controladorPeleaActivo = null;
		}
	}
	public void limpiarVistaRecompensasActual() {
        if (vistaRecompensasActiva != null) {
            controladorRecompensasActivo.dispose();
            vistaRecompensasActiva = null;
            controladorRecompensasActivo = null;
            recompensasVistaActiva = false;
        }
    }

	public void volverAlMenuPrincipal() {
		this.dispose();
		menuPrincipal.mostrarMenu();
	}

	public void salirJuego() {
		Gdx.app.exit();
	}

	public void siguienteNivel() {

		this.gestorPartida.procesarAvanceDeNivel();
	}

}
