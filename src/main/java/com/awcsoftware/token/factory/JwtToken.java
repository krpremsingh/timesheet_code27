package com.awcsoftware.token.factory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtToken implements Token {
	
	public String generateToken(String username, String password) {
		long nowMillis = System.currentTimeMillis();
		LocalDateTime currentTime = LocalDateTime.now();
		 
		JwtBuilder builder = Jwts.builder()
				 .setSubject(username)
                .setIssuer("AWC-Software")
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
		          .setExpiration(Date.from(currentTime
		                  .plusSeconds(2000000)
		                  .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, "AWC-Software");
		 
		String token = builder.compact();
		 //System.out.println("token " + token);
		 //System.out.println("time taken " + (System.currentTimeMillis() - nowMillis));
		return token;
	}

	public String parseToken(String token) {
		Claims claims = Jwts.parser()         
			       .setSigningKey("AWC-Software")
			       .parseClaimsJws(token).getBody();
			    //System.out.println("ID: " + claims.getId());
			    //System.out.println("Subject: " + claims.getSubject());
			    //System.out.println("Issuer: " + claims.getIssuer());
			    //System.out.println("Expiration: " + claims.getExpiration());
			    return claims.getSubject();
	}
	
}
