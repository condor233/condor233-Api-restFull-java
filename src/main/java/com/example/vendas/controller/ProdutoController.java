package com.example.vendas.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.vendas.entity.Produto;
import com.example.vendas.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria{idCategoria}/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@ApiOperation(value = "Listar", nickname = "findAllProduct")
	@GetMapping
	public List<Produto> findAll(@PathVariable Long idCategoria) {
		return produtoService.findAll(idCategoria);
	}
	
	@ApiOperation(value = "Listar por código", nickname = "findByIdProduct")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Produto>> findById(@PathVariable Long idCategoria,
			@PathVariable Long id){
		Optional<Produto> produto = produtoService.findById(id, idCategoria);
		return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	@ApiOperation(value = "Save", nickname = "saveProduct")
	@PostMapping
	public ResponseEntity<Produto> save(@PathVariable Long idCategoria, @Valid @RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(idCategoria, produto));
	}
	
	@ApiOperation(value = "Update", nickname = "updateProduct")
	@PutMapping("/{idProduto}")
	public ResponseEntity<Produto> update(@PathVariable Long idCategoria, @PathVariable Long idProduto, @Valid @RequestBody Produto produto){
		return ResponseEntity.ok(produtoService.update(idCategoria, idProduto, produto));
	}
	
	@ApiOperation(value = "Delete", nickname = "deleteProduct")
	@DeleteMapping("/{idProduto}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long idCategoria, @PathVariable Long idProduto) {
		produtoService.delete(idCategoria, idProduto);
	}

}
