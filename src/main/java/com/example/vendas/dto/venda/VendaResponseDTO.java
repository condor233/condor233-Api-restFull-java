package com.example.vendas.dto.venda;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Venda retorno DTO ")
public class VendaResponseDTO {
	
	@ApiModelProperty(value = "Codigo")
	private Long codigo;
	
	@ApiModelProperty(value = "Data")
	private LocalDate data;
	
	@ApiModelProperty(value = "Itens da venda")
	private List<ItemVendaResponseDTO> itemVendaDTO;

	public VendaResponseDTO(Long codigo, LocalDate data, List<ItemVendaResponseDTO> itemVendaDTO) {
		super();
		this.codigo = codigo;
		this.data = data;
		this.itemVendaDTO = itemVendaDTO;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<ItemVendaResponseDTO> getItemVendaDTO() {
		return itemVendaDTO;
	}

	public void setItemVendaDTO(List<ItemVendaResponseDTO> itemVendaDTO) {
		this.itemVendaDTO = itemVendaDTO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof VendaResponseDTO)) {
			return false;
		}
		VendaResponseDTO other = (VendaResponseDTO) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(data, other.data);
	}
	
	

}
