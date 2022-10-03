package com.github.silviacristinaa.api.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long id; 
	private String name; 
	private String email; 
	
	@JsonIgnore
	private String password;
}
