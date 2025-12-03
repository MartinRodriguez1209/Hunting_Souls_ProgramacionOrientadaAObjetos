package io.github.primer_test.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

public class JsonManager {

	private static JsonManager instancia;

	private final Json json;
	private final DataJson data;
	private final CartasDataBaraja cartas;

	private JsonManager() {
		// aca lo tengo que abrir al json
		json = new Json();
		data = json.fromJson(DataJson.class, Gdx.files.internal("sheets.json"));
		System.out.println(data);

		// copia de cartas
		cartas = json.fromJson(CartasDataBaraja.class, Gdx.files.internal("cartas.json"));

		for (int i = 0; i < cartas.tipoAtaque.size; i++) {
			System.out.println(cartas.tipoAtaque.get(i).toString());

		}
	}

	public static JsonManager getInstanciaJsonManager() {
		if (instancia == null) {
			instancia = new JsonManager();
		}
		return instancia;
	}

	public String getPathEnemigoSprite(String nombreEnemigo) {
		for (EnemigosDataSprite ene : data.unidadesData) {
			if (ene.name.equals(nombreEnemigo))
				return ene.path;
		}
		System.out.println(nombreEnemigo);
		throw new GdxRuntimeException("este enemigo no tiene sprite sheet asociado");
	}

	public Integer getRowsEnemigoSprite(String nombreEnemigo) {
		for (EnemigosDataSprite ene : data.unidadesData) {
			if (ene.name.equals(nombreEnemigo))
				return ene.cols;
		}
		throw new GdxRuntimeException("este enemigo no tiene sprite sheet asociado");
	}

	public Integer getColsEnemigoSpriteInteger(String nombreEnemigo) {
		for (EnemigosDataSprite ene : data.unidadesData) {
			if (ene.name.equals(nombreEnemigo))
				return ene.rows;
		}
		throw new GdxRuntimeException("este enemigo no tiene sprite sheet asociado");

	}

}
