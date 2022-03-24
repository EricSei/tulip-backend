package com.cognixia.jump.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="reviews")
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewID;
	
	@Column
	private String sourcePort;
	
	@Column
	private String destPort;
	
//Not actually necessary
//	  @Column
//    @Past
//    private DateTime takeOffTime
//	
//    @Column
//    @Past
//    private DateTime landingTime
	
	@Column(nullable = false)
	@Max(value=10)
	@Min(value=1)
	private int rating;
	
	//First Class, Business Class, Economy class. Since classes vary by Airline, validation needs to be done on front end
	@Column(nullable=false)
	private String flightClass;
	
	@Column(nullable=false)
	private String reviewText;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="userID")
    private User user;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="airlineID")
    private Airline airline;
	
}