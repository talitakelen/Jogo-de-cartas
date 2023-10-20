import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Jogo21 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new Random();

        int saldo = 1000;

        while (saldo > 0) {
            System.out.println("Seu saldo: " + saldo);
            System.out.print("Faça sua aposta (ou 0 para sair): ");
            int aposta = scanner.nextInt();

            if (aposta == 0) {
                System.out.println("Obrigado por jogar! Seu saldo final: " + saldo);
                break;
            }

            if (aposta > saldo) {
                System.out.println("Aposta maior que o saldo disponível. Aposte um valor menor.");
                continue;
            }

            List<Integer> baralho = criarBaralho();
            List<Integer> maoJogador = new ArrayList<>();
            List<Integer> maoComputador = new ArrayList<>();

            for (int i = 0; i < 2; i++) {
                maoJogador.add(pegarCarta(baralho));
                maoComputador.add(pegarCarta(baralho));
            }

            boolean jogadorGanhou = false;

            while (true) {
                int somaJogador = calcularSoma(maoJogador);
                int somaComputador = calcularSoma(maoComputador);

                exibirMao(maoJogador, "Sua mão");
                exibirMao(maoComputador, "Mão do computador");

                if (somaJogador == 21) {
                    System.out.println("Você tem 21! Parabéns, você ganhou!");
                    jogadorGanhou = true;
                    break;
                } else if (somaJogador > 21) {
                    System.out.println("Você estourou! Sua pontuação é maior que 21. Você perdeu. tente novamente");
                    break;
                }

                System.out.print("Escolha uma ação (1 = Pedir carta, 2 = Parar): ");
                int escolha = scanner.nextInt();

                if (escolha == 1) {
                    maoJogador.add(pegarCarta(baralho));
                } else if (escolha == 2) {
                    while (somaComputador < 17) {
                        maoComputador.add(pegarCarta(baralho));
                        somaComputador = calcularSoma(maoComputador);
                    }

                    jogadorGanhou = (somaComputador > 21 || somaJogador > somaComputador);
                    break;
                }
            }

            exibirMao(maoComputador, "Mão do computador");

            if (jogadorGanhou) {
                System.out.println("Você ganhou! Parabéns , Arrasou");
                saldo += aposta;
            } else if (calcularSoma(maoJogador) == calcularSoma(maoComputador)) {
                System.out.println("Empate. Sua aposta será devolvida.");
            } else {
                System.out.println("Você perdeu. Boa sorte na próxima vez ,não desista .");
                saldo -= aposta;
            }
        }
        scanner.close();
    }

    public static List<Integer> criarBaralho() {
        List<Integer> baralho = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 0; j < 4; j++) {
                baralho.add(i);
            }
        }
        return baralho;
    }

    public static int pegarCarta(List<Integer> baralho) {
        Random random = new Random();
        return baralho.remove(random.nextInt(baralho.size()));
    }

    public static int calcularSoma(List<Integer> mao) {
        int soma = 0;
        int numAces = 0;

        for (int carta : mao) {
            if (carta == 1) {
                numAces++;
                soma += 11;
            } else if (carta > 10) {
                soma += 10;
            } else {
                soma += carta;
            }
        }

        while (soma > 21 && numAces > 0) {
            soma -= 10;
            numAces--;
        }

        return soma;
    }

    public static void exibirMao(List<Integer> mao, String mensagem) {
        System.out.print(mensagem + ": ");
        for (int carta : mao) {
            System.out.print(carta + " ");
        }
        System.out.println("(Total: " + calcularSoma(mao) + ")");
    }
}
