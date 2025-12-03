package io.github.primer_test.Controlador;

import com.badlogic.gdx.Gdx;

import io.github.primer_test.MainMenu;
import io.github.primer_test.Vista.IngresoUsuarioVista;

public class MenuControlador {
    private IngresoUsuarioVista ingresoUsuarioVista;
    private MainMenu main;
    private String nombreJugador;

    public MenuControlador(MainMenu main) {
        this.main = main;
    }

    public void iniciarJuego() {
        main.mostrarIngreso();
    }

    public void aceptarBoton() {
           String nombre = ingresoUsuarioVista.getNombreIngresado();
           if (nombre == null || nombre.trim().isEmpty()) {
              ingresoUsuarioVista.mostrarError("El nombre no puede estar vacío.");
              return;
           }
           nombreJugador = nombre;
           main.mostrarPelea();
    }

    public void volverMenuPrincipal() {
        main.mostrarMenu();
    }

    public void salirJuego() {
        Gdx.app.exit();
    }

    public void MusicaJuego() {
        main.activarmusica();
    }
    public void mostrarRanking() {
    	main.mostrarHistorial();
    }
    public void mostrarTutorial() {
    main.mostrarTutorial();
    }
    public String getNombreJugador() {
    return nombreJugador;
    }
    public void setIngresoUsuarioVista(IngresoUsuarioVista vista) {
        this.ingresoUsuarioVista = vista;
    }
    }
