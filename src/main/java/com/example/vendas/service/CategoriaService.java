package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.vendas.entity.Categoria;
import com.example.vendas.exception.BusinessRuleException;
import com.example.vendas.repository.CategoriaRepository;



@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Optional<Categoria> findById(Long id) {
		return categoriaRepository.findById(id);
	}

	public Categoria save(Categoria categoria) {
		validateCategoriaduplicate(categoria);
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Long id, Categoria categoria) {
		Categoria categoriaSave = validateCategoryExists(id);
		validateCategoriaduplicate(categoria);
		BeanUtils.copyProperties(categoria, categoriaSave, "codigo");
		return categoriaRepository.save(categoriaSave);
	}

	public void delete(Long id) {
		categoriaRepository.deleteById(id);
	}

	private Categoria validateCategoryExists(Long id) {
		Optional<Categoria> categoria = findById(id);
		if (categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}

		return categoria.get();
	}

	private void validateCategoriaduplicate(Categoria categoria) {
		Categoria categoriaFound = categoriaRepository.findByNome(categoria.getNome());
		if (categoriaFound != null && categoriaFound.getCodigo() != categoria.getCodigo()) {
			throw new BusinessRuleException(
					String.format("A categoria %s já esta cadastrada ", categoria.getNome().toUpperCase()));
		}
	}

}
