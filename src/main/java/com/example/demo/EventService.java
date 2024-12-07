package com.example.demo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EventService {

    private final Eventrepo eventRepository;

    public EventService(Eventrepo eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Events saveEvent(Events event, MultipartFile photo) throws IOException {
        if (photo != null) {
            event.setPhoto(photo.getBytes());
        }
        return eventRepository.save(event);
    }
    
    
}
