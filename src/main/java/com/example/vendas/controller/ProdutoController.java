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

import com.example.vendas.dto.produto.ProdutoRequestDTO;
import com.example.vendas.dto.produto.ProdutoResponseDTO;
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
	public List<ProdutoResponseDTO> findAll(@PathVariable Long idCategoria) {
		return produtoService.listarTodos(idCategoria).stream()
				.map(produto -> ProdutoResponseDTO.convertToProductDTO(produto)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por código", nickname = "findByIdProduct")
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> findById(@PathVariable Long idCategoria, @PathVariable Long id) {
		Optional<Produto> produto = produtoService.buscarPorCodigo(id, idCategoria);
		return produto.isPresent() ? ResponseEntity.ok(ProdutoResponseDTO.convertToProductDTO(produto
				.get())) : ResponseEntity.notFound().build();
	}

	@ApiOperation(value = "Save", nickname = "saveProduct")
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> save(@PathVariable Long idCategoria, @Valid @RequestBody ProdutoRequestDTO produto) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ProdutoResponseDTO.convertToProductDTO(produtoService
						.salvar(idCategoria, produto.convertToEntity(idCategoria))));
	}

	@ApiOperation(value = "Update", nickname = "updateProduct")
	@PutMapping("/{idProduto}")
	public ResponseEntity<ProdutoResponseDTO> update(@PathVariable Long idCategoria, @PathVariable Long idProduto,
			@Valid @RequestBody ProdutoRequestDTO produto) {
		return ResponseEntity.ok(ProdutoResponseDTO.convertToProductDTO(produtoService.atualizar(idCategoria, idProduto, produto.convertToEntity(idCategoria, idProduto))));
	}

	@ApiOperation(value = "Delete", nickname = "deleteProduct")
	@DeleteMapping("/{idProduto}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long idCategoria, @PathVariable Long idProduto) {
		produtoService.deletar(idCategoria, idProduto);
	}

}
