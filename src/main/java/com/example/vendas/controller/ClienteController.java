package com.example.vendas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.dto.cliente.ClienteRequestDTO;
import com.example.vendas.dto.cliente.ClienteResponseDTO;
import com.example.vendas.entity.Cliente;
import com.example.vendas.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "findAll", nickname = "findAllClientes")
	@GetMapping
	public List<ClienteResponseDTO> findAll() {
		return clienteService.findAll().stream().map(cliente -> ClienteResponseDTO.convertToClienteDTO(cliente))
				.collect(Collectors.toList());

	}

	@ApiOperation(value = "findById", nickname = "findById")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDTO> findById(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);
		return cliente.isPresent() ? ResponseEntity.ok(ClienteResponseDTO.convertToClienteDTO(cliente.get()))
				: ResponseEntity.notFound().build();

	}

	@ApiOperation(value = "Save", nickname = "saveCategoria")
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> save(@RequestBody ClienteRequestDTO clienteDTO) {
		Cliente saveCliente = clienteService.save(clienteDTO.convertToEntity());
		return ResponseEntity.status(HttpStatus.CREATED).body(ClienteResponseDTO.convertToClienteDTO(saveCliente));
	}

}
