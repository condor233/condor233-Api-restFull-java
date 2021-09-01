package com.example.vendas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@ApiOperation(value = "Listar", nickname = "findAll")
	@GetMapping
	public List<Produto> findAll(@PathVariable Long idCategoria) {
		return produtoService.findAll(idCategoria);
	}
	
	@ApiOperation(value = "Listar por código", nickname = "findById")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Produto>> findById(@PathVariable Long idCategoria,
			@PathVariable Long id){
		Optional<Produto> produto = produtoService.findById(id, idCategoria);
		return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}

}
