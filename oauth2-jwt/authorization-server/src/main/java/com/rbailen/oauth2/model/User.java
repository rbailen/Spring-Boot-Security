package com.rbailen.oauth2.model;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6780569531767443372L;

	/** The username. */
	private String username;

	/** The password. */
	private String password;
	
	/** The roles. */
	private Set<String> roles;
	
}
