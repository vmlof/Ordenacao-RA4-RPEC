package resultados;

public class TesteResultado {
    private final String algoritmo;
    private final String grupo;
    private final int tamanho;
    private final int rodada;
    private final long tempoExecucao;
    private final long trocas;
    private final long iteracoes;

    public TesteResultado(String algoritmo, String grupo, int tamanho, int rodada,
                          long tempoExecucao, long trocas, long iteracoes) {
        this.algoritmo = algoritmo;
        this.grupo = grupo;
        this.tamanho = tamanho;
        this.rodada = rodada;
        this.tempoExecucao = tempoExecucao;
        this.trocas = trocas;
        this.iteracoes = iteracoes;
    }

    public String getAlgoritmo() { return algoritmo; }
    public String getGrupo() { return grupo; }
    public int getTamanho() { return tamanho; }
    public int getRodada() { return rodada; }
    public long getTempoExecucao() { return tempoExecucao; }
    public long getTrocas() { return trocas; }
    public long getIteracoes() { return iteracoes; }

    @Override
    public String toString() {
        return String.format("%s (Grupo %s) - Tamanho: %d, Rodada: %d, Tempo: %.2fms, Trocas: %d, Iterações: %d",
                algoritmo, grupo, tamanho, rodada, tempoExecucao / 1_000_000.0, trocas, iteracoes);
    }
}