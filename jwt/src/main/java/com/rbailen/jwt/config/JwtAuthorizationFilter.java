package com.rbailen.jwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rbailen.jwt.service.JwtUserDetailsService;
import com.rbailen.jwt.util.JwtUtil;

import io.jsonwebtoken.Claims;

/**
 * The Class JwtAuthorizationFilter.
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	/** The jwt user details service. */
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	/** The jwt util. */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * Do filter internal.
	 *
	 * @param request the request
	 * @param response the response
	 * @param filterChain the filter chain
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtUtil.getToken(request);

		if (null != token) {
			String username = jwtUtil.getClaimFromToken(token, Claims::getSubject);

			if (jwtUtil.validateToken(token)) {
				UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		filterChain.doFilter(request, response);
	}

}
