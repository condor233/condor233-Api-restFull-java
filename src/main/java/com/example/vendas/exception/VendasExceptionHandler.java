package com.example.vendas.exception;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class VendasExceptionHandler extends ResponseEntityExceptionHandler{

	private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> erros = gerarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String msgUser = "Recurso Não encontrado.";
		String msgDev = ex.toString();
		List<Error> erros = Arrays.asList(new Error(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String msgUser = "Recurso Não encontrado.";
		String msgDev = ex.toString();
		List<Error> erros = Arrays.asList(new Error(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(JpaObjectRetrievalFailureException.class) 
	public ResponseEntity<Object> handleJpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException ex, WebRequest request) {
		String msgUser = "Recurso Não encontrado.";
		String msgDev = ex.toString();
		List<Error> erros = Arrays.asList(new Error(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(BusinessRuleException.class)
		public ResponseEntity<Object> handleBusinessRuleException(BusinessRuleException ex, WebRequest request){
		String msgUser = ex.getMessage();
		String msgDev = ex.getMessage();
		List<Error> erros = Arrays.asList(new Error(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private List<Error> gerarListaDeErros(BindingResult bindingResult) {
		List<Error> erros = new ArrayList<Error>();
		bindingResult.getFieldErrors().forEach(fieldError -> {
			String msgUser = tratarMensagemDeErroParaUsuario(fieldError);
			String msgDev = fieldError.toString();
			erros.add(new Error(msgUser, msgDev));
		});
		return erros;
	}

	private String tratarMensagemDeErroParaUsuario(FieldError fieldError) {
		if (fieldError.getCode().equals(CONSTANT_VALIDATION_NOT_BLANK)) {
			return fieldError.getDefaultMessage().concat(" é obrigatório.");
		}
		
		if(fieldError.getCode().equals("Length")) {
			return fieldError.getDefaultMessage().concat(String.format(" deve ter entre %s e %s caracteres.", fieldError.getArguments()[2], fieldError.getArguments()[1]));
		}
		return fieldError.toString();
	}
}
