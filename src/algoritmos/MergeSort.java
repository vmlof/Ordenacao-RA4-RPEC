package algoritmos;

public class MergeSort implements OrdenaAlgoritmo {
    private int trocas;
    private int iteracoes;

    @Override
    public SortResultado sort(int[] array, int tamanho) {
        long tempoInicial = System.nanoTime();
        trocas = 0;
        iteracoes = 0;

        mergeSort(array, 0, tamanho - 1);

        long tempoFinal = System.nanoTime();
        return new SortResultado(trocas, iteracoes, tempoFinal - tempoInicial);
    }

    private void mergeSort(int[] array, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = esquerda + (direita - esquerda) / 2;

            mergeSort(array, esquerda, meio);
            mergeSort(array, meio + 1, direita);
            merge(array, esquerda, meio, direita);
        }
    }

    private void merge(int[] array, int esquerda, int meio, int direita) {
        int tamanhoEsquerdo = meio - esquerda + 1;
        int tamanhoDireito = direita - meio;

        int[] arrayEsquerdo = new int[tamanhoEsquerdo];
        int[] arrayDireito = new int[tamanhoDireito];

        System.arraycopy(array, esquerda, arrayEsquerdo, 0, tamanhoEsquerdo);
        System.arraycopy(array, meio + 1, arrayDireito, 0, tamanhoDireito);

        int i = 0, j = 0, k = esquerda;

        while (i < tamanhoEsquerdo && j < tamanhoDireito) {
            iteracoes++;
            if (arrayEsquerdo[i] <= arrayDireito[j]) {
                array[k] = arrayEsquerdo[i];
                i++;
            } else {
                array[k] = arrayDireito[j];
                j++;
                trocas++;
            }
            k++;
        }

        while (i < tamanhoEsquerdo) {
            array[k] = arrayEsquerdo[i];
            i++;
            k++;
            iteracoes++;
        }

        while (j < tamanhoDireito) {
            array[k] = arrayDireito[j];
            j++;
            k++;
            iteracoes++;
        }
    }

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public String getGroup() {
        return "B";
    }
}