package br.com.ecommerce.marcel.philippe.exception.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

import java.util.Date;

import org.springframework.http.HttpStatus;

@ControllerAdvice(basePackages = "br.com.ecommerce.marcel.philippe.controller")
public class UsuarioControllerAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ErrorDTO handleUsuarioNotFound(UsuarioNotFoundException usuarioNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Usuário não encontrado.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
}
