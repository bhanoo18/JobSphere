package com.bhanoo.authenticationService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bhanoo.authenticationService.dto.AuthRequest;
import com.bhanoo.authenticationService.dto.AuthResponse;
import com.bhanoo.authenticationService.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody AuthRequest authRequest) {
		return authService.register(authRequest);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest authRequest) {
		return authService.login(authRequest);
	}
	
	
}
