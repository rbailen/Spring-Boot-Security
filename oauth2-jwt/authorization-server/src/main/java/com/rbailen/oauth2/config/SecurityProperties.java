package com.rbailen.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import lombok.Data;

/**
 * The Class SecurityProperties.
 */
@ConfigurationProperties("security")
@Data
public class SecurityProperties {

	/** The jwt. */
	private JwtProperties jwt;

	/**
	 * Instantiates a new jwt properties.
	 */
	@Data
	public static class JwtProperties {

		/** The key store. */
		private Resource keyStore;

		/** The key store password. */
		private String keyStorePassword;

		/** The key pair alias. */
		private String keyPairAlias;
	}

}