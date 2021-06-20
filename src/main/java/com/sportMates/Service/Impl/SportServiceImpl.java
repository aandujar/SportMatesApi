package com.sportMates.Service.Impl;

import com.sportMates.Entities.Sport;
import com.sportMates.Exception.BadRequestException;
import com.sportMates.Repository.SportRepository;
import com.sportMates.Service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportServiceImpl implements SportService {

    @Autowired
    private SportRepository sportRepository;

    @Override
    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    @Override
    public Sport getSportByCode(String sportCode) {
        Sport sport = sportRepository.findByCode(sportCode);
        if(sport == null){
            throw new BadRequestException("No se ha encontrado el deporte");
        }
        return sport;
    }
}
