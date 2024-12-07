package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "https://smart-city-kappa.vercel.app"})
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private Userrepo  userRepository ;
    @PostMapping("/signup")
    public ResponseEntity<Users> signUp(@RequestBody Users user) {
        Users savedUser = userService.signUpUser(user);
        return ResponseEntity.ok(savedUser);
    }
     
    @GetMapping("/serviceProviders")
    public ResponseEntity<List<Users>> getAllServiceProviders() {
        List<Users> serviceProviders = userService.getServiceProviders();
        return ResponseEntity.ok(serviceProviders);
    }
    @GetMapping("/users")
    public ResponseEntity<List<Users>> getresiednts() {
        List<Users> serviceProviders = userService.residents();
        return ResponseEntity.ok(serviceProviders);
    }
    
    

    // Endpoint to accept a service provider's role (change status to accepted)
    @PutMapping("/serviceProviders/{id}/accept")
    public ResponseEntity<Users> acceptServiceProvider(@PathVariable Long id) {
        Users updatedUser = userService.acceptServiceProvider(id);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/residents/{id}/accept")
    public ResponseEntity<Users> acceptresident(@PathVariable Long id) {
        Users updatedUser = userService.acceptresident(id);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint to reject a service provider's role (delete user)
    @DeleteMapping("/serviceProviders/{id}/reject")
    public ResponseEntity<Void> rejectServiceProvider(@PathVariable Long id) {
        boolean isDeleted = userService.rejectServiceProvider(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/resident/{id}/reject")
    public ResponseEntity<Void> rejectresident(@PathVariable Long id) {
        boolean isDeleted = userService.rejectServiceProvider(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/signin")
    public String signIn(@RequestParam String email, @RequestParam String password, @RequestParam String role) {
        return userService.signIn(email, password, role);
    }
    @GetMapping("/user-details/{email}")
    public ResponseEntity<?> getUserDetailsByEmail(@PathVariable String email) {
        try {
            // Fetch user by email using the repository method findByEmail
            Users user = userRepository.findByEmail(email);

            if (user != null) {
                // Return a response containing both username and city
                Map<String, String> userDetails = new HashMap<>();
                userDetails.put("name", user.getName());
                userDetails.put("city", user.getCity());
                return ResponseEntity.ok(userDetails);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            // Log the exception for debugging
            System.err.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please try again later.");
        }
    }


    }


