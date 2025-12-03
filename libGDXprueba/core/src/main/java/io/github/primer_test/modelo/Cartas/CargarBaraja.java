package io.github.primer_test.modelo.Cartas;

import java.util.List;

import io.github.primer_test.cartasbd.CartasDAOImpl;


public class CargarBaraja {

    public static Baraja cargar() {
        Baraja baraja = new Baraja();
        try {
            CartasDAOImpl dao = new CartasDAOImpl();
            List<Carta> cartas = dao.findAll();
            if (cartas != null) {
                for (Carta c : cartas) {
                    baraja.agregarCarta(c);
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando baraja desde DAO: " + e.getMessage());
        }
        return baraja;
    }
}
