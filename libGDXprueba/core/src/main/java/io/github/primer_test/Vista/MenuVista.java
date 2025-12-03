package io.github.primer_test.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.Controlador.MenuControlador;

public class MenuVista {
	private Stage stage;
	private Texture fondoTexture;
	private MenuControlador controlador;
	private Skin skin;

	public MenuVista(MenuControlador controlador) {
		this.controlador = controlador;
	}

	public void show() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// Fondo
		fondoTexture = new Texture(Gdx.files.internal("fondo_menu.jpg"));
		Image fondo = new Image(new TextureRegion(fondoTexture));
		fondo.setFillParent(true);
		stage.addActor(fondo);

		// Cargo skin
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
		// Fuente título
		BitmapFont fuenteTitulo = new BitmapFont(Gdx.files.internal("titulofont.fnt"));
		BitmapFont fuenteSubtitulo = new BitmapFont(Gdx.files.internal("titulofont.fnt"));
		fuenteTitulo.getData().setScale(2f);
		fuenteSubtitulo.getData().setScale(0.55f);
		LabelStyle estiloTitulo = new LabelStyle();
		estiloTitulo.font = fuenteTitulo;
		estiloTitulo.fontColor = Color.RED;
		LabelStyle estiloSubtitulo = new LabelStyle();
		estiloSubtitulo.font = fuenteSubtitulo;
		estiloSubtitulo.fontColor = Color.FIREBRICK;
		Label titulo = new Label("HUNTING SOULS", estiloTitulo);
		Label subtitulo = new Label("Face the forgotten souls of the monastery…", estiloSubtitulo);
		Table tablaTitulo = new Table();
		tablaTitulo.setFillParent(true);
		tablaTitulo.top();
		tablaTitulo.add(titulo).padTop(20);
		tablaTitulo.row();
		tablaTitulo.add(subtitulo).padTop(5);
		stage.addActor(tablaTitulo);

		// Botones
		TextButton jugarButton = new TextButton("JUGAR", skin, "round");
		TextButton rankingButton = new TextButton("HISTORIAL", skin, "round");
		TextButton tutorialButton = new TextButton("TUTORIAL", skin, "round");
		TextButton salirButton = new TextButton("SALIR", skin, "round");
		Button sonidoButton = new Button(skin, "music");
		Label version = new Label("v.0.1.2", estiloTitulo);

		Table tablaSonido = new Table();
		tablaSonido.setFillParent(true);
		tablaSonido.top().right();
		tablaSonido.add(sonidoButton).pad(10).size(50, 50);
		stage.addActor(tablaSonido);

		Table tablaVersion = new Table();
		tablaVersion.setFillParent(true);
		tablaVersion.bottom().right();
		version.setFontScale(1f);
		tablaVersion.add(version).pad(5).size(100, 100);
		stage.addActor(tablaVersion);

		Table tablaBotones = new Table();
		tablaBotones.setFillParent(true);
		tablaBotones.center();
		tablaBotones.add(jugarButton).pad(10).size(180, 60).row();
		tablaBotones.add(rankingButton).pad(10).size(180, 60).row();
		tablaBotones.add(tutorialButton).pad(10).size(180, 60).row();
		tablaBotones.add(salirButton).pad(10).size(180, 60).row();
		stage.addActor(tablaBotones);

		// Listeners y clases anonimas
		jugarButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.iniciarJuego();
			}
		});

		rankingButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.mostrarRanking(); //
			}
		});

		salirButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.salirJuego();
			}
		});

		sonidoButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.MusicaJuego();
			}
		});
		tutorialButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.mostrarTutorial();
			}
		});
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
		fondoTexture.dispose();
		skin.dispose();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}
