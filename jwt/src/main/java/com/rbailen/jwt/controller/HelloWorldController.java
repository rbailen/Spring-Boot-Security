package com.rbailen.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class HelloWorldController.
 */
@RestController
public class HelloWorldController {

	/**
	 * Hello world.
	 *
	 * @param name the name
	 * @return the string
	 */
	@RequestMapping("hello")
	@ResponseStatus(value = HttpStatus.OK)
	public String helloWorld(@RequestParam(value = "name", defaultValue = "World") String name) {
		return "Hello " + name + "!!";
	}
	
	/**
	 * Hello world admin.
	 *
	 * @return the string
	 */
	@RequestMapping("hello/admin")
	@ResponseStatus(value = HttpStatus.OK)
	public String helloWorldAdmin() {
		return "Hello World Admin!";
	}
	
	/**
	 * Hello world authenticated.
	 *
	 * @return the string
	 */
	@RequestMapping("hello/authenticated")
	@ResponseStatus(value = HttpStatus.OK)
	public String helloWorldAuthenticated() {
		return "Hello World authenticated with any role!";
	}
}