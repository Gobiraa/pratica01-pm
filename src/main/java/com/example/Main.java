package com.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o sobrenome: ");
        String sobrenome = scanner.nextLine();

        System.out.print("Digite a idade: ");
        int idade = scanner.nextInt();

        System.out.print("Digite a altura (em metros com virgula): ");
        double altura = scanner.nextDouble();

        System.out.print("Digite o peso (em kg com virgula): ");
        double peso = scanner.nextDouble();

        Pessoa pessoa = new Pessoa(nome, sobrenome, idade, altura, peso, 0);

        pessoa.CalculaIMC();

        System.out.println("Nome: " + pessoa.nome + " " + pessoa.sobrenome);
        System.out.printf("IMC: %.2f%n", pessoa.imc);

        pessoa.InformaObesidade();

        scanner.close();
    }
}
