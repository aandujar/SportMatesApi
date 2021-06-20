package com.sportMates.Service;

import com.sportMates.Entities.Event;
import com.sportMates.Entities.FilterEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {

    Page<Event> getEventsByType(String eventType, int userId, FilterEvent filterEvent, Pageable pageable);

    boolean deleteEvent(int eventId, int userId);

    boolean inscribeUserToEvent(int eventId, int userId);

    boolean unsubscribeUserToEvent(int eventId, int userId);

    Event createEvent(Event event);

}
