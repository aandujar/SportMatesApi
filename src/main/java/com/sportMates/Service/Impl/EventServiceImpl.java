package com.sportMates.Service.Impl;

import com.sportMates.Entities.Event;
import com.sportMates.Entities.FilterEvent;
import com.sportMates.Entities.Sport;
import com.sportMates.Entities.User;
import com.sportMates.Exception.BadRequestException;
import com.sportMates.Repository.EventRepository;
import com.sportMates.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.EventFilter;
import java.time.LocalDateTime;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventUserService eventUserService;

    @Autowired
    private SportService sportService;

    @Autowired
    private UserService userService;

    @Override
    public Page<Event> getEventsByType(String eventType, int userId, FilterEvent filterEvent, Pageable pageable) {
        Page<Event> events;
        switch (eventType) {
            case "unsubscripted":
                events = this.getUnsubscriptedEvents(userId, filterEvent, pageable);
                break;
            case "inscripted":
                events = this.getInscriptedEvents(userId, filterEvent, pageable);
                break;
            case "owned":
                events = this.getOwnedEvents(userId, filterEvent, pageable);
                break;
            default:
                throw new BadRequestException("El tipo de evento no existe");
        }

        return  events;
    }

    private Page<Event> getUnsubscriptedEvents(int userId, FilterEvent filterEvent, Pageable pageable) {
        this.checkGetEventErrors(filterEvent);
        Page<Event> events;
        if (filterEvent.getFinishDate() == null) {
            events = eventRepository.getUnsubscriptedEventsList(userId, filterEvent.getSport(), filterEvent.getCountry(), filterEvent.getProvince(), filterEvent.getCity(), filterEvent.getPostalCode(), filterEvent.getDirection(), filterEvent.getStartDate(), pageable);
        } else {
            events = eventRepository.getUnsubscriptedEventsListBetweenDates(userId, filterEvent.getSport(), filterEvent.getCountry(), filterEvent.getProvince(), filterEvent.getCity(), filterEvent.getPostalCode(), filterEvent.getDirection(), filterEvent.getStartDate(), filterEvent.getFinishDate(), pageable);
        }
        return events;
    }

    private Page<Event> getInscriptedEvents(int userId, FilterEvent filterEvent, Pageable pageable) {
        this.checkGetEventErrors(filterEvent);
        Page<Event> events;
        if (filterEvent.getFinishDate() == null) {
            events = eventRepository.getInscriptedEventsList(userId, filterEvent.getSport(), filterEvent.getCountry(), filterEvent.getProvince(), filterEvent.getCity(), filterEvent.getPostalCode(), filterEvent.getDirection(), filterEvent.getStartDate(), pageable);
        } else{
            events = eventRepository.getInscriptedEventsListBetweenDates(userId, filterEvent.getSport(), filterEvent.getCountry(), filterEvent.getProvince(), filterEvent.getCity(), filterEvent.getPostalCode(), filterEvent.getDirection(), filterEvent.getStartDate(), filterEvent.getFinishDate(), pageable);
        }
        return events;
    }

    private Page<Event> getOwnedEvents(int userId, FilterEvent filterEvent, Pageable pageable) {
        this.checkGetEventErrors(filterEvent);
        Page<Event> events;
        if (filterEvent.getFinishDate() == null) {
            events = eventRepository.getOwnedEventsList(userId, filterEvent.getSport(), filterEvent.getCountry(), filterEvent.getProvince(), filterEvent.getCity(), filterEvent.getPostalCode(), filterEvent.getDirection(), filterEvent.getStartDate(), pageable);
        } else{
            events = eventRepository.getOwnedEventsListBetweenDates(userId, filterEvent.getSport(), filterEvent.getCountry(), filterEvent.getProvince(), filterEvent.getCity(), filterEvent.getPostalCode(), filterEvent.getDirection(), filterEvent.getStartDate(), filterEvent.getFinishDate(), pageable);
        }
        return events;
    }

    @Override
    @Transactional
    public boolean deleteEvent(int eventId, int userId) {
        Event event = this.getEventById(eventId);
        User user = userService.getById(userId);
        if(!isSamePerson(event, user.getId())) {
            throw new BadRequestException("No puedes borrar un evento que no has creado");
        }
        eventUserService.removeAllUsersFromEvent(eventId);
        eventRepository.delete(event);
        return true;
    }
    @Override
    public boolean inscribeUserToEvent(int eventId, int userId) {
        Event event = this.getEventById(eventId);

        if (isSamePerson(event,  userId)) {
            throw new BadRequestException("El creador no puede inscribirse al evento");
        }

        if (event.getCurrentParticipants() >= event.getNumberOfParticipants()) {
            throw new BadRequestException("Número máximo de inscritos alcanzado");
        }

        eventUserService.addUserToEvent(event, userId);

        return true;
    }


    @Override
    public boolean unsubscribeUserToEvent(int eventId, int userId) {
        Event event = this.getEventById(eventId);
        if (isSamePerson(event, userId)) {
            throw new BadRequestException("El creador no puede desinscribirse del evento");
        }
        eventUserService.removeUserFromEvent(eventId, userId);
        return true;
    }

    @Override
    @Transient
    public Event createEvent(Event event) {
        this.checkEventCorrectFields(event);
        User user = userService.getById(event.getCreator().getId());
        Sport sport = sportService.getSportByCode(event.getSport().getCode());
        event.setCreator(user);
        event.setSport(sport);
        eventRepository.save(event);
        eventUserService.addUserCreatorToEvent(event, user);
        return event;
    }

    private void checkEventCorrectFields(Event event) {
        if(event.getSport() == null || event.getSport().getCode() == null){
            throw new BadRequestException("Deporte incorrecto");
        }
        if(event.getCity() == null) {
            throw new BadRequestException("Ciudad incorrecta");
        }

        if(event.getProvince() == null) {
            throw new BadRequestException("Provincia incorrecta");
        }

        if(event.getCountry() == null) {
            throw new BadRequestException("País incorrecto");
        }

        if(event.getLatitude() == null || event.getLongitude() == null) {
            throw new BadRequestException("Coordenadas incorrecto");
        }

        if(event.getDate() == null || event.getDate().compareTo(LocalDateTime.now())<= 0) {
            throw new BadRequestException("Fecha incorrecta");
        }

        if(event.getObservation() == null) {
            event.setObservation("");
        }

        if(event.getPostalCode() == null) {
            event.setPostalCode("");
        }

        if(event.getDirection() == null) {
            event.setDirection("");
        }
    }

    private Event getEventById(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new BadRequestException("No se ha encontrado el evento"));
    }

    private void checkGetEventErrors(FilterEvent filterEvent) {
        LocalDateTime now = LocalDateTime.now();
        if(filterEvent.getStartDate() != null && filterEvent.getStartDate().compareTo(now) < 0){
            throw new BadRequestException("No puedes obtener eventos anteriores a ahora");
        }

        if(filterEvent.getStartDate() == null) {
            filterEvent.setStartDate(now);
        }

        if(filterEvent.getFinishDate() != null && filterEvent.getStartDate().compareTo(filterEvent.getFinishDate()) > -1){
            throw new BadRequestException("La fecha de fin es superior o igual a la de inicio");
        }

        if(filterEvent.getSport() != null && filterEvent.getSport().isBlank()){
            filterEvent.setSport(null);
        }
    }

    private boolean isSamePerson(Event event, int userId) {
        return event.getCreator().getId() == userId;
    }
}
