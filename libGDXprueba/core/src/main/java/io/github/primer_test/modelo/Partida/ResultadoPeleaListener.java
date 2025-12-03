package io.github.primer_test.modelo.Partida;

import java.util.ArrayList;

import io.github.primer_test.modelo.Enemigos.Enemigov3;

public interface ResultadoPeleaListener {
    void onPeleaTerminada(boolean victoria, ArrayList<Enemigov3> enemigosDerrotados);
}