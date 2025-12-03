package io.github.primer_test.Controlador;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.github.primer_test.Vista.PeleaVista;
import io.github.primer_test.Vista.UnidadVista;
import io.github.primer_test.modelo.Enemigos.BossFinal;
import io.github.primer_test.modelo.Enemigos.Unidad;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Usuario.AdministradorTurnoJugador;
import io.github.primer_test.modelo.Usuario.Jugador;

public class BossFinalControlador extends PeleaControlador implements PropertyChangeListener {

    private final BossFinal boss;

    public BossFinalControlador(PeleaVista peleaView, BossFinal boss, Jugador jugador){
        super(peleaView, new ArrayList<>(List.of(boss)),jugador,null);
        this.boss = boss;

    
        UnidadVista vistaJefe = new UnidadVista(boss); //vista con property change
        this.peleaView.getEnemigosPanel().add(vistaJefe).height(200).expand().fill();

        boss.addPropertyChangeListener(this);
        boss.addPropertyChangeListener(vistaJefe);
        vistaJefe.addListener(new ClickListener(){

        @Override
        public void clicked(InputEvent event, float x, float y) {

        administradorTurnoJugador.seleccionarUnidadObjetivo(boss, jugador);
        }
        });
        }
    
        @Override
            protected void turnoEnemigos(){
            peleaView.getCajaTextoTable().appendText("Turno del Boss Final\n");
            boss.turno(jugador, new ArrayList<>());
            iniciarTurnoJugador();
            }

            @Override
            protected void iniciarTurnoJugador(){
                super.iniciarTurnoJugador();
                peleaView.getCajaTextoTable().appendText("Turno del jugador\n");
            }

    @Override
	public void propertyChange(PropertyChangeEvent evt) {
		String notificacion = evt.getPropertyName();

		switch (notificacion) {

        case "transicionA":
            peleaView.getCajaTextoTable().appendText("El boss ha cambiado de forma:" + ((BossFinal) evt.getSource()).getEstadoActualBoss().getClass().getSimpleName() + "\n");
            break;
		case "muerte":
            peleaView.getCajaTextoTable().appendText("El Boss Final ha sido derrotado");
            casoVictoria();
            break;
        default:
        break;
		}

	}
}






