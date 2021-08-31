package com.example.vendas.exception;

public class BusinessRuleException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public BusinessRuleException(String mensagem) {
		super(mensagem);
	}

}
