package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
	
	public Produto save(Long idCategoria, Produto produto) {
		validCategoriaDoProduto(idCategoria);
		validProdutoDuplicate(produto);
		return produtoRepository.save(produto);
	}
	
	public Produto update(Long idCategoria, Long idProduto, Produto produto) {
		Produto produtoSave = validProdutoExist(idProduto, idCategoria);
		validCategoriaDoProduto(idCategoria);
		validProdutoDuplicate(produto);
		BeanUtils.copyProperties(produto, produtoSave, "codigo");
		return produtoRepository.save(produtoSave);
	}
	
	public void delete(Long idCategoria, Long idProduto) {
		Produto produto = validProdutoExist(idProduto, idCategoria);
		produtoRepository.delete(produto);
	}
	
	protected Produto validProdutoExist(Long codigoProduto) {
		Optional<Produto> produto = produtoRepository.findById(codigoProduto);
		if(produto.isEmpty()) {
			throw new BusinessRuleException(String.format("Produto de código %s não encontrado", codigoProduto));
		}
		return produto.get();
	}
	
	private Produto validProdutoExist(Long idProduto, Long idCategoria) {
		Optional<Produto> produto = findById(idProduto, idCategoria);
		if(produto.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return produto.get();
	}

	private void validProdutoDuplicate(Produto produto) {
		Optional<Produto> produtoDescription = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
		if(produtoDescription.isPresent() && produtoDescription.get().getCodigo() != produto.getCodigo()) {
			throw new BusinessRuleException(String.format("O produto %s já está cadastrado", produto.getDescricao()));
		}
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
