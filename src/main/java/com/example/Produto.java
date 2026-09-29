package com.example;

import java.math.BigDecimal;
import java.util.Locale;

public class Produto {
    private String nome;
    private String codigo;
    private BigDecimal preco;
    private int quantidadeEstoque;

    public Produto(String nome, String codigo, BigDecimal preco) {
        this(nome, codigo, preco, 0);
    }

    public Produto(String nome, String codigo, BigDecimal preco, int quantidadeEstoque) {
        this.nome = validarNome(nome);
        this.codigo = validarCodigo(codigo);
        this.preco = validarPreco(preco);
        this.quantidadeEstoque = validarQuantidadeEstoque(quantidadeEstoque);
    }

    private static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto não pode ficar vazio.");
        }
        return nome.trim();
    }

    private static String validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código do produto não pode ficar vazio.");
        }
        return codigo.trim();
    }

    private static BigDecimal validarPreco(BigDecimal preco) {
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        return preco;
    }

    private static int validarQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa.");
        }
        return quantidadeEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = validarNome(nome);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = validarCodigo(codigo);
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = validarPreco(preco);
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = validarQuantidadeEstoque(quantidadeEstoque);
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade a adicionar deve ser maior que zero.");
        }
        quantidadeEstoque = Math.addExact(quantidadeEstoque, quantidade);
    }

    public boolean retirarEstoque(int quantidade) {
        if (quantidade <= 0 || quantidade > quantidadeEstoque) {
            return false;
        }
        quantidadeEstoque -= quantidade;
        return true;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s | %s | %.2f | estoque: %d",
                codigo, nome, preco, quantidadeEstoque);
    }
}