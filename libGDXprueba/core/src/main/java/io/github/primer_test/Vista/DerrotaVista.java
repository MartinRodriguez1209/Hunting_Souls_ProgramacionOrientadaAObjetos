package io.github.primer_test.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.Controlador.MenuControlador;

public class DerrotaVista {

    private Stage stage;
    private MenuControlador controlador;
    private Texture fondoTextura;
    private Skin skin;
    private BitmapFont fuenteTitulo;

    public DerrotaVista(MenuControlador controlador) {
        this.controlador = controlador;
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        fondoTextura = new Texture(Gdx.files.internal("derrota.png"));
        Image fondo = new Image(fondoTextura);
        fondo.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(fondo);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        fuenteTitulo = new BitmapFont(Gdx.files.internal("titulofont.fnt"));
        fuenteTitulo.getData().setScale(2f); 
        LabelStyle estiloTitulo = new LabelStyle();
        estiloTitulo.font = fuenteTitulo;
        estiloTitulo.fontColor = Color.FIREBRICK;

        Label titulo = new Label("HAS SIDO DERROTADO", estiloTitulo);        
        Label subtitulo = new Label("El mal ha triunfado sobre ti", estiloTitulo);

        subtitulo.setFontScale(0.75f); 

        Table tablaTitulo = new Table();
        tablaTitulo.setFillParent(true);
        tablaTitulo.top(); 
        tablaTitulo.add(titulo).padTop(100); 
        tablaTitulo.row(); 
        tablaTitulo.add(subtitulo).padTop(15); 
        stage.addActor(tablaTitulo);


        Table tablaBotones = new Table();
        tablaBotones.setFillParent(true);
        tablaBotones.bottom().padBottom(50);
        stage.addActor(tablaBotones);

        TextButton botonVolverMenu = new TextButton("VOLVER AL MENU", skin);
        TextButton botonSalir = new TextButton("SALIR", skin);

        tablaBotones.add(botonVolverMenu).pad(10).size(180, 60);
        tablaBotones.add(botonSalir).pad(10).size(180, 60);

        botonVolverMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlador.volverMenuPrincipal();
            }
        });

        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlador.salirJuego();
            }
        });
    }

    public Stage getStage() {
        return this.stage;
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
        if (skin != null) skin.dispose();
        if (fuenteTitulo != null) fuenteTitulo.dispose();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}