package com.example.vendas.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.vendas.dto.venda.ClienteVendaResponseDTO;
import com.example.vendas.dto.venda.ItemVendaRequestDTO;
import com.example.vendas.dto.venda.ItemVendaResponseDTO;
import com.example.vendas.dto.venda.VendaResponseDTO;
import com.example.vendas.entity.ItemVenda;
import com.example.vendas.entity.Produto;
import com.example.vendas.entity.Venda;

public class AbstractVendaService {
	protected ClienteVendaResponseDTO retornandoClienteVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
		return new ClienteVendaResponseDTO(venda.getCliente().getNome(), Arrays.asList(
				criandoVendaResponseDTO(venda, itensVendaList)));
	}

	protected VendaResponseDTO criandoVendaResponseDTO(Venda venda, List<ItemVenda> itensVendaList) {
		List<ItemVendaResponseDTO> itensVendaResponseDto = itensVendaList.stream()
				.map(this::criandoItensVendaResponseDto).collect(Collectors.toList());
		return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itensVendaResponseDto);

	}

	protected ItemVendaResponseDTO criandoItensVendaResponseDto(ItemVenda itemVenda) {
		return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
				itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
	}
	
	protected ItemVenda criandoItemVenda(ItemVendaRequestDTO itemVendaDto, Venda venda) {
		return new ItemVenda(itemVendaDto.getQuantidade(), itemVendaDto.getPrecoVendido(),
				new Produto(itemVendaDto.getCodigoProduto()), venda);
	}


}
