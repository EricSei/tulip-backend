package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

	Optional<Airline> findByAirlineName(String name);
	
}