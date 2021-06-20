package com.sportMates.Controller;

import com.sportMates.Entities.Event;
import com.sportMates.Entities.FilterEvent;
import com.sportMates.Enum.EventType;
import com.sportMates.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/{eventType}")
    public Object getEventsByType(@PathVariable String eventType, @RequestParam int userId, @RequestBody FilterEvent filterEvent, Pageable pageable) {
        return eventService.getEventsByType(eventType, userId, filterEvent, pageable);
    }

    @PostMapping("/inscribe/{eventId}/{userId}")
    public Object inscribeUserToEvent(@PathVariable int eventId, @PathVariable int userId) {
        return eventService.inscribeUserToEvent(eventId, userId);
    }

    @DeleteMapping("/unsubscribe/{eventId}/{userId}")
    public Object unsubscribeUserToEvent(@PathVariable int eventId, @PathVariable int userId) {
        return eventService.unsubscribeUserToEvent(eventId, userId);
    }

    @DeleteMapping("/delete/{eventId}/{userId}")
    public Object deleteEvent(@PathVariable int eventId, @PathVariable int userId) {
        return eventService.deleteEvent(eventId, userId);
    }

    @PostMapping()
    public Object createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }
}
