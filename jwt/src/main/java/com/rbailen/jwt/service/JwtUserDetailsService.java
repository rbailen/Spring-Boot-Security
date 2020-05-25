package com.rbailen.jwt.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rbailen.jwt.model.User;

/**
 * The Class JwtUserDetailsService.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				new BCryptPasswordEncoder().encode(user.getPassword()),
				user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
	}

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	private User findByUsername(String username) {
		List<User> users = getUsers();

		return users.stream().filter(user -> username.equalsIgnoreCase(user.getUsername())).findAny().orElse(null);
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	private List<User> getUsers() {
		Set<String> userRoles = new HashSet<String>();
		userRoles.add("ROLE_USER");

		Set<String> adminRoles = new HashSet<String>();
		adminRoles.add("ROLE_ADMIN");

		return Arrays.asList(new User("rbailen", "pwd", userRoles), new User("admin", "pwdAdmin", adminRoles));
	}

}
