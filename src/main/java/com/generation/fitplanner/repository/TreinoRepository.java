package com.generation.fitplanner.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.generation.fitplanner.model.Treino;

public interface TreinoRepository extends JpaRepository<Treino, Long >{

	public Page<Treino> findAllByNomeTreinoContainingIgnoreCase(@Param("nomeTreino") String nomeTreino, Pageable pageable);

	@Query(value = "SELECT * FROM db_fitplanner.tb_treinos WHERE usuario_id = :id", nativeQuery = true)
	Page<Treino> listarTreinoPorUsuario(Long id, Pageable pageable);
}
