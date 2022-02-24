package com.farmaciaApp.demo.controller;

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

import java.util.List;
import java.util.Optional;

import com.farmaciaApp.demo.model.ProdutoModel;
import com.farmaciaApp.demo.repository.ProdutoRepository;

@RestController
@RequestMapping("/produto")
@CrossOrigin ("*")
public class ProdutoController {

	@Autowired
	public ProdutoRepository repository;

	//Get All 
	@GetMapping("/tudo")
	public ResponseEntity<List<ProdutoModel>> getAll(){
		List<ProdutoModel> list = repository.findAll();

		if(list.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Sem resultado");
		} else {
			return ResponseEntity.status(200).body(list);
		}
	} 

	//Get By Id 
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoModel> getById(@PathVariable(value = "id") Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp)).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Id não encontrado.");
		});

	}

	//Post 
	@PostMapping("/post")
	public ResponseEntity<ProdutoModel> post(@RequestBody ProdutoModel produto) {
		return ResponseEntity.status(201).body(repository.save(produto));
	}

	//Put 
	@PutMapping("/edit")
	public ResponseEntity<ProdutoModel> put(@RequestBody ProdutoModel produto) {
		return repository.findById(produto.getId()).map(resp -> ResponseEntity.status(201).body(resp)).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado.");
		});
	}

	//Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<ProdutoModel> optional = repository.findById(id);

		if(optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).build();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado.");
		}

	}

}
