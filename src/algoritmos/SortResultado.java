package algoritmos;

public class SortResultado {
    private final long trocas;
    private final long iteracoes;
    private final long tempoExecucao;

    public SortResultado(long trocas, long iteracoes, long tempoExecucao) {
        this.trocas = trocas;
        this.iteracoes = iteracoes;
        this.tempoExecucao = tempoExecucao;
    }

    public long getTrocas() {
        return trocas;
    }

    public long getIteracoes() {
        return iteracoes;
    }

    public long getTempoExecucao() {
        return tempoExecucao;
    }
}