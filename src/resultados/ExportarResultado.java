package resultados;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ExportarResultado {

    public static void exportarParaCSV(List<TesteResultado> detalhesResultado,
                                       List<ResultadoMedio> resultadosMedios,
                                       String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("=== RESULTADOS DETALHADOS ===");
            writer.println("Algoritmo,Grupo,Tamanho,Rodada,Tempo(ns),Tempo(ms),Trocas,Iteracoes");

            for (TesteResultado resultado : detalhesResultado) {
                writer.printf("%s,%s,%d,%d,%d,%.2f,%d,%d%n",
                        resultado.getAlgoritmo(),
                        resultado.getGrupo(),
                        resultado.getTamanho(),
                        resultado.getRodada(),
                        resultado.getTempoExecucao(),
                        resultado.getTempoExecucao() / 1_000_000.0,
                        resultado.getTrocas(),
                        resultado.getIteracoes()
                );
            }

            writer.println();
            writer.println("=== RESULTADOS MEDIOS ===");
            writer.println("Algoritmo,Grupo,Tamanho,Tempo_Medio(ns),Tempo_Medio(ms),Trocas_Medias,Iteracoes_Medias");

            for (ResultadoMedio resultado : resultadosMedios) {
                writer.printf("%s,%s,%d,%.0f,%.2f,%.0f,%.0f%n",
                        resultado.getAlgoritmo(),
                        resultado.getGrupo(),
                        resultado.getTamanho(),
                        resultado.getTempoMedio(),
                        resultado.getTempoMedio() / 1_000_000.0,
                        resultado.getTrocasMedias(),
                        resultado.getIteracoesMedias()
                );
            }

            System.out.println("Dados exportados para: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao exportar dados: " + e.getMessage());
        }
    }

    public static void exportarTabelaComparativa(List<ResultadoMedio> resultadosMedios, String nomeArquivo, List<String> algoritmos, int[] tamanhos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {

            writer.println("=== TEMPO DE EXECUCAO (ms) ===");
            writer.print("Tamanho,");
            for (String algoritmo : algoritmos) {
                writer.print(algoritmo + ",");
            }
            writer.println();

            for (int tamanho : tamanhos) {
                writer.print(tamanho + ",");
                for (String algoritmo : algoritmos) {
                    ResultadoMedio resultado = encontrarResultado(resultadosMedios, algoritmo, tamanho);
                    if (resultado != null) {
                        writer.printf("%.2f,", resultado.getTempoMedio() / 1_000_000.0);
                    } else {
                        writer.print("-,");
                    }
                }
                writer.println();
            }

            writer.println();
            writer.println("=== NUMERO DE TROCAS ===");
            writer.print("Tamanho,");
            for (String algoritmo : algoritmos) {
                writer.print(algoritmo + ",");
            }
            writer.println();

            for (int tamanho : tamanhos) {
                writer.print(tamanho + ",");
                for (String algoritmo : algoritmos) {
                    ResultadoMedio resultado = encontrarResultado(resultadosMedios, algoritmo, tamanho);
                    if (resultado != null) {
                        writer.printf("%.0f,", resultado.getTrocasMedias());
                    } else {
                        writer.print("-,");
                    }
                }
                writer.println();
            }

            writer.println();
            writer.println("=== NUMERO DE ITERACOES ===");
            writer.print("Tamanho,");
            for (String algoritmo : algoritmos) {
                writer.print(algoritmo + ",");
            }
            writer.println();

            for (int tamanho : tamanhos) {
                writer.print(tamanho + ",");
                for (String algoritmo : algoritmos) {
                    ResultadoMedio resultado = encontrarResultado(resultadosMedios, algoritmo, tamanho);
                    if (resultado != null) {
                        writer.printf("%.0f,", resultado.getIteracoesMedias());
                    } else {
                        writer.print("-,");
                    }
                }
                writer.println();
            }

            System.out.println("Tabela comparativa exportada para: " + nomeArquivo);

        } catch (IOException e) {
            System.err.println("Erro ao exportar tabela comparativa: " + e.getMessage());
        }
    }

    private static ResultadoMedio encontrarResultado(List<ResultadoMedio> resultados, String algorithm, int size) {
        return resultados.stream()
                .filter(r -> r.getAlgoritmo().equals(algorithm) && r.getTamanho() == size)
                .findFirst()
                .orElse(null);
    }
}