package io.github.primer_test.Sonidos;

public final class SonidoData {
    private final String fileName;
    private final float volume;
    private final boolean loop;
    
    public SonidoData(String fileName, float volume, boolean loop) {
        this.fileName = fileName;
        this.volume = volume;
        this.loop = loop;
    }

	public String getFileName() {
		return fileName;
	}
    
	public float getVolume() {
		return volume;
	}
    
    public boolean getLoop() {
    	return loop;
    }
}