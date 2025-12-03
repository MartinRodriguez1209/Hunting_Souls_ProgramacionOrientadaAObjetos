package io.github.primer_test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
	private static final int FRAME_COLS = 4, FRAME_ROWS =1;
	Animation<TextureRegion> walkAnimation;
	Texture animacion;
	SpriteBatch spriteBatch;
	float stateTime;
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		animacion = new Texture("fantasmaSpriteSheet.png");
		TextureRegion[][] tmp = TextureRegion.split(animacion,
				animacion.getWidth() / FRAME_COLS,
				animacion.getHeight() / FRAME_ROWS);
		
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation<TextureRegion>(0.2f, walkFrames);
		
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		
		float scale = 0.5f; // la mitad de su tamaño
		float width = currentFrame.getRegionWidth() * scale;
		float height = currentFrame.getRegionHeight() * scale;

		
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 50, 50, width, height);// Draw current frame at (50, 50)
		spriteBatch.end();
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		animacion.dispose();
		
	}
}
