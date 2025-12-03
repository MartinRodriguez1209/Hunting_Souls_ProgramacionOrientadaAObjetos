package io.github.primer_test.Vista;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import io.github.primer_test.modelo.Cartas.Carta;
import io.github.primer_test.modelo.Cartas.CartaAtaque;
import io.github.primer_test.modelo.Cartas.CartaCuracion;
import io.github.primer_test.modelo.Cartas.CartaDefensa;

public class CartaVista extends Table implements Disposable, PropertyChangeListener {

	private final Image imagenCarta;
	private final Texture textura;
	private Image imagenCartaEspecifica; 
	private Texture texturaCartaEspecifica; 
	private final Label texto;
	private Label manaLabel;
	private Label nombreLabel;
	private final BitmapFont font;
	private final BitmapFont fontMana;
	private Carta carta;

	public CartaVista() {
		textura = new Texture("carta.png");
		imagenCarta = new Image(textura);

		// fuentes
		font = new BitmapFont(Gdx.files.internal("cartafont.fnt"));
		Label.LabelStyle estilo = new Label.LabelStyle();
		estilo.font = font;
		estilo.fontColor = Color.BROWN;

		fontMana = new BitmapFont(Gdx.files.internal("cartafont.fnt"));
		Label.LabelStyle estiloMana = new Label.LabelStyle();
		estiloMana.font = fontMana;
		estiloMana.fontColor = Color.BLACK;

		nombreLabel = new Label("", estilo);
		nombreLabel.setFontScale(0.3f);
		nombreLabel.setAlignment(Align.center);
		nombreLabel.setWrap(true);

		texto = new Label("", estilo);
		texto.setFontScale(0.3f);
		texto.setAlignment(Align.center);
		texto.setWrap(true);

		manaLabel = new Label("", estiloMana);
		manaLabel.setFontScale(0.4f);

		// group simple con posicionamiento manual
		Group cartaCompleta = new Group();
		cartaCompleta.addActor(imagenCarta);
		cartaCompleta.addActor(texto);
		cartaCompleta.addActor(manaLabel);
		cartaCompleta.addActor(nombreLabel);
		// tamaño imagen
		imagenCarta.setSize(100, 140);
		imagenCarta.setPosition(0, 0);

		texto.setPosition(5, 10); // abajo en el medio
		texto.setSize(90, 25); // tamaño fijo
		texto.setAlignment(Align.center); // centrar el texto
		texto.setWrap(true);
		manaLabel.setPosition(5, 125); // arriba a la izquierda
		nombreLabel.setPosition(5, 115); // arriba en el medio
		nombreLabel.setSize(90, 15);

		this.add(cartaCompleta).width(100).height(140);
		this.setOrigin(Align.center);
		this.setSize(this.getPrefWidth(), this.getPrefHeight());
	}

public void setCarta(Carta carta) {
    carta.addPropertyChangeListener(this);
    this.carta = carta;
    if (carta != null) {
        String textoMostrar = "";

        if (carta instanceof CartaAtaque) {
            CartaAtaque cartaAtaque = (CartaAtaque) carta;
            textoMostrar = cartaAtaque.getAtaque() + " de ataque";
        } else if (carta instanceof CartaDefensa) {
            CartaDefensa cartaDefensa = (CartaDefensa) carta;
            textoMostrar = cartaDefensa.getDefensa() + " de defensa";
        } else if (carta instanceof CartaCuracion) {
            CartaCuracion cartaCuracion = (CartaCuracion) carta;
            textoMostrar = cartaCuracion.getCuracion() + " de curacion";
        } else {
            textoMostrar = "Sin efecto"; // para otras cartas(ver si añadir descripcion en caso que no tenga
                                        // atributos, ejemplo carta matar)
        }
        // limpiamos imagen anterior si existe
        if (imagenCartaEspecifica != null) {
            imagenCartaEspecifica.remove(); // Remover imagen anterior del grupo
        }
        if (texturaCartaEspecifica != null) {
            texturaCartaEspecifica.dispose(); // Liberar textura anterior
        }

        // cargamos imagen
        texturaCartaEspecifica = new Texture(Gdx.files.internal(carta.getImagenPath()));
        imagenCartaEspecifica = new Image(texturaCartaEspecifica);

        imagenCartaEspecifica.setPosition(3, 50);
        imagenCartaEspecifica.setSize(95, 50); // Ancho: 90px, Alto: 35px

        // aañdimos la imagen al grupo de la carta
        Group cartaCompleta = (Group) this.getChildren().get(0);
        cartaCompleta.addActor(imagenCartaEspecifica);

        texto.setText(textoMostrar);
        manaLabel.setText(String.valueOf(carta.getCostoMana()));
        nombreLabel.setText(carta.getNombre());
    }
}

	@Override
	public void dispose() {
		textura.dispose();
		fontMana.dispose();
		font.dispose();
		texturaCartaEspecifica.dispose();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propiedad = evt.getPropertyName();
		Object valorViejo = evt.getOldValue();
		Object valorNuevo = evt.getNewValue();

		switch (propiedad) {

		case "cartaUsada":
			this.remove();
			break;
		}
	}
}