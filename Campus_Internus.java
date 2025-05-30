import classes.Estudante;
import classes.Labirinto;
import classes.negativas.PensamentoNegativo;
import classes.negativas.Perigo;
import classes.positivas.PequenaConquista;
import java.util.Scanner;

public class Campus_Internus {

    public Campus_Internus() {
    }

    public static void digitando(String texto, int delayMillis) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        digitando("Bem-vindo(a) ao Campus Internus: A jornada de um universitário!", 30);

        while (executando) {
            System.out.println("\nMenu Principal:");
            System.out.println("1 - Começar Jornada");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        iniciarJornada(scanner);
                        break;
                    case 2:
                        digitando("Saindo do jogo. Lembre-se da sua força! Até a próxima!", 50);
                        executando = false;
                        break;
                    default:
                        digitando("Opção inválida. Tente novamente.", 50);
                        break;
                }
            } else {
                scanner.nextLine(); // Limpar entrada inválida
                digitando("Opção inválida. Por favor, insira um número.", 50);
            }
        }

        scanner.close();
    }

    private static void iniciarJornada(Scanner scanner) {
        digitando("Iniciando sua jornada de autoconhecimento...", 20);
        System.out.print("Digite o nome do(a) estudante: ");
        String nome = scanner.nextLine();

        String corVermelha = "\u001b[31m";
        String corReset = "\u001b[0m";

        System.out.println("\nEscolha uma característica que define seu personagem:");
        System.out.println("1 - Maturidade (Conquistas melhores e narrativa mais reflexiva)");
        System.out.println("2 - Ansiedade (Perigos mais intensos e narrativa mais tensa)");
        System.out.println("3 - Sabedoria (Jogo mais fácil)");
        System.out.print("Digite o número correspondente à característica: ");

        int escolhaCaracteristica = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                escolhaCaracteristica = scanner.nextInt();
                scanner.nextLine();
                if (escolhaCaracteristica >= 1 && escolhaCaracteristica <= 3) {
                    break;
                } else {
                    System.out.print("Número inválido. Digite entre 1 e 3: ");
                }
            } else {
                scanner.nextLine();
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }

        String caracteristica = "";
        int pontosConfiancaBase = 20;

        switch (escolhaCaracteristica) {
            case 1:
                caracteristica = "maturidade";
                pontosConfiancaBase += 10;
                digitando("Você escolheu Maturidade. Sua jornada será profunda e suas conquistas mais significativas.", 30);
                break;
            case 2:
                caracteristica = "ansiedade";
                pontosConfiancaBase -= 10;
                digitando("Você escolheu Ansiedade. A jornada será tensa e os perigos mais desafiadores, mas você é forte!", 30);
                break;
            case 3:
                caracteristica = "sabedoria";
                pontosConfiancaBase += 15;
                digitando("Você escolheu Sabedoria. Sua experiência o ajudará a tomar decisões mais acertadas no labirinto.", 30);
                break;
            default:
                caracteristica = "neutra";
                digitando("Nenhuma característica especial foi escolhida. Boa sorte na sua jornada!", 30);
                break;
        }

        Estudante estudante = new Estudante(nome, 0, pontosConfiancaBase);
        estudante.setCaracteristica(caracteristica);

        digitando("Olá, " + corVermelha + estudante.getNome() + corReset + ". Você se encontra em um labirinto que reflete seus desafios internos.", 20);
        digitando("Sua confiança atual é: " + estudante.getPontosDeConfianca(), 20);
        digitando("Colete 'Pequenas Conquistas' para aumentar sua confiança e enfrente 'Pensamentos Negativos'.", 20);

        Labirinto labirinto = new Labirinto(8);
        digitando("Gerando labirinto... ", 20);
        labirinto.gerarLabirinto();
        digitando("O labirinto foi gerado. A exploração começa agora! Quantidade de conquistas no labirinto: " + labirinto.getPequenasConquistasDisponiveis().size(), 20);

        int localizacao = estudante.getLocalizacao();
        boolean jogando = true;

        while (jogando) {
            labirinto.visualizarLabirinto(estudante);
            digitando("\nVocê está na posição " + localizacao + ". O que deseja fazer?", 30);
            System.out.println("1 - Cima");
            System.out.println("2 - Baixo");
            System.out.println("3 - Esquerda");
            System.out.println("4 - Direita");
            System.out.println("5 - Sair");
            System.out.print("Escolha sua ação: ");

            int acao = 0;
            if (scanner.hasNextInt()) {
                acao = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                digitando("Entrada inválida. Tente novamente.", 30);
                continue;
            }

            int novaPosicao = localizacao;

            switch (acao) {
                case 1: // Cima
                    novaPosicao = localizacao - labirinto.getTamanho();
                    break;
                case 2: // Baixo
                    novaPosicao = localizacao + labirinto.getTamanho();
                    break;
                case 3: // Esquerda
                    novaPosicao = localizacao - 1;
                    break;
                case 4: // Direita
                    novaPosicao = localizacao + 1;
                    break;
                case 5:
                    digitando("Você decidiu sair da jornada. Até a próxima!", 40);
                    jogando = false;
                    continue;
                default:
                    digitando("Opção inválida. Tente novamente.", 30);
                    continue;
            }

            if (novaPosicao >= 0 && novaPosicao < labirinto.getTamanho() * labirinto.getTamanho() && labirinto.salaLiberada(novaPosicao)) {
                localizacao = novaPosicao;
                estudante.setLocalizacao(localizacao);

                // Verificar Pequenas Conquistas
                PequenaConquista conquista = labirinto.encontrarPequenaConquistaNaPosicao(localizacao);
                if (conquista != null) {
                    digitando("Você encontrou uma Pequena Conquista: " + conquista.getNome(), 40);
                    estudante.adicionarPequenaConquista(conquista);

                    // Se for maturidade, aumenta confiança extra
                    if (caracteristica.equals("maturidade")) {
                        estudante.adicionarConfianca(5);
                        digitando("Sua maturidade permitiu absorver ainda mais dessa conquista. +5 de confiança extra!", 30);
                    } else {
                        estudante.adicionarConfianca(3);
                    }

                    labirinto.removerPequenaConquista(conquista);
                    digitando("Restam " + labirinto.getPequenasConquistasDisponiveis().size() + " conquistas no labirinto.", 20);
                    digitando("Sua confiança atual: " + estudante.getPontosDeConfianca(), 30);
                }

                // Verificar Pensamentos Negativos
                PensamentoNegativo pensamento = labirinto.encontrarPensamentoNegativoNaPosicao(localizacao);
                if (pensamento != null) {
                    digitando("Um pensamento negativo surgiu em sua mente...", 40);
                    if (caracteristica.equals("ansiedade")) {
                        pensamento.aumentarImpactoConfianca();
                    }
                    digitando(pensamento.efeito(estudante), 40);
                    digitando("Sua confiança agora é: " + estudante.getPontosDeConfianca(), 30);
                }

                // Verificar Perigos
                Perigo perigo = labirinto.encontrarPerigoNaPosicao(localizacao);
                if (perigo != null) {
                    digitando("Você se depara com um perigo: " + perigo.getDescricao(), 40);
                    estudante.diminuiConfianca(perigo.getImpacto());
                    digitando("O perigo abalou sua confiança para: " + estudante.getPontosDeConfianca(), 30);
                }

                // Verificar se confiança zerou
                if (estudante.getPontosDeConfianca() <= 0) {
                    digitando("Sua confiança chegou a zero. O labirinto interno ficou muito difícil... Mas nunca desista de você!", 50);
                    jogando = false;
                    digitando("\nFim de jogo. Obrigado por jogar Campus Internus!", 50);
                }

                // Verificar se todas conquistas coletadas para gerar saída
                if (labirinto.todasConquistasColetadas() && labirinto.getSalaSaida() == null) {
                    labirinto.gerarSalaSaida();
                }

                // Verificar se jogador está na saída
                if (labirinto.getSalaSaida() != null && estudante.getLocalizacao() == labirinto.getSalaSaida()) {
                    digitando("Você encontrou a saída e venceu o jogo! Parabéns!", 50);
                    System.exit(0);
                }

            } else {
                digitando("Você bateu numa parede invisível do seu medo. Não é possível seguir por aqui.", 40);
            }
        }
    }
}
