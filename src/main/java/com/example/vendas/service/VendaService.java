package com.example.vendas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vendas.dto.venda.ClienteVendaResponseDTO;
import com.example.vendas.dto.venda.ItemVendaResponseDTO;
import com.example.vendas.dto.venda.VendaResponseDTO;
import com.example.vendas.entity.Cliente;
import com.example.vendas.entity.ItemVenda;
import com.example.vendas.entity.Venda;
import com.example.vendas.exception.BusinessRuleException;
import com.example.vendas.repository.ItemVendaRepositorio;
import com.example.vendas.repository.VendaRepositorio;

@Service
public class VendaService {
	
	private VendaRepositorio vendaRepositorio;
	private ItemVendaRepositorio itemVendaRepositorio;
	private ClienteService clienteServico;

	@Autowired
	public VendaService(VendaRepositorio vendaRepositorio, ClienteService clienteServico,
			ItemVendaRepositorio itemVendaRepositorio) {
		this.vendaRepositorio = vendaRepositorio;
		this.clienteServico = clienteServico;
		this.itemVendaRepositorio = itemVendaRepositorio;
	}

	public ClienteVendaResponseDTO listaVendaPorCliente(Long codigoCliente) {
		Cliente cliente = validClienteVendaExist(codigoCliente);
		List<VendaResponseDTO> vendaResponseDtoList = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
				.map(this::criandoVendaResponseDTO).collect(Collectors.toList());
		return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDtoList);
	}

	private Cliente validClienteVendaExist(Long codigoCliente) {
		Optional<Cliente> cliente = clienteServico.findById(codigoCliente);
		if (cliente.isEmpty()) {
			throw new BusinessRuleException(
					String.format("o Cliente de código %s informado não existe no cadastro", codigoCliente));
		}

		return cliente.get();
	}

	private VendaResponseDTO criandoVendaResponseDTO(Venda venda) {
		List<ItemVendaResponseDTO> itensVendasResponseDTO = itemVendaRepositorio.findByVendaCodigo(venda.getCodigo())
				.stream().map(this::criandoItensVendaResponseDto).collect(Collectors.toList());
		return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendasResponseDTO);

	}

	private ItemVendaResponseDTO criandoItensVendaResponseDto(ItemVenda itemVenda) {
		return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
				itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
	}


}
