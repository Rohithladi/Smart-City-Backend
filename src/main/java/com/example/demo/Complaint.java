package com.example.demo;

import java.util.Arrays;


import jakarta.persistence.*;
import java.util.Base64;



@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String description;
    private String urgency;
    private String location;
    private String status;
    private String username;
    private String email;
    private String feedback;
    private int rating ;
    private String City ;

    @Lob
    private byte[] attachment;

    
    @Transient
    private String attachmentBase64;
	public Long getId() {
		return id;
	}
	
	public String getAttachmentAsBase64() {
        return attachment != null ? Base64.getEncoder().encodeToString(attachment) : null;
    }
	public String getAttachmentBase64() {
        return getAttachmentAsBase64();
    }

    public void setAttachmentBase64(String attachmentBase64) {
        this.attachmentBase64 = attachmentBase64;
    }


	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 

	public String getUrgency() {
		return urgency;
	}

	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		this.City = city;
	}

	@Override
	public String toString() {
		return "Complaint [id=" + id + ", category=" + category + ", description=" + description + ", urgency="
				+ urgency + ", location=" + location + ", status=" + status + ", username=" + username + ", email="
				+ email + ", feedback=" + feedback + ", rating=" + rating + ", City=" + City + ", attachment="
				+ Arrays.toString(attachment) + ", attachmentBase64=" + attachmentBase64 + "]";
	}

	
	  
	
    
}
