package com.example;

public class Aluno {
    private String nome;
    private int idade;
    private int coragem;
    private int inteligencia;
    private int ambicao;
    private int lealdade;
    private int criatividade;
    private int estrategia;
    private String casa;

    public Aluno(String nome, int idade, int coragem, int inteligencia, int ambicao, int lealdade, String casa) {
        this.nome = nome;
        this.idade = idade;
        this.coragem = coragem;
        this.inteligencia = inteligencia;
        this.ambicao = ambicao;
        this.lealdade = lealdade;
        casa = casa;
    }

    public void ExibirInformacoes() {
        System.out.println("Nome" + this.nome);
        System.out.println("\nIdade" + this.idade);
        System.out.println("Casa:" + this.casa);
    }

    public void CalcularCasa() {
        double Grifinoria = (2.0 * this.coragem) + this.lealdade;
        double Sonserina = (2.0 * this.ambicao) + this.estrategia;
        double Corvinal = (2.0 * this.inteligencia) + this.criatividade;
        double LufaLufa = ((2.0 * this.lealdade) + (this.coragem)/3.0);

        double maior = Grifinoria;
        casa = "Grifinória";

        if (Sonserina > maior)
            casa = "Sonserina";
        if (Corvinal > maior)
            casa = "Corvinal";
        if (LufaLufa > maior)
            casa = "Lufa-Lufa";
    
    }







    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public int getCoragem() {
        return coragem;
    }
    public void setCoragem(int coragem) {
        this.coragem = coragem;
    }
    public int getInteligencia() {
        return inteligencia;
    }
    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }
    public int getAmbicao() {
        return ambicao;
    }
    public void setAmbicao(int ambicao) {
        this.ambicao = ambicao;
    }
    public int getLealdade() {
        return lealdade;
    }
    public void setLealdade(int lealdade) {
        this.lealdade = lealdade;
    }
    public String getCasa() {
        return casa;
    }
    public void setCasa(String casa) {
        casa = casa;
    }

    
}
