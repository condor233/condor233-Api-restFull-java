package com.example.vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.vendas.dto.venda.ClienteVendaResponseDTO;
import com.example.vendas.dto.venda.VendaRequestDTO;
import com.example.vendas.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaControlador {
	
	@Autowired
	private VendaService vendaService;
	
	@ApiOperation(value = "Listar vendas por cliente", nickname = "listVendaToCliente")
	@GetMapping("/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO> listVendaToCliente(
			@PathVariable Long codigoCliente){
		return ResponseEntity.ok(vendaService.listaVendaPorCliente(codigoCliente));
	}
	
	@ApiOperation(value = "Listar vendas por código", nickname = "listVendaToCodigo")
	@GetMapping("/{codigoVenda}")
	public ResponseEntity<ClienteVendaResponseDTO> listVendaToPorCodigo(
			@PathVariable Long codigoVenda){
		return ResponseEntity.ok(vendaService.listarVendaPorCodigo(codigoVenda));
	}
	
	@ApiOperation(value = "Registrar venda", nickname = "save")
	@PostMapping("/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO> save(@PathVariable Long  codigoCliente, @RequestBody VendaRequestDTO vendaDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.salvar(codigoCliente, vendaDto));
	}

}
