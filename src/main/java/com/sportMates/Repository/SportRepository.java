package com.sportMates.Repository;

import com.sportMates.Entities.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, String> {

    Sport findByCode(String code);
}
