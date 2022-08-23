package com.blogwithvikram.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwithvikram.blog.exceptions.ApiException;
import com.blogwithvikram.blog.payload.JwtAuthRequest;
import com.blogwithvikram.blog.payload.JwtAuthResponse;
import com.blogwithvikram.blog.payload.UserDto;
import com.blogwithvikram.blog.security.JwtTokenHelper;
import com.blogwithvikram.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper helper;
	
	@Autowired
	private UserDetailsService detailsService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest) throws Exception{
		authenticate(authRequest.getUsername(),authRequest.getPassword());
		UserDetails userDetails = detailsService.loadUserByUsername(authRequest.getUsername());
		String token = helper.generateToken(userDetails);
		JwtAuthResponse authResponse= new JwtAuthResponse();
		authResponse.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(authResponse,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(token);
		}catch (BadCredentialsException e) {
			System.out.println("invalid Details!");
			throw new ApiException("Invalid Username or password");
		}
		
	}
	
	//Register new user API
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto registeredNewUser = userService.registerNewUser(userDto);
	return new ResponseEntity<UserDto>(registeredNewUser,HttpStatus.CREATED);
	}
}
