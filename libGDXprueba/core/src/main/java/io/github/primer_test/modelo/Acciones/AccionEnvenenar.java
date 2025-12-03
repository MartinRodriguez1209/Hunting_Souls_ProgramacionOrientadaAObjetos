package io.github.primer_test.modelo.Acciones;

import io.github.primer_test.modelo.Enemigos.Unidad;

public class AccionEnvenenar extends Accion {

	Integer cantEnvenenamiento; // cantidad de turnos que el enemigo va a estar envenenado

	public AccionEnvenenar(int valor, String nombre, Integer cantEnvenenamiento) {
		super(valor, nombre);
		this.cantEnvenenamiento = cantEnvenenamiento;
	}

//se tiene que hacer una funcion que de un enemigo envenenandonos o nosotros envenenando a un enemigo y que en cada turno del combate el jugador pierde un poco de vida por cierta cantidad de turnos

	public Integer getCantEnvenenamiento() {
		return this.cantEnvenenamiento;
	}

	@Override
	public void ejecutar(Unidad objetivo) {
		// TODO Auto-generated method stub

	}

}