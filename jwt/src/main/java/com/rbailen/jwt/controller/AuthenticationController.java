package com.rbailen.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rbailen.jwt.controller.dto.request.JwtRequest;
import com.rbailen.jwt.controller.dto.response.JwtResponse;
import com.rbailen.jwt.service.JwtUserDetailsService;
import com.rbailen.jwt.util.JwtUtil;

/**
 * The Class AuthenticationController.
 */
@RestController
public class AuthenticationController {

	/** The authentication manager. */
	@Autowired
	private AuthenticationManager authenticationManager;

	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;
	
	/** The jwt user details service. */
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	/**
	 * Authenticate.
	 *
	 * @param request the request
	 * @return the jwt response
	 */
	@PostMapping("/authenticate")
	@ResponseStatus(value = HttpStatus.OK)
	public JwtResponse authenticate(@RequestBody JwtRequest request) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticate.getName());

		String token = jwtUtil.generateToken(userDetails);

		return JwtResponse.builder().token(token).build();
	}
}