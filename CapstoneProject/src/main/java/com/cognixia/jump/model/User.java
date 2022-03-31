package com.cognixia.jump.model;



import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="user")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static enum Role {
		ROLE_USER, ROLE_ADMIN
	}
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY  )
	private Integer id;
	
	@Column(unique= true, nullable = false)
	@NotBlank(message = "Username cannot be empty or null")
	private String username;
	
	@Column(unique=true, nullable = false)
	@NotNull
	private String email;
	
	@Column(nullable = false)
	@NotNull
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(nullable = false)
	private boolean enabled;
	
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Review> reviews;
	
	public User() {
		this( "N/A", "N/A", "N/A", Role.ROLE_USER);
	}


	public User(String username, String email, String password, Role role) {
		super();
		this.id = null;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enabled = true;
		this.reviews =new HashSet<Review>();
	}
	
	public User(String username, String email, String password) {
		super();
		this.id = null;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = Role.ROLE_USER;
		this.enabled = true;
		this.reviews =new HashSet<Review>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + ", enabled=" + enabled + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (enabled != other.enabled) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (role != other.role) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
	
	
	
	
	
	
}
