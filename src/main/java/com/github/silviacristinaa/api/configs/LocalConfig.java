package com.github.silviacristinaa.api.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.silviacristinaa.api.domain.User;
import com.github.silviacristinaa.api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class LocalConfig {
	
	private final UserRepository userRepository;
	
	@Bean
	public void startDB()  {
		User u1 = new User(null, "Silvia", "silvia@mail.com", "123");
		User u2 = new User(null, "Luiz", "luiz@mail.com", "123");
		
		userRepository.saveAll(List.of(u1, u2));
	}
}
