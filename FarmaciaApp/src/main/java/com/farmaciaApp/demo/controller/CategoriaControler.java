package com.farmaciaApp.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.farmaciaApp.demo.model.CategoriaModel;
import com.farmaciaApp.demo.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin("*")

public class CategoriaControler {
	
	@Autowired
	CategoriaRepository repository;
	
	//Get
	@GetMapping("/tudo")
	public ResponseEntity<List<CategoriaModel>> getAll(){
		List<CategoriaModel> list = repository.findAll();
		
		if(list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sem resultado");
		} else {
			return ResponseEntity.status(200).body(list);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaModel> getById(@PathVariable(value = "id") Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp)).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id não encontrado.");
		});

	}
	
	//POST
	@PostMapping("/post")
	public ResponseEntity<CategoriaModel> post(@RequestBody CategoriaModel categoria) {
		return ResponseEntity.status(201).body(repository.save(categoria));
	}
	
	//Put
		@PutMapping("/edit")
		public ResponseEntity<CategoriaModel> put(@RequestBody CategoriaModel categoria) {
			return repository.findById(categoria.getId()).map(resp -> ResponseEntity.status(201).body(resp)).orElseGet(() -> {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado.");
			});
		}

		// DELETE
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			Optional<CategoriaModel> optional = repository.findById(id);

			if (optional.isPresent()) {
				repository.deleteById(id);
				return ResponseEntity.status(200).build();
			} else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado.");
			}
		}
	
}
