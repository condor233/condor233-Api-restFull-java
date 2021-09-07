package com.example.vendas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.dto.CategoriaResponseDTO;
import com.example.vendas.dto.CategoriaRequestDTO;
import com.example.vendas.entity.Categoria;
import com.example.vendas.service.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@ApiOperation(value = "findAll", nickname = "findAll")
	@GetMapping
	public List<CategoriaResponseDTO> findAll() {
		return categoriaService.findAll().stream()
				.map(categoria -> CategoriaResponseDTO.convertToCategotiaDTO(categoria)).collect(Collectors.toList());

	}

	@ApiOperation(value = "findById", nickname = "findById")
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
		Optional<Categoria> categoria = categoriaService.findById(id);
		return categoria.isPresent() ? ResponseEntity.ok(CategoriaResponseDTO.convertToCategotiaDTO(categoria.get()))
				: ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Save", nickname = "save")
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> save(@Valid @RequestBody CategoriaRequestDTO categoriaDto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(CategoriaResponseDTO.convertToCategotiaDTO(categoriaService.save(categoriaDto.covertToEntity())));
	}

	@ApiOperation(value = "Update", nickname = "update")
	@PutMapping("/{id}")
	public ResponseEntity<Categoria> update(@PathVariable long id, @Valid @RequestBody Categoria categoria) {
		return ResponseEntity.ok(categoriaService.update(id, categoria));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		categoriaService.delete(id);
	}
}
