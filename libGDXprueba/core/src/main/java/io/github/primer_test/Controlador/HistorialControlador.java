package io.github.primer_test.Controlador;

import java.util.List;

import io.github.primer_test.cartasbd.DAO;
import io.github.primer_test.cartasbd.HistorialDAOimpl;
import io.github.primer_test.modelo.Historial.Historial;

public class HistorialControlador {
    private DAO<Historial> historialDAO;

    public HistorialControlador() {
        this.historialDAO = new HistorialDAOimpl();
    }

    public List<Historial> getDatosHistorial() {
        return historialDAO.findAll();
    }
}
