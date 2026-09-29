package com.example;

import java.math.BigDecimal;

public class Produto {
    private String nome;
    private String codigo;
    private BigDecimal preco;

    public Produto(String nome, String codigo, BigDecimal preco) {
        this.nome = validarNome(nome);
        this.codigo = validarCodigo(codigo);
        this.preco = validarPreco(preco);
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
}