package resultados;

public class ResultadoMedio {
    private final String algoritmo;
    private final String grupo;
    private final int tamanho;
    private final double tempoMedio;
    private final double trocasMedias;
    private final double iteracoesMedias;

    public ResultadoMedio(String algoritmo, String grupo, int tamanho,
                          double tempoMedio, double trocasMedias, double iteracoesMedias) {
        this.algoritmo = algoritmo;
        this.grupo = grupo;
        this.tamanho = tamanho;
        this.tempoMedio = tempoMedio;
        this.trocasMedias = trocasMedias;
        this.iteracoesMedias = iteracoesMedias;
    }

    public String getAlgoritmo() { return algoritmo; }
    public String getGrupo() { return grupo; }
    public int getTamanho() { return tamanho; }
    public double getTempoMedio() { return tempoMedio; }
    public double getTrocasMedias() { return trocasMedias; }
    public double getIteracoesMedias() { return iteracoesMedias; }

    @Override
    public String toString() {
        return String.format("%s (Grupo %s) - Tamanho: %d, Tempo Médio: %.2fms, Trocas Médias: %.0f, Iterações Médias: %.0f",
                algoritmo, grupo, tamanho, tempoMedio / 1_000_000.0, trocasMedias, iteracoesMedias);
    }
}