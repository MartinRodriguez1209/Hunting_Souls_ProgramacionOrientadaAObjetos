package io.github.primer_test.Vista;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Usuario.Jugador;

public class PeleaVista extends Table implements Disposable, PropertyChangeListener {

	private final Stage stage; // el stage es el contenedor principal sirve para los inputs
	private final Texture fondo;
	private final Texture hudTexture;

	private final Table escenarioPanel; // esta es mi tabla mas grande que contiene a las demas

	private final Table jugadorPanel; // aca va a ir la vista del jugador
	private final Table enemigosPanel; // aca van las vistas de los enemigos
	private final Table hudPanel; // esto es el tercio de abajo tiene las cartas y la caja de texo
	private final Group cartasGroup; // aca las cartas que tiene para usar
	private final Table cajaTextoTable;
	// private final Image ImagenHud;
	private final TextArea logTextArea;// aca la caja donde se logea lo que va pasando
	private final Skin uiSkin;
	private Table estadisticasPanel;
	private final Boolean DEBUG_MODE = false;
	private final TextButton botonTerminarTurno;
	private final TextButton botonPausa;
	private final Table enemigosRow;
	private ArrayList<UnidadVista> vistasEnemigos;
	private Jugador jugador;
	private ArrayList<Enemigov3> enemigos;
	private UnidadVista jugadorVista;
	protected final EstadisticasVista estadisticasVista;

	public PeleaVista(ArrayList<Enemigov3> enemigos, Jugador jugador, String rutaFondo) {

		// creo el stage y luego le añado esta vista completa con el this
		stage = new Stage(new ScreenViewport());
		fondo = new Texture(Gdx.files.internal(rutaFondo)); // aca se agrega el fondo esto lo
		// deberia hacer
		hudTexture = new Texture(Gdx.files.internal("fondo_hud.png"));
		this.jugador = jugador;
		this.jugador.addPropertyChangeListener(this);
		this.enemigos = enemigos;
		for (Enemigov3 enemigo : this.enemigos) {
			enemigo.addPropertyChangeListener(this);
		}

		uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json")); // esta skin la usa la barra de vida y la caja de texo
		this.setFillParent(true);
		this.setDebug(DEBUG_MODE); // esto muestra las lineas rojas

		// creo mi tabla principal y hago que ocupe todo
		escenarioPanel = new Table();
		escenarioPanel.setBackground(new TextureRegionDrawable(new TextureRegion(fondo)));
		this.add(escenarioPanel).grow().expand().row();

		// aca agrego la tabla para el jugador y la otra para los enemigos
		escenarioPanel.setDebug(DEBUG_MODE);
		estadisticasPanel = new Table();
		estadisticasPanel.top().left().pad(5);
		escenarioPanel.add(estadisticasPanel);
		jugadorPanel = new Table();
		escenarioPanel.add(jugadorPanel);
		enemigosPanel = new Table();
		escenarioPanel.add(enemigosPanel).expand().fill();
		enemigosRow = new Table();
		enemigosRow.setDebug(DEBUG_MODE);

		// itero mi lista de enemigos
		this.vistasEnemigos = new ArrayList<>();
		for (final Enemigov3 enemigo : this.enemigos) {
			// para cada enemigo creo una vista
			new SonidoVista(enemigo);
			UnidadVista enemigoVista = new UnidadVista(enemigo);
			enemigo.addPropertyChangeListener(this);
			enemigosRow.add(enemigoVista).height(300).expand().fill();
			this.vistasEnemigos.add(enemigoVista);
		}
		enemigosPanel.add(enemigosRow).expand().fill();

		jugadorVista = new UnidadVista(this.jugador);
		estadisticasVista = new EstadisticasVista();
		estadisticasVista.setJugador(jugador);
		jugadorPanel.add(estadisticasVista).width(450).height(100).padBottom(30).top().left();
		jugadorPanel.row();
		jugadorPanel.add(jugadorVista).height(250).expand().fill().pad(5);

		hudPanel = new Table();
		hudPanel.setBackground(new TextureRegionDrawable(new TextureRegion(hudTexture)));
		botonTerminarTurno = new TextButton("Terminar turno", uiSkin);
		botonPausa = new TextButton("Pausa", uiSkin);
		enemigosPanel.add(enemigosRow).expand().fill();
		// Crear tabla para los botones
		Table botonesTable = new Table();
		botonesTable.add(botonTerminarTurno).padRight(10).height(50);
		botonesTable.add(botonPausa).height(50);

		hudPanel.add(botonesTable).padLeft(110).left().row(); // .padLeft(10).left().row(); (Original)
		hudPanel.setDebug(DEBUG_MODE);
		cartasGroup = new Group();
		cajaTextoTable = new Table();
		cajaTextoTable.setBackground(uiSkin.newDrawable("rect", uiSkin.getColor("black")));

		logTextArea = new TextArea("", uiSkin);
		logTextArea.getStyle().fontColor = uiSkin.getColor("white");
		logTextArea.getStyle().disabledFontColor = uiSkin.getColor("white");

		logTextArea.setDisabled(true);
		cajaTextoTable.add(logTextArea).expand().fill().padLeft(10).padRight(10).padTop(6).padBottom(6);

		this.add(hudPanel).width(1600).height(200).row();
		hudPanel.add(cartasGroup).expand().fill().width(800f);
		hudPanel.add(cajaTextoTable).expand().fill().width(800f).right();

		stage.addActor(this);

		Gdx.input.setInputProcessor(stage);

	}

	// metodos para el controlador
	public Table getEscenarioPanel() {
		return escenarioPanel;
	}

	public Group getCartasTable() {
		return cartasGroup;
	}

	public TextArea getCajaTextoTable() {
		return logTextArea;
	}

	public Table getEnemigosPanel() {
		return enemigosPanel;
	}

	public Table getJugadorPanel() {
		return this.jugadorPanel;
	}

	public void agregarEstadisticas(EstadisticasVista stats) {
		estadisticasPanel.add(stats).top().left().pad(10);
	}

	// este metodo se ejecuta todos los frames para redibujarse
	public void dibujar() {
		dibujar(false); // Por defecto no está pausado
	}

	public UnidadVista getJugadorVista() {
		return jugadorVista;
	}

	public ArrayList<UnidadVista> getVistasEnemigos() {
		return vistasEnemigos;
	}

	// Método principal que maneja pausa con booleano
	public void dibujar(boolean pausado) {
		// Solo actualiza animaciones si no está pausado
		if (!pausado) {
			stage.act(Gdx.graphics.getDeltaTime());
		}

		// Siempre dibujar (con o sin animaciones)
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		fondo.dispose();
		hudTexture.dispose();
	}

	public TextButton getBotonTerminarTurno() {
		return botonTerminarTurno;
	}

	public TextButton getBotonPausa() {
		return botonPausa;
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void habilitarInteraccionJugador(boolean habilitar) {

		Touchable touchableState = habilitar ? Touchable.enabled : Touchable.disabled;

		// itera las cartas
		for (Actor actor : cartasGroup.getChildren()) {
			actor.setTouchable(touchableState);
		}

		// itoera los enemigos
		for (Actor actor : enemigosPanel.getChildren()) {
			actor.setTouchable(touchableState);
		}

		// ahora sobre el jugador
		for (Actor actor : jugadorPanel.getChildren()) {
			actor.setTouchable(touchableState);
		}

		// FALTA EL BOTON FINALIZAR TURNO

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String propiedad = evt.getPropertyName();

		TiposNotificaciones notificacion = TiposNotificaciones.valueOf(propiedad);

		switch (notificacion) {
		case turnoEnemigosTerminado:
			logTextArea.appendText(evt.getNewValue().toString());
			break;
		case aturdido:
			logTextArea.appendText(evt.getOldValue().toString() + " esta aturdido no puede realizar su turno\n");
		default:
			break;
		}

	}
}