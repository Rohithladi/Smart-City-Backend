package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Registrationrepo extends JpaRepository<Registration, Long> {
	
    boolean existsByUsernameAndEmailAndEventId(String username, String email, Long eventId);

}
