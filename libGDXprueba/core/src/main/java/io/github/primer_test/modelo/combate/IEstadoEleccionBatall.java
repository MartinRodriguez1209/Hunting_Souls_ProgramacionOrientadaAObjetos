package io.github.primer_test.modelo.combate;

import io.github.primer_test.Controlador.PeleaControlador;

// este va a ser el estado del jugador porque necesita poder clickear en la interfaz

public interface IEstadoEleccionBatall extends IEstadoPelea {

	public void procesarEleccionUsuario(PeleaControlador contexto, Object eleccion);

}
