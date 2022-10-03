package com.github.silviacristinaa.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.silviacristinaa.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
