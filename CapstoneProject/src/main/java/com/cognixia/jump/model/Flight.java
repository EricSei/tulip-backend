package com.cognixia.jump.model;
//This is a possible extension. In order to facilitate that, its been built, but all functionality has been commented out.


import java.util.Set;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.validation.constraints.Pattern;
//
//import com.cognixia.jump.model.User.Role;
//import com.fasterxml.jackson.annotation.JsonIgnore;

//@Entity
//@Table(name="flights")
public class Flight {
//	Flight
//	id
//	flight code
//	destination airport - one to many
//	starting point air port - one to many
//	one to many to users
//	many to one to airlines
//	one to many to reviews
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer flightID;
	
//	@Column
	private String destPort;
	
// 	@Column
	private String startPort;
		
//	@JsonIgnore
//	@OneToMany(mappedBy="flights", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Review> reviews;

//	@JsonIgnore
//	@ManyToMany(cascade = {CascadeType.ALL})
//  @JoinTable(name = "employees_projects",
//		        joinColumns = {@JoinColumn(name = "employee_id")},
//		        inverseJoinColumns = {@JoinColumn(name = "project_id")})
//	private Set<Airline> airlines;

}


