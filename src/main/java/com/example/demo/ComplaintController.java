package com.example.demo;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = {"http://localhost:3000", "https://smart-city-kappa.vercel.app"})

public class ComplaintController {

    @Autowired
    private Complaintservice complaintService; 
    
    
    @Autowired
    private Complaintrepo complaintrepo; 

    @PostMapping
    public String postComplaint(
        @RequestParam String category,
        @RequestParam String description,
        @RequestParam String urgency,
        @RequestParam String location,
        @RequestParam MultipartFile attachment,
        @RequestParam String username,
        @RequestParam String email
    ) {
        complaintService.saveComplaint(category, description, urgency, location, attachment, username, email);
        return "Complaint posted successfully!";
    }
    
    @GetMapping
    public ResponseEntity<List<Complaint>> getComplaintsByUsername(@RequestParam String username) {
        List<Complaint> complaints = complaintrepo.findByUsername(username);
        for (Complaint complaint : complaints) {
            complaint.setAttachmentBase64(complaint.getAttachmentAsBase64());
        }

        return ResponseEntity.ok(complaints);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintrepo.findAll();
        
        for (Complaint complaint : complaints) {
            complaint.setAttachmentBase64(complaint.getAttachmentAsBase64());
        }

        return ResponseEntity.ok(complaints);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestBody Complaint complaint) {
        // You can choose to set the status directly from the frontend or via the body request
        Complaint updatedComplaint = complaintService.updateStatus(id, "In Progress");
        return ResponseEntity.ok(updatedComplaint);
    }
    
    @PutMapping("/resolve/{id}")
    public ResponseEntity<Complaint> updateComplaintresolve(
            @PathVariable Long id,
            @RequestBody Complaint complaint) {
        // You can choose to set the status directly from the frontend or via the body request
        Complaint updatedComplaint = complaintService.updateStatus(id, "Resolved");
        return ResponseEntity.ok(updatedComplaint);
    }
    
    
    @PutMapping("/reject/{id}")
    public ResponseEntity<Complaint> updateComplaintReject(
            @PathVariable Long id,
            @RequestBody Complaint complaint) {
        // You can choose to set the status directly from the frontend or via the body request
        Complaint updatedComplaint = complaintService.updateStatus(id, "Rejected");
        return ResponseEntity.ok(updatedComplaint);
    }

 

    // Submit feedback (directly as a string)
    @PostMapping("/feedback/{id}")
    public ResponseEntity<String> submitFeedback(@PathVariable Long id, @RequestBody String feedback) {
        boolean isFeedbackSubmitted = complaintService.submitFeedback(id, feedback);
        if (isFeedbackSubmitted) {
            return ResponseEntity.ok("Feedback submitted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to submit feedback");
        }
    }
 // Modify this to accept a body with a "rating" field
    @PostMapping("/rate/{id}")
    public ResponseEntity<String> submitRating(@PathVariable Long id, @RequestBody RatingRequest ratingRequest) {
        boolean isRated = complaintService.submitRating(id, ratingRequest.getRating());
        if (isRated) {
            return ResponseEntity.ok("Rating submitted successfully!");
        } else {
            return ResponseEntity.badRequest().body("Failed to submit rating");
        }
    }
    
    
    

    // Create a new class to wrap the rating field
    public static class RatingRequest {
        private int rating;

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }

    

    
    
    

}
 