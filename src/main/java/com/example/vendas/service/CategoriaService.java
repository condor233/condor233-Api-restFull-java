package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.vendas.entity.Categoria;
import com.example.vendas.repository.CategoriaRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository  categoriaRepository;
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
	
	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}
	
	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	public Categoria update(Long id, Categoria categoria) {
		Categoria categoriaSave = validateCategoryExists(id);
		BeanUtils.copyProperties(categoria, categoriaSave, "codigo");
		return categoriaRepository.save(categoriaSave);
	}

	private Categoria validateCategoryExists(Long id) {
		Optional<Categoria> categoria = findById(id);
		if(categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categoria.get();
	}
	
}
