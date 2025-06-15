package algoritmos;

public class ShellSort implements OrdenaAlgoritmo {

    @Override
    public SortResultado sort(int[] array, int tamanho) {
        long tempoInicial = System.nanoTime();
        int trocas = 0;
        int iteracoes = 0;

        int intervalo = 1;
        while (intervalo < tamanho / 3) {
            intervalo = intervalo * 3 + 1;
        }

        while (intervalo >= 1) {
            for (int i = intervalo; i < tamanho; i++) {
                int valorAtual = array[i];
                int posicao = i;

                while (posicao >= intervalo && array[posicao - intervalo] > valorAtual) {
                    iteracoes++;
                    array[posicao] = array[posicao - intervalo];
                    posicao -= intervalo;
                    trocas++;
                }

                if (posicao >= intervalo) iteracoes++;
                array[posicao] = valorAtual;
            }
            intervalo = intervalo / 3;
        }

        long tempoFinal = System.nanoTime();
        return new SortResultado(trocas, iteracoes, tempoFinal - tempoInicial);
    }

    @Override
    public String getName() {
        return "Shell Sort";
    }

    @Override
    public String getGroup() {
        return "B";
    }
}