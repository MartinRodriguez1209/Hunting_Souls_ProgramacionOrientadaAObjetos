package io.github.primer_test.Vista;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import io.github.primer_test.Controlador.RecompensasControlador;
import io.github.primer_test.Recompensas.Recompensa;
import io.github.primer_test.Recompensas.RecompensasDelNivel;
import io.github.primer_test.Recompensas.TipoRecompensa;

public class RecompensasVista {

	private Stage stage;
	private Skin skin;
	private SpriteBatch batch;
	private Texture fondoTextura;
	private Texture overlayTextura;

	private Table overlayEstadisticas;
	private Table overlayHabilidades;

	private RecompensasControlador controlador;
	private RecompensasDelNivel recompensasCompletas;

	public RecompensasVista() {
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		batch = new SpriteBatch();
		fondoTextura = new Texture(Gdx.files.internal("fondo_recompensas.png"));
		overlayTextura = new Texture(Gdx.files.internal("overlay_recompensas.png"));
		crearOverlays();
	}

	private void crearOverlays() {
		overlayEstadisticas = new Table();
		overlayHabilidades = new Table();

		overlayEstadisticas.setSize(400, 300);
		overlayHabilidades.setSize(400, 300);

		overlayEstadisticas.setPosition(600, 250);
		overlayHabilidades.setPosition(600, 250);

		stage.addActor(overlayEstadisticas);
		stage.addActor(overlayHabilidades);

		ocultarTodosLosOverlays();
	}

	public void setControlador(RecompensasControlador controlador) {
		this.controlador = controlador;
	}

	public void iniciarSecuenciaRecompensas(RecompensasDelNivel recompensas, RecompensasControlador controlador) {
		this.recompensasCompletas = recompensas;
		this.controlador = controlador;
		configurarOverlayEstadisticas();
		configurarOverlayHabilidades();
		aplicarTodasLasRecompensasEstadisticas();
		mostrarOverlayEstadisticas();
	}

	private void aplicarTodasLasRecompensasEstadisticas() {
		ArrayList<Recompensa> recompensasEst = recompensasCompletas.getRecompensasEstadisticas();
		if (recompensasEst != null && !recompensasEst.isEmpty()) {
			for (Recompensa recompensa : recompensasEst) {
				controlador.aplicarRecompensa(recompensa);
			}
		}
	}

	private void configurarOverlayEstadisticas() {
		overlayEstadisticas.clear();
		overlayEstadisticas.setBackground(crearFondoOverlay());
		Label titulo = new Label("¡NIVEL COMPLETADO!", skin);
		titulo.setColor(Color.WHITE);
		overlayEstadisticas.add(titulo).padBottom(20);
		overlayEstadisticas.row();

		ArrayList<Recompensa> recompensasEst = recompensasCompletas.getRecompensasEstadisticas();
		if (recompensasEst != null && !recompensasEst.isEmpty()) {
			for (Recompensa recompensa : recompensasEst) {
				String descripcion = "+" + recompensa.getValor() + " " + getNombreBonificacion(recompensa.getTipo());
				Label labelRecompensa = new Label(descripcion, skin);
				labelRecompensa.setColor(Color.WHITE);
				overlayEstadisticas.add(labelRecompensa).padBottom(10);
				overlayEstadisticas.row();
			}
		}

		overlayEstadisticas.add(new Label("", skin)).padBottom(20);
		overlayEstadisticas.row();

		TextButton botonContinuar = new TextButton("CONTINUAR", skin);
		botonContinuar.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				mostrarOverlayHabilidades();
			}
		});
		overlayEstadisticas.add(botonContinuar).width(200).height(60);
	}

	private String getNombreBonificacion(TipoRecompensa tipo) {
		switch (tipo) {
		case VIDA_EXTRA:
			return "Vida Máxima";
		case MANA_EXTRA:
			return "Maná Máximo";
		case ESCUDO_EXTRA:
			return "Escudo";
		default:
			return tipo.name();
		}
	}

	private void mostrarOverlayEstadisticas() {
		overlayEstadisticas.setVisible(true);
		overlayHabilidades.setVisible(false);
	}

	private void configurarOverlayHabilidades() {
		overlayHabilidades.clear();
		overlayHabilidades.setBackground(crearFondoOverlay());

		Label titulo = new Label("¡ELIGE UNA HABILIDAD!", skin);
		titulo.setColor(Color.WHITE);
		overlayHabilidades.add(titulo).colspan(2).padBottom(30);
		overlayHabilidades.row();
		TextButton botonContinuar = new TextButton("CONTINUAR", skin);
		botonContinuar.setVisible(true);
		botonContinuar.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controlador.completarRecompensas();
			}
		});
		overlayHabilidades.add(botonContinuar).width(200).height(60);

		ArrayList<Recompensa> opciones = recompensasCompletas.getOpcionesHabilidades();

		if (opciones.isEmpty()) {
			Label noHabilidades = new Label("No hay habilidades disponibles", skin);
			noHabilidades.setColor(Color.WHITE);
			overlayHabilidades.add(noHabilidades).colspan(2);
			return;
		}

		for (int i = 0; i < opciones.size(); i++) {
			Recompensa opcion = opciones.get(i);

			TextButton botonHabilidad = new TextButton(opcion.toString(), skin);
			final int indice = i;
			botonHabilidad.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					controlador.aplicarHabilidad(indice);
					controlador.completarRecompensas();
				}
			});

			overlayHabilidades.add(botonHabilidad).width(300).height(80).pad(10);
			if ((i + 1) % 2 == 0)
				overlayHabilidades.row();
		}

	}

	private void mostrarOverlayHabilidades() {
		ocultarTodosLosOverlays();
		overlayHabilidades.setVisible(true);
	}

	private void ocultarTodosLosOverlays() {
		overlayEstadisticas.setVisible(false);
		overlayHabilidades.setVisible(false);
	}

	private Drawable crearFondoOverlay() {
		return new TextureRegionDrawable(new TextureRegion(overlayTextura));
	}

	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	public void hide() {
		ocultarTodosLosOverlays();
	}

	public void render() {
		batch.begin();
		batch.draw(fondoTextura, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		stage.act();
		stage.draw();
	}

	public void dispose() {
		stage.dispose();
		skin.dispose();
		batch.dispose();
		fondoTextura.dispose();
		overlayTextura.dispose();
	}

	public Stage getStage() {
    	return this.stage;
	}
}
