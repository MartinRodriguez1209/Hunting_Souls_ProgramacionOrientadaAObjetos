package io.github.primer_test.Vista;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.badlogic.gdx.Gdx;

import io.github.primer_test.Sonidos.Sonido;
import io.github.primer_test.Sonidos.SonidoData;
import io.github.primer_test.Utils.SfxDataManager;
import io.github.primer_test.modelo.ENUMS.TiposNotificaciones;
import io.github.primer_test.modelo.Enemigos.Unidad;

public class SonidoVista implements PropertyChangeListener {
	
	private final SfxDataManager sfxManager;
    private final String unitName;

	    public SonidoVista(Unidad unidad) {	   
	    	
	    	this.sfxManager = SfxDataManager.getInstancia();

	        this.unitName = unidad.getClass().getSimpleName();
	        
	    	unidad.addPropertyChangeListener(this);
	    }
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		String evento = evt.getPropertyName();
		TiposNotificaciones notificacion = TiposNotificaciones.valueOf(evento);
		String actionName = null;
		
		try {
			switch (notificacion) {
			case vidaDaniada:
			case vidaCurada:

				break;
			case escudoDaniado:

				break;
			case muerte:				
				if (evt.getOldValue() instanceof String) {
			        actionName = (String) evt.getOldValue();
			    }
			    break;
			    
			case ataque:
			    
			    if (evt.getOldValue() instanceof String) {
			        actionName = (String) evt.getOldValue();
			    }
			    break;
                
                

			case escudoAumentado:
				
				break;

			
			default:
				break;

			}
			
			if (actionName != null) {
			    
			    Gdx.app.log("SFX_DEBUG", "Buscando SFX para: " + this.unitName + " -> " + actionName); 
			    
			    SonidoData datosSonido = sfxManager.getSfxData(this.unitName, actionName);
			    
			    if (datosSonido != null) {
			        
			        Gdx.app.log("SFX_DEBUG", "Reproduciendo archivo: " + datosSonido.getFileName());
			        Sonido.reproducir(datosSonido);
			    } else {
			        
			        Gdx.app.error("SFX_DEBUG", "ERROR: Clave de SFX no encontrada en JSON para: " + this.unitName + " -> " + actionName);
			    }
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Error al convertir el nombre del evento a Enum: " + evento);

		}
	}
}
