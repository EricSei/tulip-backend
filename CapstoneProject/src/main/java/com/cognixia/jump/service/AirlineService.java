package com.cognixia.jump.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Airline;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.AirlineRepository;

@Service
public class AirlineService {
	
	@Autowired
	AirlineRepository repo;

	//Begin Crud Operations
	
	//Create
	
	public Airline createAirline(Airline al) {
		Airline newa = new Airline();
		newa.setAirlineName(al.getAirlineName());
		newa.setReviews(al.getReviews());
		Airline created = repo.save(newa);
		return created;
	}
	
	//Read
	
	public List<Airline> getAllAirlines() {
		return repo.findAll();
	}	
	public Optional<Airline> getAirlineById(int id) {
		return repo.findById(id);
	}
	public Optional<Airline> getAirlineByName(String name) {
		return repo.findByAirlineName(name);
	}
	
	//Update
	public Airline updateAirline(Airline al) {
		if(repo.existsById(al.getAirlineID())) {
			Airline update = repo.save(al);
		}
		return null;
	}
	
	//Delete
	
	public boolean deleteAirlineById(int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}


}