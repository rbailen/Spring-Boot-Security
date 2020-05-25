package com.rbailen.jwt.controller.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 601734428357967191L;

	/** The username. */
	private String username;

	/** The password. */
	private String password;

}