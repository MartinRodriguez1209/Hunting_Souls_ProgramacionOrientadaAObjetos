package io.github.primer_test.Vista;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

import io.github.primer_test.Utils.JsonManager;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Unidad;

//vista de un enemigo el controlador crea varias y las añade a la peleavista

public class UnidadVista extends Table implements Disposable, PropertyChangeListener {

	private final Unidad enemigoModelo;
	private Animation<TextureRegion> animacion;

	// esta variable se usa para que se actualicen los frames correctamente
	private float stateTime;
	private Texture spritesheet;

	// esto podria ser dinamico para aquellos enemigos que tengan spritesheets con
	// distintas columnas a 4
	private int FRAME_COLS, FRAME_ROWS;
	private final Image enemigoImage;
	private final TextureRegionDrawable animacionDrawable;
	private final ProgressBar healthBar;
	private final ProgressBar shieldBar;
	private final Skin uiSkin;
	private final Boolean DEBUG_MODE = false;

	public UnidadVista(Unidad enemigoModelo) {

		// casi todo sigue el ejemplo de
		// https://libgdx.com/wiki/graphics/2d/2d-animation

		this.enemigoModelo = enemigoModelo;
		this.uiSkin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		this.enemigoModelo.addPropertyChangeListener(this); // ACA ME SUBSCRIBO
		JsonManager dataJsonManager = JsonManager.getInstanciaJsonManager();

		spritesheet = new Texture(Gdx.files.internal(enemigoModelo.getAssetString()));
		FRAME_COLS = enemigoModelo.getColsSheet();
		FRAME_ROWS = enemigoModelo.getAssetRows();

		TextureRegion[][] tmp = TextureRegion.split(spritesheet, spritesheet.getWidth() / FRAME_COLS,
				spritesheet.getHeight() / FRAME_ROWS);
		TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		// aca va la velocidad de la animacion
		animacion = new Animation<>(0.25f, frames);
		stateTime = 0f;

		// crea la imagen
		animacionDrawable = new TextureRegionDrawable(animacion.getKeyFrame(stateTime, true));
		enemigoImage = new Image(animacionDrawable);

		// la barra de vida
		healthBar = new ProgressBar(0, enemigoModelo.getVidaTotal(), 1, false, uiSkin);
		healthBar.setValue(enemigoModelo.getVidaActual());
		healthBar.setAnimateDuration(0.1f);

		// estilo de la barra de vida para utilizar en la barra de escudo
		ProgressBar.ProgressBarStyle defaultStyle = uiSkin.get("default-horizontal",
				ProgressBar.ProgressBarStyle.class);
		Drawable originalKnob = defaultStyle.knobBefore;

		// lo creo en celeste
		Drawable celesteKnob = uiSkin.newDrawable("white", new Color(0.3f, 0.8f, 1.0f, 1.0f)); // r, g, b, a

		// copio la altura para que esten iguales
		celesteKnob.setMinHeight(originalKnob.getMinHeight());

		// estilo del escudo
		ProgressBar.ProgressBarStyle shieldBarStyle = new ProgressBar.ProgressBarStyle(defaultStyle);
		shieldBarStyle.knobBefore = celesteKnob;

		// barra de escudo personalizada
		shieldBar = new ProgressBar(0, 100, 1, false, uiSkin);
		shieldBar.setStyle(shieldBarStyle);
		shieldBar.setValue(enemigoModelo.getEscudo());
		shieldBar.setAnimateDuration(0.1f);

		// aplicamos una transparencia final a todo el actor de la barra de escudo.
		shieldBar.setColor(1f, 1f, 1f, 0.5f); // 60% de opacidad

		// stackeo las barras una encima de la otra
		Stack barStack = new Stack();
		barStack.add(healthBar);
		barStack.add(shieldBar);

		this.add(new Label(enemigoModelo.getNombre(), uiSkin));
		this.row();

		// agrego primero la barra para que este arriba
		this.add(barStack).width(enemigoImage.getWidth() * 1.2f);
		this.row();
		this.add(enemigoImage);
		this.row();
		this.setDebug(DEBUG_MODE);
	}

	// esto es llamado por el stage aca se actualiza mi enemigo
	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		animacionDrawable.setRegion(animacion.getKeyFrame(stateTime, true));

	}

	@Override
	public void dispose() {
		spritesheet.dispose();
		uiSkin.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		String evento = evt.getPropertyName();
		TiposNotificaciones notificacion = TiposNotificaciones.valueOf(evento);
		try {
			switch (notificacion) {
			case vidaDaniada:
			case vidaCurada:
				if (healthBar != null && evt.getNewValue() instanceof Integer) { // verificación 1
					healthBar.setValue((Integer) evt.getNewValue());
				}
				break;
			case escudoDaniado:
				if (shieldBar != null && evt.getNewValue() instanceof Integer) { // verificación 2
					shieldBar.setValue((Integer) evt.getNewValue());
				}
				break;
			case muerte:

				this.addAction(Actions.sequence(Actions.fadeOut(0.5f), // animacion de desvanecerse
						Actions.removeActor() // removerse despues de la animación
				));
				break;

			case ataque:

			case escudoAumentado:
				if (shieldBar != null && evt.getNewValue() instanceof Integer) { // verificación 3
					shieldBar.setValue((Integer) evt.getNewValue());
				}
				break;

			case transicionA:

				spritesheet = new Texture((String) evt.getOldValue());
				TextureRegion[][] tmp = TextureRegion.split(spritesheet, spritesheet.getWidth() / FRAME_COLS,
						spritesheet.getHeight() / FRAME_ROWS);
				TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
				int index = 0;
				for (int i = 0; i < FRAME_ROWS; i++) {
					for (int j = 0; j < FRAME_COLS; j++) {
						frames[index++] = tmp[i][j];
					}
				}
				animacion = new Animation<>(0.25f, frames);
				stateTime = 0f;
				break;
			default:
				break;

			}
		} catch (IllegalArgumentException e) {
			System.err.println("Error al convertir el nombre del evento a Enum: " + evento);

		}
	}

	public Unidad getEnemigoModelo() {
		return enemigoModelo;
	}
}