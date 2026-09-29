package com.example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Fatura {
    private final List<Item> itens = new ArrayList<>();
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public void comprar(Produto produto, int quantidade) {
        Objects.requireNonNull(produto, "O produto é obrigatório.");
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        for (Item item : itens) {
            if (item.getProduto().getCodigo().equalsIgnoreCase(produto.getCodigo())) {
                item.adicionarQuantidade(quantidade);
                recalcularValorTotal();
                return;
            }
        }

        itens.add(new Item(produto, quantidade));
        recalcularValorTotal();
    }

    public void removerItem(int indice) {
        validarIndice(indice);
        itens.remove(indice);
        recalcularValorTotal();
    }

    public void alterarQuantidade(int indice, int quantidade) {
        validarIndice(indice);
        itens.get(indice).setQuantidade(quantidade);
        recalcularValorTotal();
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= itens.size()) {
            throw new IndexOutOfBoundsException("O item selecionado não existe na fatura.");
        }
    }

    private void recalcularValorTotal() {
        valorTotal = itens.stream()
                .map(Item::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Item> getItens() {
        return Collections.unmodifiableList(new ArrayList<>(itens));
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public boolean estaVazia() {
        return itens.isEmpty();
    }
}