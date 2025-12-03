// GeneradorDeNiveles.java 
package io.github.primer_test.modelo.Partida;

import java.util.ArrayList;

import io.github.primer_test.modelo.Enemigos.BossFinal;
import io.github.primer_test.modelo.Enemigos.Enemigov3;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaCardenal;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaCulturista;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaEnemigos;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaEsqueletos;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaFantasma;
import io.github.primer_test.modelo.Enemigos.Fabricas.FabricaMonja;

public class GeneradorNiveles {

	private final FabricaEnemigos fabricaFantasma = new FabricaFantasma();
	private final FabricaEnemigos fabricaEsqueletos = new FabricaEsqueletos();
	private final FabricaEnemigos fabricaCulturista = new FabricaCulturista();
	private final FabricaEnemigos fabricaMonja = new FabricaMonja();
	private final FabricaEnemigos fabricaCardenal = new FabricaCardenal();

	public Nivel crearNivel(int numeroNivel) {
		switch (numeroNivel) {
		case 1:
			return crearNivel1();
		case 2:
			return crearNivel2();
		case 3:
			return crearNivel3();
		case 4:
			return crearNivel4();

		default:

			return null;
		}
	}

	private Nivel crearNivel1() {
		String fondo = "fondo_escenario_cementerio.png";
		ArrayList<Enemigov3> enemigos = new ArrayList<>();
		enemigos.add(fabricaEsqueletos.crearEnemigov3(1));
		enemigos.add(fabricaEsqueletos.crearEnemigov3(1));
		return new Nivel(enemigos, fondo);
	}

	private Nivel crearNivel2() {
		String fondo = "fondo_escenario_vestibulo.png";
		ArrayList<Enemigov3> enemigos = new ArrayList<>();
		enemigos.add(fabricaFantasma.crearEnemigov3(2));
		enemigos.add(fabricaCulturista.crearEnemigov3(2));
		return new Nivel(enemigos, fondo);
	}

	private Nivel crearNivel3() {
		String fondo = "fondo_escenario_salarituales.png";
		ArrayList<Enemigov3> enemigos = new ArrayList<>();
		enemigos.add(fabricaMonja.crearEnemigov3(2));
		enemigos.add(fabricaCardenal.crearEnemigov3(2));
		return new Nivel(enemigos, fondo);
	}

	private Nivel crearNivel4() {
		String fondo = "fondo_escenario_boss.png";
		BossFinal boss = new BossFinal();
		ArrayList<Enemigov3> enemigos = new ArrayList<>();
		enemigos.add(boss);
		return new Nivel(enemigos, fondo);

	}
}