package com.github.silviacristinaa.api.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.silviacristinaa.api.domain.User;
import com.github.silviacristinaa.api.repositories.UserRepository;
import com.github.silviacristinaa.api.services.UserService;
import com.github.silviacristinaa.api.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	
	@Override
	public User findById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.orElseThrow(() -> new ObjectNotFoundException(String.format("Usuário %s não encontrado", id)));
	}
}
