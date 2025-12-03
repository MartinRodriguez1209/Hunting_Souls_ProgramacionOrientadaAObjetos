package io.github.primer_test.Vista;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.Jugador;

public class EstadisticasVista extends Table implements Disposable, PropertyChangeListener {

	private final Image imagenFondo;
	private final Texture textura;
	private final BitmapFont font;

	private Label nombreLabel;
	private Label vidaLabel;
	private Label escudoLabel;
	private Label manaLabel;
	private Jugador jugador;

	public EstadisticasVista() {
		textura = new Texture("fondo_estadisticas.png");
		imagenFondo = new Image(textura);
		font = new BitmapFont(Gdx.files.internal("minecraft.fnt"));
		font.getData().setScale(0.45f);
		Label.LabelStyle estilo = new Label.LabelStyle(font, Color.WHITE);

		nombreLabel = new Label("", estilo);
		nombreLabel.setAlignment(Align.center);

		vidaLabel = new Label("", estilo);
		vidaLabel.setAlignment(Align.left);

		escudoLabel = new Label("", estilo);
		escudoLabel.setAlignment(Align.left);

		manaLabel = new Label("", estilo);
		manaLabel.setAlignment(Align.left);

		Stack stack = new Stack();
		stack.setSize(imagenFondo.getWidth(), imagenFondo.getHeight());

		Table labelsTable = new Table();
		labelsTable.top().left();

		labelsTable.add(nombreLabel).left().pad(7).row();
		labelsTable.add(vidaLabel).left().pad(7).row();
		labelsTable.add(escudoLabel).left().pad(7).row();
		labelsTable.add(manaLabel).left().pad(7).row();

		stack.add(imagenFondo); // fondo detrás
		stack.add(labelsTable); // labels encima

		this.add(stack).width(190).height(130);
		this.setSize(100, 150);
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
		jugador.addPropertyChangeListener(this);
		nombreLabel.setText("NOMBRE              " + jugador.getNombre());
		vidaLabel.setText("VIDA              " + jugador.getVida() + " / " + jugador.getVidaTotal());
		escudoLabel.setText("ESCUDO              " + jugador.getEscudo());
		manaLabel.setText("MANA              " + jugador.getManaActual());
	}

	@Override
	public void dispose() {
		textura.dispose();
		font.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Convertimos el String del evento a nuestro enum para un switch seguro.
		try {
			TiposNotificaciones notificacion = TiposNotificaciones.valueOf(evt.getPropertyName());

			switch (notificacion) {
			case vidaDaniada:
			case vidaCurada:
				vidaLabel.setText("VIDA              " + jugador.getVidaActual() + " / " + jugador.getVidaTotal());
				break;

			case escudoAumentado:
			case escudoDaniado:
				escudoLabel.setText("ESCUDO              " + jugador.getEscudo());
				break;

			case manaRestaurado:
			case manaGastado: // Suponiendo que tienes un evento para esto
				manaLabel.setText("MANA              " + jugador.getManaActual());
				break;
			}
		} catch (IllegalArgumentException e) {
			// Ignorar eventos que no nos interesan
		}
	}
}