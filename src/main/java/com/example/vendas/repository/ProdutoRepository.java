package com.example.vendas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.vendas.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	List<Produto> findByCategoriaCodigo(Long codigoCategoria);
	
	@Query("Select prod"
			+ " from Produto prod"
			+ " where prod.codigo = :codigo"
			+ "    and prod.categoria.codigo = :idCategoria")
	Optional<Produto> findById(Long codigo, Long idCategoria);
	
	Optional<Produto> findByCategoriaCodigoAndDescricao(Long codigoCategoria, String descricao);

}
