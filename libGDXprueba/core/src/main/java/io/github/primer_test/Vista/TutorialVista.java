package io.github.primer_test.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.Controlador.MenuControlador;

public class TutorialVista {

    private Stage stage;
    private MenuControlador controlador;

    private Texture fondoTextura;
    private Texture flechaTexture;

    public TutorialVista(MenuControlador controlador) {
        this.controlador = controlador;
    }
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); 
        fondoTextura = new Texture(Gdx.files.internal("tutorial.png"));
        Image fondo = new Image(fondoTextura);
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(fondo);

        Table tablaBotones = new Table();
        tablaBotones.setFillParent(true);
        tablaBotones.top().left();
        flechaTexture = new Texture(Gdx.files.internal("izquierda.png"));
        ImageButton botonVolver = new ImageButton(new Image(flechaTexture).getDrawable());
        tablaBotones.add(botonVolver).pad(10).size(30,30);
        stage.addActor(tablaBotones);
        botonVolver.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.volverMenuPrincipal();
			}
		});
    }

    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        fondoTextura.dispose();
        flechaTexture.dispose();
    }
}