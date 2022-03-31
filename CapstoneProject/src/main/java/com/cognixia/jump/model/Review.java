package com.cognixia.jump.model;


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

import com.cognixia.jump.model.User.Role;
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
	
	@Column
	@Max(value=10)
	@Min(value=1)
	private int rating;
	
	//First Class, Business Class, Economy class. Since classes vary by Airline, validation needs to be done on front end
	@Column
	private String flightClass;
	
	@Column
	private String reviewText;
	
	@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="id")
    private User user;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="airlineID")
    private Airline airline;

	public Review() {
		this( "N/A", "N/A", 2, "N/A", "N/A");
	}
	
	
	public Review(String sourcePort, String destPort, @Max(10) @Min(1) int rating, String flightClass,
			String reviewText) {
		super();
		this.reviewID = null;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
		this.rating = rating;
		this.flightClass = flightClass;
		this.reviewText = reviewText;
	}
	
	public Review(String sourcePort, String destPort, @Max(10) @Min(1) int rating, String flightClass,
			String reviewText, User user) {
		super();
		this.reviewID = null;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
		this.rating = rating;
		this.flightClass = flightClass;
		this.reviewText = reviewText;
		this.user = user;
	}

	public Review(Integer reviewID, String sourcePort, String destPort, @Max(10) @Min(1) int rating, String flightClass,
			String reviewText, User user, Airline airline) {
		super();
		this.reviewID = reviewID;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
		this.rating = rating;
		this.flightClass = flightClass;
		this.reviewText = reviewText;
		this.user = user;
		this.airline = airline;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime * result + ((destPort == null) ? 0 : destPort.hashCode());
		result = prime * result + ((flightClass == null) ? 0 : flightClass.hashCode());
		result = prime * result + rating;
		result = prime * result + ((reviewID == null) ? 0 : reviewID.hashCode());
		result = prime * result + ((reviewText == null) ? 0 : reviewText.hashCode());
		result = prime * result + ((sourcePort == null) ? 0 : sourcePort.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Review other = (Review) obj;
		if (airline == null) {
			if (other.airline != null) {
				return false;
			}
		} else if (!airline.equals(other.airline)) {
			return false;
		}
		if (destPort == null) {
			if (other.destPort != null) {
				return false;
			}
		} else if (!destPort.equals(other.destPort)) {
			return false;
		}
		if (flightClass == null) {
			if (other.flightClass != null) {
				return false;
			}
		} else if (!flightClass.equals(other.flightClass)) {
			return false;
		}
		if (rating != other.rating) {
			return false;
		}
		if (reviewID == null) {
			if (other.reviewID != null) {
				return false;
			}
		} else if (!reviewID.equals(other.reviewID)) {
			return false;
		}
		if (reviewText == null) {
			if (other.reviewText != null) {
				return false;
			}
		} else if (!reviewText.equals(other.reviewText)) {
			return false;
		}
		if (sourcePort == null) {
			if (other.sourcePort != null) {
				return false;
			}
		} else if (!sourcePort.equals(other.sourcePort)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Review [reviewID=" + reviewID + ", sourcePort=" + sourcePort + ", destPort=" + destPort + ", rating="
				+ rating + ", flightClass=" + flightClass + ", reviewText=" + reviewText + ", user=" + user
				+ ", airline=" + airline + "]";
	}

	public Integer getReviewID() {
		return reviewID;
	}

	public void setReviewID(Integer reviewID) {
		this.reviewID = reviewID;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	public String getDestPort() {
		return destPort;
	}

	public void setDestPort(String destPort) {
		this.destPort = destPort;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(@Max(10) @Min(1) int rating) {
		this.rating = rating;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
//	@JsonIgnore
//    @ManyToOne(fetch=FetchType.LAZY, optional=false)
//    @JoinColumn(name="flightID")
//    private Flight flight;
	
	
	
}