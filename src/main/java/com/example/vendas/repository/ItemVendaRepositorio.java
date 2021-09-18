package com.example.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vendas.entity.ItemVenda;

public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {
	
	List<ItemVenda> findByVendaCodigo(Long codigoVenda);

}
