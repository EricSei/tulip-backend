package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.exception.UnAuthorizedException;
import com.cognixia.jump.model.Airline;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;
import com.cognixia.jump.repository.AirlineRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class ReviewService {
	
	@Autowired
	ReviewRepository repo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AirlineRepository airlineRepo;

	public Review createReview(Review review) {
		review.setReviewID(null);
		repo.save(review);
		return review;
	}
	public List<Review> getAllReview() {
		return repo.findAll();
	}	
	public List<Review> getUserReviews() throws ResourceNotFoundException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User Not Found");
		}
		int id = user.get().getId();
		return repo.findByUser(id);
	}

	public List<Review> getReviewById(int id) {
		return repo.findByUser(id);
	}

	public List<Review> getReviewsByUser(String username) throws ResourceNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User Not Found");
		}
		int id = user.get().getId();
		return repo.findByUser(id);
	}

	public List<Review> getReviewsByAirline(String airline) throws ResourceNotFoundException {
		Optional<Airline> air = airlineRepo.findByAirlineName(airline);
		if (air.isEmpty()) {
			throw new ResourceNotFoundException("Airline Not Found");
		}
		int id = air.get().getAirlineID();
		return repo.findByAirline(id);
	}

	public Review updateReview(Review newReview) throws ResourceNotFoundException, UnAuthorizedException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User Not Found");
		}
		Optional<Review> oldReview = repo.findById(newReview.getReviewID());
		if (oldReview.isEmpty()) {
			throw new ResourceNotFoundException("Review Not Found");
		} else if(newReview.getUser().getId()==user.get().getId()||user.get().getRole()==Role.ROLE_ADMIN) {
			repo.save(newReview);
			return newReview;
		}
		throw new UnAuthorizedException("Unauthorized");
	}

	public Optional<Review> deleteReview(int id) throws ResourceNotFoundException, UnAuthorizedException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
		if (user.isEmpty()) {
			throw new ResourceNotFoundException("User Not Found");
		}
		Optional<Review> review = repo.findById(id);
		if (review.isEmpty()){
			throw new ResourceNotFoundException("Review Not Found");
		} else if(review.get().getUser().getId()==user.get().getId()||user.get().getRole()==Role.ROLE_ADMIN) {
			repo.deleteById(id);
			return review;
		}
		throw new UnAuthorizedException("Unauthorized");
	}

}
