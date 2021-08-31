package com.example.vendas.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.entity.Categoria;
import com.example.vendas.service.CategoriaService;



@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<Categoria> findAll(){
		return categoriaService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Categoria>> findById(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.save(categoria));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> update(@PathVariable long id, @Valid @RequestBody Categoria categoria){
		return ResponseEntity.ok(categoriaService.update(id, categoria));
	}
}
