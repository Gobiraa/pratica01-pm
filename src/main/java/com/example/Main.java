package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final int LIMITE_ALUNOS = 10;
    private static final String[] CASAS = {"Grifinória", "Sonserina", "Corvinal", "Lufa-Lufa"};

    public static void main(String[] args) {
        List<Aluno> alunos = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {
            int opcao;
            do {
                exibirMenu(alunos.size());
                opcao = lerInteiro(scanner, "Escolha uma opção: ");
                switch (opcao) {
                    case 1 -> cadastrarAluno(scanner, alunos);
                    case 2 -> listarAlunos(alunos);
                    case 3 -> listarPorCasa(scanner, alunos);
                    case 4 -> listarAlunosPorCasa(alunos);
                    case 5 -> listarPorMaioridade(alunos, true);
                    case 6 -> listarPorMaioridade(alunos, false);
                    case 7 -> buscarPorSobrenome(scanner, alunos);
                    case 8 -> System.out.println("Encerrando o sistema.");
                    default -> System.out.println("Opção inválida.");
                }
            } while (opcao != 8);
        }

        System.out.println("\nAlunos cadastrados:");
        listarAlunos(alunos);
    }

    private static void exibirMenu(int quantidadeAlunos) {
        System.out.printf("%n=== Cadastro de Hogwarts (%d/%d) ===%n", quantidadeAlunos, LIMITE_ALUNOS);
        System.out.println("1. Cadastrar aluno");
        System.out.println("2. Listar todos os alunos");
        System.out.println("3. Exibir alunos de uma casa");
        System.out.println("4. Exibir alunos por casa");
        System.out.println("5. Exibir alunos maiores de idade (17 anos ou mais)");
        System.out.println("6. Exibir alunos menores de idade");
        System.out.println("7. Buscar alunos por sobrenome");
        System.out.println("8. Encerrar");
    }

    private static void cadastrarAluno(Scanner scanner, List<Aluno> alunos) {
        if (alunos.size() >= LIMITE_ALUNOS) {
            System.out.println("O limite de 10 alunos já foi atingido.");
            return;
        }

        String nome = lerTextoObrigatorio(scanner, "Nome completo: ");
        LocalDate dataNascimento = lerDataNascimento(scanner);
        int coragem = lerInteiro(scanner, "Coragem: ");
        int inteligencia = lerInteiro(scanner, "Inteligência: ");
        int ambicao = lerInteiro(scanner, "Ambição: ");
        int lealdade = lerInteiro(scanner, "Lealdade: ");
        int criatividade = lerInteiro(scanner, "Criatividade: ");
        int estrategia = lerInteiro(scanner, "Estratégia: ");

        Aluno aluno = new Aluno(nome, dataNascimento, coragem, inteligencia,
                ambicao, lealdade, criatividade, estrategia);
        aluno.setCodigoMatricula(Aluno.gerarCodigoMatricula(nome, alunos.size() + 1));
        alunos.add(aluno);
        System.out.printf("Aluno cadastrado. Casa selecionada: %s. Matrícula: %s%n",
                aluno.formatarCasa(), aluno.getCodigoMatricula());
    }

    private static LocalDate lerDataNascimento(Scanner scanner) {
        while (true) {
            System.out.print("Data de nascimento (dd/MM/aaaa ou aaaa-MM-dd): ");
            String entrada = scanner.nextLine().trim();
            try {
                LocalDate data = entrada.contains("/")
                    ? LocalDate.parse(entrada, DateTimeFormatter.ofPattern("dd/MM/uuuu")
                        .withResolverStyle(ResolverStyle.STRICT))
                        : LocalDate.parse(entrada);
                if (!data.isAfter(LocalDate.now())) {
                    return data;
                }
                System.out.println("A data de nascimento não pode estar no futuro.");
            } catch (DateTimeParseException exception) {
                System.out.println("Data inválida. Tente novamente.");
            }
        }
    }

    private static void listarAlunos(List<Aluno> alunos) {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (Aluno aluno : alunos) {
            aluno.exibirInformacoes();
        }
    }

    private static void listarPorCasa(Scanner scanner, List<Aluno> alunos) {
        System.out.print("Informe a casa: ");
        String casa = scanner.nextLine().trim();
        int total = 0;
        for (Aluno aluno : alunos) {
            if (aluno.verificarCasa(casa)) {
                aluno.exibirInformacoes();
                total++;
            }
        }
        System.out.printf("Total de alunos da casa %s: %d%n", casa.toUpperCase(Locale.ROOT), total);
    }

    private static void listarAlunosPorCasa(List<Aluno> alunos) {
        for (String casa : CASAS) {
            System.out.printf("%n%s:%n", casa.toUpperCase(Locale.ROOT));
            int total = 0;
            for (Aluno aluno : alunos) {
                if (aluno.verificarCasa(casa)) {
                    aluno.exibirInformacoes();
                    total++;
                }
            }
            System.out.println("Total: " + total);
        }
    }

    private static void listarPorMaioridade(List<Aluno> alunos, boolean maiores) {
        boolean encontrou = false;
        for (Aluno aluno : alunos) {
            if (aluno.verificarMaioridadeMagica() == maiores) {
                aluno.exibirInformacoes();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum aluno encontrado nesta faixa etária.");
        }
    }

    private static void buscarPorSobrenome(Scanner scanner, List<Aluno> alunos) {
        String sobrenome = lerTextoObrigatorio(scanner, "Informe todo ou parte do sobrenome: ");
        boolean encontrou = false;
        for (Aluno aluno : alunos) {
            if (aluno.verificarPresencaDePalavra(sobrenome)) {
                aluno.exibirInformacoes();
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum aluno encontrado com esse sobrenome.");
        }
    }

    private static String lerTextoObrigatorio(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo não pode ficar vazio.");
        }
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }
}