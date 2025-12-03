package io.github.primer_test.Sonidos;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sonido {

	private static final java.util.Map<String, Sound> sfxCache = new HashMap<>();
	private static final java.util.Map<String, Music> musicCache = new HashMap<>();
	
	public static void precargarSfx(String fileName) {
	    if (!sfxCache.containsKey(fileName)) {
	        sfxCache.put(fileName, Gdx.audio.newSound(Gdx.files.internal(fileName)));
            Gdx.app.log("SFX", "Cargado: " + fileName);
	    }
	}
	
	public static void disposeTodo() {
	    // Limpiar SFX
	    for (Sound sound : sfxCache.values()) {
	        sound.dispose();
	    }
	    sfxCache.clear();
	    
	    // Limpiar Music
	    for (Music music : musicCache.values()) {
	        music.dispose();
	    }
	    musicCache.clear();
	}
	
	public static void reproducirSfx(String fileName, float volume) {
        
       
        Sound sound = sfxCache.get(fileName);

        
        if (sound != null) {
        	long id = sound.play(volume);
        	Gdx.app.log("SFX_PLAY", "Intentando reproducir: " + fileName + " con volumen: " + volume);
            sound.play(volume); 
            
        } else {
            Gdx.app.error("SFX_ERROR", "El sonido '" + fileName + "' no está precargado en el caché.");
        }
    }

    public static void reproducirMusica(String fileName, boolean loop, float volume) {

        final Music music = Gdx.audio.newMusic(Gdx.files.internal(fileName));
        
        music.setLooping(loop);
        music.setVolume(volume);
        music.play();
        musicCache.put(fileName, music);

        if (!loop) {
            music.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    music.dispose();
                }
            });
        }
    }

    public static void reproducir(SonidoData datos) {

        if (datos.getLoop()) {
        	reproducirMusica(datos.getFileName(), datos.getLoop(), datos.getVolume());
        } else {
        	reproducirSfx(datos.getFileName(), datos.getVolume());
        }
    }
}