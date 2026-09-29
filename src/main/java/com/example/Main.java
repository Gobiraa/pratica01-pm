package com.example;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final NumberFormat MOEDA = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));

    public static void main(String[] args) {
        Estoque estoque = criarEstoqueInicial();
        Fatura fatura = new Fatura();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean executando = true;
            while (executando) {
                exibirMenuPrincipal();
                switch (lerInteiro(scanner, "Escolha uma opção: ", 1, 10)) {
                    case 1 -> comprar(scanner, estoque, fatura);
                    case 2 -> verFatura(scanner, fatura);
                    case 3 -> excluirItem(scanner, fatura);
                    case 4 -> alterarItem(scanner, fatura);
                    case 5 -> consultarProduto(scanner, estoque);
                    case 6 -> adicionarProduto(scanner, estoque);
                    case 7 -> removerProduto(scanner, estoque, fatura);
                    case 8 -> reporEstoque(scanner, estoque);
                    case 9 -> listarEstoqueBaixo(scanner, estoque);
                    case 10 -> {
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
        System.out.println("5 - Consultar produto");
        System.out.println("6 - Adicionar produto ao estoque");
        System.out.println("7 - Remover produto do estoque");
        System.out.println("8 - Repor estoque");
        System.out.println("9 - Produtos com estoque baixo (menos de 5)");
        System.out.println("10 - Finalizar");
    }

    private static Estoque criarEstoqueInicial() {
        Estoque estoque = new Estoque();
        estoque.adicionarProduto(new Produto("Caderno universitário", "P001", new BigDecimal("24.90"), 12));
        estoque.adicionarProduto(new Produto("Caneta azul", "P002", new BigDecimal("3.50"), 25));
        estoque.adicionarProduto(new Produto("Mochila escolar", "P003", new BigDecimal("119.90"), 4));
        estoque.adicionarProduto(new Produto("Estojo", "P004", new BigDecimal("18.75"), 2));
        return estoque;
    }

    private static void comprar(Scanner scanner, Estoque estoque, Fatura fatura) {
        System.out.println("\nProdutos disponíveis (digite 0 para voltar):");
        estoque.listarProdutos();
        while (true) {
            System.out.print("Código do produto: ");
            String codigo = scanner.nextLine().trim();
            if (codigo.equals("0")) {
                return;
            }

            if (!estoque.verificarExistencia(codigo)) {
                System.out.println("Código não encontrado. Digite novamente ou 0 para voltar.");
                continue;
            }

            Produto produto = estoque.buscarProduto(codigo);
            int quantidade = lerInteiro(scanner, "Quantidade (0 para voltar): ", 0, Integer.MAX_VALUE);
            if (quantidade == 0) {
                return;
            }
            if (fatura.comprar(produto, quantidade)) {
                System.out.println("Compra realizada. Saldo em estoque: " + produto.getQuantidadeEstoque());
            } else {
                System.out.println("Estoque insuficiente. Disponível: " + produto.getQuantidadeEstoque());
            }
            return;
        }
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

    private static void consultarProduto(Scanner scanner, Estoque estoque) {
        System.out.print("Código do produto (0 para voltar): ");
        String codigo = scanner.nextLine().trim();
        if (codigo.equals("0")) {
            return;
        }
        Produto produto = estoque.buscarProduto(codigo);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        exibirProduto(produto);
    }

    private static void adicionarProduto(Scanner scanner, Estoque estoque) {
        System.out.print("Código do novo produto (0 para voltar): ");
        String codigo = scanner.nextLine().trim();
        if (codigo.equals("0")) {
            return;
        }
        if (estoque.verificarExistencia(codigo)) {
            System.out.println("Já existe um produto com esse código.");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        while (nome.isEmpty()) {
            System.out.print("O nome não pode ficar vazio. Nome: ");
            nome = scanner.nextLine().trim();
        }
        BigDecimal preco = lerPreco(scanner);
        int quantidade = lerInteiro(scanner, "Quantidade inicial (0 para voltar): ", 0, Integer.MAX_VALUE);
        if (quantidade == 0) {
            return;
        }

        Produto produto = new Produto(nome, codigo, preco, quantidade);
        if (estoque.adicionarProduto(produto)) {
            System.out.println("Produto cadastrado no estoque.");
        } else {
            System.out.println("Já existe um produto com esse código.");
        }
    }

    private static void removerProduto(Scanner scanner, Estoque estoque, Fatura fatura) {
        System.out.print("Código do produto a remover (0 para voltar): ");
        String codigo = scanner.nextLine().trim();
        if (codigo.equals("0")) {
            return;
        }
        boolean estaNaFatura = fatura.getItens().stream()
                .anyMatch(item -> item.getProduto().getCodigo().equalsIgnoreCase(codigo));
        if (estaNaFatura) {
            System.out.println("Remova o item da fatura antes de retirar o produto do estoque.");
        } else if (estoque.removerProduto(codigo)) {
            System.out.println("Produto removido do estoque.");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private static void reporEstoque(Scanner scanner, Estoque estoque) {
        System.out.print("Código do produto a repor (0 para voltar): ");
        String codigo = scanner.nextLine().trim();
        if (codigo.equals("0")) {
            return;
        }
        Produto produto = estoque.buscarProduto(codigo);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        int quantidade = lerInteiro(scanner, "Quantidade a adicionar (0 para voltar): ", 0, Integer.MAX_VALUE);
        if (quantidade == 0) {
            return;
        }
        try {
            produto.adicionarEstoque(quantidade);
            System.out.println("Estoque atualizado. Saldo atual: " + produto.getQuantidadeEstoque());
        } catch (ArithmeticException exception) {
            System.out.println("A quantidade informada excede o limite permitido.");
        }
    }

    private static void listarEstoqueBaixo(Scanner scanner, Estoque estoque) {
        List<Produto> produtosBaixos = new ArrayList<>();
        for (Produto produto : estoque.getProdutos()) {
            if (produto.getQuantidadeEstoque() < 5) {
                produtosBaixos.add(produto);
            }
        }
        System.out.println("\nProdutos com menos de 5 unidades:");
        if (produtosBaixos.isEmpty()) {
            System.out.println("Nenhum produto com estoque baixo.");
        } else {
            produtosBaixos.forEach(Main::exibirProduto);
        }
        System.out.print("Pressione Enter ou 0 para voltar ao menu: ");
        scanner.nextLine();
    }

    private static void exibirProduto(Produto produto) {
        System.out.printf("Código: %s | Nome: %s | Preço: %s | Quantidade em estoque: %d%n",
                produto.getCodigo(), produto.getNome(), formatarMoeda(produto.getPreco()),
                produto.getQuantidadeEstoque());
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
        try {
            fatura.alterarQuantidade(numero - 1, quantidade);
            System.out.println("Quantidade atualizada.");
        } catch (IllegalStateException exception) {
            System.out.println(exception.getMessage());
        }
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

    private static BigDecimal lerPreco(Scanner scanner) {
        while (true) {
            System.out.print("Preço (use ponto ou vírgula decimal): ");
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                BigDecimal preco = new BigDecimal(entrada);
                if (preco.signum() >= 0) {
                    return preco;
                }
            } catch (NumberFormatException ignored) {
                // Solicita outra entrada abaixo.
            }
            System.out.println("Digite um preço válido, maior ou igual a zero.");
        }
    }

    private static String formatarMoeda(BigDecimal valor) {
        return MOEDA.format(valor);
    }
}