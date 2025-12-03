package io.github.primer_test.Recompensas;

import java.util.ArrayList; 

import io.github.primer_test.modelo.Enemigos.Enemigov3;

public class SistemaRecompensas {

    public RecompensasDelNivel generarRecompensasCompletas(ArrayList<Enemigov3> enemigosDerrrotados, int nivelCompletado) {
        RecompensasDelNivel recompensas = new RecompensasDelNivel();

        recompensas.setRecompensasEstadisticas(generarRecompensasEstadisticasPorNivel(nivelCompletado));
        recompensas.setOpcionesHabilidades(generarOpcionesHabilidades(enemigosDerrrotados));

        return recompensas;
    }

    private ArrayList<Recompensa> generarRecompensasEstadisticasPorNivel(int nivel) {
        ArrayList<Recompensa> recompensasEstadisticas = new ArrayList<>();
        
        int vidaBonus = 5 + (nivel * 4);
        int manaBonus = 1 + (nivel * 2);
        int escudoBonus = 5 + (nivel * 2);
                
        recompensasEstadisticas.add(new Recompensa(TipoRecompensa.VIDA_EXTRA, vidaBonus));
        recompensasEstadisticas.add(new Recompensa(TipoRecompensa.MANA_EXTRA, manaBonus));
        recompensasEstadisticas.add(new Recompensa(TipoRecompensa.ESCUDO_EXTRA, escudoBonus));
        
        return recompensasEstadisticas;
    }

    private ArrayList<Recompensa> generarOpcionesHabilidades(ArrayList<Enemigov3> enemigos) {
        ArrayList<Recompensa> opciones = new ArrayList<>();
        for (Enemigov3 enemigo : enemigos) {
            Recompensa recompensaPotencial = enemigo.getRecompensaHabilidad();
            if (recompensaPotencial == null) {
                continue;
            }
            boolean yaEstaEnLaLista = false;
            for (Recompensa recompensaExistente : opciones) {                
                boolean mismoTipo = recompensaExistente.getTipo() == recompensaPotencial.getTipo();
                boolean mismoValor = recompensaExistente.getValor() == recompensaPotencial.getValor();
                if (mismoTipo && mismoValor) 
                {
                    yaEstaEnLaLista = true;
                    break; 
                }
            }
            if (!yaEstaEnLaLista) {
                opciones.add(recompensaPotencial);
            }
        }
        return opciones;
    }
}