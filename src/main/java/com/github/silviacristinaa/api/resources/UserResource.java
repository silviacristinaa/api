package com.github.silviacristinaa.api.resources;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.silviacristinaa.api.domain.dtos.UserDto;
import com.github.silviacristinaa.api.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserResource {
	
	private final ModelMapper mapper;
	private final UserService userService; 
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDto.class));
	}
}
