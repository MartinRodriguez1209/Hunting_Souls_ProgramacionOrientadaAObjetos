package io.github.primer_test.Recompensas;

public class Recompensa {
    private TipoRecompensa tipo;
    private int valor;
    
    public Recompensa(TipoRecompensa tipo, int valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
    
    public TipoRecompensa getTipo() {
        return tipo;
    }
    
    public int getValor() {
        return valor;
    }
    
    @Override
    public String toString() {
        return tipo.name() + " (+" + valor + ")";
    }
}
