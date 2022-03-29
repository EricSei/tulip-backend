package com.cognixia.jump.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.IncorrectLoginException;
import com.cognixia.jump.exception.NoSuchUserException;
import com.cognixia.jump.model.Airline;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
<<<<<<< HEAD
import com.cognixia.jump.service.JwtUserDetailsService;
import com.cognixia.jump.service.ReviewService;
=======
>>>>>>> c44f639306334a084ba0238fa38d7132fe66e0c8
import com.cognixia.jump.service.AirlineService;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@RestController
public class AirlineController {

	
//	@Autowired
//	AuthenticationManager authenticationManager;
	
//	
//	@Autowired
//	JwtUtil jwtUtil;
	
	@Autowired
<<<<<<< HEAD
	JwtUtil jwtUtil;
	
	@Autowired
	AirlineService airlineService;
	
	
	
	@PostMapping("/airline")
	public ResponseEntity<?> createAirline(@RequestBody Airline al) {
		Airline update = airlineService.createAirline(al);
		return ResponseEntity.status(201).body("Book " + al.getAirlineName() + " was created");
=======
	AirlineService serv;
	
	@PostMapping("/airline")
	public ResponseEntity<?> createAirline(@RequestBody Airline al) {
		Airline update = serv.createAirline(al);
		return ResponseEntity.status(201).body("Airline " + al.getAirlineName() + " was created");
>>>>>>> c44f639306334a084ba0238fa38d7132fe66e0c8
	}
	
//	R - (“/airline/id/{id}”) - gets airline get id - admin only
//	R - (“/airline/{airlineName}”) - gets airline with that name
//	R - (“/airline”) - lists airlines
	
	@GetMapping("/airline")
	public List<Airline> getAllAirlines() {
		return airlineService.getAllAirlines();
	}
	
	@GetMapping("/airline/{name}")
	public ResponseEntity<?> getAirlineByName(@PathVariable String name) {
<<<<<<< HEAD
		Optional<Airline> temp = airlineService.getAirlineByName(name);
		int id = temp.get().getAirlineID();
=======
		Optional<Airline> temp = serv.getAirlineByName(name);
>>>>>>> c44f639306334a084ba0238fa38d7132fe66e0c8
		if(temp.isEmpty()) {
			return ResponseEntity.status(404).body("Airline not found");
		}
		Airline al = temp.get();
		return ResponseEntity.status(200).body(al); 
	}
	
	@GetMapping("/airline/id/{id}") 
	public ResponseEntity<?> getAirlineByID(@PathVariable int id) {
		Optional<Airline> found = airlineService.getAirlineById(id);
		if(found.isEmpty()) {
			return ResponseEntity.status(404).body("Airline not found");
		}
		Airline al = found.get();
		return ResponseEntity.status(200).body(al); 
	}
	
	@PutMapping("/airline")
	public ResponseEntity<?> updateAirline(@RequestBody Airline al) {
		Airline update = serv.updateAirline(al);
		if(update == null) {
			return ResponseEntity.status(404).body("Airline " + al.getAirlineName() + " was not found");
		}
		else {
			return ResponseEntity.status(202).body("Airline " + al.getAirlineName() + " was updated");
		}
	}
	
	@DeleteMapping("/airline/{name}")
	public ResponseEntity<?> deleteAirline(@PathVariable String name) {
		Optional<Airline> temp = airlineService.getAirlineByName(name);
		int id = temp.get().getAirlineID();
		if(airlineService.deleteAirlineById(id)) {
			return ResponseEntity.status(200).body("Airline:" + name + " was deleted");
		}
		return ResponseEntity.status(404).body("Book not found");	
	}
	
	
//	//- creates new Airline, admin only

//	U - (“/airline”) - updates airline info, admin only
//	D - (“/airline”) - deletes airline info, admin only
	
	
	
}
