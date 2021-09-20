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
	
	@Autowired
	private CategoriaService categoriaService;
		
	public List<Produto> listarTodos(Long codigoCategoria) {
		return produtoRepository.findByCategoriaCodigo(codigoCategoria);
	}
	
	public Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria) {
		return produtoRepository.findById(codigo, codigoCategoria);
	}
	
	public Produto salvar(Long codigoCategoria, Produto produto) {
		validarCategoriaDoProdutoExiste(codigoCategoria);
		validarProdutoDuplicado(produto);
		return produtoRepository.save(produto);
	}
	
	public Produto atualizar(Long codigoCategoria, Long codigoProduto, Produto produto) {
		Produto produtoSalvar = validarProdutoExiste(codigoProduto, codigoCategoria);
		validarCategoriaDoProdutoExiste(codigoCategoria);
		validarProdutoDuplicado(produto);
		BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
		return produtoRepository.save(produtoSalvar);
	}
	
	public void deletar(Long codigoCategoria, Long codigoProduto) {
		Produto produto = validarProdutoExiste(codigoProduto, codigoCategoria);
		produtoRepository.delete(produto);
	}
	
	protected void atualizarQuantidadeEmEstoque(Produto produto) {
		produtoRepository.save(produto);
	}

	protected Produto validarProdutoExiste(Long codigoProduto) {
		Optional<Produto> produto = produtoRepository.findById(codigoProduto);
		if(produto.isEmpty()) {
			throw new BusinessRuleException(String.format("Produto de código %s não encontrado", codigoProduto));
		}
		return produto.get();
	}
	
	private Produto validarProdutoExiste(Long codigoProduto, Long codigoCategoria) {
		Optional<Produto> produto = buscarPorCodigo(codigoProduto, codigoCategoria);
		if(produto.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produto.get();
	}


	private void validarProdutoDuplicado(Produto produto) {
		Optional<Produto> produtoPorDescricao = produtoRepository.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao());
		if(produtoPorDescricao.isPresent() && produtoPorDescricao.get().getCodigo() != produto.getCodigo()) {
			throw new BusinessRuleException(String.format("O produto %s já está cadastrado", produto.getDescricao()));
		}
	}
	
	private void validarCategoriaDoProdutoExiste(Long codigoCategoria) {
		if(codigoCategoria == null) {
			throw new BusinessRuleException("A categoria não pode ser nula");
		}
		
		if(categoriaService.findById(codigoCategoria).isEmpty()) {
			throw new BusinessRuleException(String.format("A categoria de código %s informada não existe no cadastro", codigoCategoria));
		}
	}
}
