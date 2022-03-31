package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
//	R - (“/user”) - lists all users, reviews not included, admin only
//	R - (“/user/{username}”) - gets specified user - admin or authenticated user 
//	R - (“/user/email/{email}”) - gets User by email - admin only
//	R - (“/user/details”) - returns details for current authenticated user
//	U - (“/user”) - updates user account, users can only do their own, or admin access required
//	D - (“/user”) - deletes user account, users can only do their own, or admin access required

	
	@GetMapping("/health")
	public String getHealth(){
		return  "running...";
	}
	
	@GetMapping("/user")
	public List<User> getUsers(){
		return  userService.getAllUsers();
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) throws Exception{
		
		User created = userService.createUser(user);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<?> getUserByName(@PathVariable String username) throws ResourceNotFoundException{

	
		Optional<User> found = userService.getUserByName(username);
		
		if(!found.isPresent()) {
			throw new ResourceNotFoundException("user name not found");
		}
		User savedUser = found.get();
		return ResponseEntity.status(201).body(savedUser);
		
	}
	
	@GetMapping("/user/email/{email}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable String email){
		
		
		Optional<User> found = userService.getUserByEmail(email);
		
		if(found == null) {
			return ResponseEntity
					.status(401)
					.body("error :" + "User with " + email+ "can not found");
		}
		
		return ResponseEntity
				.status(201)
				.body(found.get());
		
	}
	
	@GetMapping("/user/authenticated/details")
	public ResponseEntity<?> getUserDetails(HttpServletRequest request) throws Exception{
		
		
		Optional<User> found = userService.getAuthenticatedUserDetail(request);
		
		if(found == null) {
			return ResponseEntity
					.status(401)
					.body("error :" + "Unauthorized. :(");
		}
		
		return ResponseEntity
				.status(201)
				.body(found.get());
		
	}
	
	
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteBookById(@PathVariable int id){
		
		if(userService.deleteUserById(id)) {
			return ResponseEntity
					.status(201)
					.body(id + "was deleted");
		}
		return ResponseEntity
				.status(401)
				.body(id + "was not found, or can't be deleted.");
	}
	
}
