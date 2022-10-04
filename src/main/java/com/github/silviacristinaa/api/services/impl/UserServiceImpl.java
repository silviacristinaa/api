package com.github.silviacristinaa.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.github.silviacristinaa.api.domain.User;
import com.github.silviacristinaa.api.domain.dtos.UserDto;
import com.github.silviacristinaa.api.repositories.UserRepository;
import com.github.silviacristinaa.api.services.UserService;
import com.github.silviacristinaa.api.services.exceptions.DataIntegratyViolationException;
import com.github.silviacristinaa.api.services.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	@Override
	public User findById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.orElseThrow(() -> new ObjectNotFoundException(String.format("Usuário %s não encontrado", id)));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User create(UserDto obj) {
		findByEmail(obj);
		return userRepository.save(modelMapper.map(obj, User.class));
	}

	@Override
	public void findByEmail(UserDto obj) {
		Optional<User> user = userRepository.findByEmail(obj.getEmail());
		if(user.isPresent()) {
			throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
		}
	}
}
