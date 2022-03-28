package com.cognixia.jump.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByUsername(String username);
	
	public Optional<User> findByEmail(String email);
}
