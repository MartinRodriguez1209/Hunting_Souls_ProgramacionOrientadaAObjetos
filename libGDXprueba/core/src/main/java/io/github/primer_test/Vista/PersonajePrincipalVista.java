package io.github.primer_test.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

import io.github.primer_test.modelo.Usuario.Jugador;

//sigue la misma logica que enemigo vista, capaz deberia juntarlas y que trabajen con la abstraccion unidad para no repetir codigo

public class PersonajePrincipalVista extends Table implements Disposable {

	private final Animation<TextureRegion> animacion;
	private float stateTime;
	private final Texture spritesheet;
	private static final int FRAME_COLS = 4, FRAME_ROWS = 1;

	public PersonajePrincipalVista(Jugador jugadorModelo) {
		// TODO: esta estatico deberia ser por parametro
		spritesheet = new Texture(Gdx.files.internal("personaje_principal_sheet.png"));
		TextureRegion[][] tmp = TextureRegion.split(spritesheet, spritesheet.getWidth() / FRAME_COLS,
				spritesheet.getHeight() / FRAME_ROWS);
		TextureRegion[] frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		animacion = new Animation<>(0.2f, frames);
		stateTime = 0f;
		Image imagenJugador = new Image(animacion.getKeyFrame(stateTime, true));
		imagenJugador.setSize(100, 100);
		this.add(imagenJugador).expand().fill().center();
		this.setDebug(true);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;

		Image imagenJugador = (Image) this.getChildren().get(0);
		imagenJugador.setDrawable(new TextureRegionDrawable(animacion.getKeyFrame(stateTime, true)));
	}

	@Override
	public void dispose() {
		spritesheet.dispose();
	}

}
