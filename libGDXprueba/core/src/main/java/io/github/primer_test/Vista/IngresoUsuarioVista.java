package io.github.primer_test.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.Controlador.MenuControlador;

public class IngresoUsuarioVista {

	private Label errorLabel;
	private Stage stage;
	private Texture fondoTexture;
	private BitmapFont fuenteTitulo;
	private TextField campoNombre;
	private MenuControlador controlador;
	private Skin skin;

	public IngresoUsuarioVista(MenuControlador controlador) {
		this.controlador = controlador;
	}

	public void show() {
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		// Fondo
		fondoTexture = new Texture(Gdx.files.internal("fondo_menu.jpg"));
		Image fondo = new Image(fondoTexture);
		fondo.setFillParent(true);
		stage.addActor(fondo);

		// Cargar skin Shade
		skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

		// Fuente personalizada para el título
		fuenteTitulo = new BitmapFont(Gdx.files.internal("titulofont.fnt"));
		fuenteTitulo.getData().setScale(2f);

		LabelStyle estiloTitulo = new LabelStyle(fuenteTitulo, Color.RED);
		Label titulo = new Label("HUNTING SOULS", estiloTitulo);
		Label version = new Label("v.0.1.2", estiloTitulo);
		// posicionamiento titulo

		BitmapFont fuenteSubtitulo = new BitmapFont(Gdx.files.internal("titulofont.fnt"));
		fuenteSubtitulo.getData().setScale(0.55f);

		LabelStyle estiloSubtitulo = new LabelStyle();
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        errorLabel.setVisible(false);
		estiloSubtitulo.font = fuenteSubtitulo;
		estiloSubtitulo.fontColor = Color.FIREBRICK;
		// Texto de ingreso con estilo de skin
		Label textoIngreso = new Label("INGRESE UN NOMBRE DE USUARIO", skin, "title-plain");
		Label subtitulo = new Label("Face the forgotten souls of the monastery…", estiloSubtitulo);

		Table tablaTitulo = new Table();
		tablaTitulo.setFillParent(true);
		tablaTitulo.top();
		tablaTitulo.add(titulo).padTop(20);
		tablaTitulo.row();
		tablaTitulo.add(subtitulo).padTop(5);
		stage.addActor(tablaTitulo);
		// Campo de texto con estilo de skin
		campoNombre = new TextField("", skin);
		campoNombre.setMessageText("NOMBRE");

		// Botón aceptar con estilo de skin
		TextButton aceptarButton = new TextButton("ACEPTAR", skin, "round");

		Table tablaVersion = new Table();
		tablaVersion.setFillParent(true);
		tablaVersion.bottom().right();
		version.setFontScale(1f);
		tablaVersion.add(version).pad(5).size(100, 100);
		stage.addActor(tablaVersion);

		// boton music
		Button sonidoButton = new Button(skin, "music");
		Table tablaSonido = new Table();
		tablaSonido.setFillParent(true);
		tablaSonido.top().right();
		tablaSonido.add(sonidoButton).pad(10).size(50, 50);
		stage.addActor(tablaSonido);

		// Tabla principal centrada
		Table tablaCentral = new Table();
		tablaCentral.setFillParent(true);
		tablaCentral.center();
		tablaCentral.add(textoIngreso).padBottom(10).row();
		tablaCentral.add(campoNombre).width(300).height(50).padBottom(10).row();
		tablaCentral.add(aceptarButton).width(200).height(50).padBottom(10);
		tablaCentral.row();
		tablaCentral.add(errorLabel).padTop(10);
		stage.addActor(tablaCentral);

		// Botón flecha para volver (imagen personalizada)
		Texture flechaTexture = new Texture(Gdx.files.internal("izquierda.png"));
		ImageButton botonVolver = new ImageButton(new Image(flechaTexture).getDrawable());

		botonVolver.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.volverMenuPrincipal();
			}
		});

		sonidoButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.MusicaJuego();
			}
		});
		aceptarButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.aceptarBoton();
			}
		});

		Table tablaFlecha = new Table();
		tablaFlecha.setFillParent(true);
		tablaFlecha.top().left();
		tablaFlecha.add(botonVolver).pad(10).size(30, 30);
		stage.addActor(tablaFlecha);
	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose() {
		stage.dispose();
		fondoTexture.dispose();
		fuenteTitulo.dispose();
		skin.dispose();
	}

	public String getNombreIngresado() {
		return campoNombre.getText();
	}
	public void mostrarError(String mensaje) {
		errorLabel.setText(mensaje);
		errorLabel.setVisible(true);
	}
}
