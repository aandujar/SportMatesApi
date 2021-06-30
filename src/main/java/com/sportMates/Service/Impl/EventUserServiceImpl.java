package com.sportMates.Service.Impl;

import com.sportMates.Entities.Event;
import com.sportMates.Entities.EventUser;
import com.sportMates.Entities.User;
import com.sportMates.Exception.BadRequestException;
import com.sportMates.Repository.EventUserRepository;
import com.sportMates.Service.EventUserService;
import com.sportMates.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventUserServiceImpl implements EventUserService {

    @Autowired
    private EventUserRepository eventUserRepository;

    @Autowired
    private UserService userService;

    @Override
    public boolean isUserAddedToEvent(int eventId, int userId) {
        EventUser eventUser = eventUserRepository.findByEvent_IdAndUser_Id(eventId, userId);
        return eventUser != null;
    }

    @Override
    public EventUser addUserToEvent(Event event, int userId) {
        if (this.isUserAddedToEvent(event.getId(), userId)) {
            throw new BadRequestException("El usuario ya esta inscrito al evento");
        }

        if(event.getDate().compareTo(LocalDateTime.now()) < 1) {
            throw new BadRequestException("No puedes inscribirte a un evento que ya ha empezado");
        }

        EventUser eventUser = new EventUser();
        eventUser.setEvent(event);
        User user = userService.getById(userId);
        eventUser.setUser(user);
        eventUserRepository.save(eventUser);
        return eventUser;
    }

    @Override
    public EventUser addUserCreatorToEvent(Event event, User user) {
        if(event.getCreator().getId() != user.getId()) {
            throw new BadRequestException("El usuario no es el creador del evento");
        }
        if (this.isUserAddedToEvent(event.getId(), user.getId())) {
            throw new BadRequestException("El usuario ya esta inscrito al evento");
        }

        EventUser eventUser = new EventUser();
        eventUser.setEvent(event);
        eventUser.setUser(user);
        eventUserRepository.save(eventUser);
        return eventUser;
    }

    @Override
    public void removeUserFromEvent(int eventId, int userId) {
        EventUser eventUser = eventUserRepository.findByEvent_IdAndUser_Id(eventId, userId);
        if(eventUser == null) {
            throw new BadRequestException("El usuario no esta inscrito al evento");
        }
        eventUserRepository.delete(eventUser);
    }

    @Override
    public void removeAllUsersFromEvent(int eventId) {
        eventUserRepository.deleteByEvent_Id(eventId);
    }

}
