package io.github.primer_test.Vista;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.primer_test.Controlador.PausaControlador;

public class PausaVista {
    private Stage stage;
    private PausaControlador controlador;
    private Skin skin;
    private Table overlay;
    private boolean disposed = false;

    public PausaVista(PausaControlador controlador) {
        this.controlador = controlador;
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        overlay = new Table();
        overlay.setFillParent(true);

        BitmapFont fuenteTitulo = new BitmapFont(Gdx.files.internal("titulofont.fnt"));
        fuenteTitulo.getData().setScale(1.5f);
        LabelStyle estiloTitulo = new LabelStyle();
        estiloTitulo.font = fuenteTitulo;
        estiloTitulo.fontColor = Color.RED;

        Label titulo = new Label("JUEGO PAUSADO", estiloTitulo);
        TextButton botonReanudar = new TextButton("REANUDAR", skin, "round");
        TextButton botonMenuPrincipal = new TextButton("MENU PRINCIPAL", skin, "round");
        TextButton botonSalir = new TextButton("SALIR", skin, "round");

        Table contenido = new Table();
        contenido.add(titulo).pad(20).row();
        contenido.add(botonReanudar).pad(10).size(200, 50).row();
        contenido.add(botonMenuPrincipal).pad(10).size(200, 50).row();
        contenido.add(botonSalir).pad(10).size(200, 50).row();

        overlay.add(contenido);
        overlay.setVisible(false);
        stage.addActor(overlay);

        botonReanudar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlador.reanudar();
            }
        });

        botonMenuPrincipal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlador.volverAlMenu();
            }
        });

        botonSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controlador.salir();
            }
        });
    }

    public void render() {
        if (stage != null) {
            stage.act();
            stage.draw();
        }
    }

    public void setVisible(boolean visible) {
        if (overlay != null) {
            overlay.setVisible(visible);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    public void dispose() {
    if (!disposed) {
        if (stage != null) {
            stage.dispose();
        }
        if (skin != null) {
            skin.dispose();
        }
        disposed = true;
    }
}
}
