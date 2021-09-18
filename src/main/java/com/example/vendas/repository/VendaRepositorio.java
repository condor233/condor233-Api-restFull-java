package com.example.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.entity.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long>{

	List<Venda> findByClienteCodigo(Long codigoCliente);
}
