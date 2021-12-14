package com.anupam.ProjectManagement.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.anupam.ProjectManagement.demo.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.anupam.ProjectManagement.security.SecurityConstants.EXPIRATION_TIME;
import static com.anupam.ProjectManagement.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {
	
	// Generated the token
	
	public String generateToken(Authentication authentication) {
		
		User user=(User)authentication.getPrincipal();
		Date now=new Date(System.currentTimeMillis());
		Date expiryDate =new Date(now.getTime()+EXPIRATION_TIME);
		
		String userId=Long.toString(user.getId());
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("id", Long.toString(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder().setSubject(userId).setClaims(claims)
				.setIssuedAt(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
	}
	
	// validate the token
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			System.err.println("Invalid JWT Signature");
		}
		catch(MalformedJwtException e) {
			System.err.println("Invalid JWT token");
		}
		catch(ExpiredJwtException e) {
			System.err.println("Expired JWT token");
		}
		catch(UnsupportedJwtException e) {
			System.err.println("Unsupported JWT token");
		}
		catch(IllegalArgumentException e) {
			System.err.println("JWT claims String is empty");
		}
		return false;
	}
	
	//Get user id from the token
	
	public Long getUserIdFromJWT(String token) {
		Claims claims=Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		return Long.parseLong(id);
	}
}
