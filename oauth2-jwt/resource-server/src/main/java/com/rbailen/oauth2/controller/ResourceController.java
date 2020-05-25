package com.rbailen.oauth2.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class ResourceController.
 */
@RestController
@RequestMapping("/me")
public class ResourceController {

	/**
	 * Gets the principal.
	 *
	 * @param principal the principal
	 * @return the principal
	 */
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Principal> getPrincipal(final Principal principal) {
		return ResponseEntity.ok(principal);
	}

}