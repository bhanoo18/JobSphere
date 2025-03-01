package com.bhanoo.authenticationService.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;

@SuppressWarnings("deprecation")
@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Value("${jwt.expirationMS}")
	private long jwtExpirationMS;
	
	public String genrateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMS))
				.signWith(SignatureAlgorithm.HS256,jwtSecret)
				.compact();
				
	}
	
	public String getEmailFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(jwtSecret.getBytes())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(jwtSecret.getBytes()).build()
			.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
