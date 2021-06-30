package com.sportMates.Controller;

import com.sportMates.Service.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eventUser")
public class EventUser {

    @Autowired
    private EventUserService eventUserService;

    @GetMapping("/{eventId}/{userId}")
    public Object isUserInscriptedInEvent(@PathVariable int eventId, @PathVariable int userId) {
        return eventUserService.isUserAddedToEvent(eventId, userId);
    }
}
