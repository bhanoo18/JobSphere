package com.bhanoo.authenticationService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhanoo.authenticationService.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public Optional<User> findByEmail(String email);

}
