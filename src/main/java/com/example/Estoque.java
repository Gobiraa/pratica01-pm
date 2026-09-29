package com.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Estoque {
    private final HashMap<String, Produto> produtos = new HashMap<>();

    public boolean adicionarProduto(Produto produto) {
        if (produto == null) {
            return false;
        }
        return produtos.putIfAbsent(normalizarCodigo(produto.getCodigo()), produto) == null;
    }

    public Produto buscarProduto(String codigo) {
        if (codigo == null) {
            return null;
        }
        return produtos.get(normalizarCodigo(codigo));
    }

    public boolean removerProduto(String codigo) {
        if (codigo == null) {
            return false;
        }
        return produtos.remove(normalizarCodigo(codigo)) != null;
    }

    public boolean verificarExistencia(String codigo) {
        return buscarProduto(codigo) != null;
    }

    public int getTamanho() {
        return produtos.size();
    }

    public Collection<Produto> getProdutos() {
        return List.copyOf(produtos.values());
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("O estoque está vazio.");
            return;
        }
        for (Produto produto : produtos.values()) {
            System.out.println(produto);
        }
    }

    private String normalizarCodigo(String codigo) {
        return codigo.trim().toUpperCase(Locale.ROOT);
    }
}