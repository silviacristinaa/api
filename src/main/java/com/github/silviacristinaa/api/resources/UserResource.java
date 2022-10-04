package com.github.silviacristinaa.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.silviacristinaa.api.domain.dtos.UserDto;
import com.github.silviacristinaa.api.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserResource {
	
	private static final String ID = "/{id}";
	private final ModelMapper modelMapper;
	private final UserService userService; 
	
	@GetMapping(value = ID)
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(modelMapper.map(userService.findById(id), UserDto.class));
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok().body(userService.findAll()
				.stream().map(x -> modelMapper.map(x, UserDto.class)).collect(Collectors.toList()));
	}
	
	@PostMapping
	public ResponseEntity<UserDto> create(@RequestBody UserDto obj) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path(ID).buildAndExpand(userService.create(obj).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = ID)
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto obj) {
		obj.setId(id);
		return ResponseEntity.ok().body(modelMapper.map(userService.update(obj), UserDto.class));
	}
	
	@DeleteMapping(value = ID)
	public ResponseEntity<UserDto> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}