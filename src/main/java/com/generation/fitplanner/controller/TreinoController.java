package com.generation.fitplanner.controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.fitplanner.model.Treino;
import com.generation.fitplanner.repository.TreinoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/treinos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TreinoController {

	@Autowired
	private TreinoRepository treinoRepository;

	@GetMapping
	public ResponseEntity<Page<Treino>> getAll(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(treinoRepository.findAll(pageable));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Treino> getById(@PathVariable Long id) {
		return treinoRepository.findById(id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nomeTreino/{nomeTreino}")
	public ResponseEntity<Page<Treino>> getByTitulo(@PathVariable String nomeTreino, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(treinoRepository.findAllByNomeTreinoContainingIgnoreCase(nomeTreino, pageable));
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<Page<Treino>> getByUsuario(@PathVariable Long id, @PageableDefault Pageable pageable){

		return ResponseEntity.ok(treinoRepository.listarTreinoPorUsuario(id, pageable));
	}

	@PostMapping
	public ResponseEntity<Treino> post(@Valid @RequestBody Treino treino) {
		return ResponseEntity.status(HttpStatus.CREATED).body(treinoRepository.save(treino));
	}

	@PutMapping
	public ResponseEntity<Treino> put(@Valid @RequestBody Treino treino) {
		return treinoRepository.findById(treino.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(treinoRepository.save(treino)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Treino> treino = treinoRepository.findById(id);

		if (treino.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		treinoRepository.deleteById(id);
	}

}
