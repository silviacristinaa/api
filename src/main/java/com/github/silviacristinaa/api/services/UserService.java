package com.github.silviacristinaa.api.services;

import java.util.List;

import com.github.silviacristinaa.api.domain.User;
import com.github.silviacristinaa.api.domain.dtos.UserDto;

public interface UserService {

	User findById(Long id);
	List<User> findAll();
	User create(UserDto obj);
}
