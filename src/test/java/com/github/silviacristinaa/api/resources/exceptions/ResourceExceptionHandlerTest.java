package com.github.silviacristinaa.api.resources.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.silviacristinaa.api.services.exceptions.DataIntegratyViolationException;
import com.github.silviacristinaa.api.services.exceptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
public class ResourceExceptionHandlerTest {
	
	@InjectMocks
	private ResourceExceptionHandler resourceExceptionHandler;
	
	@Test
	void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
		ResponseEntity<StandardError> response = resourceExceptionHandler
				.objectNotFound(
						new ObjectNotFoundException(String.format("Usuário %s não encontrado", 1L)),
						new MockHttpServletRequest());
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals(String.format("Usuário %s não encontrado", 1L), response.getBody().getError());
		assertEquals(404, response.getBody().getStatus());
		assertNotEquals("/users/2", response.getBody().getPath());
		assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
	}
	
	@Test
	void dataIntegrityViolationException() {
		ResponseEntity<StandardError> response = resourceExceptionHandler
				.dataIntegrityViolationException(
						new DataIntegratyViolationException("E-mail já cadastrado no sistema"),
						new MockHttpServletRequest());
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(StandardError.class, response.getBody().getClass());
		assertEquals("E-mail já cadastrado no sistema", response.getBody().getError());
		assertEquals(400, response.getBody().getStatus());
	}
}
