import algoritmos.*;
import gerador.GeradorAleatorio;
import resultados.ExportarResultado;
import resultados.ResultadoMedio;
import resultados.TesteResultado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AnalisaOrdem {
    private static final int[] TAMANHOS_VETORES = {1000, 10000, 100000, 500000, 1000000};
    private static final int RODADAS = 5;

    private final List<OrdenaAlgoritmo> algoritmos;
    private final List<TesteResultado> resultadosDetalhados;
    private final List<ResultadoMedio> resultadosMedios;

    public AnalisaOrdem() {
        this.algoritmos = Arrays.asList(
                new SelectionSort(),
                new MergeSort(),
                new ShellSort(),
                new GnomeSort()
        );
        this.resultadosDetalhados = new ArrayList<>();
        this.resultadosMedios = new ArrayList<>();
    }

    public static void main(String[] args) {
        AnalisaOrdem analisador = new AnalisaOrdem();
        analisador.executarAnalise();
    }

    public void executarAnalise() {
        System.out.println("=".repeat(80));
        System.out.println("           ANÁLISE DE ALGORITMOS DE ORDENAÇÃO");
        System.out.println("=".repeat(80));
        System.out.println();

        imprimirConfiguracao();

        System.out.println("Iniciando testes...\n");


        int totalTestes = algoritmos.size() * 5 * RODADAS;
        int testesConcluidos = 0;

        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            System.out.println("Testando: " + algoritmo.getName() + " (Grupo " + algoritmo.getGroup() + ")");

            for (int tamanho : TAMANHOS_VETORES) {
                System.out.printf("  Tamanho %s: ", formatarNumero(tamanho));

                for (int rodada = 1; rodada <= RODADAS; rodada++) {
                    long seed = algoritmo.getName().hashCode() + tamanho + rodada;
                    int[] vetor = GeradorAleatorio.gerarArrayAleatorio(tamanho, seed);

                    TesteResultado resultado = executarTesteIndividual(algoritmo, vetor, tamanho, rodada);
                    resultadosDetalhados.add(resultado);

                    testesConcluidos++;
                    System.out.print(".");
                }
                System.out.println(" ✓");
            }
            System.out.println();
        }

        calcularMedias();

        mostrarResultados();

        exportarResultados();

        System.out.println("\nAnálise concluída!");
    }

    private TesteResultado executarTesteIndividual(OrdenaAlgoritmo algoritmo, int[] vetor, int tamanho, int rodada) {

        int[] copiaVetor = GeradorAleatorio.copiarArray(vetor, tamanho);

        SortResultado resultado = algoritmo.sort(copiaVetor, tamanho);

        return new TesteResultado(
                algoritmo.getName(),
                algoritmo.getGroup(),
                tamanho,
                rodada,
                resultado.getTempoExecucao(),
                resultado.getTrocas(),
                resultado.getIteracoes()
        );
    }

    private void calcularMedias() {
        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            for (int tamanho : TAMANHOS_VETORES) {
                List<TesteResultado> resultadosAlgoritmo = resultadosDetalhados.stream()
                        .filter(r -> r.getAlgoritmo().equals(algoritmo.getName()) && r.getTamanho() == tamanho)
                        .toList();

                double tempoMedio = resultadosAlgoritmo.stream()
                        .mapToLong(TesteResultado::getTempoExecucao)
                        .average()
                        .orElse(0.0);

                double trocasMedias = resultadosAlgoritmo.stream()
                        .mapToLong(TesteResultado::getTrocas)
                        .average()
                        .orElse(0.0);

                double iteracoesMedias = resultadosAlgoritmo.stream()
                        .mapToLong(TesteResultado::getIteracoes)
                        .average()
                        .orElse(0.0);

                resultadosMedios.add(new ResultadoMedio(
                        algoritmo.getName(),
                        algoritmo.getGroup(),
                        tamanho,
                        tempoMedio,
                        trocasMedias,
                        iteracoesMedias
                ));
            }
        }
    }

    private void imprimirConfiguracao() {
        System.out.println("CONFIGURAÇÃO DOS TESTES:");
        System.out.println("• Algoritmos:");
        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            System.out.printf("  - %s (Grupo %s)%n", algoritmo.getName(), algoritmo.getGroup());
        }

        System.out.print("• Tamanhos dos vetores: ");

        for (int i = 0; i < 5; i++) {
            System.out.print(formatarNumero(TAMANHOS_VETORES[i]));

            if (i < 4) System.out.print(", ");
        }
        System.out.println();

        System.out.println("• Rodadas por configuração: " + RODADAS);
        System.out.println("• Seeds diferentes para cada rodada (reprodutibilidade garantida)");
        System.out.println("• Métricas: Tempo de execução, Número de trocas, Número de iterações");
        System.out.println();
    }

    private void mostrarResultados() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                           RESULTADOS");
        System.out.println("=".repeat(80));

        mostrarTempo();

        mostrarTrocas();

        mostrarIteracoes();

        mostrarResumo();
    }

    private void mostrarTempo() {
        System.out.println("\nTEMPO DE EXECUÇÃO (ms):");
        System.out.println("-".repeat(80));

        System.out.printf("%-12s", "Tamanho");
        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            System.out.printf("%-15s", algoritmo.getName());
        }
        System.out.println();
        System.out.println("-".repeat(80));

        for (int tamanho : TAMANHOS_VETORES) {
            System.out.printf("%-12s", formatarNumero(tamanho));
            for (OrdenaAlgoritmo algoritmo : algoritmos) {
                ResultadoMedio resultado = encontrarResultadoMedio(algoritmo.getName(), tamanho);
                if (resultado != null) {
                    double tempoMs = resultado.getTempoMedio() / 1_000_000.0;
                    System.out.printf("%-15.2f", tempoMs);
                } else {
                    System.out.printf("%-15s", "-");
                }
            }
            System.out.println();
        }
    }

    private void mostrarTrocas() {
        System.out.println("\nNÚMERO DE TROCAS:");
        System.out.println("-".repeat(80));

        System.out.printf("%-12s", "Tamanho");
        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            System.out.printf("%-15s", algoritmo.getName());
        }
        System.out.println();
        System.out.println("-".repeat(80));

        for (int tamanho : TAMANHOS_VETORES) {
            System.out.printf("%-12s", formatarNumero(tamanho));
            for (OrdenaAlgoritmo algoritmo : algoritmos) {
                ResultadoMedio resultado = encontrarResultadoMedio(algoritmo.getName(), tamanho);
                if (resultado != null) {
                    System.out.printf("%-15s", formatarNumero((long) resultado.getTrocasMedias()));
                } else {
                    System.out.printf("%-15s", "-");
                }
            }
            System.out.println();
        }
    }

    private void mostrarIteracoes() {
        System.out.println("\nNÚMERO DE ITERAÇÕES:");
        System.out.println("-".repeat(80));

        System.out.printf("%-12s", "Tamanho");
        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            System.out.printf("%-15s", algoritmo.getName());
        }
        System.out.println();
        System.out.println("-".repeat(80));

        for (int tamanho : TAMANHOS_VETORES) {
            System.out.printf("%-12s", formatarNumero(tamanho));
            for (OrdenaAlgoritmo algoritmo : algoritmos) {
                ResultadoMedio resultado = encontrarResultadoMedio(algoritmo.getName(), tamanho);
                if (resultado != null) {
                    System.out.printf("%-15s", formatarNumero((long) resultado.getIteracoesMedias()));
                } else {
                    System.out.printf("%-15s", "-");
                }
            }
            System.out.println();
        }
    }

    private void mostrarResumo() {
        System.out.println("\nRESUMO POR ALGORITMO:");
        System.out.println("-".repeat(80));

        for (OrdenaAlgoritmo algoritmo : algoritmos) {
            System.out.printf("\n%s (Grupo %s):%n", algoritmo.getName(), algoritmo.getGroup());

            List<ResultadoMedio> resultadosAlgoritmo = resultadosMedios.stream()
                    .filter(r -> r.getAlgoritmo().equals(algoritmo.getName()))
                    .toList();

            for (ResultadoMedio resultado : resultadosAlgoritmo) {
                System.out.printf("  Tamanho %s: %.2fms, %s trocas, %s iterações%n",
                        formatarNumero(resultado.getTamanho()),
                        resultado.getTempoMedio() / 1_000_000.0,
                        formatarNumero((long) resultado.getTrocasMedias()),
                        formatarNumero((long) resultado.getIteracoesMedias()));
            }
        }
    }

    private void exportarResultados() {
        System.out.println("\nExportando resultados...");

        ExportarResultado.exportarParaCSV(resultadosDetalhados, resultadosMedios, "analise_algoritmos_completa.csv");

        List<String> nomesAlgoritmos = this.algoritmos.stream()
                .map(OrdenaAlgoritmo::getName)
                .collect(Collectors.toList());
        ExportarResultado.exportarTabelaComparativa(resultadosMedios, "tabela_comparativa.csv", nomesAlgoritmos, TAMANHOS_VETORES);
    }

    private ResultadoMedio encontrarResultadoMedio(String algoritmo, int tamanho) {
        return resultadosMedios.stream()
                .filter(r -> r.getAlgoritmo().equals(algoritmo) && r.getTamanho() == tamanho)
                .findFirst()
                .orElse(null);
    }

    private String formatarNumero(long numero) {
        return String.format("%,d", numero).replace(',', '.');
    }
}