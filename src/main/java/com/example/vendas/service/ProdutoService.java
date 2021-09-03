package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vendas.entity.Produto;
import com.example.vendas.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> findAll(Long idCategoria){
		return produtoRepository.findByCategoriaCodigo(idCategoria);
	}
	
	public Optional<Produto> findById(Long id, Long idCategoria){
		return produtoRepository.findById(id, idCategoria);
	}
	
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
	

}
