package com.github.silviacristinaa.api.services.impl;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.silviacristinaa.api.domain.User;
import com.github.silviacristinaa.api.domain.dtos.UserDto;
import com.github.silviacristinaa.api.repositories.UserRepository;
import com.github.silviacristinaa.api.services.exceptions.DataIntegratyViolationException;
import com.github.silviacristinaa.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class UserServiceImplTest {
	
	private static final Long   ID       = 1L;
	private static final String NAME     = "Silvia";
	private static final String EMAIL    = "silvia@mail.com";
	private static final String PASSWORD = "123";
	private static final int INDEX = 0;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	private User user;
	private UserDto userDto;
	private Optional<User> optionalUser;
	
	@BeforeEach
	void setUp() {
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		
		User response = userServiceImpl.findById(ID);
		
		assertNotNull(response);
		
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
		        () -> userServiceImpl.findById(ID));
		
		assertEquals(String.format("Usuário %s não encontrado", ID), exception.getMessage());
	}
	
	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(userRepository.findAll()).thenReturn(List.of(user));
		
		List<User> response = userServiceImpl.findAll();
		
		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(User.class, response.get(INDEX).getClass());
		
		assertEquals(ID, response.get(INDEX).getId());
		assertEquals(NAME, response.get(INDEX).getName());
		assertEquals(EMAIL, response.get(INDEX).getEmail());
		assertEquals(PASSWORD, response.get(INDEX).getPassword());
	}
	
	@Test
	void whenCreateThenReturnSuccess() {
		when(userRepository.save(Mockito.any())).thenReturn(user);
		
		User response = userServiceImpl.create(userDto);
		
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}
	
	@Test
	void whenCreateThenReturnAnDataIntegrityViolationException() {
		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
		
		userDto.setId(2L);
		
		DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class,
		        () -> userServiceImpl.create(userDto));
		
		assertEquals("E-mail já cadastrado no sistema", exception.getMessage());
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(userRepository.save(Mockito.any())).thenReturn(user);
		
		User response = userServiceImpl.update(userDto);
		
		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}
	
	@Test
	void whenUpdateThenReturnAnDataIntegrityViolationException() {
		when(userRepository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
		
		userDto.setId(2L);
		
		DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class,
		        () -> userServiceImpl.update(userDto));
		
		assertEquals("E-mail já cadastrado no sistema", exception.getMessage());
	}
	
	@Test
	void deleteWithSuccess() {
		when(userRepository.findById(anyLong())).thenReturn(optionalUser);
		userServiceImpl.delete(ID);
		verify(userRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	void deleteWithObjectNotFoundException() {
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
		        () -> userServiceImpl.delete(ID));
		
		assertEquals(String.format("Usuário %s não encontrado", ID), exception.getMessage());
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
}
