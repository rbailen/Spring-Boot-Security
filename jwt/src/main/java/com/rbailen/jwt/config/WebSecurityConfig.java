package com.rbailen.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rbailen.jwt.service.JwtUserDetailsService;

/**
 * The Class WebSecurityConfig.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/** The jwt authorization filter. */
	@Autowired
	private JwtAuthorizationFilter jwtAuthorizationFilter;
	
	/** The jwt user details service. */
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	/** The jwt authentication entry point. */
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	/** The jwt access denied handler. */
	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	/**
	 * Authentication manager bean.
	 *
	 * @return the authentication manager
	 * @throws Exception the exception
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/**
	 * Password encoder.
	 *
	 * @return the b crypt password encoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configure global.
	 *
	 * @param auth the auth
	 * @throws Exception the exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//		auth.inMemoryAuthentication().withUser("rbailen").password(new BCryptPasswordEncoder().encode("pwd"))
//				.roles("USER");
	}
	
	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			
			.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
			
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
	        .and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/authenticate").permitAll()
			.antMatchers(HttpMethod.GET, "/hello").hasRole("USER")
			.antMatchers(HttpMethod.GET, "/hello/admin").hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated();
	}
}
