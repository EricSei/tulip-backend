package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Airline;
import com.cognixia.jump.service.AirlineService;

@RequestMapping("/api")
@RestController
public class AirlineController {
	
	@Autowired
	AirlineService serv;
	
	@PostMapping("/airline")
	public ResponseEntity<?> createAirline(@RequestBody Airline al) {
		Airline update = serv.createAirline(al);
		return ResponseEntity.status(201).body(update);
	}
	
	@GetMapping("/airline")
	public List<Airline> getAllAirlines() {
		return serv.getAllAirlines();
	}
	
	@GetMapping("/airline/{name}")
	public ResponseEntity<?> getAirlineByName(@PathVariable String name) {
		Optional<Airline> temp = serv.getAirlineByName(name);
		if(temp.isEmpty()) {
			return ResponseEntity.status(404).body("Airline not found");
		}
		Airline al = temp.get();
		return ResponseEntity.status(200).body(al); 
	}
	
	@GetMapping("/airline/id/{id}") 
	public ResponseEntity<?> getAirlineByID(@PathVariable int id) {
		Optional<Airline> found = serv.getAirlineById(id);
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
		Optional<Airline> temp = serv.getAirlineByName(name);
		int id = temp.get().getAirlineID();
		if(serv.deleteAirlineById(id)) {
			return ResponseEntity.status(200).body("Airline:" + name + " was deleted");
		}
		return ResponseEntity.status(404).body("Book not found");	
	}
	

}
