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
import com.cognixia.jump.service.JwtUserDetailsService;
import com.cognixia.jump.service.ReviewService;
import com.cognixia.jump.service.AirlineService;
import com.cognixia.jump.util.JwtUtil;

@RequestMapping("/api")
@RestController
public class AirlineController {

	
//	@Autowired
//	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AirlineService airlineService;
	
	@Autowired
	ReviewService reviewService;
	
	@PostMapping("/airline")
	public ResponseEntity<?> createAirline(@RequestBody Airline al) {
		Airline update = airlineService.createAirline(al);
		return ResponseEntity.status(201).body("Book " + al.getAirlineName() + " was created");
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
		Optional<Airline> temp = airlineService.getAirlineByName(name);
		int id = temp.get().getAirlineID();
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
	
	@PostMapping("/review")
	public ResponseEntity<?> createReview(@RequestBody Review review){
		Optional<Review> created = reviewService.createReview(review);
		if(created.isEmpty()) {
			return ResponseEntity.status(404).body("Failed to create review");
		}
		return ResponseEntity.status(201).body(created.get());
	}
	
	@GetMapping("/review/myreview")
	public List<Review> getUserReviews(){
		return reviewService.getUserReviews();
	}
	
	@GetMapping("/review/{id}")
	public List<Review> getReviewById(@PathVariable int id){
		return reviewService.getReviewById(id);
	}
	
	@GetMapping("/review/{user}")
	public List<Review> getReviewsByUser(@PathVariable String user){
		return reviewService.getReviewsByUser(user);
	}
	
	@GetMapping("/review/{airline}")
	public List<Review> getReviewsByAirline(@PathVariable String airline){
		return reviewService.getReviewsByAirline(airline);
	}
	
	@PutMapping("/review")
	public ResponseEntity<?> updateReview(@RequestBody Review review){
		Optional<Review> updated = reviewService.updateReview(review);
			if(updated.isEmpty()) {
				return ResponseEntity.status(404).body("Failed to update review");
			}
			return ResponseEntity.status(200).body(updated.get());
		
	}
	
	@DeleteMapping("review/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable int id){
		Optional<Review> deleted = reviewService.deleteReview(id);
		if(deleted.isEmpty()) {
			return ResponseEntity.status(404).body("Failed to delete review");
		}
		return ResponseEntity.status(200).body(deleted.get());
	}
	
}
