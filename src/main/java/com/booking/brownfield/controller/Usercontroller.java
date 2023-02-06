package com.booking.brownfield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.brownfield.dto.UserDto;
import com.booking.brownfield.service.UserService;

@RestController
@RequestMapping("/brownfield")
public class Usercontroller {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> adduser(@RequestBody UserDto userdto)
	{
		userService.addUser(userdto);
		return new ResponseEntity<>("User Added", HttpStatus.CREATED);
	}
	
	@GetMapping("/login/{email}&{password}")
	public ResponseEntity<?> checkLogin(@PathVariable("email") String email, @PathVariable("password") String password)
	{
		String loginMsg = null;
		if(userService.checkUser(email, password))
		{
			loginMsg= "Login Successfull";
		}
		
		return new ResponseEntity<>(loginMsg, HttpStatus.OK);
	}
	
	@PutMapping("/modify")
	public ResponseEntity<?> modifyUser(@RequestBody UserDto userdto)
	{
		userService.modifyUser(userdto);
		return new ResponseEntity<>("User Details Modified", HttpStatus.OK);
	}
	
//	@GetMapping("/{email}") //HIDING IT TO PREVENT DATA BREACH
//	public ResponseEntity<?> getUser(@PathVariable("email") String email)
//	{
//		return new ResponseEntity<>(userService.getUser(email),HttpStatus.FOUND);
//	}

}
