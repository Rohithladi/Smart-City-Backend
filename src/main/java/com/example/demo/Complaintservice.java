package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class Complaintservice {

    @Autowired
    private Complaintrepo complaintRepository;

    public void saveComplaint(String category, String description, String urgency, String location, MultipartFile attachment, String username, String email) {
        Complaint complaint = new Complaint();
        complaint.setCategory(category);
        complaint.setDescription(description);
        complaint.setUrgency(urgency);
        complaint.setLocation(location);
        complaint.setUsername(username);
        complaint.setEmail(email);
        complaint.setStatus("Pending");

        if (attachment != null && !attachment.isEmpty()) {
            try {
                complaint.setAttachment(attachment.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload attachment", e);
            }
        }

        complaintRepository.save(complaint);
    }

    public Complaint updateStatus(Long complaintId, String newStatus) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        // Update status to In Progress
        complaint.setStatus(newStatus);
        return complaintRepository.save(complaint);
    }
    
    public boolean submitRating(Long complaintId, int rating) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint != null) {
            complaint.setRating(rating);
            complaint.setStatus("Satisfied");
            complaintRepository.save(complaint);
            return true;
        }
        return false;
    }

    public boolean submitFeedback(Long complaintId, String feedback) {
        Complaint complaint = complaintRepository.findById(complaintId).orElse(null);
        if (complaint != null) {
            complaint.setFeedback(feedback);
            complaint.setStatus("Sent to Admin");
            complaintRepository.save(complaint);
            return true;
        }
        return false;
    }
    
}
