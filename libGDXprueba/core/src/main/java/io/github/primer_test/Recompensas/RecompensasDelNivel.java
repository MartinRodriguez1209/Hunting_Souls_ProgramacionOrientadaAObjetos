package io.github.primer_test.Recompensas;

import java.util.ArrayList;

public class RecompensasDelNivel {
    private ArrayList<Recompensa> recompensasEstadisticas;
    private ArrayList<Recompensa> opcionesHabilidades;
    
    public RecompensasDelNivel() {
        this.opcionesHabilidades = new ArrayList<>();
        this.recompensasEstadisticas = new ArrayList<>();
    }
    
    public ArrayList<Recompensa> getOpcionesHabilidades() {
        return opcionesHabilidades;
    }
    
    public void setOpcionesHabilidades(ArrayList<Recompensa> opcionesHabilidades) {
        this.opcionesHabilidades = opcionesHabilidades;
    }
    
    public void agregarOpcionHabilidad(Recompensa habilidad) {
        this.opcionesHabilidades.add(habilidad);
    }
    
    public ArrayList<Recompensa> getRecompensasEstadisticas() {
        return recompensasEstadisticas;
    }
    
    public void setRecompensasEstadisticas(ArrayList<Recompensa> recompensasEstadisticas) {
        this.recompensasEstadisticas = recompensasEstadisticas;
    }
    
    public void agregarRecompensaEstadistica(Recompensa recompensa) {
        this.recompensasEstadisticas.add(recompensa);
    }
    
    @Override
    public String toString() {
        return "Recompensas del Nivel - Estadísticas: " + recompensasEstadisticas.size() + 
               ", Habilidades disponibles: " + opcionesHabilidades.size();
    }
}