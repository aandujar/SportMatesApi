package com.sportMates.Repository;

import com.sportMates.Entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.creator.id <> :userId "
            + "AND (:sportCode IS NULL OR e.sport.code = :sportCode) "
            + "AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%')) "
            + "AND LOWER(e.province) LIKE LOWER(CONCAT('%',:province,'%')) "
            + "AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')) "
            + "AND LOWER(e.postalCode) LIKE LOWER(CONCAT('%',:postalCode,'%')) "
            + "AND LOWER(e.direction) LIKE LOWER(CONCAT('%',:direction,'%')) "
            + "AND e.date > :date "
            + "AND e.id NOT IN (Select eu.event.id FROM EventUser eu WHERE eu.user.id = :userId)")
    Page<Event> getUnsubscriptedEventsList(@Param("userId") int userId, @Param("sportCode") String sportCode, @Param("country") String country, @Param("province") String province, @Param("city") String city, @Param("postalCode") String postalCode, @Param("direction") String direction, @Param("date")LocalDateTime date, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.creator.id <> :userId "
            + "AND (:sportCode IS NULL OR e.sport.code = :sportCode) "
            + "AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%')) "
            + "AND LOWER(e.province) LIKE LOWER(CONCAT('%',:province,'%')) "
            + "AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')) "
            + "AND LOWER(e.postalCode) LIKE LOWER(CONCAT('%',:postalCode,'%')) "
            + "AND LOWER(e.direction) LIKE LOWER(CONCAT('%',:direction,'%')) "
            + "AND e.date BETWEEN :startDate AND :finishDate "
            + "AND e.id NOT IN (Select eu.event.id FROM EventUser eu WHERE eu.user.id = :userId)")
    Page<Event> getUnsubscriptedEventsListBetweenDates(@Param("userId") int userId, @Param("sportCode") String sportCode, @Param("country") String country, @Param("province") String province, @Param("city") String city, @Param("postalCode") String postalCode, @Param("direction") String direction, @Param("startDate")LocalDateTime startDate, @Param("finishDate") LocalDateTime finishDate, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.creator.id <> :userId "
            + "AND (:sportCode IS NULL OR e.sport.code = :sportCode) "
            + "AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%')) "
            + "AND LOWER(e.province) LIKE LOWER(CONCAT('%',:province,'%')) "
            + "AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')) "
            + "AND LOWER(e.postalCode) LIKE LOWER(CONCAT('%',:postalCode,'%')) "
            + "AND LOWER(e.direction) LIKE LOWER(CONCAT('%',:direction,'%')) "
            + "AND e.date > :date "
            + "AND e.id IN (Select eu.event.id FROM EventUser eu WHERE eu.user.id = :userId)")
    Page<Event> getInscriptedEventsList(@Param("userId") int userId, @Param("sportCode") String sportCode, @Param("country") String country, @Param("province") String province, @Param("city") String city, @Param("postalCode") String postalCode, @Param("direction") String direction, @Param("date") LocalDateTime date, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.creator.id <> :userId "
            + "AND (:sportCode IS NULL OR e.sport.code = :sportCode) "
            + "AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%')) "
            + "AND LOWER(e.province) LIKE LOWER(CONCAT('%',:province,'%')) "
            + "AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')) "
            + "AND LOWER(e.postalCode) LIKE LOWER(CONCAT('%',:postalCode,'%')) "
            + "AND LOWER(e.direction) LIKE LOWER(CONCAT('%',:direction,'%')) "
            + "AND e.date BETWEEN :startDate AND :finishDate "
            + "AND e.id IN (Select eu.event.id FROM EventUser eu WHERE eu.user.id = :userId)")
    Page<Event> getInscriptedEventsListBetweenDates(@Param("userId") int userId, @Param("sportCode") String sportCode, @Param("country") String country, @Param("province") String province, @Param("city") String city, @Param("postalCode") String postalCode, @Param("direction") String direction, @Param("startDate") LocalDateTime startDate, @Param("finishDate")LocalDateTime finishDate, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.creator.id = :userId "
            + "AND (:sportCode IS NULL OR e.sport.code = :sportCode) "
            + "AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%')) "
            + "AND LOWER(e.province) LIKE LOWER(CONCAT('%',:province,'%')) "
            + "AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')) "
            + "AND LOWER(e.postalCode) LIKE LOWER(CONCAT('%',:postalCode,'%')) "
            + "AND LOWER(e.direction) LIKE LOWER(CONCAT('%',:direction,'%')) "
            + "AND e.date > :date")
    Page<Event> getOwnedEventsList(@Param("userId") int userId, @Param("sportCode") String sportCode, @Param("country") String country, @Param("province") String province, @Param("city") String city, @Param("postalCode") String postalCode, @Param("direction") String direction, @Param("date")LocalDateTime date, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.creator.id = :userId "
            + "AND (:sportCode IS NULL OR e.sport.code = :sportCode) "
            + "AND LOWER(e.country) LIKE LOWER(CONCAT('%',:country,'%')) "
            + "AND LOWER(e.province) LIKE LOWER(CONCAT('%',:province,'%')) "
            + "AND LOWER(e.city) LIKE LOWER(CONCAT('%',:city,'%')) "
            + "AND LOWER(e.postalCode) LIKE LOWER(CONCAT('%',:postalCode,'%')) "
            + "AND LOWER(e.direction) LIKE LOWER(CONCAT('%',:direction,'%')) "
            + "AND e.date BETWEEN :startDate AND :finishDate")
    Page<Event> getOwnedEventsListBetweenDates(@Param("userId") int userId, @Param("sportCode") String sportCode, @Param("country") String country, @Param("province") String province, @Param("city") String city, @Param("postalCode") String postalCode, @Param("direction") String direction, @Param("startDate") LocalDateTime startDate, @Param("finishDate") LocalDateTime finishDate, Pageable pageable);
}
