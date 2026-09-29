package com.example;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Locale;

public class Aluno {
    private static final String[] CASAS = {"Grifinória", "Sonserina", "Corvinal", "Lufa-Lufa"};

    private String nome;
    private LocalDate dataNascimento;
    private int coragem;
    private int inteligencia;
    private int ambicao;
    private int lealdade;
    private int criatividade;
    private int estrategia;
    private String casa;
    private String codigoMatricula;

    public Aluno(String nome, LocalDate dataNascimento, int coragem, int inteligencia,
            int ambicao, int lealdade, int criatividade, int estrategia) {
        this.nome = formatarNome(nome);
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento é inválida ou está no futuro.");
        }
        this.dataNascimento = dataNascimento;
        this.coragem = coragem;
        this.inteligencia = inteligencia;
        this.ambicao = ambicao;
        this.lealdade = lealdade;
        this.criatividade = criatividade;
        this.estrategia = estrategia;
        calcularCasaInternamente();
    }

    public void exibirInformacoes() {
        System.out.printf("Matrícula: %s | Nome: %s | Idade: %d | Casa: %s | Usuário: %s%n",
                codigoMatricula == null ? "(não gerada)" : codigoMatricula,
                nome, calcularIdade(), formatarCasa(), gerarNomeUsuario());
        System.out.printf("  Nascimento: %s | Coragem: %d | Inteligência: %d | Ambição: %d | Lealdade: %d | Criatividade: %d | Estratégia: %d%n",
                dataNascimento, coragem, inteligencia, ambicao, lealdade, criatividade, estrategia);
    }

    public void calcularCasa() {
        calcularCasaInternamente();
    }

    private void calcularCasaInternamente() {
        double pontosGrifinoria = (2.0 * coragem) + lealdade;
        double pontosSonserina = (2.0 * ambicao) + estrategia;
        double pontosCorvinal = (2.0 * inteligencia) + criatividade;
        double pontosLufaLufa = ((2.0 * lealdade) + coragem) / 3.0;

        casa = CASAS[0];
        double maiorPontuacao = pontosGrifinoria;
        if (pontosSonserina > maiorPontuacao) {
            casa = CASAS[1];
            maiorPontuacao = pontosSonserina;
        }
        if (pontosCorvinal > maiorPontuacao) {
            casa = CASAS[2];
            maiorPontuacao = pontosCorvinal;
        }
        if (pontosLufaLufa > maiorPontuacao) {
            casa = CASAS[3];
        }
    }

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public boolean verificarMaioridadeMagica() {
        return calcularIdade() >= 17;
    }

    public String formatarCasa() {
        return casa == null ? "" : casa.toUpperCase(Locale.ROOT);
    }

    public String gerarNomeUsuario() {
        String[] partesNome = nome.trim().split("\\s+");
        StringBuilder usuario = new StringBuilder(partesNome[0].substring(0, 1));
        for (int indice = 1; indice < partesNome.length; indice++) {
            usuario.append(partesNome[indice]);
        }
        return usuario.toString().toLowerCase(Locale.ROOT);
    }

    public static String gerarCodigoMatricula(String nome, int posicaoCadastro) {
        StringBuilder iniciais = new StringBuilder();
        for (String parte : nome.trim().split("\\s+")) {
            if (!parte.isEmpty()) {
                iniciais.append(parte.substring(0, 1).toUpperCase(Locale.ROOT));
            }
        }
        return String.format(Locale.ROOT, "%s-%d-%02d", iniciais, LocalDate.now().getYear(), posicaoCadastro);
    }

    public boolean verificarCasa(String casaInformada) {
        return normalizarCasa(casa).equals(normalizarCasa(casaInformada));
    }

    public boolean verificarPresencaDePalavra(String palavra) {
        if (palavra == null || palavra.isBlank()) {
            return false;
        }
        String[] partesNome = nome.trim().split("\\s+");
        if (partesNome.length < 2) {
            return false;
        }
        String sobrenomes = String.join(" ", Arrays.copyOfRange(partesNome, 1, partesNome.length));
        return normalizarTexto(sobrenomes).contains(normalizarTexto(palavra));
    }

    private static String normalizarTexto(String texto) {
        return Normalizer.normalize(texto == null ? "" : texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }

    private static String normalizarCasa(String casaInformada) {
        return normalizarTexto(casaInformada).replaceAll("[^a-z0-9]", "");
    }

    private static String obterCasaCanonica(String casaInformada) {
        String casaNormalizada = normalizarCasa(casaInformada);
        for (String casaValida : CASAS) {
            if (normalizarCasa(casaValida).equals(casaNormalizada)) {
                return casaValida;
            }
        }
        throw new IllegalArgumentException("Casa inválida. Informe Grifinória, Sonserina, Corvinal ou Lufa-Lufa.");
    }

    private static String formatarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ficar vazio.");
        }
        String[] partes = nome.trim().toLowerCase(Locale.forLanguageTag("pt-BR")).split("\\s+");
        StringBuilder nomeFormatado = new StringBuilder();
        for (String parte : partes) {
            if (nomeFormatado.length() > 0) {
                nomeFormatado.append(' ');
            }
            nomeFormatado.append(parte.substring(0, 1).toUpperCase(Locale.forLanguageTag("pt-BR")))
                    .append(parte.substring(1));
        }
        return nomeFormatado.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = formatarNome(nome);
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        if (dataNascimento == null || dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de nascimento é inválida ou está no futuro.");
        }
        this.dataNascimento = dataNascimento;
    }

    public int getIdade() {
        return calcularIdade();
    }

    public int getCoragem() {
        return coragem;
    }

    public void setCoragem(int coragem) {
        this.coragem = coragem;
        calcularCasa();
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
        calcularCasa();
    }

    public int getAmbicao() {
        return ambicao;
    }

    public void setAmbicao(int ambicao) {
        this.ambicao = ambicao;
        calcularCasa();
    }

    public int getLealdade() {
        return lealdade;
    }

    public void setLealdade(int lealdade) {
        this.lealdade = lealdade;
        calcularCasa();
    }

    public int getCriatividade() {
        return criatividade;
    }

    public void setCriatividade(int criatividade) {
        this.criatividade = criatividade;
        calcularCasa();
    }

    public int getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(int estrategia) {
        this.estrategia = estrategia;
        calcularCasa();
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = obterCasaCanonica(casa);
    }

    public String getCodigoMatricula() {
        return codigoMatricula;
    }

    public void setCodigoMatricula(String codigoMatricula) {
        this.codigoMatricula = codigoMatricula;
    }
}