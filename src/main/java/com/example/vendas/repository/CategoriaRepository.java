package com.example.vendas.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	Categoria findByNome(String nome);
}
