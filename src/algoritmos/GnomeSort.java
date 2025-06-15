package algoritmos;

public class GnomeSort implements OrdenaAlgoritmo {

    @Override
    public SortResultado sort(int[] array, int tamanho) {
        long tempoInicial = System.nanoTime();
        long trocas = 0;
        long iteracoes = 0;
        int indice = 0;

        while (indice < tamanho) {
            if (indice == 0) {
                indice++;
            } else {
                iteracoes++;
                if (array[indice] >= array[indice - 1]) {
                    indice++;
                } else {

                    int temp = array[indice];
                    array[indice] = array[indice - 1];
                    array[indice - 1] = temp;
                    trocas++;
                    indice--;
                }
            }
        }

        long tempoFinal = System.nanoTime();
        return new SortResultado(trocas, iteracoes, tempoFinal - tempoInicial);
    }

    @Override
    public String getName() {
        return "Gnome Sort";
    }

    @Override
    public String getGroup() {
        return "C";
    }
}