package com.example;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private final Produto produto;
    private int quantidade;
    private BigDecimal valorTotal;

    public Item(Produto produto, int quantidade) {
        this.produto = Objects.requireNonNull(produto, "O produto é obrigatório.");
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
        atualizarValorTotal();
    }

    public void adicionarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        setQuantidade(this.quantidade + quantidade);
    }

    private void atualizarValorTotal() {
        valorTotal = produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
        atualizarValorTotal();
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}