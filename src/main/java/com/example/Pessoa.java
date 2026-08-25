package com.example;

public class Pessoa {
    public String nome;
    public String sobrenome;
    public int idade;
    public double altura;
    public double peso;
    public double imc;



    public Pessoa(String nome, String sobrenome, int idade, double altura, double peso, double imc) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.imc = imc;
    }



    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public double getImc() {
        return imc;
    }
    public void setImc(double imc) {
        this.imc = imc;
    }


    
}

