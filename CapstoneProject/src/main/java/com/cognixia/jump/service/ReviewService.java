package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.ReviewRepository;

public class ReviewService {
	
	@Autowired
	ReviewRepository repo;

	public Review createReview(Review review) {
		review.setReviewID(null);
		repo.save(review);
		return review;
	}

	public List<Review> getUserReviews() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String user = userDetails.getUsername();
		return repo.findByUser(user);
	}

	public List<Review> getReviewById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Review> getReviewsByUser(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Review> getReviewsByAirline(String airline) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Review> updateReview(Review review) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Review> deleteReview(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
