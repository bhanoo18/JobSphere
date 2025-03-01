package com.bhanoo.authenticationService.service;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bhanoo.authenticationService.dto.AuthRequest;
import com.bhanoo.authenticationService.dto.AuthResponse;
import com.bhanoo.authenticationService.model.User;
import com.bhanoo.authenticationService.repository.UserRepository;
import com.bhanoo.authenticationService.util.JWTUtil;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTUtil jwtUtil;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}
	
	public String register(AuthRequest authRequest) {
		if(userRepository.findByEmail(authRequest.getEmail()).isPresent()) {
			throw new RuntimeException("User already exists");
		}
		
		User user = new User();
		user.setEmail(authRequest.getEmail());
		user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		user.setRoles(Collections.singleton("ROLE_JOB_SEEKER"));//Default Role
		userRepository.save(user);
		
		return "User registered successfully";
	}
	
	public AuthResponse login(AuthRequest authRequest) {
		User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}
		
		String token = jwtUtil.genrateToken(user.getEmail());
		return new AuthResponse(token);
	}
	
	
}
