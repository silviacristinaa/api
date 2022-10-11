package com.github.silviacristinaa.api.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long id; 
	private String name; 
	private String email; 
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
}
