package com.example;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
    private static final List<Produto> PRODUTOS = List.of(
            new Produto("Caderno universitário", "P001", new BigDecimal("24.90")),
            new Produto("Caneta azul", "P002", new BigDecimal("3.50")),
            new Produto("Mochila escolar", "P003", new BigDecimal("119.90")),
            new Produto("Estojo", "P004", new BigDecimal("18.75"))
    );

    public static void main(String[] args) {
        Fatura fatura = new Fatura();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean executando = true;
            while (executando) {
                exibirMenuPrincipal();
                switch (lerInteiro(scanner, "Escolha uma opção: ", 1, 5)) {
                    case 1 -> comprar(scanner, fatura);
                    case 2 -> verFatura(scanner, fatura);
                    case 3 -> excluirItem(scanner, fatura);
                    case 4 -> alterarItem(scanner, fatura);
                    case 5 -> {
                        System.out.println("\nCompra finalizada.");
                        exibirFatura(fatura);
                        executando = false;
                    }
                    default -> throw new IllegalStateException("Opção de menu inesperada.");
                }
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== Loja de suprimentos ===");
        System.out.println("1 - Comprar");
        System.out.println("2 - Ver fatura");
        System.out.println("3 - Excluir item");
        System.out.println("4 - Alterar item");
        System.out.println("5 - Finalizar");
    }

    private static void comprar(Scanner scanner, Fatura fatura) {
        System.out.println("\nProdutos disponíveis (digite 0 para voltar):");
        exibirProdutos();
        while (true) {
            System.out.print("Código do produto: ");
            String codigo = scanner.nextLine().trim();
            if (codigo.equals("0")) {
                return;
            }

            Produto produto = buscarProduto(codigo);
            if (produto == null) {
                System.out.println("Código não encontrado. Digite novamente ou 0 para voltar.");
                continue;
            }

            int quantidade = lerInteiro(scanner, "Quantidade (0 para voltar): ", 0, Integer.MAX_VALUE);
            if (quantidade == 0) {
                return;
            }
            fatura.comprar(produto, quantidade);
            System.out.println("Item adicionado à fatura.");
            return;
        }
    }

    private static void exibirProdutos() {
        for (Produto produto : PRODUTOS) {
            System.out.printf("%s | %s | %s%n",
                    produto.getCodigo(), produto.getNome(), formatarMoeda(produto.getPreco()));
        }
    }

    private static Produto buscarProduto(String codigo) {
        return PRODUTOS.stream()
                .filter(produto -> produto.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    private static void verFatura(Scanner scanner, Fatura fatura) {
        System.out.println();
        exibirFatura(fatura);
        System.out.print("Pressione Enter ou 0 para voltar ao menu: ");
        scanner.nextLine();
    }

    private static void exibirFatura(Fatura fatura) {
        if (fatura.estaVazia()) {
            System.out.println("A fatura não possui itens.");
        } else {
            List<Item> itens = fatura.getItens();
            for (int indice = 0; indice < itens.size(); indice++) {
                Item item = itens.get(indice);
                System.out.printf("%d. %s (%s) | %s x %d = %s%n",
                        indice + 1,
                        item.getProduto().getNome(),
                        item.getProduto().getCodigo(),
                        formatarMoeda(item.getProduto().getPreco()),
                        item.getQuantidade(),
                        formatarMoeda(item.getValorTotal()));
            }
        }
        System.out.println("Valor total: " + formatarMoeda(fatura.getValorTotal()));
    }

    private static void excluirItem(Scanner scanner, Fatura fatura) {
        if (!prepararSelecaoDeItem(scanner, fatura, "excluir")) {
            return;
        }
        int numero = lerInteiro(scanner, "Número do item (0 para voltar): ", 0, fatura.getItens().size());
        if (numero == 0) {
            return;
        }
        fatura.removerItem(numero - 1);
        System.out.println("Item excluído da fatura.");
    }

    private static void alterarItem(Scanner scanner, Fatura fatura) {
        if (!prepararSelecaoDeItem(scanner, fatura, "alterar")) {
            return;
        }
        int numero = lerInteiro(scanner, "Número do item (0 para voltar): ", 0, fatura.getItens().size());
        if (numero == 0) {
            return;
        }
        int quantidade = lerInteiro(scanner, "Nova quantidade (0 para voltar): ", 0, Integer.MAX_VALUE);
        if (quantidade == 0) {
            return;
        }
        fatura.alterarQuantidade(numero - 1, quantidade);
        System.out.println("Quantidade atualizada.");
    }

    private static boolean prepararSelecaoDeItem(Scanner scanner, Fatura fatura, String acao) {
        System.out.printf("%nItens para %s (digite 0 para voltar):%n", acao);
        if (fatura.estaVazia()) {
            System.out.println("A fatura não possui itens. Pressione Enter ou 0 para voltar.");
            scanner.nextLine();
            return false;
        }
        exibirFatura(fatura);
        return true;
    }

    private static int lerInteiro(Scanner scanner, String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
                // Solicita outra entrada abaixo.
            }
            System.out.printf("Digite um número inteiro entre %d e %d.%n", minimo, maximo);
        }
    }

    private static String formatarMoeda(BigDecimal valor) {
        return MOEDA.format(valor);
    }
}