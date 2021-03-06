package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.cognixia.jump.exception.UnAuthorizedException;
import com.cognixia.jump.model.Airline;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.AirlineService;
import com.cognixia.jump.service.ReviewService;
import com.cognixia.jump.service.UserService;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	AirlineService airlineService;
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("review/users/{userId}/airlines/{airlineId}")
	public ResponseEntity<?> createReview(@RequestBody Review review, @PathVariable int userId, @PathVariable int airlineId){
		
		Airline airline = airlineService.getAirlineById(airlineId).get();
		User user = userService.getUserByid(userId).get();
		
		review.setAirline(airline);
		review.setUser(user);
		
		Review created = reviewService.createReview(review);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@GetMapping("/review/myreview")
	public List<Review> getUserReviews() throws ResourceNotFoundException{
		return reviewService.getUserReviews();
	}
	
	@GetMapping("/review/{id}")
	public List<Review> getReviewById(@PathVariable int id){
		return reviewService.getReviewById(id);
	}
	
	@GetMapping("/review/user/{user}")
	public List<Review> getReviewsByUser(@PathVariable String user) throws ResourceNotFoundException{
		return reviewService.getReviewsByUser(user);
	}
	
	@GetMapping("/review/airline/{airline}")
	public List<Review> getReviewsByAirline(@PathVariable String airline) throws ResourceNotFoundException{
		return reviewService.getReviewsByAirline(airline);
	}
	
	@PutMapping("/review")
	public ResponseEntity<?> updateReview(@RequestBody Review review) throws ResourceNotFoundException, UnAuthorizedException{
		Review updated = reviewService.updateReview(review);
			return ResponseEntity.status(200).body(updated);
		
	}
	
	@DeleteMapping("review/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable int id) throws ResourceNotFoundException, UnAuthorizedException{
		Optional<Review> deleted = reviewService.deleteReview(id);
		if(deleted.isEmpty()) {
			return ResponseEntity.status(404).body("Failed to delete review");
		}
		return ResponseEntity.status(200).body(deleted.get());
	}
}
