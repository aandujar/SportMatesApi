package com.sportMates.Repository;

import com.sportMates.Entities.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventUserRepository extends JpaRepository<EventUser, Integer> {

    EventUser findByEvent_IdAndUser_Id(int eventId, int userId);

    void deleteByEvent_Id(int eventId);
}
