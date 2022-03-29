package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query(" select r from review r where r.user = ?1 ")
	public List<Review> findByUser(String user);
	
}