package com.github.silviacristinaa.api.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.silviacristinaa.api.domain.User;
import com.github.silviacristinaa.api.domain.dtos.UserDto;
import com.github.silviacristinaa.api.services.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
public class UserResourceTest {
	
	private static final Long   ID       = 1L;
	private static final String NAME     = "Silvia";
	private static final String EMAIL    = "silvia@mail.com";
	private static final String PASSWORD = "123";
	private static final Integer INDEX   = 0;
	
	private User user = new User();
	private UserDto userDto = new UserDto();
	
	@InjectMocks
	private UserResource userResource;

	@Mock
	private UserServiceImpl userServiceImpl;
	
	@Mock
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {
		startUser();
	}
	
	@Test
	void whenFindByIdThenReturnSuccess() {
		when(userServiceImpl.findById(anyLong())).thenReturn(user);
		when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDto);
		
		ResponseEntity<UserDto> response = userResource.findById(ID);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDto.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(PASSWORD, response.getBody().getPassword());
	}
	
	@Test
	void whenFindAllThenReturnAListOfUserDto() {
		when(userServiceImpl.findAll()).thenReturn(List.of(user));
		when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDto);
		
		ResponseEntity<List<UserDto>> response = userResource.findAll();
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDto.class, response.getBody().get(INDEX).getClass());
		
		assertEquals(ID, response.getBody().get(INDEX).getId());
		assertEquals(NAME, response.getBody().get(INDEX).getName());
		assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
		assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
	}
	
	@Test
	void whenCreateThenReturnCreated() {
		when(userServiceImpl.create(Mockito.any())).thenReturn(user);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		ResponseEntity<UserDto> response = userResource.create(userDto);
		
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getHeaders().get("Location"));
	}
	
	@Test
	void whenUpdateThenReturnSuccess() {
		when(userServiceImpl.update(userDto)).thenReturn(user);
		when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(userDto);
		
		ResponseEntity<UserDto> response = userResource.update(ID, userDto);
		
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDto.class, response.getBody().getClass());
		
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
	}
	
	@Test
	void whenDeleteThenReturnSuccess() {
		ResponseEntity<UserDto> response = userResource.delete(ID);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(userServiceImpl, times(1)).delete(anyLong());
	}
	
	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
	}
}
