package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private Userrepo userRepository;

    public Users signUpUser(Users user) {
        // Check if a user with the same email and role already exists
        Users existingUser = userRepository.findByEmailAndRole(user.getEmail(), user.getRole());
        if (existingUser != null) {
            return null ;  
        }

        if ("moderator".equals(user.getRole()) || "resident".equals(user.getRole())) {
            user.setStatus("pending");
        } else {
            user.setStatus("approved");
        }
        return userRepository.save(user);
    }

    
    public List<Users> getServiceProviders() {
        return userRepository.findByRole("moderator");
    }
    public List<Users> residents() {
        return userRepository.findByRole("resident");
    }

    public Users acceptServiceProvider(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if ("pending".equals(user.getStatus())) {
                user.setStatus("accepted");
                return userRepository.save(user);
            }
        }
        return null;
    }
    public Users acceptresident(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if ("pending".equals(user.getStatus())) {
                user.setStatus("accepted");
                return userRepository.save(user);
            }
        }
        return null;
    }

    public boolean rejectServiceProvider(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }
    public boolean rejectresident(Long id) {
        Optional<Users> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return true;
        }
        return false;
    }
    
    public String signIn(String email, String password, String role) {
        Users user = userRepository.findByEmail(email);

        if (user == null) {
            return "Invalid credentials"; // User does not exist
        }

        if (!user.getPassword().equals(password)) {
            return "Invalid credentials"; // Password mismatch
        }

        if (!user.getRole().equals(role)) {
            return "Invalid credentials"; // Role mismatch
        }

        if (user.getStatus().equals("pending")) {
            if ("admin".equals(role)) {
                return "Admin approval is pending"; // If the status is pending for Admin
            } else if ("moderator".equals(role)) {
                return "Service provider approval is pending"; // If the status is pending for Service provider
            } else if ("resident".equals(role)) {
                return "Resident approval is pending"; // If the status is pending for Resident
            }
        }

        if (user.getStatus().equals("accepted")) {
            switch (role) {
                case "admin":
                    return "admin";
                case "moderator":
                    return "service provider"; // For service provider
                case "resident":
                    return "resident"; // For resident
                default:
                    return "Invalid credentials"; // Catch-all for any other roles
            }
        }

        return "Invalid credentials"; // Catch-all for any other errors
    }

    
    
    
   
}
