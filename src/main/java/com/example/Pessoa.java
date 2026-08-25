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

    public void CalculaIMC() { 
        this.imc = this.peso / Math.pow(this.altura, 2); 

    } 
    public void InformaObesidade() { 
        this.CalculaIMC(); 
        if (this.imc < 18.5)
            System.out.println("Abaixo do peso");  
        else if (this.imc < 25.0)
            System.out.println("Peso normal"); 
        else if (this.imc < 30.0) 
            System.out.println("Sobrepeso"); 
        else if (this.imc < 35.0) 
             System.out.println("Obesidade grau I"); 
        else if (this.imc < 40.0) 
            System.out.println("Obesidade grau II");  
        else 
            System.out.println("Obesidade grau III");
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

