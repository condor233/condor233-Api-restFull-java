package com.example.vendas.dto.cliente;

import com.example.vendas.entity.Cliente;
import com.example.vendas.entity.Endereco;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Cliente requisição DTO")
public class ClienteRequestDTO {

	@ApiModelProperty(value = "Nome")
	private String nome;

	@ApiModelProperty(value = "Telefone")
	private String telefone;

	@ApiModelProperty(value = "Ativo")
	private Boolean ativo;

	@ApiModelProperty(value = "Endereço")
	private EnderecoRequestDTO enderecoDTO;

	public Cliente convertToEntity() {
		Endereco endereco = new Endereco(enderecoDTO.getLogradouro(), enderecoDTO.getNumero(),
				enderecoDTO.getComplemento(), enderecoDTO.getBairro(), enderecoDTO.getCep(), enderecoDTO.getCidade(),
				enderecoDTO.getEstado());
		return new Cliente(nome, telefone, ativo, endereco);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public EnderecoRequestDTO getEnderecoDTO() {
		return enderecoDTO;
	}

	public void setEnderecoDTO(EnderecoRequestDTO enderecoDTO) {
		this.enderecoDTO = enderecoDTO;
	}

}
