package com.generation.fitplanner.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_exercicios")
public class Exercicio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nomeExercicio;

	@NotNull
	private Integer repeticoes;

	@NotNull
	private Integer series;

	@NotBlank
	private String instrucao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exercicio", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("exercicio")
	private List<Treino> treino;
	
	public Exercicio(Long id, String nomeExercicio, Integer repeticoes, Integer series,String instrucao) {
		this.id = id;
		this.nomeExercicio = nomeExercicio;
		this.repeticoes = repeticoes;
		this.series = series;
		this.instrucao = instrucao;
	}
	
	public Exercicio() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeExercicio() {
		return nomeExercicio;
	}

	public void setNomeExercicio(String nomeExercicio) {
		this.nomeExercicio = nomeExercicio;
	}

	public Integer getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(Integer repeticoes) {
		this.repeticoes = repeticoes;
	}

	public Integer getSeries() {
		return series;
	}

	public void setSeries(Integer series) {
		this.series = series;
	}

	public List<Treino> getTreino() {
		return treino;
	}

	public void setTreino(List<Treino> treino) {
		this.treino = treino;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

}
