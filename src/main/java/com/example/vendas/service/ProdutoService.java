package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vendas.entity.Produto;
import com.example.vendas.exception.BusinessRuleException;
import com.example.vendas.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired CategoriaService categoriaServico;
	
	public List<Produto> findAll(Long idCategoria){
		return produtoRepository.findByCategoriaCodigo(idCategoria);
	}
	
	public Optional<Produto> findById(Long id, Long idCategoria){
		return produtoRepository.findById(id, idCategoria);
	}
	
	public Produto save(Produto produto) {
		validCategoriaDoProduto(produto.getCategoria().getCodigo());
		return produtoRepository.save(produto);
	}
	
	private void validCategoriaDoProduto(Long id) {
		if(id == null) {
			throw new BusinessRuleException("A categoria não pode ser nula");
		}
		
		if(categoriaServico.findById(id).isEmpty()) {
			throw new BusinessRuleException(String.format("A categoria de código %s informada não existe no cadastro", id));
		}
	}

}
