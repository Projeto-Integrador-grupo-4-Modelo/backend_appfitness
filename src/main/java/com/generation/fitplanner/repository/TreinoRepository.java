package com.generation.fitplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation.fitplanner.model.Treino;

public interface TreinoRepository extends JpaRepository<Treino, Long >{

	public List<Treino> findAllByNomeTreinoContainingIgnoreCase(@Param("nomeTreino") String nomeTreino);

	@Query(value = "SELECT * FROM db_fitplanner.tb_treinos WHERE usuario_id = :id", nativeQuery = true)
	List<Treino> listarTreinoPorUsuario(Long id);
}
