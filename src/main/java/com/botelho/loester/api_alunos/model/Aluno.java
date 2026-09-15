package com.botelho.loester.api_alunos.model;

import java.time.LocalDate;
import java.util.Objects;

public class Aluno {

    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private Double media;

    private String cpf;
    private String telefone;
    private String cep;
    private Integer idade;
    private Double altura;
    private Double peso;
    private Integer quantidadeDependentes;
    private LocalDate dataMatricula;
    private LocalDate dataConclusao;
    private String site;
    private String observacao;
    private Boolean ativo;

    public Aluno() {
    }

    public Aluno(
            Integer id,
            String nome,
            String email,
            String senha,
            LocalDate dataNascimento,
            Double media,
            String cpf,
            String telefone,
            String cep,
            Integer idade,
            Double altura,
            Double peso,
            Integer quantidadeDependentes,
            LocalDate dataMatricula,
            LocalDate dataConclusao,
            String site,
            String observacao,
            Boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.media = media;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cep = cep;
        this.idade = idade;
        this.altura = altura;
        this.peso = peso;
        this.quantidadeDependentes = quantidadeDependentes;
        this.dataMatricula = dataMatricula;
        this.dataConclusao = dataConclusao;
        this.site = site;
        this.observacao = observacao;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getMedia() {
        return media;
    }

    public void setMedia(Double media) {
        this.media = media;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getQuantidadeDependentes() {
        return quantidadeDependentes;
    }

    public void setQuantidadeDependentes(Integer quantidadeDependentes) {
        this.quantidadeDependentes = quantidadeDependentes;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Aluno [id=" + id
                + ", nome=" + nome
                + ", email=" + email
                + ", dataNascimento=" + dataNascimento
                + ", cpf=" + cpf
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataNascimento, email, id, nome);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Aluno other = (Aluno) obj;

        return Objects.equals(dataNascimento, other.dataNascimento)
                && Objects.equals(email, other.email)
                && Objects.equals(id, other.id)
                && Objects.equals(nome, other.nome);
    }
}
