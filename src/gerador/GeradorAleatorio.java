package gerador;

import java.util.Random;

public class GeradorAleatorio {

    public static int[] gerarArrayAleatorio(int tamanho, long seed) {
        Random random = new Random(seed);
        int[] array = new int[tamanho];

        for (int i = 0; i < tamanho; i++) {
            array[i] = random.nextInt(100000);
        }

        return array;
    }

    public static int[] copiarArray(int[] original, int tamanho) {
        int[] copia = new int[tamanho];
        System.arraycopy(original, 0, copia, 0, tamanho);
        return copia;
    }
}