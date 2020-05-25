package com.rbailen.jwt.util;

import java.util.Date;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * The Class JwtUtil.
 */
@Component
public class JwtUtil {
	
	/** The Constant HEADER. */
	private static final String HEADER = "Authorization";
	
	/** The Constant PREFIX. */
	private static final  String PREFIX = "Bearer ";
	
	/** The secret. */
	@Value("${jwt.secret}")
	private String secret;

	/**
	 * Generate token.
	 *
	 * @param userDetails the user details
	 * @return the string
	 */
	public String generateToken(UserDetails userDetails) {		
		String token = Jwts
				.builder()
					.setSubject(userDetails.getUsername())
					.claim("authorities", userDetails.getAuthorities())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 600000))
					.signWith(SignatureAlgorithm.HS512, secret)
				.compact();

		return token;
	}
	
	/**
	 * Gets the token.
	 *
	 * @param request the request
	 * @return the token
	 */
	public String getToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER);

		if (null == token || !token.startsWith(PREFIX)) {
			return null;
		}

		return token.replace(PREFIX, "");
	}

	/**
	 * Validate token.
	 *
	 * @param token the token
	 * @return true, if successful
	 */
	public boolean validateToken(String token) {
		getClaimsFromToken(token);
		return true;
	}

	/**
	 * Gets the claim from token.
	 *
	 * @param <T> the generic type
	 * @param token the token
	 * @param claimsResolver the claims resolver
	 * @return the claim from token
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getClaimsFromToken(token);

		return claimsResolver.apply(claims);
	}

	/**
	 * Gets the claims from token.
	 *
	 * @param token the token
	 * @return the claims from token
	 */
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

}
