package com.sportMates.Service;

import com.sportMates.Entities.Event;
import com.sportMates.Entities.EventUser;
import com.sportMates.Entities.User;

public interface EventUserService {

    boolean isUserAddedToEvent(int eventId, int userId);

    EventUser addUserToEvent(Event event, int userId);

    void removeUserFromEvent(int eventId, int userId);

    void removeAllUsersFromEvent(int eventId);

    EventUser addUserCreatorToEvent(Event event, User user);

}
