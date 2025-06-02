package com.event.service;
import com.event.entity.Event;
import com.event.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Create new event
    public Event createEvent(Event event) {
        event.setStatus(Event.EventStatus.ACTIVE);
        return eventRepository.save(event);
    }

    // View all events
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // View event by ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Update event details
    public Event updateEvent(Long id, Event updatedEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setName(updatedEvent.getName());
            event.setDate(updatedEvent.getDate());
            event.setTime(updatedEvent.getTime());
            event.setLocation(updatedEvent.getLocation());
            event.setDescription(updatedEvent.getDescription());
            return eventRepository.save(event);
        }
        return null;
    }
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
    // Cancel event
    public boolean cancelEvent(Long id) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setStatus(Event.EventStatus.CANCELLED);
            eventRepository.save(event);
            return true;
        }
        return false;
    }
}
