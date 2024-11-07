package br.com.ecommerce.marcel.philippe.exception.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.InvalidDateFormatException;
import br.com.ecommerce.marcel.philippe.exception.UsuarioJaCadastradoException;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

import java.util.Date;

import org.springframework.http.HttpStatus;

@ControllerAdvice(basePackages = "br.com.ecommerce.marcel.philippe.controller")
public class UsuarioControllerAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ErrorDTO handleUserNotFound(UsuarioNotFoundException usuarioNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Usuário não encontrado.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDateFormatException.class)
	public  ErrorDTO handleInvalidDateFormatException(InvalidDateFormatException invalidDateFormatException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDTO.setMessage("Formato de data inválido, formato válido: dd-MM-yyyy");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorDTO handleGeneralException(Exception exception) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorDTO.setMessage("Erro inesperado!");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(UsuarioJaCadastradoException.class)
	public ErrorDTO handleUsuarioJaCadastradoException(UsuarioJaCadastradoException usuarioJaCadastradoException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.CONFLICT.value());
		errorDTO.setMessage("Usuário já cadastrado.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
}
