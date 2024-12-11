package com.generation.fitplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.fitplanner.model.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long>{
	
	public List<Exercicio> findAllByNomeExercicioContainingIgnoreCase(@Param("nomeExercicio")String nomeExercicio);

}
