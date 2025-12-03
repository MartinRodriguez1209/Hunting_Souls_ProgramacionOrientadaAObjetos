package io.github.primer_test.modelo.Enemigos.Fabricas;

import io.github.primer_test.modelo.Enemigos.Enemigov3;

public abstract class FabricaEnemigos {
    public Enemigov3 producirEnemigov3(Integer dificultad) {
        return crearEnemigov3(dificultad);
    }

    public abstract Enemigov3 crearEnemigov3(Integer dificultad);

}
/*
 * Dificultad ----> Nivel 1 = 0 > Nivel 2 = 0.25 > Nivel 3 = 0.5
 */
