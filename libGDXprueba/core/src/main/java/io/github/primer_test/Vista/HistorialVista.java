package io.github.primer_test.Vista;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.Controlador.HistorialControlador;
import io.github.primer_test.Controlador.MenuControlador;
import io.github.primer_test.modelo.Historial.Historial;

public class HistorialVista {

    private Stage stage;
    private Skin skin;
    private Texture fondoTexture;
    private Texture flechaTexture;
    
    private MenuControlador menuControlador;
    private HistorialControlador historialControlador;

    public HistorialVista(MenuControlador menuControlador) {
        this.menuControlador = menuControlador;
        this.historialControlador = new HistorialControlador();
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        fondoTexture = new Texture(Gdx.files.internal("fondo_menu.jpg"));
        Image fondo = new Image(fondoTexture);
        fondo.setFillParent(true);
        stage.addActor(fondo);

        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        flechaTexture = new Texture(Gdx.files.internal("izquierda.png"));
        ImageButton botonVolver = new ImageButton(new Image(flechaTexture).getDrawable());

        botonVolver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuControlador.volverMenuPrincipal();
            }
        });

        Table tablaFlecha = new Table();
        tablaFlecha.setFillParent(true);
        tablaFlecha.top().left();
        tablaFlecha.add(botonVolver).pad(10).size(30, 30);
        stage.addActor(tablaFlecha);

        Table tablaStats = new Table();
        tablaStats.setFillParent(true);
        tablaStats.center();
        
        Label titulo = new Label("HISTORIAL DE PARTIDAS", skin, "title-plain");
        tablaStats.add(titulo).colspan(6).padBottom(20);
        tablaStats.row();

        tablaStats.add(new Label("NOMBRE", skin)).pad(10).minWidth(150);
        tablaStats.add(new Label("DANIO REALIZADO", skin)).pad(10).minWidth(150);
        tablaStats.add(new Label("CARTAS USADAS", skin)).pad(10).minWidth(150);
        tablaStats.add(new Label("ENEMIGOS MUERTOS", skin)).pad(10).minWidth(150);
        tablaStats.add(new Label("CURACION RECIBIDA", skin)).pad(10).minWidth(150);
        tablaStats.add(new Label("DANIO RECIBIDO", skin)).pad(10).minWidth(150);
        tablaStats.add(new Label("RESULTADO", skin)).pad(10).minWidth(150);
        tablaStats.row();

        List<Historial> historial = historialControlador.getDatosHistorial();

        for (Historial partida : historial) {
            tablaStats.add(new Label(partida.getNombreJugador(), skin)).pad(5);
            tablaStats.add(new Label(String.valueOf(partida.getDanioRealizado()), skin)).pad(5);
            tablaStats.add(new Label(String.valueOf(partida.getCantidadCartasUsadas()), skin)).pad(5);
            tablaStats.add(new Label(String.valueOf(partida.getCantidadEnemigosMuertos()), skin)).pad(5);
            tablaStats.add(new Label(String.valueOf(partida.getCuracionRecibida()), skin)).pad(5);
            tablaStats.add(new Label(String.valueOf(partida.getDanioRecibido()), skin)).pad(5);
            tablaStats.add(new Label(partida.getResultado(), skin)).pad(5);
            tablaStats.row();
        }

        stage.addActor(tablaStats);
    }

    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        fondoTexture.dispose();
        flechaTexture.dispose();
    }
}