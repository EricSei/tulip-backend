package com.cognixia.jump.model;

import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="airlines")
public class Airline {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer airlineID;
	
	@Column(nullable=false, unique=true)
	private String airlineName;
	
//	@ManyToMany(mappedBy = "projects", cascade = { CascadeType.ALL })
//  private Set<Flight> flights;
	
	@JsonIgnore
	@OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Review> reviews;
	
	public Airline() {
		this.airlineID = null;
		reviews = new HashSet<Review>();
	}
	

	public Airline(Integer airlineID, String airlineName, Set<Review> reviews) {
		super();
		this.airlineID = airlineID;
		this.airlineName = airlineName;
		this.reviews = reviews;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airlineID == null) ? 0 : airlineID.hashCode());
		result = prime * result + ((airlineName == null) ? 0 : airlineName.hashCode());
		result = prime * result + ((reviews == null) ? 0 : reviews.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Airline other = (Airline) obj;
		if (airlineID == null) {
			if (other.airlineID != null) {
				return false;
			}
		} else if (!airlineID.equals(other.airlineID)) {
			return false;
		}
		if (airlineName == null) {
			if (other.airlineName != null) {
				return false;
			}
		} else if (!airlineName.equals(other.airlineName)) {
			return false;
		}
		if (reviews == null) {
			if (other.reviews != null) {
				return false;
			}
		} else if (!reviews.equals(other.reviews)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Airline [airlineID=" + airlineID + ", airlineName=" + airlineName + ", reviews=" + reviews + "]";
	}

	public Integer getAirlineID() {
		return airlineID;
	}

	public void setAirlineID(Integer airlineID) {
		this.airlineID = airlineID;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
	


	
}