package com.github.silviacristinaa.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Api(value="API REST Usuários")
public class UserResource {
	
	private static final String ID = "/{id}";
	private final ModelMapper modelMapper;
	private final UserService userService; 
	
	@GetMapping(value = ID)
	@ApiOperation(value="Retorna um usuário único")
	public ResponseEntity<UserDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(modelMapper.map(userService.findById(id), UserDto.class));
	}
	
	@GetMapping
	@ApiOperation(value="Retorna uma lista de usuários")
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok().body(userService.findAll()
				.stream().map(x -> modelMapper.map(x, UserDto.class)).collect(Collectors.toList()));
	}
	
	@PostMapping
	@ApiOperation(value="Cria um usuário")
	public ResponseEntity<UserDto> create(@RequestBody @Valid UserDto obj) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path(ID).buildAndExpand(userService.create(obj).getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = ID)
	@ApiOperation(value="Atualiza um usuário")
	public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserDto obj) {
		obj.setId(id);
		return ResponseEntity.ok().body(modelMapper.map(userService.update(obj), UserDto.class));
	}
	
	@DeleteMapping(value = ID)
	@ApiOperation(value="Deleta um usuário")
	public ResponseEntity<UserDto> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}