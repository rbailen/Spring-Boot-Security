package com.rbailen.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.rbailen.oauth2.service.JwtUserDetailsService;

/**
 * The Class AuthorizationServerConfiguration.
 */
@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	/** The security properties. */
	@Autowired
	private SecurityProperties securityProperties;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/** The user details service. */
	@Autowired
	private JwtUserDetailsService userDetailsService;

	/** The authentication manager. */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Jwt access token converter. 
	 * Convert Oauth2 Token to JWT Token. 
	 * Uses the self-signed certificate to sign the generated tokens.
	 *
	 * @return the jwt access token converter
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(securityProperties.getJwt().getKeyStore(),
				securityProperties.getJwt().getKeyStorePassword().toCharArray());
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(securityProperties.getJwt().getKeyPairAlias()));
		return converter;
	}

	/**
	 * Reads data from the tokens .
	 *
	 * @return the token store
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * Configure features of the Authorization Server endpoints. 
	 * Token store, user approvals and grant types. 
	 * If you use password grants then you need to provide an AuthenticationManager.
	 *
	 * @param endpoints the endpoints
	 */
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
			.authenticationManager(authenticationManager)
			.accessTokenConverter(jwtAccessTokenConverter())
			.userDetailsService(userDetailsService)
			.tokenStore(tokenStore());
	}

	/**
	 * Authentication filters for the token endpoint. 
	 * Allow access to /oauth/check_token and /oauth/token_key endpoints. 
	 * These endpoints are not exposed by default (have access denyAll()).
	 *
	 * @param oauthServer the oauth server
	 */
	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
			.passwordEncoder(passwordEncoder)
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}

	/**
	 * OAuth2 clients details service.
	 *
	 * @param clients the clients
	 * @throws Exception the exception
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("clientId")
			.secret(passwordEncoder.encode("secret"))
			.authorizedGrantTypes("password", "refresh_token", "client_credentials")
			.scopes("read", "write")
			.authorities("ROLE_CLIENT")
			.accessTokenValiditySeconds(300);
	}
	
}
