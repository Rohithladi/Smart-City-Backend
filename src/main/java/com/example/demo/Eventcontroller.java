package com.example.demo;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = {"http://localhost:3000", "https://smart-city-kappa.vercel.app"})
public class Eventcontroller {

    @Autowired
    private EventService eventService;
    
    
    @Autowired
    private Eventrepo eventrepo;
    
    @Autowired
    private Registrationrepo registerrepo;




    @PostMapping
    public ResponseEntity<String> createEvent(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("category") String category,
            @RequestParam("startDateTime") String startDateTime,
            @RequestParam("endDateTime") String endDateTime,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            Events event = new Events();
            event.setName(name);
            event.setDescription(description);
            event.setLocation(location);
            event.setCategory(category);
            event.setStartDateTime(startDateTime);
            event.setEndDateTime(endDateTime);
            eventService.saveEvent(event, photo);
            return ResponseEntity.ok("Event saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving event: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Events>> getAllEvents() {
        List<Events> events = eventrepo.findAll();

        for (Events event : events) {
            // Convert the photo byte[] to base64 string for each event
            if (event.getPhoto() != null) {
                String base64Image = java.util.Base64.getEncoder().encodeToString(event.getPhoto());
                event.setBase64Photo(base64Image);  // Add this line to store the base64Image in the event object
            }
        }

        return ResponseEntity.ok(events);
    }
    
   
}
   
    

