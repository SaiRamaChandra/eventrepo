package com.event.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.event.entity.Event;
import com.event.repository.*;
import com.event.service.EventService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Create a new event
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // View all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Rest Template calls this function
    // View event by ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return updatedEvent != null ? new ResponseEntity<>(updatedEvent, HttpStatus.OK) :
                                      new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Cancel event
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String> cancelEvent(@PathVariable Long id) {
        boolean isCancelled = eventService.cancelEvent(id);
        return isCancelled ? ResponseEntity.ok("Event cancelled successfully.") :
                             ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting event: " + e.getMessage());
        }
    }
}
