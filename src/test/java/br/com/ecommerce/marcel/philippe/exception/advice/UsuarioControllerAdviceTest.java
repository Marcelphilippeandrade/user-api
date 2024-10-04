package br.com.ecommerce.marcel.philippe.exception.advice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.InternalServerError;
import br.com.ecommerce.marcel.philippe.exception.InvalidDateFormatException;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

public class UsuarioControllerAdviceTest {
	
	@InjectMocks
    private UsuarioControllerAdvice usuarioControllerAdvice;

    @Mock
    private UsuarioNotFoundException usuarioNotFoundException;
    
    @Mock
    private InvalidDateFormatException invalidDateFormatException;
    
    @Mock
    private InternalServerError internalServerError;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarOErroHandleUsuarioNotFound() {
        when(usuarioNotFoundException.getMessage()).thenReturn("Usuário não encontrado.");

        ErrorDTO errorDTO = usuarioControllerAdvice.handleUserNotFound(usuarioNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND.value(), errorDTO.getStatus());
        assertEquals("Usuário não encontrado.", errorDTO.getMessage());
        assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
    }
    
    @Test
    public void deveRetornarOErroHandleInvalidDateFormatException() {
    	when(invalidDateFormatException.getMessage()).thenReturn("Formato de data inválido, formato válido: dd-MM-yyyy");
    	
    	ErrorDTO errorDTO = usuarioControllerAdvice.handleInvalidDateFormatException(invalidDateFormatException);
    	
    	assertEquals(HttpStatus.BAD_REQUEST.value(), errorDTO.getStatus());
        assertEquals("Formato de data inválido, formato válido: dd-MM-yyyy", errorDTO.getMessage());
        assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
    }
    
    @Test
    public void deveRetornarOErroHandleGeneralException() {
    	when(internalServerError.getMessage()).thenReturn("Erro inesperado!");
    	
    	ErrorDTO errorDTO = usuarioControllerAdvice.handleGeneralException(internalServerError);
    	
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorDTO.getStatus());
        assertEquals("Erro inesperado!", errorDTO.getMessage());
        assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
    }
}
