package com.rbailen.oauth2.config;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * The Class ResourceServerConfiguration.
 */
@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	/** The security properties. */
	@Autowired
	private SecurityProperties securityProperties;

	/** The Constant ROOT_PATTERN. */
	private static final String ROOT_PATTERN = "/**";

	/**
	 * Jwt access token converter.
	 *
	 * @return the jwt access token converter
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setVerifierKey(getPublicKeyAsString());
		return converter;
	}

	/**
	 * Token store.
	 *
	 * @return the token store
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, ROOT_PATTERN).access("#oauth2.hasScope('read')")
				.antMatchers(HttpMethod.POST, ROOT_PATTERN).access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.PATCH, ROOT_PATTERN).access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.PUT, ROOT_PATTERN).access("#oauth2.hasScope('write')")
				.antMatchers(HttpMethod.DELETE, ROOT_PATTERN).access("#oauth2.hasScope('write')");
	}

	/**
	 * Gets the public key as string.
	 *
	 * @return the public key as string
	 */
	private String getPublicKeyAsString() {
		try {
			return IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
