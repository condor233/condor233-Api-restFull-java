package com.example.vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.vendas.entity.Cliente;
import com.example.vendas.exception.BusinessRuleException;
import com.example.vendas.repository.ClienteRepository;

import net.bytebuddy.implementation.bytecode.Throw;

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
	
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}
	
	public Cliente update(Long id, Cliente cliente) {
		 Cliente clienteUpdate = validarClienteExiste(id);
		 validarClienteDuplicado(cliente);
		 BeanUtils.copyProperties(cliente, clienteUpdate, "codigo");
		 return clienteRepository.save(clienteUpdate);
	}
	
	private Cliente validarClienteExiste(Long id) {
		Optional<Cliente> cliente = findById(id);
		if(cliente.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return cliente.get();
	}
	

	private void validarClienteDuplicado(Cliente cliente) {
		Cliente clienteName = clienteRepository.findByNome(cliente.getNome());
		if (clienteName != null && clienteName.getCodigo() != cliente.getCodigo()) {
			throw new BusinessRuleException(
					String.format("O cliente %s já está cadastrado", cliente.getNome().toUpperCase()));
		}
	}
	
	

}
