package io.github.primer_test.modelo.Cartas;

public class CartaFactory {
    public static Carta crearCarta(
        String tipo,
        String nombre,
        String descripcion,
        int costoMana,
        String imagenPath,
        Integer valorAtaque,
        Integer probabilidadCritico,
        Integer valorCuracion,
        Integer valorDefensa
    ) {
        switch (tipo.toUpperCase()) {
            case "ATAQUE":
                return new CartaAtaque(nombre, descripcion, costoMana, imagenPath, valorAtaque, probabilidadCritico);
            case "CURACION":
                return new CartaCuracion(nombre, descripcion, costoMana, imagenPath, valorCuracion);
            case "DEFENSA":
                return new CartaDefensa(nombre, descripcion, costoMana, imagenPath, valorDefensa);
            case "MATAR":
                return new CartaMatar(nombre, descripcion, costoMana, imagenPath);
            default:
                throw new IllegalArgumentException("Tipo de carta no reconocido: " + tipo);
        }
    }
}