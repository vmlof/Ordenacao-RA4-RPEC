package algoritmos;

public class SelectionSort implements OrdenaAlgoritmo {

    @Override
    public SortResultado sort(int[] array, int tamanho) {
        long tempoInicial = System.nanoTime();
        int trocas = 0;
        int iteracoes = 0;

        for (int i = 0; i < tamanho - 1; i++) {
            int indiceMinimo = i;

            for (int j = i + 1; j < tamanho; j++) {
                iteracoes++;
                if (array[j] < array[indiceMinimo]) {
                    indiceMinimo = j;
                }
            }

            if (indiceMinimo != i) {

                int temporario = array[i];
                array[i] = array[indiceMinimo];
                array[indiceMinimo] = temporario;
                trocas++;
            }
        }

        long tempoFinal = System.nanoTime();
        return new SortResultado(trocas, iteracoes, tempoFinal - tempoInicial);
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }

    @Override
    public String getGroup() {
        return "A";
    }
}