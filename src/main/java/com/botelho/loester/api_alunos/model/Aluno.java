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

	public Aluno() {
	}

	public Aluno(Integer id, String nome, String email, String senha, LocalDate dataNascimento, Double media) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.media = media;
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

	@Override
	public String toString() {
		return "Aluno [id=" + id + 
				", nome=" + nome + 
				", email=" + email + 
				", dataNascimento=" + dataNascimento + 
				"]";
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
		return Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(email, other.email)
				&& Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}

}
