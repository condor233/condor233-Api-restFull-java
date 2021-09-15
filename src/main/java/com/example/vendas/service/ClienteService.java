package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vendas.entity.Cliente;
import com.example.vendas.exception.BusinessRuleException;
import com.example.vendas.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Optional<Cliente> findById(Long id) {
		return clienteRepository.findById(id);
	}

	public Cliente save(Cliente cliente) {
		validarClienteDuplicado(cliente);
		return clienteRepository.save(cliente);
	}

	private void validarClienteDuplicado(Cliente cliente) {
		Cliente clienteName = clienteRepository.findByNome(cliente.getNome());
		if (clienteName != null && clienteName.getCodigo() != cliente.getCodigo()) {
			throw new BusinessRuleException(
					String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
		}
	}

}
